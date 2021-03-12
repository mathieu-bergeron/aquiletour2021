package ca.aquiletour.web;

import java.util.Map;

import ca.aquiletour.core.messages.AddStudentCsvMessage;
import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboards.teacher.messages.DeleteCourseMessage;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.MoveAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.aquiletour.core.pages.users.messages.AddUserMessage;
import ca.aquiletour.core.pages.users.messages.AddUserToCourseMessage;
import ca.aquiletour.core.pages.users.messages.DeleteUserFromCourseMessage;
import ca.aquiletour.core.pages.users.messages.DeleteUserMessage;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageFactory;
import ca.ntro.services.Ntro;
import ca.ntro.services.ResourceLoaderTask;

public class AquiletourBackendRequestHandler {
	
	
	public static void sendMessages(NtroContext<User> context, Path path, Map<String, String[]> parameters) {
		T.call(AquiletourBackendRequestHandler.class);
		
		if(path.startsWith("mescours")) {

			sendDashboardMessages(path.subPath(1), parameters, context.getUser());

		}else if(path.startsWith("csv")) {
			if(parameters.containsKey("queueId")) {// /csv?queueId=3C6
				
				String queueId = parameters.get("queueId")[0];
			
				AddStudentCsvMessage addStudentCsvMessage = new AddStudentCsvMessage();
				addStudentCsvMessage.setQueueId(queueId);
				addStudentCsvMessage.setUser(context.getUser());
				
				ResourceLoaderTask loadCsv = Ntro.resourceLoader().loadResourceTask("__test__/test01.csv");
				loadCsv.execute();
	
				addStudentCsvMessage.setCsvString(loadCsv.getResourceAsString());
				
				System.out.println(loadCsv.getResourceAsString());
				
				Ntro.backendService().sendMessageToBackend(addStudentCsvMessage);
			}
			

		} else if(path.startsWith("billetteries")) {
			
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

			AddCourseMessage addCourseMessage = MessageFactory.createMessage(AddCourseMessage.class);
			addCourseMessage.setCourse(new CourseSummary(courseTitle, courseId, false, false, 0));
			addCourseMessage.setUser(user);
			Ntro.backendService().sendMessageToBackend(addCourseMessage);

		} else if(parameters.containsKey("deleteCourse")) {
			DeleteCourseMessage deleteCourseMessage = new DeleteCourseMessage();
			
			String courseId = path.getName(0);
			deleteCourseMessage.setCourseId(courseId);
			deleteCourseMessage.setUser(user);
			Ntro.backendService().sendMessageToBackend(deleteCourseMessage);
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

		if(parameters.containsKey("makeAppointment")) { //localhost8080/billeterie/courseId?makeAppointment
			
			// FIXME: we need a Ntro service for dates
			/*
			Calendar rightNow = Calendar.getInstance();
			int hour = rightNow.get(Calendar.HOUR_OF_DAY);
			int minute = rightNow.get(Calendar.MINUTE);
			String time = hour + ":" + minute;
			 */
			AddAppointmentMessage addAppointmentMessage = new AddAppointmentMessage();
			Appointment newAppointment = new Appointment();
			newAppointment.setStudentId(user.getId());
			newAppointment.setStudentName(user.getName());
			newAppointment.setStudentSurname(user.getSurname());
			addAppointmentMessage.setAppointment(newAppointment);
			addAppointmentMessage.setUser(user);
			addAppointmentMessage.setCourseId(courseId);
			Ntro.backendService().sendMessageToBackend(addAppointmentMessage);
			
		} else if(parameters.containsKey("deleteAppointment") && user instanceof Teacher){
			
			DeleteAppointmentMessage deleteAppointmentMessage = new DeleteAppointmentMessage();
			
			String appointmentId = parameters.get("deleteAppointment")[0];
			deleteAppointmentMessage.setAppointmentId(appointmentId);
			deleteAppointmentMessage.setUser(user);
			deleteAppointmentMessage.setCourseId(courseId);
			Ntro.backendService().sendMessageToBackend(deleteAppointmentMessage);
		} else if(parameters.containsKey("move")) { // /billetterie/IdDuCours?move=Id1&before=Id2
			String departureId = parameters.get("move")[0];
			String destinationId = parameters.get("before")[0];
			MoveAppointmentMessage moveAppointmentMessage = MessageFactory.createMessage(MoveAppointmentMessage.class);
			Appointment newAppointment = new Appointment();
			moveAppointmentMessage.setappointmentDepartureId(departureId);
			moveAppointmentMessage.setappointmentDestinationId(destinationId);
			moveAppointmentMessage.setUser(user);
			moveAppointmentMessage.setCourseId(courseId);
			//moveAppointmentMessage.sendMessage();
		} else if(parameters.containsKey("teacherClosedQueue")&& user instanceof Teacher) {//localhost8080/billeterie/courseId?teacherClosedQueue
			TeacherClosesQueueMessage teacherClosesQueueMessage = new TeacherClosesQueueMessage();
			teacherClosesQueueMessage.setCourseId(courseId);
			teacherClosesQueueMessage.setTeacher(user);
			Ntro.backendService().sendMessageToBackend(teacherClosesQueueMessage);
		}
	}

	private static void sendUsersMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourBackendRequestHandler.class);

		if(parameters.containsKey("email") 
		&& parameters.containsKey("password")) {
			
			String email = parameters.get("email")[0];
			String password = parameters.get("password")[0];
			T.here();
			
			AddUserMessage addUserMessage = MessageFactory.createMessage(AddUserMessage.class);
			User newUser = new User();
			newUser.setUserEmail(email);			
			newUser.setUserPassword(password);	
			newUser.setName("test");
			newUser.setAuthToken("test");
			newUser.setId(email);
			addUserMessage.setUser(newUser);
			Ntro.backendService().sendMessageToBackend(addUserMessage);

		} else if(parameters.containsKey("deleteUser")){

			DeleteUserMessage deleteUserMessage = MessageFactory.createMessage(DeleteUserMessage.class);
			
			String userId = parameters.get("deleteUser")[0];
			deleteUserMessage.setUserId(userId);
			
			Ntro.backendService().sendMessageToBackend(deleteUserMessage);

		} else if(parameters.containsKey("addUser")) { // /usagers?addUser=Id&to=IdDuCours
			T.here();
			AddUserToCourseMessage addUserToCourseMessage = new AddUserToCourseMessage();
			T.here();
			String userId = parameters.get("addUser")[0];
			String courseId = parameters.get("to")[0];
			 
			addUserToCourseMessage.setUserId(userId);
			addUserToCourseMessage.setCourseId(courseId);
			Ntro.backendService().sendMessageToBackend(addUserToCourseMessage);
			 
			 
			
		}else if(parameters.containsKey("removeUser")) { // /usagers?removeUser=Id&from=IdDuCours
			T.here();
			DeleteUserFromCourseMessage deleteUserFromCourseMessage = new DeleteUserFromCourseMessage();
			T.here();
			String userId = parameters.get("removeUser")[0];
			String courseId = parameters.get("from")[0];
			 
			deleteUserFromCourseMessage.setUserId(userId);
			deleteUserFromCourseMessage.setCourseId(courseId);
			Ntro.backendService().sendMessageToBackend(deleteUserFromCourseMessage);
		}
	}

}
