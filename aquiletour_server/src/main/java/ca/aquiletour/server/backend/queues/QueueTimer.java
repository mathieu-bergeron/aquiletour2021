package ca.aquiletour.server.backend.queues;

import java.util.Timer;
import java.util.TimerTask;

public class QueueTimer extends Timer {
	String queueId;
	TimerTask timerTask;
	
	public String getQueueId() {
		return queueId;
	}
	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}
	public void endTimerEarly() {
		cancel();
		timerTask.run();
		
	}
	
	@Override
	public void schedule(TimerTask task, long delay) {
		// TODO Auto-generated method stub
		super.schedule(task, delay);
		this.timerTask = task;
	}

}
