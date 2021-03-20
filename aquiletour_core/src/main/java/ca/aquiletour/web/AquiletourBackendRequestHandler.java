package ca.aquiletour.web;

import java.util.Map;

import ca.aquiletour.core.messages.UserInitiatesLoginMessage;
import ca.aquiletour.core.messages.UserLogsOutMessage;
import ca.aquiletour.core.messages.UserSendsLoginCodeMessage;
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
import ca.ntro.core.Path;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class AquiletourBackendRequestHandler {
	
	public static void sendMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourBackendRequestHandler.class);

		if(parameters.containsKey("userId")) {
			
			UserInitiatesLoginMessage userInitiatesLoginMessage = Ntro.messages().create(UserInitiatesLoginMessage.class);
			userInitiatesLoginMessage.setProvidedId(parameters.get("userId")[0]);
			Ntro.backendService().sendMessageToBackend(userInitiatesLoginMessage);

		} else if(parameters.containsKey("loginCode")) {
			
			UserSendsLoginCodeMessage userSendsLoginCodeMessage = Ntro.messages().create(UserSendsLoginCodeMessage.class);
			userSendsLoginCodeMessage.setLoginCode(parameters.get("loginCode")[0]);
			Ntro.backendService().sendMessageToBackend(userSendsLoginCodeMessage);

		} else if(path.startsWith("deconnexion")) {

			Ntro.backendService().sendMessageToBackend(Ntro.messages().create(UserLogsOutMessage.class));

		} else if(path.startsWith("mescours")) {

			sendDashboardMessages(path.subPath(1), parameters, (User) Ntro.userService().currentUser());

		} else if(path.startsWith("profs")) {
			
			sendQueuesMessages(path.subPath(1), parameters);

		}else if(path.startsWith("billetterie")) {
			
			sendQueueMessages(path.subPath(1), parameters , (User) Ntro.userService().currentUser());
			
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

		if(parameters.containsKey("addQueue")) {

			String courseTitle = parameters.get("addQueue")[0];
			String courseId = courseTitle; // FIXME we need a real ID

			AddCourseMessage addCourseMessage = Ntro.messages().create(AddCourseMessage.class);
			addCourseMessage.setCourse(new CourseSummary(courseTitle, courseId, false, false, 0));
			addCourseMessage.setUser(user);
			Ntro.backendService().sendMessageToBackend(addCourseMessage);

		} else if(parameters.containsKey("closeQueue")) {

			TeacherClosesQueueMessage teacherClosesQueueMessage = Ntro.messages().create(TeacherClosesQueueMessage.class);
			
			String courseId = parameters.get("closeQueue")[0];
			teacherClosesQueueMessage.setCourseId(courseId);
			Ntro.backendService().sendMessageToBackend(teacherClosesQueueMessage);

		} else if(parameters.containsKey("deleteQueue")) {

			DeleteCourseMessage deleteCourseMessage = Ntro.messages().create(DeleteCourseMessage.class);
			
			String courseId = parameters.get("deleteQueue")[0];
			deleteCourseMessage.setCourseId(courseId);
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
			MoveAppointmentMessage moveAppointmentMessage = Ntro.messages().create(MoveAppointmentMessage.class);
			Appointment newAppointment = new Appointment();
			moveAppointmentMessage.setappointmentDepartureId(departureId);
			moveAppointmentMessage.setappointmentDestinationId(destinationId);
			moveAppointmentMessage.setUser(user);
			moveAppointmentMessage.setCourseId(courseId);
			//moveAppointmentMessage.sendMessage();
		}
	}
}
