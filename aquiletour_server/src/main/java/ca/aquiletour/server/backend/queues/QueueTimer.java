package ca.aquiletour.server.backend.queues;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queues.QueuesModel;
import ca.ntro.core.services.stores.LocalStore;
import ca.ntro.jdk.models.ModelStoreSync;

public class QueueTimer {
	static Timer timer = new Timer();
	static boolean isTimerCurrentlyOngoing = false;

	public static void startTimer(QueueModel queueModel, ModelStoreSync modelStore, String courseId) {
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				List<String> studentIds = queueModel.getStudentIds();
				for (String studentId : studentIds) {
					DashboardModel dashboardModel = modelStore.getModel(DashboardModel.class, 
		                    "admin",
		                    studentId);
					if(dashboardModel != null) {
						dashboardModel.setTeacherAvailability(false, courseId);
						LocalStore.save(dashboardModel);
					}
				}
				QueuesModel openQueuesModel = modelStore.getModel(QueuesModel.class, "admin", "openQueues");
				openQueuesModel.deleteQueue(courseId);
				LocalStore.save(openQueuesModel);
			}
		}, 5*60*1000);//5 minutes
	}
	
	public static void cancelTimer() {
		timer.cancel();
	}
	
	public static void restartTimer(QueueModel queueModel, ModelStoreSync modelStore, String courseId) {
		cancelTimer();
		startTimer(queueModel, modelStore, courseId);
		
	}
	
	public static boolean isTimerOngoing() {
		return isTimerCurrentlyOngoing;
	}
	
	
}
