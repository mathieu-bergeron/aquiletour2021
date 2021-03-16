package ca.aquiletour.server.backend.queues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queues.QueuesModel;
import ca.ntro.core.system.trace.T;

public class QueueTimerCenter {
	private static ArrayList<QueueTimer> queueTimers = new ArrayList<QueueTimer>();

	public static void startATimer(TimerTask timerTask, String queueId) {
		QueueTimer queueTimer = findTimer(queueId);
		if(queueTimer == null) {// if a timer with the queueId doesn't exist then create one else restart the timer back to 5min
			T.here();
			QueueTimer newQueueTimer = new QueueTimer();
			newQueueTimer.setQueueId(queueId);
			newQueueTimer.schedule(timerTask, 5 * 60 * 1000);// 5 minutes
			queueTimers.add(newQueueTimer);
		} else {
			T.here();
			restartTimer(timerTask, queueId, queueTimer);
		}

	}

	public static void endATimer(String queueId) {
		QueueTimer queueTimer = findTimer(queueId);
		if(queueTimer != null) {
			queueTimer.endTimerEarly();
			queueTimers.remove(queueTimer);
		}

	}
	public static void restartTimer(TimerTask timerTask, String queueId, QueueTimer queueTimer) {
		queueTimer.cancel();
		queueTimers.remove(queueTimer);
		startATimer(timerTask, queueId);
	}

	private static QueueTimer findTimer(String queueId) {
		QueueTimer queueTimer = null;
		for (int i = 0; i < queueTimers.size(); i++) {
			QueueTimer currentTimer = queueTimers.get(i);
			if (currentTimer.getQueueId().equals(queueId)) {
				queueTimer = currentTimer;
			}
		}
		return queueTimer;
	}

	public static void stopAllOperations() {
		for (int i = 0; i < queueTimers.size(); i++) {
			QueueTimer queueTimer = queueTimers.get(i);
			queueTimer.endTimerEarly();
		}
		
	}

}
