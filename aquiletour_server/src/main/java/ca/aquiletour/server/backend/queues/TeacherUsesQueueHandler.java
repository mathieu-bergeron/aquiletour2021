package ca.aquiletour.server.backend.queues;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.aquiletour.core.pages.queues.QueuesModel;
import ca.aquiletour.core.pages.queues.values.QueueSummary;
import ca.aquiletour.core.pages.users.messages.AddUserToCourseMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.services.Ntro;

public class TeacherUsesQueueHandler extends BackendMessageHandler<TeacherUsesQueueMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, TeacherUsesQueueMessage message) {
		T.call(this);

		Teacher teacher = (Teacher) message.getTeacher();
		String courseId = message.getCourseId();

		QueueModel queueModel = modelStore.getModel(QueueModel.class, teacher.getAuthToken(), courseId);
		QueuesModel allQueuesModel = modelStore.getModel(QueuesModel.class, "admin", "allQueues");
		QueuesModel openQueuesModel = modelStore.getModel(QueuesModel.class, "admin", "openQueues");

		if (queueModel != null && allQueuesModel != null && openQueuesModel != null) {

			TimerTask timerTask = setQueueClosedTimerTask(courseId, queueModel, modelStore);

			QueueTimerCenter.startATimer(timerTask, courseId);

			QueueSummary queue = allQueuesModel.findQueueByQueueId(courseId);
			openQueuesModel.addQueueToList(queue);
			modelStore.save(openQueuesModel);

			Ntro.threadService().executeLater(new NtroTaskSync() {
				@Override
				protected void runTask() {
					List<String> studentIds = queueModel.getStudentIds();
					for (String studentId : studentIds) {
						DashboardModel dashboardModelStudent = modelStore.getModel(DashboardModel.class, "admin",
								studentId);
						if (dashboardModelStudent != null) {
							T.here();
							dashboardModelStudent.setTeacherAvailability(true, courseId);
							modelStore.save(dashboardModelStudent);
						}
					}
					DashboardModel dashboardModelTeacher = modelStore.getModel(DashboardModel.class, "admin",
							queueModel.getTeacherId());
					dashboardModelTeacher.setTeacherAvailability(true, courseId);
					modelStore.save(dashboardModelTeacher);

					QueuesModel allQueuesModel = modelStore.getModel(QueuesModel.class, "admin", "allQueues");
					QueueSummary queue = allQueuesModel.findQueueByQueueId(courseId);

					QueuesModel openQueuesModel = modelStore.getModel(QueuesModel.class, "admin", "openQueues");
					openQueuesModel.addQueueToList(queue);
					modelStore.save(openQueuesModel);

				}

				@Override
				protected void onFailure(Exception e) {
				}
			});

		} else {

			// TODO: error handling

		}
	}

	private TimerTask setQueueClosedTimerTask(String courseId, QueueModel queueModel, ModelStoreSync modelStore) {
		TimerTask timerTask = new TimerTask() { // do that after timer is over
			@Override
			public void run() {
				
				queueModel.clearQueue();
				modelStore.save(queueModel);

				DashboardModel dashboardModelTeacher = modelStore.getModel(DashboardModel.class, "admin",
						queueModel.getTeacherId());

				dashboardModelTeacher.updateNbAppointmentOfCourse(courseId, 0);
				dashboardModelTeacher.setTeacherAvailability(false, courseId);
				modelStore.save(dashboardModelTeacher);

				List<String> studentIds = queueModel.getStudentIds();
				for (String studentId : studentIds) {
					DashboardModel dashboardModelStudent = modelStore.getModel(DashboardModel.class, "admin", studentId);
					if (dashboardModelStudent != null) {
						dashboardModelStudent.setTeacherAvailability(false, courseId);
						dashboardModelStudent.updateMyAppointment(courseId, false);
						dashboardModelStudent.updateNbAppointmentOfCourse(courseId, 0);
						modelStore.save(dashboardModelStudent);
					}

				}
				QueuesModel openQueuesModel = modelStore.getModel(QueuesModel.class, "admin", "openQueues");
				openQueuesModel.deleteQueue(courseId);
				modelStore.save(openQueuesModel);
			}
		};
		return timerTask;
	}
}
