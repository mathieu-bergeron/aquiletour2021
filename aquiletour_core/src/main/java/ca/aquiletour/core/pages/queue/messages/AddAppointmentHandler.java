package ca.aquiletour.core.pages.queue.messages;

import ca.aquiletour.core.pages.queue.QueueModel;
import ca.ntro.core.mvc.ModelMessageHandler;
import ca.ntro.core.system.trace.T;

public class AddAppointmentHandler extends ModelMessageHandler<QueueModel, AddAppointmentMessage> {

	@Override
	protected void handle(QueueModel model, AddAppointmentMessage message) {
		T.call(this);
		
		model.addAppointment(message.getAppointment());
		model.save();
	}
}
