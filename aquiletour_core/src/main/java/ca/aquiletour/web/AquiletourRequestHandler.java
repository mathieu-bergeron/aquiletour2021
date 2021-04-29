package ca.aquiletour.web;

import java.util.HashMap;
import java.util.Map;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.course.CourseMessage;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.models.users.Guest;
import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course.messages.ShowCourseMessage;
import ca.aquiletour.core.pages.course.student.messages.ShowCourseMessageStudent;
import ca.aquiletour.core.pages.course.teacher.messages.ShowCourseMessageTeacher;
import ca.aquiletour.core.pages.course_list.messages.AddCourseMessage;
import ca.aquiletour.core.pages.course_list.messages.SelectCourseListSubset;
import ca.aquiletour.core.pages.course_list.messages.ShowCourseListMessage;
import ca.aquiletour.core.pages.course_list.models.CourseItem;
import ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardMessage;
import ca.aquiletour.core.pages.dashboards.teacher.messages.ShowTeacherDashboardMessage;
import ca.aquiletour.core.pages.git.messages.ShowCommitListMessage;
import ca.aquiletour.core.pages.group_list.messages.SelectGroupListSubset;
import ca.aquiletour.core.pages.group_list.messages.ShowGroupListMessage;
import ca.aquiletour.core.pages.home.ShowHomeMessage;
import ca.aquiletour.core.pages.login.ShowLoginMessage;
import ca.aquiletour.core.pages.open_queue_list.messages.ShowOpenQueueListMessage;
import ca.aquiletour.core.pages.queue.student.messages.ShowStudentQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.ShowTeacherQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.aquiletour.core.pages.root.ShowLoginDialogMessage;
import ca.aquiletour.core.pages.semester_list.messages.ShowSemesterListMessage;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.ntro_messages.NtroErrorMessage;
import ca.ntro.services.Ntro;

public class AquiletourRequestHandler {
	
	
	public static void sendMessages(NtroContext<User, SessionData> context, Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);
		
