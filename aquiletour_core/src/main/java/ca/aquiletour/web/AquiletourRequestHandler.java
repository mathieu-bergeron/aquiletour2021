package ca.aquiletour.web;

import java.util.Map;

import ca.aquiletour.core.pages.dashboard.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboard.messages.ShowDashboardMessage;
import ca.aquiletour.core.pages.dashboard.values.CourseSummary;
import ca.aquiletour.core.pages.queue.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.messages.ShowQueueMessage;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.aquiletour.core.pages.settings.ShowSettingsMessage;
import ca.ntro.core.Path;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageFactory;

public class AquiletourRequestHandler {
	
	
	public static void sendMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);

		if(path.startsWith("settings")) {
			
			sendSettingsMessages(path.subPath(1), parameters);
			
		}else if(path.startsWith("dashboard")) {

			sendDashboardMessages(path.subPath(1), parameters);

		}else if(path.startsWith("queue")) {

			sendQueueMessages(path.subPath(1), parameters);
		}
	}

	private static void sendSettingsMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);

		ShowSettingsMessage showSettingsMessage = MessageFactory.getOutgoingMessage(ShowSettingsMessage.class);
		showSettingsMessage.sendMessage();
	}

	private static void sendDashboardMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);

		ShowDashboardMessage showDashboardMessage = MessageFactory.getOutgoingMessage(ShowDashboardMessage.class);
		showDashboardMessage.sendMessage();
		
		if(parameters.containsKey("title") 
				&& parameters.containsKey("summary")
				&& parameters.containsKey("date")) {

			String courseTitle = parameters.get("title")[0];
			String summaryText = parameters.get("summary")[0];
			String summaryDate = parameters.get("date")[0];

			AddCourseMessage addCourseMessage = MessageFactory.getOutgoingMessage(AddCourseMessage.class);
			addCourseMessage.setCourse(new CourseSummary(courseTitle, summaryText, summaryDate));
			addCourseMessage.sendMessage();
		}
	}

	private static void sendQueueMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);

		ShowQueueMessage showQueueMessage = MessageFactory.getOutgoingMessage(ShowQueueMessage.class);
		showQueueMessage.sendMessage();

		if(parameters.containsKey("makeAppointment")) { //regarde si parametre makeAppointment/ regarde si delete appointment

			// FIXME: we need a Ntro service for dates
			/*
			Calendar rightNow = Calendar.getInstance();
			int hour = rightNow.get(Calendar.HOUR_OF_DAY);
			int minute = rightNow.get(Calendar.MINUTE);
			String time = hour + ":" + minute;
			*/
			
			AddAppointmentMessage addAppointmentMessage = MessageFactory.getOutgoingMessage(AddAppointmentMessage.class);
			Appointment newAppointment = new Appointment();
			newAppointment.setTime("11:59");
			addAppointmentMessage.setAppointment(newAppointment);

			addAppointmentMessage.sendMessage();

		} else if(parameters.containsKey("deleteAppointment")){

			DeleteAppointmentMessage deleteAppointmentMessage = MessageFactory.getOutgoingMessage(DeleteAppointmentMessage.class);
			
			String appointmentId = parameters.get("deleteAppointment")[0];
			deleteAppointmentMessage.setAppointmentId(appointmentId);
			
			deleteAppointmentMessage.sendMessage();
		}
	}



}
