package ca.aquiletour.server.backend.dashboard;

import java.util.Queue;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queues.QueuesModel;
import ca.aquiletour.core.pages.queues.values.QueueSummary;
import ca.ntro.core.Ntro;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.jdk.messages.BackendMessageHandler;
import ca.ntro.jdk.models.ModelStoreSync;

public class AddCourseHandler extends BackendMessageHandler<AddCourseMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, AddCourseMessage message) {
		T.call(this);
		
		User fromUser = message.getUser();
		String courseId = message.getCourse().getTitle();
		
		DashboardModel dashboardModel = modelStore.getModel(DashboardModel.class, 
				                                            fromUser.getAuthToken(),
				                                            fromUser.getId());
		
		if(dashboardModel != null) {
			
			dashboardModel.addCourse(message.getCourse());
			dashboardModel.save();
			
			Ntro.threadService().executeLater(new NtroTaskSync() {
				@Override
				protected void runTask() {
					QueueModel queueModel = modelStore.getModel(QueueModel.class, 
													   fromUser.getAuthToken(),
													   courseId);
					queueModel.setTeacherId(fromUser.getId());//TODO is the teacher always the person who creates the queue

					queueModel.save();
					QueuesModel queuesModel = modelStore.getModel(QueuesModel.class, fromUser.getAuthToken(), "allQueues");
					QueueSummary queue = new QueueSummary();
					queue.setId(courseId);
					queue.setTeacherName(fromUser.getName());
					queue.setTeacherSurname(fromUser.getSurname());
					queuesModel.addQueueToList(queue);
					queuesModel.save();
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
