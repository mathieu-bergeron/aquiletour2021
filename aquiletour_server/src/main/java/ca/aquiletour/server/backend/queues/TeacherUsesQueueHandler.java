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
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.jdk.messages.BackendMessageHandler;
import ca.ntro.jdk.models.ModelStoreSync;
import ca.ntro.services.Ntro;

public class TeacherUsesQueueHandler extends BackendMessageHandler<TeacherUsesQueueMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, TeacherUsesQueueMessage message) {
		T.call(this);
		
		Teacher teacher = (Teacher) message.getTeacher();
		String courseId = message.getCourseId();
		
		QueueModel queueModel = modelStore.getModel(QueueModel.class, 
				teacher.getAuthToken(),
				courseId);
		
		if(queueModel != null) {
			
			if(QueueTimer.isTimerOngoing()) {
				QueueTimer.restartTimer(queueModel, modelStore, courseId);
			}else {
				QueueTimer.startTimer(queueModel, modelStore, courseId);
			}
			
			
			Ntro.threadService().executeLater(new NtroTaskSync() {
				@Override
				protected void runTask() {
					List<String> studentIds = queueModel.getStudentIds();
					for (String studentId : studentIds) {
						DashboardModel dashboardModel = modelStore.getModel(DashboardModel.class, 
			                    "admin",
			                    studentId);
						if(dashboardModel != null) {
							dashboardModel.setTeacherAvailability(true, courseId);
							modelStore.save(dashboardModel);
						}
					}
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

		}else {
			
			// TODO: error handling
			
		}
	}
}
