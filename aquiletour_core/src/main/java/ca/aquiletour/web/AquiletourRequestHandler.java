package ca.aquiletour.web;

import java.util.Map;

import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardMessage;
import ca.aquiletour.core.pages.dashboards.teacher.messages.ShowTeacherDashboardMessage;
import ca.aquiletour.core.pages.home.ShowHomeMessage;
import ca.aquiletour.core.pages.login.ShowLoginMessage;
import ca.aquiletour.core.pages.queue.messages.ShowQueueMessage;
import ca.aquiletour.core.pages.queues.messages.ShowQueuesMessage;
import ca.aquiletour.core.pages.root.ShowLoginDialogMessage;
import ca.aquiletour.core.pages.users.messages.ShowUsersMessage;
import ca.ntro.core.Ntro;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageFactory;

public class AquiletourRequestHandler {
	
	
	public static void sendMessages(NtroContext<User> context, Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);
		
		if(path.startsWith("mescours")) {

			sendDashboardMessages(path.subPath(1), parameters, context.getUser());

		}else if(path.startsWith("billetteries")) {
			
			sendQueuesMessages(path.subPath(1), parameters);

		}else if(path.startsWith("billetterie")) {
			
			sendQueueMessages(path.subPath(1), parameters);
			
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

		ShowHomeMessage showHomeMessage = MessageFactory.getOutgoingMessage(ShowHomeMessage.class);
		showHomeMessage.sendMessage();
		
	}

	private static void sendLoginMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);

		/*
		ShowLoginMessage showLoginMessage = MessageFactory.getOutgoingMessage(ShowLoginMessage.class);
		showLoginMessage.sendMessage();
		*/

		ShowLoginMessage showLoginMessage = new ShowLoginMessage();
		Ntro.messageService().sendMessage(showLoginMessage);
	}

	private static void sendDashboardMessages(Path path, Map<String, String[]> parameters, User user) {
		T.call(AquiletourRequestHandler.class);
		
		if(user instanceof Teacher) {

			ShowTeacherDashboardMessage showTeacherDashboardMessage = MessageFactory.getOutgoingMessage(ShowTeacherDashboardMessage.class);
			showTeacherDashboardMessage.sendMessage();
			
		}else if(user instanceof Student){
			
			ShowStudentDashboardMessage showStudentDashboardMessage = MessageFactory.getOutgoingMessage(ShowStudentDashboardMessage.class);
			showStudentDashboardMessage.sendMessage();

		}else {
			
			ShowLoginDialogMessage showLoginDialogMessage = MessageFactory.getOutgoingMessage(ShowLoginDialogMessage.class);
			showLoginDialogMessage.sendMessage();
			
		}
	}

	private static void sendQueuesMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);

		ShowQueuesMessage showQueuesMessage = MessageFactory.getOutgoingMessage(ShowQueuesMessage.class);
		showQueuesMessage.sendMessage();
	}
		

	private static void sendQueueMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);
		
		if(path.size() >= 1) {//TODO 

			String courseId = path.getName(0);
			
			ShowQueueMessage showQueueMessage = MessageFactory.getOutgoingMessage(ShowQueueMessage.class);
			//teacher uses queue message (prof encore actif)
			
			showQueueMessage.setCourseId(courseId);
			
			showQueueMessage.sendMessage();

		}
	}

	private static void sendUsersMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);

		ShowUsersMessage showUsersMessage = MessageFactory.getOutgoingMessage(ShowUsersMessage.class);
		showUsersMessage.sendMessage();

	}

}
