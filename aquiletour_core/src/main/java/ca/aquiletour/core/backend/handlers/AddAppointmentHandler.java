package ca.aquiletour.core.backend.handlers;

import ca.aquiletour.core.backend.QueueBackendController;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.messages.AddAppointmentMessage;
import ca.ntro.core.mvc.MessageHandler;
import ca.ntro.core.system.trace.T;

public class AddAppointmentHandler extends MessageHandler<QueueBackendController,AddAppointmentMessage> {

	@Override
	protected void handle(AddAppointmentMessage message) {
		T.call(this);
		
		User requestingUser = message.getUser();
		
		QueueModel queueModel = getController().getModel(QueueModel.class, 
				                                                 requestingUser.getAuthToken(),
				                                                 requestingUser.getId());
		
		if(queueModel != null) {
			
			queueModel.addAppointment(message.getAppointment());
			queueModel.save();
		}else {
			
			// TODO: error handling
			
		}
	}
}
