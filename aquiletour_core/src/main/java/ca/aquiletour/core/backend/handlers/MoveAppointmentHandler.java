package ca.aquiletour.core.backend.handlers;

import java.util.List;

import ca.aquiletour.core.backend.QueueBackendController;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.MoveAppointmentMessage;
import ca.ntro.core.mvc.MessageHandler;
import ca.ntro.core.system.trace.T;

public class MoveAppointmentHandler extends MessageHandler<QueueBackendController,MoveAppointmentMessage> {

	@Override
	protected void handle(MoveAppointmentMessage message) {
		T.call(this);
		
		User requestingUser = message.getUser();
		String courseId = message.getCourseId();
		String appointmentDestinationId = message.getappointmentDestinationId();		
		String appointmentDepartureId = message.getappointmentDepartureId();		
		
		QueueModel queueModel = getController().getModel(QueueModel.class, 
				requestingUser.getAuthToken(),
				courseId);

		
		if(queueModel != null) {
			queueModel.updateOrder(appointmentDestinationId, appointmentDepartureId);
			queueModel.save();

		}else {
			
			// TODO: error handling
			
		}
	}
}