		if(path.startsWith(Constants.DASHBOARD_URL_SEGMENT)) {

			sendDashboardMessages(path.subPath(1), parameters, context.user());

		}else if(path.startsWith(Constants.QUEUES_URL_SEGMENT)) {
			
			sendQueuesMessages(path.subPath(1), parameters);

		}else if(path.startsWith(Constants.QUEUE_URL_SEGMENT)) {
			
			sendQueueMessages(path.subPath(1), parameters, context.user());

		} else if(path.startsWith(Constants.COURSE_URL_SEGMENT)) {

			sendCourseMessages(path.subPath(1), parameters, context.user(), context.sessionData());
		
		}else if(path.startsWith(Constants.LOGIN_URL_SEGMENT)) {
			
			sendLoginMessages(path.subPath(1), parameters);
			
		} else if(path.startsWith(Constants.HOME_URL_SEGMENT)) {
			
			sendHomeMessages(path.subPath(1), parameters);

		} else if(path.startsWith(Constants.LOGOUT_URL_SEGMENT)) {

			Ntro.messages().send(Ntro.messages().create(ShowHomeMessage.class));
			
		} else if(path.startsWith(Constants.GIT_PROGRESS_URL_SEGMENT)) {

			sendGitMessages(path.subPath(1), parameters, context.user());

		} else if(path.startsWith(Constants.SEMESTER_LIST_URL_SEGMENT)) {

			sendCalendarListMessages(path.subPath(1), parameters, context.user());

		} else if(path.startsWith(Constants.COURSE_LIST_URL_SEGMENT)) {

			sendCourseListMessages(path.subPath(1), parameters, context.user(), context.sessionData());

		} else if(path.startsWith(Constants.GROUP_LIST_URL_SEGMENT)) {

			sendGroupListMessages(path.subPath(1), parameters, context.user(), context.sessionData());
		}
	}

	private static void sendGroupListMessages(Path subPath, 
			                                  Map<String, String[]> parameters, 
			                                  User user, 
			                                  SessionData sessionData) {

		T.call(AquiletourRequestHandler.class);
		
		ShowGroupListMessage showGroupListMessage = Ntro.messages().create(ShowGroupListMessage.class);
		Ntro.messages().send(showGroupListMessage);
		
		String currentSemesterId = null;
		if(parameters.containsKey(Constants.SEMESTER_URL_PARAM)) {
			currentSemesterId = parameters.get(Constants.SEMESTER_URL_PARAM)[0];
		}else {
			currentSemesterId = sessionData.getCurrentSemester();
		}
		
		String currentCourseId = null;
		if(parameters.containsKey(Constants.COURSE_URL_PARAM)) {
			currentCourseId = parameters.get(Constants.COURSE_URL_PARAM)[0];
		}

		SelectGroupListSubset selectGroupListSubset = Ntro.messages().create(SelectGroupListSubset.class);
		selectGroupListSubset.setSemesterId(currentSemesterId);
		selectGroupListSubset.setCourseId(currentCourseId);
		Ntro.messages().send(selectGroupListSubset);
	}

	private static void sendCourseListMessages(Path subPath, 
			                                   Map<String, String[]> parameters, 
			                                   User user, 
			                                   SessionData sessionData) {

		T.call(AquiletourRequestHandler.class);
		
		ShowCourseListMessage showCourseListMessage = Ntro.messages().create(ShowCourseListMessage.class);
		Ntro.messages().send(showCourseListMessage);
		
		String currentSemesterId = null;
		if(parameters.containsKey(Constants.SEMESTER_URL_PARAM)) {
			currentSemesterId = parameters.get(Constants.SEMESTER_URL_PARAM)[0];
		}else {
			currentSemesterId = sessionData.getCurrentSemester();
		}

		SelectCourseListSubset selectCourseListSubset = Ntro.messages().create(SelectCourseListSubset.class);
		selectCourseListSubset.setSemesterId(currentSemesterId);
		Ntro.messages().send(selectCourseListSubset);
		
	}

	private static void sendCalendarListMessages(Path subPath, Map<String, String[]> parameters, User user) {
		T.call(AquiletourRequestHandler.class);
		
		ShowSemesterListMessage showCalendarListMessage = Ntro.messages().create(ShowSemesterListMessage.class);
		Ntro.messages().send(showCalendarListMessage);
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

		ShowOpenQueueListMessage showQueuesMessage = Ntro.messages().create(ShowOpenQueueListMessage.class);
		Ntro.messages().send(showQueuesMessage);
	}
	
	static <MSG extends CourseMessage> MSG createCourseMessage(Class<MSG> messageClass, 
			                                                   Path path, 
			                                                   Map<String, String[]> parameters,
			                                                   SessionData sessionData) {
		T.call(AquiletourRequestHandler.class);
		
		MSG message = Ntro.messages().create(messageClass);

		String teacherId = path.name(0);
		String courseId = path.name(1);
		Path taskPath = path.subPath(2);

		String semesterId = null;
		if(parameters.containsKey(Constants.SEMESTER_URL_PARAM)) {
			semesterId = parameters.get(Constants.SEMESTER_URL_PARAM)[0];
		}else {
			semesterId = sessionData.getCurrentSemester();
		}

		message.setTeacherId(teacherId);
		message.setCourseId(courseId);
		message.setSemesterId(semesterId);

		if(taskPath.nameCount() > 0) {
			message.setTaskPath(taskPath);
		}
		
		return message;
	}

	private static void sendCourseMessages(Path path, Map<String, String[]> parameters, User user, SessionData sessionData) {
		T.call(AquiletourRequestHandler.class);
		
		if(path.nameCount() >= 2) {
			
			String teacherId = path.name(0);
			
			ShowCourseMessage showCourseMessage = null;
			
			if(user.getId().equals(teacherId)) {

				showCourseMessage = createCourseMessage(ShowCourseMessageTeacher.class, path, parameters, sessionData);

			}else if(user instanceof Teacher){
				
				showCourseMessage = createCourseMessage(ShowCourseMessageTeacher.class, path, parameters, sessionData);
				
			}else {
				
				showCourseMessage = createCourseMessage(ShowCourseMessageStudent.class, path, parameters, sessionData);

			}
			
			String groupId = Constants.COURSE_STRUCTURE_ID;

			if(parameters.containsKey(Constants.GROUP_URL_PARAM)) {
				groupId = parameters.get(Constants.GROUP_URL_PARAM)[0];
			}
			
			showCourseMessage.setGroupId(groupId);
			
			Ntro.messages().send(showCourseMessage);
		}
	}
		

	private static void sendQueueMessages(Path path, Map<String, String[]> parameters, User user) {
		T.call(AquiletourRequestHandler.class);
		
		if(path.nameCount() >= 1) {//TODO 

			String teacherId = path.name(0);

			if(user instanceof Guest){
				
				ShowLoginDialogMessage showLoginDialogMessage = Ntro.messages().create(ShowLoginDialogMessage.class);
				Ntro.messages().send(showLoginDialogMessage);

			}else if(user instanceof Teacher 
					&& user.getId().equals(teacherId)) {

				TeacherUsesQueueMessage teacherUsesQueueMessage = Ntro.messages().create(TeacherUsesQueueMessage.class);
				teacherUsesQueueMessage.setCourseId(teacherId);
				Ntro.backendService().sendMessageToBackend(teacherUsesQueueMessage);

				ShowTeacherQueueMessage showTeacherQueueMessage = Ntro.messages().create(ShowTeacherQueueMessage.class);
				showTeacherQueueMessage.setCourseId(teacherId);
				Ntro.messages().send(showTeacherQueueMessage);

			}else {
				
				ShowStudentQueueMessage showStudentQueueMessage = Ntro.messages().create(ShowStudentQueueMessage.class);
				showStudentQueueMessage.setCourseId(teacherId);
				Ntro.messages().send(showStudentQueueMessage);
			}
		}
	}
}
