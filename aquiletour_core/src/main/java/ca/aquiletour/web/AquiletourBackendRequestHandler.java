package ca.aquiletour.web;

import java.util.Map;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.queue.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.aquiletour.core.pages.users.messages.AddUserMessage;
import ca.aquiletour.core.pages.users.messages.DeleteUserMessage;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageFactory;

public class AquiletourBackendRequestHandler {
	
	
	public static void sendMessages(NtroContext<User> context, Path path, Map<String, String[]> parameters) {
		T.call(AquiletourBackendRequestHandler.class);
		
		if(path.startsWith("mescours")) {

			sendDashboardMessages(path.subPath(1), parameters, context.getUser());

		}else if(path.startsWith("billetteries")) {
			
			sendQueuesMessages(path.subPath(1), parameters);

		}else if(path.startsWith("billetterie")) {
			
			sendQueueMessages(path.subPath(1), parameters , context.getUser());
			
		}else if(path.startsWith("usagers")) {
			
			sendUsersMessages(path.subPath(1), parameters);
		
		}else if(path.startsWith("connexion")) {
			
			sendLoginMessages(path.subPath(1), parameters);
	
		}else if(path.startsWith("home")) {

			sendHomeMessages(path.subPath(1), parameters);
		}
	}

	private static void sendHomeMessages(Path subPath, Map<String, String[]> parameters) {
		T.call(AquiletourBackendRequestHandler.class);
		
	}

	private static void sendLoginMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourBackendRequestHandler.class);

	}

	private static void sendDashboardMessages(Path path, Map<String, String[]> parameters, User user) {
		T.call(AquiletourBackendRequestHandler.class);

		if(parameters.containsKey("title")) {

			String courseTitle = parameters.get("title")[0];
			String courseId = parameters.get("title")[0];

			AddCourseMessage addCourseMessage = MessageFactory.getOutgoingMessage(AddCourseMessage.class);
			addCourseMessage.setCourse(new CourseSummary(courseTitle, courseId, true, "yes", 100));
			addCourseMessage.setUser(user);
			addCourseMessage.sendMessage();
		}
	}

	private static void sendQueuesMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourBackendRequestHandler.class);

	}
		

	private static void sendQueueMessages(Path path, Map<String, String[]> parameters, User user) {
		T.call(AquiletourBackendRequestHandler.class);
		
		if(path.size() >= 1) {
			sendAppointmentMessages(parameters, user, path.getName(0));
		}
	}

	private static void sendAppointmentMessages(Map<String, String[]> parameters, User user, String courseId) {
		T.call(AquiletourBackendRequestHandler.class);

		if(parameters.containsKey("makeAppointment")) { //localhost8080/billeterie/courseId/makeAppointment
			
			// FIXME: we need a Ntro service for dates
			/*
			Calendar rightNow = Calendar.getInstance();
			int hour = rightNow.get(Calendar.HOUR_OF_DAY);
			int minute = rightNow.get(Calendar.MINUTE);
			String time = hour + ":" + minute;
			 */

			AddAppointmentMessage addAppointmentMessage = MessageFactory.getOutgoingMessage(AddAppointmentMessage.class);
			Appointment newAppointment = new Appointment();
			newAppointment.setStudentId(user.getId());
			newAppointment.setStudentName(user.getName());
			newAppointment.setStudentSurname(user.getSurname());
			addAppointmentMessage.setAppointment(newAppointment);
			addAppointmentMessage.setUser(user);
			addAppointmentMessage.setCourseId(courseId);
			
			addAppointmentMessage.sendMessage();
			
		} else if(parameters.containsKey("deleteAppointment")){
			
			DeleteAppointmentMessage deleteAppointmentMessage = MessageFactory.getOutgoingMessage(DeleteAppointmentMessage.class);
			
			String appointmentId = parameters.get("deleteAppointment")[0];
			deleteAppointmentMessage.setAppointmentId(appointmentId);
			
			deleteAppointmentMessage.sendMessage();
		}
	}

	private static void sendUsersMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourBackendRequestHandler.class);

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
