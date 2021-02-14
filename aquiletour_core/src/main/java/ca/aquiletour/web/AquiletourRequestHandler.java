package ca.aquiletour.web;

import java.util.Map;

import ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardMessage;
import ca.aquiletour.core.pages.dashboards.teacher.messages.ShowTeacherDashboardMessage;
import ca.aquiletour.core.pages.login.ShowLoginMessage;
import ca.aquiletour.core.pages.queue.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.messages.ShowQueueMessage;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.aquiletour.core.pages.queues.messages.ShowQueuesMessage;
import ca.aquiletour.core.pages.users.messages.AddUserMessage;
import ca.aquiletour.core.pages.users.messages.DeleteUserMessage;
import ca.aquiletour.core.pages.users.messages.ShowUsersMessage;
import ca.aquiletour.core.pages.users.values.User;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageFactory;

public class AquiletourRequestHandler {
	
	
	public static void sendMessages(NtroContext context, Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);
		
		if(path.startsWith("mescours")) {

			sendDashboardMessages(path.subPath(1), parameters, context.getUser().getRole());

		}else if(path.startsWith("billetteries")) {
			
			sendQueuesMessages(path.subPath(1), parameters);

		}else if(path.startsWith("billetterie")) {
			
			sendQueueMessages(path.subPath(1), parameters);
			
		}else if(path.startsWith("usagers")) {
			
			sendUsersMessages(path.subPath(1), parameters);
		
		}else if(path.startsWith("connexion")) {

			sendLoginMessages(path.subPath(1), parameters);
		}
	}

	private static void sendLoginMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);

		ShowLoginMessage showLoginMessage = MessageFactory.getOutgoingMessage(ShowLoginMessage.class);
		showLoginMessage.sendMessage();
	}

	private static void sendDashboardMessages(Path path, Map<String, String[]> parameters, String role) {
		T.call(AquiletourRequestHandler.class);
		
		if(role.equals("teacher")) {

			ShowTeacherDashboardMessage showTeacherDashboardMessage = MessageFactory.getOutgoingMessage(ShowTeacherDashboardMessage.class);
			showTeacherDashboardMessage.sendMessage();
			
		}else{
			
			ShowStudentDashboardMessage showStudentDashboardMessage = MessageFactory.getOutgoingMessage(ShowStudentDashboardMessage.class);
			showStudentDashboardMessage.sendMessage();
		}
	}

	private static void sendQueuesMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);

		ShowQueuesMessage showQueuesMessage = MessageFactory.getOutgoingMessage(ShowQueuesMessage.class);
		showQueuesMessage.sendMessage();
	}
		

	private static void sendQueueMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);
		
		if(path.size() >= 1) {

			String courseId = path.getName(0);
			
			ShowQueueMessage showQueueMessage = MessageFactory.getOutgoingMessage(ShowQueueMessage.class);
			
			showQueueMessage.setCourseId(courseId);
			
			showQueueMessage.sendMessage();
			
			sendAppointmentMessages(parameters);
			
		}
	}

	private static void sendAppointmentMessages(Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);

		if(parameters.containsKey("makeAppointment")) { 
			
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

	private static void sendUsersMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);

		ShowUsersMessage showUsersMessage = MessageFactory.getOutgoingMessage(ShowUsersMessage.class);
		showUsersMessage.sendMessage();

		if(parameters.containsKey("email") 
		&& parameters.containsKey("password")) {
			
			String email = parameters.get("email")[0];
			String password = parameters.get("password")[0];
			
			AddUserMessage addUserMessage = MessageFactory.getOutgoingMessage(AddUserMessage.class);
			User newUser = new User();
			newUser.setUserEmail(email);			
			newUser.setUserPassword(password);		
			addUserMessage.setUser(newUser);
			addUserMessage.sendMessage();

		} else if(parameters.containsKey("deleteUser")){

			DeleteUserMessage deleteUserMessage = MessageFactory.getOutgoingMessage(DeleteUserMessage.class);
			
			String userId = parameters.get("deleteUser")[0];
			deleteUserMessage.setUserId(userId);
			
			deleteUserMessage.sendMessage();
		}
	}



}
