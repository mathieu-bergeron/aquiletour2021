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
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.aquiletour.core.pages.queues.QueuesModel;
import ca.aquiletour.core.pages.queues.values.QueueSummary;
import ca.aquiletour.core.pages.users.messages.AddUserToCourseMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.services.Ntro;

public class TeacherClosesQueueHandler extends BackendMessageHandler<TeacherClosesQueueMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, TeacherClosesQueueMessage message) {
		T.call(this);
		
		Teacher teacher = (Teacher) message.getTeacher();
		String courseId = message.getCourseId();
		
		QueueModel queueModel = modelStore.getModel(QueueModel.class, 
				teacher.getAuthToken(),
				courseId);
		
		if(queueModel != null) {
			
			//TODO cancel timer
			QueueTimer.cancelTimer();
			
			
			Ntro.threadService().executeLater(new NtroTaskSync() {
				@Override
				protected void runTask() {
					List<String> studentIds = queueModel.getStudentIds();
					for (String studentId : studentIds) {
						DashboardModel dashboardModel = modelStore.getModel(DashboardModel.class, 
			                    "admin",
			                    studentId);
						if(dashboardModel != null) {
							dashboardModel.setTeacherAvailability(false, courseId);
							modelStore.save(dashboardModel);
						}
					}
					QueuesModel openQueuesModel = modelStore.getModel(QueuesModel.class, "admin", "openQueues");
					openQueuesModel.deleteQueue(courseId);
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
