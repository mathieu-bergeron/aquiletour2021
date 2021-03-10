package ca.aquiletour.server.backend.queues;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queues.QueuesModel;
import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.models.ModelStoreSync;

public class QueueTimer {
	static Timer timer = new Timer();
	static boolean isTimerCurrentlyOngoing = false;

	public static void startTimer(TimerTask timerTask) {
		isTimerCurrentlyOngoing = true;
		timer = new Timer();
		timer.schedule(timerTask , 5*60*1000);//5 minutes
	}
	
	public static void cancelTimer() {
		if(isTimerCurrentlyOngoing) {
			timer.cancel();
		}
		isTimerCurrentlyOngoing = false;
	}
	
	public static void restartTimer(TimerTask timerTask) {
		cancelTimer();
		startTimer(timerTask);
	}
	
	public static boolean isTimerOngoing() {
		return isTimerCurrentlyOngoing;
	}
	
	
}
