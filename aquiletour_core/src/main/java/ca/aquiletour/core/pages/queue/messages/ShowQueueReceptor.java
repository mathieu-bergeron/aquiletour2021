package ca.aquiletour.core.pages.queue.messages;

import ca.aquiletour.core.pages.queue.QueueController;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;
import ca.ntro.messages.MessageReceptor;

public class ShowQueueReceptor extends MessageReceptor {
	
	private QueueController queueController;

	public ShowQueueReceptor(QueueController queueController) {
		this.queueController = queueController;
	}

	@Override
	protected void initializeTask() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		
		queueController.showQueue();
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
