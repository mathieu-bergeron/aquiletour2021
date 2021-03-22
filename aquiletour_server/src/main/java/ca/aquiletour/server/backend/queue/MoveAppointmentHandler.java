package ca.aquiletour.server.backend.queue;


import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.teacher.messages.MoveAppointmentMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class MoveAppointmentHandler extends BackendMessageHandler<MoveAppointmentMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore,MoveAppointmentMessage message) {
		T.call(this);
		
		User requestingUser = message.getUser();
		String courseId = message.getCourseId();
		String appointmentDestinationId = message.getappointmentDestinationId();		
		String appointmentDepartureId = message.getappointmentDepartureId();		
		
		QueueModel queueModel = modelStore.getModel(QueueModel.class, 
				requestingUser.getAuthToken(),
				courseId);

		
		if(queueModel != null) {

			queueModel.updateOrder(appointmentDestinationId, appointmentDepartureId);
			Ntro.modelStore().save(queueModel);

		}else {
			
			// TODO: error handling
			
		}
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, MoveAppointmentMessage message) {
		T.call(this);
	}
}
