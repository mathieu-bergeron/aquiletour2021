package ca.aquiletour.server.backend;


import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.teacher.messages.MoveAppointmentMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class MoveAppointmentHandler extends BackendMessageHandler<MoveAppointmentMessage> {

	@Override
	public void handle(ModelStoreSync modelStore, MoveAppointmentMessage message) {
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
			modelStore.save(queueModel);

		}else {
			
			// TODO: error handling
			
		}
	}
}