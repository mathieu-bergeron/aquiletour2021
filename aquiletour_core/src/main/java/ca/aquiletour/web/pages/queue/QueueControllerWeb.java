package ca.aquiletour.web.pages.queue;

import java.util.Calendar;
import java.util.Map;
import java.util.Map.Entry;

import ca.aquiletour.core.pages.queue.QueueController;
import ca.aquiletour.core.pages.queue.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.Ntro;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageFactory;
import ca.ntro.web.Path;
import ca.ntro.web.RequestHandlerTask;

public abstract class QueueControllerWeb extends QueueController implements RequestHandlerTask {

	private AddAppointmentMessage addAppointmentMessage = MessageFactory.getOutgoingMessage(AddAppointmentMessage.class);
	private DeleteAppointmentMessage deleteAppointmentMessage = MessageFactory.getOutgoingMessage(DeleteAppointmentMessage.class);

	public QueueControllerWeb(RootController parentController) {
		super(parentController);
	}

	@Override
	protected ViewLoader createViewLoader(String lang) {
		T.call(this);
		T.here();

		return Ntro.viewLoaderWeb()
		           .setHtmlUrl("/views/queue/structure.html")
		           .setCssUrl("/views/queue/style.css")
		           .setTranslationsUrl("/i18/"+lang+"/strings.json")
		           .setTargetClass(QueueViewWeb.class);
	}

	@Override
	protected  ViewLoader createAppointmentViewLoader(String lang) { 
		T.call(this);
		T.here();
		return Ntro.viewLoaderWeb()
		           .setHtmlUrl("/views/appointment/structure.html")
		           .setCssUrl("/views/appointment/style.css")
		           .setTranslationsUrl("/i18/"+lang+"/strings.json")
		           .setTargetClass(AppointmentViewWeb.class);
	}

	@Override
	public void initialRequest(Path path, 
			                   Map<String, String[]> parameters, 
			                   String authToken) {
		T.call(this);
		T.here();
		
		if(parameters.containsKey("makeAppointment")) { //regarde si parametre makeAppointment/ regarde si delete appointment
			
			T.here();
			Calendar rightNow = Calendar.getInstance();
			int hour = rightNow.get(Calendar.HOUR_OF_DAY);
			int minute = rightNow.get(Calendar.MINUTE);
			String time = hour + ":" + minute;
			
			addAppointmentMessage.setAppointment(new Appointment(time));
			
			addNextTask(addAppointmentMessage);
		} else if(parameters.containsKey("deleteAppointment")){
			T.here();
			//deleteAppointmentMessage.deleteAppointment(appointment);
			deleteAppointmentMessage.deleteAppointment(1);
			addNextTask(deleteAppointmentMessage);
		}
		
	}

	@Override
	public void newRequest(Path oldPath, 
			               Path path, 
			               Map<String, String[]> oldParameters,
			               Map<String, String[]> parameters, 
			               String authToken) {
		T.call(this);
		
	}

}
