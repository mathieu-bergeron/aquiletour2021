package ca.aquiletour.core.pages.queue.messages;

import ca.aquiletour.core.pages.queue.QueueController;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;
import ca.ntro.messages.MessageReceptor;

public class DeleteAppointmentReceptor extends MessageReceptor {

	private QueueController queueController;

	public DeleteAppointmentReceptor(QueueController queueController) {
		this.queueController = queueController;
	}

	@Override
	protected void initializeTask() {
		T.call(this);
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);

		DeleteAppointmentMessage message = (DeleteAppointmentMessage) getMessage();
		
		queueController.deleteAppointement((message.getAppointmentId()));

		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
