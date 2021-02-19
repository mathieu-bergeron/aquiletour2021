package ca.aquiletour.core.backend.handlers;

import ca.aquiletour.core.backend.QueueBackendController;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.dashboards.values.ObservableCourseList;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.messages.AddAppointmentMessage;
import ca.ntro.core.mvc.MessageHandler;
import ca.ntro.core.system.trace.T;

public class AddAppointmentHandler extends MessageHandler<QueueBackendController,AddAppointmentMessage> {

	@Override
	protected void handle(AddAppointmentMessage message) {
		T.call(this);
		
		User requestingUser = message.getUser();
		String courseId = message.getCourseId();
		
		QueueModel queueModel = getController().getModel(QueueModel.class, 
				requestingUser.getAuthToken(),
				courseId);

		
		if(queueModel != null) {
			
			queueModel.addAppointment(message.getAppointment());
			queueModel.save();
			
			//TODO charger le dashboard modelk de chaque etudiant de la billetrie 
			//et metre a jour nb of appointment et myappointment
			//queuemodel -> students ->  chercher dashboardmodel -> mettre a jour les donnees
			queueModel.getStudentIds();
			
			DashboardModel dashboardModel = getController().getModel(DashboardModel.class, 
                    requestingUser.getAuthToken(),
                    requestingUser.getId());
			

				
			dashboardModel.updateNbAppointmentOfCourse(courseId);
			dashboardModel.save();
			
			

		}else {
			
			// TODO: error handling
			
		}
	}
}
