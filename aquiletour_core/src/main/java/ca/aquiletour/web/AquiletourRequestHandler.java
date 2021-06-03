package ca.aquiletour.web;

import java.util.Map;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.course.CourseTaskMessage;
import ca.aquiletour.core.messages.user.ShowPasswordMenu;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.models.user.Guest;
import ca.aquiletour.core.models.user.Student;
import ca.aquiletour.core.models.user.Teacher;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.course.messages.ShowCourseMessage;
import ca.aquiletour.core.pages.course.student.messages.ShowCourseMessageStudent;
import ca.aquiletour.core.pages.course.teacher.messages.ShowCourseMessageTeacher;
import ca.aquiletour.core.pages.course_list.messages.SelectCourseListSubset;
import ca.aquiletour.core.pages.course_list.messages.ShowCourseListMessage;
import ca.aquiletour.core.pages.course_list.student.messages.SelectCourseListSubsetStudent;
import ca.aquiletour.core.pages.course_list.student.messages.ShowCourseListMessageStudent;
import ca.aquiletour.core.pages.course_list.teacher.messages.SelectCourseListSubsetTeacher;
import ca.aquiletour.core.pages.course_list.teacher.messages.ShowCourseListMessageTeacher;
import ca.aquiletour.core.pages.dashboard.student.messages.ShowDashboardMessageStudent;
import ca.aquiletour.core.pages.dashboard.teacher.messages.ShowTeacherDashboardMessage;
import ca.aquiletour.core.pages.git.commit_list.messages.ShowCommitListForTimePeriodMessage;
import ca.aquiletour.core.pages.git.commit_list.messages.ShowCommitListMessage;
import ca.aquiletour.core.pages.git.late_students.messages.ShowLateStudentsMessage;
import ca.aquiletour.core.pages.git.student_summaries.messages.ShowStudentSummariesMessage;
import ca.aquiletour.core.pages.group_list.messages.SelectGroupListSubset;
import ca.aquiletour.core.pages.group_list.messages.ShowGroupListMessage;
import ca.aquiletour.core.pages.home.ShowHomeMessage;
import ca.aquiletour.core.pages.login.ShowLoginMessage;
import ca.aquiletour.core.pages.open_queue_list.messages.ShowOpenQueueListMessage;
import ca.aquiletour.core.pages.queue.student.messages.ShowQueueMessageStudent;
import ca.aquiletour.core.pages.queue.teacher.messages.ShowQueueMessageTeacher;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.aquiletour.core.pages.root.messages.ShowLoginMenuMessage;
import ca.aquiletour.core.pages.semester_list.admin.messages.ShowSemesterListAdmin;
import ca.aquiletour.core.pages.semester_list.teacher.messages.ShowSemesterListTeacher;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class AquiletourRequestHandler {
	
	public static void sendMessages(NtroContext<User, SessionData> context, Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);

		sendRootMessages(context, path, parameters);
		
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
			
		} else if(path.startsWith(Constants.SEMESTER_LIST_URL_SEGMENT)) {

			sendSemesterListMessages(path.subPath(1), parameters, context.user());

		} else if(path.startsWith(Constants.COURSE_LIST_URL_SEGMENT)) {

			sendCourseListMessages(path.subPath(1), parameters, context.user(), context.sessionData());

		} else if(path.startsWith(Constants.GROUP_LIST_URL_SEGMENT)) {

			sendGroupListMessages(path.subPath(1), parameters, context.user(), context.sessionData());

		} else if (path.startsWith(Constants.GIT_COMMIT_LIST_URL_SEGMENT)) {

			sendGitCommitListMessages(path.subPath(1), parameters, context.user(), context.sessionData());

		} else if (path.startsWith(Constants.GIT_LATE_STUDENTS_URL_SEGMENT)) {

			sendGitLateStudentsMessages(path.subPath(1), parameters, context.user());

		} else if (path.startsWith(Constants.GIT_STUDENT_SUMMARIES_URL_SEGMENT)) {

			sendGitStudentSummariesMessages(path.subPath(1), parameters, context.user());
		}
	}

	public static void sendRootMessages(NtroContext<User, SessionData> context, Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);

		if(parameters.containsKey("showPasswordMenu")) {
			
			ShowPasswordMenu showPasswordMenu = Ntro.messages().create(ShowPasswordMenu.class);
			Ntro.messages().send(showPasswordMenu);
		}
	}

	private static void sendGroupListMessages(Path subPath, 
			                                  Map<String, String[]> parameters, 
			                                  User user, 
			                                  SessionData sessionData) {

		T.call(AquiletourRequestHandler.class);
		
		ShowGroupListMessage showGroupListMessage = Ntro.messages().create(ShowGroupListMessage.class);
		Ntro.messages().send(showGroupListMessage);

		String currentSemesterId = Constants.ACTIVE_SEMESTERS_ID;
		if(parameters.containsKey(Constants.SEMESTER_URL_PARAM)) {
			currentSemesterId = parameters.get(Constants.SEMESTER_URL_PARAM)[0];
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

		if(user instanceof Guest){
			
			ShowLoginMenuMessage showLoginDialogMessage = Ntro.messages().create(ShowLoginMenuMessage.class);
			showLoginDialogMessage.setMessageToUser("SVP vous connecter pour voir vos cours");
			Ntro.messages().send(showLoginDialogMessage);

		} else {
			
			ShowCourseListMessage showCourseListMessage = null;
			
			if(user.actsAsTeacher()) {
				showCourseListMessage = Ntro.messages().create(ShowCourseListMessageTeacher.class);
			}else {
				showCourseListMessage = Ntro.messages().create(ShowCourseListMessageStudent.class);
			}

			Ntro.messages().send(showCourseListMessage);
			
			String currentCategoryId = AquiletourMain.currentCategoryId();
			if(parameters.containsKey(Constants.CATEGORY_URL_PARAM)) {
				currentCategoryId = parameters.get(Constants.CATEGORY_URL_PARAM)[0];
				AquiletourMain.setCurrentCategoryId(currentCategoryId);
			}

			SelectCourseListSubset selectCourseListSubset = null;

			if(user instanceof Teacher) {
				selectCourseListSubset = Ntro.messages().create(SelectCourseListSubsetTeacher.class);
			}else {
				selectCourseListSubset = Ntro.messages().create(SelectCourseListSubsetStudent.class);
			}

			selectCourseListSubset.setCategoryId(currentCategoryId);
			Ntro.messages().send(selectCourseListSubset);
		}
	}

	private static void sendSemesterListMessages(Path subPath, Map<String, String[]> parameters, User user) {
		T.call(AquiletourRequestHandler.class);

		if(user.actsAsAdmin()) {
			
			ShowSemesterListAdmin showSemesterList = Ntro.messages().create(ShowSemesterListAdmin.class);
			Ntro.messages().send(showSemesterList);

		}else if(user.actsAsTeacher()){

			ShowSemesterListTeacher showSemesterList = Ntro.messages().create(ShowSemesterListTeacher.class);
			Ntro.messages().send(showSemesterList);
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
		

		if(user instanceof Teacher && user.actsAsTeacher()) {

			ShowTeacherDashboardMessage showTeacherDashboardMessage = Ntro.messages().create(ShowTeacherDashboardMessage.class);
			Ntro.messages().send(showTeacherDashboardMessage);

			
		}else if(user instanceof Student || (user instanceof Teacher && !user.actsAsTeacher())){
			
			ShowDashboardMessageStudent showStudentDashboardMessage = Ntro.messages().create(ShowDashboardMessageStudent.class);
			Ntro.messages().send(showStudentDashboardMessage);

		}else {

			ShowLoginMenuMessage showLoginMenuMessage = Ntro.messages().create(ShowLoginMenuMessage.class);
			showLoginMenuMessage.setMessageToUser("SVP vous connecter pour voir votre tableau bord.");
			Ntro.messages().send(showLoginMenuMessage);
		}
	}

	private static void sendQueuesMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourRequestHandler.class);

		ShowOpenQueueListMessage showQueuesMessage = Ntro.messages().create(ShowOpenQueueListMessage.class);
		Ntro.messages().send(showQueuesMessage);
	}
	
	static <MSG extends CourseTaskMessage> MSG createCourseTaskMessage(Class<MSG> messageClass, 
			                                                   Path path, 
			                                                   Map<String, String[]> parameters,
			                                                   SessionData sessionData) {
		T.call(AquiletourRequestHandler.class);
		
		MSG message = Ntro.messages().create(messageClass);

		String teacherId = path.name(0);
		String semesterId = path.name(1);
		String courseId = path.name(2);
		Path taskPath = path.subPath(3);

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
			
			if(teacherId.equals(user.getId())
					&& user.actsAsTeacher()) {

				showCourseMessage = createCourseTaskMessage(ShowCourseMessageTeacher.class, path, parameters, sessionData);
				
			}else {
				
				showCourseMessage = createCourseTaskMessage(ShowCourseMessageStudent.class, path, parameters, sessionData);

			}
			
			String groupId = null;
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


			if(user instanceof Teacher 
					&& user.getId().equals(teacherId)
					&& user.actsAsTeacher()) {

				TeacherUsesQueueMessage teacherUsesQueueMessage = Ntro.messages().create(TeacherUsesQueueMessage.class);
				teacherUsesQueueMessage.setCourseId(teacherId);
				Ntro.backendService().sendMessageToBackend(teacherUsesQueueMessage);

				ShowQueueMessageTeacher showTeacherQueueMessage = Ntro.messages().create(ShowQueueMessageTeacher.class);
				showTeacherQueueMessage.setQueueId(teacherId);
				Ntro.messages().send(showTeacherQueueMessage);

			}else {
				
				ShowQueueMessageStudent showStudentQueueMessage = Ntro.messages().create(ShowQueueMessageStudent.class);
				showStudentQueueMessage.setQueueId(teacherId);
				Ntro.messages().send(showStudentQueueMessage);
			}
		}
	}

	private static void sendGitCommitListMessages(Path subPath, Map<String, String[]> parameters, User user, SessionData sessionData) {
		T.call(AquiletourRequestHandler.class);

		if (subPath.nameCount() >= 1) {
			
			ShowCommitListMessage showGitMessage = null;
			if (parameters.containsKey("endTime")) {
				ShowCommitListForTimePeriodMessage message = AquiletourBackendRequestHandler.createAquiletourGitMessage(ShowCommitListForTimePeriodMessage.class, 
						                                                                                                subPath, 
						                                                                                                parameters, 
						                                                                                                sessionData);
				// TODO
				//message.setStartTime(startTime);
				//message.setEndTime(endTime);
				showGitMessage = message;
			}else {
				showGitMessage= AquiletourBackendRequestHandler.createAquiletourGitMessage(ShowCommitListMessage.class, 
						                                                                   subPath, 
						                                                                   parameters, 
						                                                                   sessionData);
			}

			Ntro.messages().send(showGitMessage);
		}
	}

	private static void sendGitLateStudentsMessages(Path subPath, Map<String, String[]> parameters, User user) {
		T.call(AquiletourRequestHandler.class);

		if (subPath.nameCount() >= 1) {

			String courseId = subPath.name(0);
			String exerciseId = new Path().toString();
			if (subPath.nameCount() > 1) {
				exerciseId = subPath.subPath(1).toString();
			}

			ShowLateStudentsMessage showGitMessage = Ntro.messages().create(ShowLateStudentsMessage.class);
			showGitMessage.setCourseId("mathieu.bergeron/StruDon");
			showGitMessage.setExercisePath("/TP1/Exercice 1");
			showGitMessage.setGroupId("01"); // TODO
			showGitMessage.setSemesterId("H2021"); // TODO
			showGitMessage.setDeadline("1615215942");
			Ntro.messages().send(showGitMessage);
		}
	}
	private static void sendGitStudentSummariesMessages(Path subPath, Map<String, String[]> parameters, User user) {
		T.call(AquiletourRequestHandler.class);

		if (subPath.nameCount() >= 1) {

			String courseId = subPath.name(0);
			String exerciseId = new Path().toString();
			if (subPath.nameCount() > 1) {
				exerciseId = subPath.subPath(1).toString();
			}

			ShowStudentSummariesMessage showGitMessage = Ntro.messages().create(ShowStudentSummariesMessage.class);
			showGitMessage.setCourseId("mathieu.bergeron/StruDon");
			showGitMessage.setExercisePath("/TP1/Exercice 1");
			showGitMessage.setGroupId("01"); // TODO
			showGitMessage.setSemesterId("H2021"); // TODO
			showGitMessage.setDeadline("1615215942");
			Ntro.messages().send(showGitMessage);
		}
	}
}
