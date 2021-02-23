package ca.aquiletour.core.backend.handlers;

import java.util.List;

import ca.aquiletour.core.backend.QueueBackendController;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.messages.DeleteAppointmentMessage;
import ca.ntro.core.mvc.MessageHandler;
import ca.ntro.core.system.trace.T;

public class DeleteAppointmentHandler extends MessageHandler<QueueBackendController,DeleteAppointmentMessage> {

	@Override
	protected void handle(DeleteAppointmentMessage message) {
		T.call(this);
		
		User requestingUser = message.getUser();
		String courseId = message.getCourseId();
		
		QueueModel queueModel = getController().getModel(QueueModel.class, 
				requestingUser.getAuthToken(),
				courseId);

		
		if(queueModel != null) {
			
			queueModel.deleteAppointment(message.getAppointmentId());
			queueModel.save();
			
			//TODO charger le dashboard modelk de chaque etudiant de la billetrie 
			//et metre a jour nb of appointment et myappointment
			//queuemodel -> students ->  chercher dashboardmodel -> mettre a jour les donnees
			List<String> studentIds = queueModel.getStudentIds();
			for (String studentId : studentIds) {
				int nbAppointment = queueModel.getAppointments().size();
				DashboardModel dashboardModel = getController().getModel(DashboardModel.class, 
	                    "admin",
	                    studentId);
				if(dashboardModel != null) {
					dashboardModel.updateNbAppointmentOfCourse(courseId, nbAppointment);
					if(requestingUser.getId().equals(studentId)) {
						dashboardModel.updateMyAppointment(courseId, false);
					}
					dashboardModel.save();
				}
			}
			

		}else {
			
			// TODO: error handling
			
		}
	}
}
