package ca.aquiletour.web;

import java.util.Map;

import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardMessage;
import ca.aquiletour.core.pages.dashboards.teacher.messages.ShowTeacherDashboardMessage;
import ca.aquiletour.core.pages.home.ShowHomeMessage;
import ca.aquiletour.core.pages.login.ShowLoginMessage;
import ca.aquiletour.core.pages.queue.student.messages.ShowStudentQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.ShowTeacherQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.aquiletour.core.pages.queues.messages.ShowQueuesMessage;
import ca.aquiletour.core.pages.root.ShowLoginDialogMessage;
import ca.aquiletour.core.pages.users.messages.ShowUsersMessage;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class AquiletourRequestHandler {
	
	
	public static void sendMessages(NtroContext<User> context, Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);
		
		if(path.startsWith("mescours")) {

			sendDashboardMessages(path.subPath(1), parameters, context.user());

		}else if(path.startsWith("billetteries")) {
			
			sendQueuesMessages(path.subPath(1), parameters);

		}else if(path.startsWith("billetterie")) {
			
			sendQueueMessages(path.subPath(1), parameters, context.user());
			
		}else if(path.startsWith("usagers")) {
			
			sendUsersMessages(path.subPath(1), parameters);
		
		}else if(path.startsWith("connexion")) {
			
			sendLoginMessages(path.subPath(1), parameters);
			
		} else if(path.startsWith("accueil")) {

			sendHomeMessages(path.subPath(1), parameters);
		}
	}

	private static void sendHomeMessages(Path subPath, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);

		ShowHomeMessage showHomeMessage = Ntro.messages().create(ShowHomeMessage.class);
		Ntro.messages().sendMessage(showHomeMessage);
	}

	private static void sendLoginMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);

		ShowLoginMessage showLoginMessage = Ntro.messages().create(ShowLoginMessage.class);
		Ntro.messages().sendMessage(showLoginMessage);
	}

	private static void sendDashboardMessages(Path path, Map<String, String[]> parameters, User user) {
		T.call(AquiletourRequestHandler.class);
		
		if(user instanceof Teacher) {

			ShowTeacherDashboardMessage showTeacherDashboardMessage = Ntro.messages().create(ShowTeacherDashboardMessage.class);
			Ntro.messages().sendMessage(showTeacherDashboardMessage);

			
		}else if(user instanceof Student){
			
			ShowStudentDashboardMessage showStudentDashboardMessage = Ntro.messages().create(ShowStudentDashboardMessage.class);
			Ntro.messages().sendMessage(showStudentDashboardMessage);

		}else {
			
			ShowLoginDialogMessage showLoginDialogMessage = Ntro.messages().create(ShowLoginDialogMessage.class);
			Ntro.messages().sendMessage(showLoginDialogMessage);
			
		}

	}

	private static void sendQueuesMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);

		ShowQueuesMessage showQueuesMessage = Ntro.messages().create(ShowQueuesMessage.class);
		Ntro.messages().sendMessage(showQueuesMessage);
	}
		

	private static void sendQueueMessages(Path path, Map<String, String[]> parameters, User user) {
		T.call(AquiletourRequestHandler.class);
		
		if(path.size() >= 1) {//TODO 

			String courseId = path.getName(0);
			
			if(user instanceof Teacher) {

				ShowTeacherQueueMessage showTeacherQueueMessage = Ntro.messages().create(ShowTeacherQueueMessage.class);
				showTeacherQueueMessage.setCourseId(courseId);
				Ntro.messages().sendMessage(showTeacherQueueMessage);
				
				TeacherUsesQueueMessage teacherUsesQueueMessage = Ntro.messages().create(TeacherUsesQueueMessage.class);
				teacherUsesQueueMessage.setCourseId(courseId);
				teacherUsesQueueMessage.setTeacher(user);
				Ntro.backendService().sendMessageToBackend(teacherUsesQueueMessage);

				
			}else if(user instanceof Student){
				
				ShowStudentQueueMessage showStudentQueueMessage = Ntro.messages().create(ShowStudentQueueMessage.class);
				showStudentQueueMessage.setCourseId(courseId);
				Ntro.messages().sendMessage(showStudentQueueMessage);

			}else {
				
				ShowLoginDialogMessage showLoginDialogMessage = Ntro.messages().create(ShowLoginDialogMessage.class);
				Ntro.messages().sendMessage(showLoginDialogMessage);
			}

		}
	}

	private static void sendUsersMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);

		ShowUsersMessage showUsersMessage = Ntro.messages().create(ShowUsersMessage.class);
		Ntro.messages().sendMessage(showUsersMessage);
	}

}
