package ca.aquiletour.core.pages.queue.messages;

import ca.aquiletour.core.pages.queue.QueueController;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageReceptor;

public class AddAppointmentReceptor extends MessageReceptor {

	private QueueController queueController;

	public AddAppointmentReceptor(QueueController queueController) {
		this.queueController = queueController;
	}

	@Override
	protected void initializeTask() {
		T.call(this);
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		T.here();
		
		AddAppointmentMessage message = (AddAppointmentMessage) getMessage();
		
		queueController.addAppointement((message.getAppointment()));

		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
