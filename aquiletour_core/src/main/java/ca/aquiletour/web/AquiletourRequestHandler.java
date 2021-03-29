package ca.aquiletour.web;

import java.util.HashMap;
import java.util.Map;

import ca.aquiletour.core.messages.git.GetCommitsForPath;
import ca.aquiletour.core.messages.git.RegisterRepoMessage;
import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course.messages.ShowCourseMessage;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardMessage;
import ca.aquiletour.core.pages.dashboards.teacher.messages.ShowTeacherDashboardMessage;
import ca.aquiletour.core.pages.git.messages.ShowCommitListMessage;
import ca.aquiletour.core.pages.home.ShowHomeMessage;
import ca.aquiletour.core.pages.login.ShowLoginMessage;
import ca.aquiletour.core.pages.queue.student.messages.ShowStudentQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.ShowTeacherQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.aquiletour.core.pages.queues.messages.ShowQueuesMessage;
import ca.aquiletour.core.pages.root.ShowLoginDialogMessage;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.ntro_messages.GetModelNtroMessage;
import ca.ntro.services.Ntro;

public class AquiletourRequestHandler {
	
	
	public static void sendMessages(NtroContext<User> context, Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);
		
		if(path.startsWith("mescours")) {

			sendDashboardMessages(path.subPath(1), parameters, context.user());

		}else if(path.startsWith("profs")) {
			
			sendQueuesMessages(path.subPath(1), parameters);

		}else if(path.startsWith("billetterie")) {
			
			sendQueueMessages(path.subPath(1), parameters, context.user());

		} else if(path.startsWith("cours")) {

			sendCourseMessages(path.subPath(1), parameters, context.user());
		
		}else if(path.startsWith("connexion")) {
			
			sendLoginMessages(path.subPath(1), parameters);
			
		} else if(path.startsWith("accueil")) {
			
			sendHomeMessages(path.subPath(1), parameters);

		} else if(path.startsWith("deconnexion")) {

			Ntro.messages().send(Ntro.messages().create(ShowHomeMessage.class));
			
		} else if(path.startsWith("progressiongit")) {

			sendGitMessages(path.subPath(1), parameters, context.user());
		}
	}

	private static void sendGitMessages(Path subPath, Map<String, String[]> parameters, User user) {
		T.call(AquiletourRequestHandler.class);
		
		if(subPath.nameCount() >= 1) {
			
			String courseId = subPath.name(0);
			String exerciseId = new Path().toString();
			if(subPath.nameCount() > 1) {
				exerciseId = subPath.subPath(1).toString();
			}

			ShowCommitListMessage showGitMessage = Ntro.messages().create(ShowCommitListMessage.class);
			showGitMessage.setCourseId(courseId);
			showGitMessage.setExercisePath(exerciseId);
			showGitMessage.setStudentId(user.getId());
			showGitMessage.setGroupId("01"); // TODO
			showGitMessage.setSemesterId("H2021"); // TODO
			Ntro.messages().send(showGitMessage);
		}
	}

	private static void sendHomeMessages(Path subPath, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);

		ShowHomeMessage showHomeMessage = Ntro.messages().create(ShowHomeMessage.class);
		Ntro.messages().send(showHomeMessage);
	}

	private static void sendLoginMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);
		
		ShowLoginMessage showLoginMessage = Ntro.messages().create(ShowLoginMessage.class);
		
		if(parameters.containsKey("message")) {
			showLoginMessage.setMessageToUser(parameters.get("message")[0]);
		}

		Ntro.messages().send(showLoginMessage);
	}

	private static void sendDashboardMessages(Path path, Map<String, String[]> parameters, User user) {
		T.call(AquiletourRequestHandler.class);
		

		if(user instanceof Teacher) {

			ShowTeacherDashboardMessage showTeacherDashboardMessage = Ntro.messages().create(ShowTeacherDashboardMessage.class);
			Ntro.messages().send(showTeacherDashboardMessage);

			
		}else if(user instanceof Student){
			
			ShowStudentDashboardMessage showStudentDashboardMessage = Ntro.messages().create(ShowStudentDashboardMessage.class);
			Ntro.messages().send(showStudentDashboardMessage);

		}else {
			
			Map<String, String[]> newParams = new HashMap<>();
			newParams.put("message", new String[] {"SVP entrer votre DA pour voir vos cours"});
			sendLoginMessages(path, newParams);
		}
	}

	private static void sendQueuesMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);

		ShowQueuesMessage showQueuesMessage = Ntro.messages().create(ShowQueuesMessage.class);
		Ntro.messages().send(showQueuesMessage);
	}

	private static void sendCourseMessages(Path path, Map<String, String[]> parameters, User user) {
		T.call(AquiletourRequestHandler.class);
		
		if(path.nameCount() >= 1) {//TODO 

			String courseId = path.name(0);
			Path taskPath = path.subPath(1);
			
			ShowCourseMessage showCourseMessage = Ntro.messages().create(ShowCourseMessage.class);
			showCourseMessage.setCourseId(courseId);

			if(taskPath.nameCount() > 0) {
				showCourseMessage.setTaskPath(taskPath);
			}

			Ntro.messages().send(showCourseMessage);
			
			
		}
	}
		

	private static void sendQueueMessages(Path path, Map<String, String[]> parameters, User user) {
		T.call(AquiletourRequestHandler.class);
		
		if(path.nameCount() >= 1) {//TODO 

			String courseId = path.name(0);
			
			if(user instanceof Teacher) {

				TeacherUsesQueueMessage teacherUsesQueueMessage = Ntro.messages().create(TeacherUsesQueueMessage.class);
				teacherUsesQueueMessage.setCourseId(courseId);
				Ntro.backendService().sendMessageToBackend(teacherUsesQueueMessage);

				ShowTeacherQueueMessage showTeacherQueueMessage = Ntro.messages().create(ShowTeacherQueueMessage.class);
				showTeacherQueueMessage.setCourseId(courseId);
				Ntro.messages().send(showTeacherQueueMessage);

			}else if(user instanceof Student){
				
				ShowStudentQueueMessage showStudentQueueMessage = Ntro.messages().create(ShowStudentQueueMessage.class);
				showStudentQueueMessage.setCourseId(courseId);
				Ntro.messages().send(showStudentQueueMessage);

			}else {
				
				ShowLoginDialogMessage showLoginDialogMessage = Ntro.messages().create(ShowLoginDialogMessage.class);
				Ntro.messages().send(showLoginDialogMessage);
			}

		}
	}
}
