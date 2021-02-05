package ca.aquiletour.core.pages.queue.messages;

import ca.aquiletour.core.pages.queue.QueueModel;
import ca.ntro.core.mvc.ModelMessageHandler;
import ca.ntro.core.system.trace.T;

public class DeleteAppointmentHandler extends ModelMessageHandler<QueueModel, DeleteAppointmentMessage> {

	@Override
	protected void handle(QueueModel model, DeleteAppointmentMessage message) {
		T.call(this);
		
		model.deleteAppointment(message.getAppointmentId());
		model.save();
	}
}
