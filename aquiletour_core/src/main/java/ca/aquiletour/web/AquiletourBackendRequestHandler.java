package ca.aquiletour.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.course.AtomicTaskMessage;
import ca.aquiletour.core.messages.queue.UpdateIsQueueOpenMessage;
import ca.aquiletour.core.messages.user.ItsNotMeMessage;
import ca.aquiletour.core.messages.user.ToggleAdminModeMessage;
import ca.aquiletour.core.messages.user.ToggleStudentModeMessage;
import ca.aquiletour.core.messages.user.UpdateUserInfoMessage;
import ca.aquiletour.core.messages.user.UserChangesPasswordMessage;
import ca.aquiletour.core.messages.user.UserInitiatesLoginMessage;
import ca.aquiletour.core.messages.user.UserLogsOutMessage;
import ca.aquiletour.core.messages.user.UserSendsLoginCodeMessage;
import ca.aquiletour.core.messages.user.UserSendsPasswordMessage;
import ca.aquiletour.core.models.courses.atomic_tasks.default_task.DefaultCompletion;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.dates.CourseDateScheduleItem;
import ca.aquiletour.core.models.dates.SemesterDate;
import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.models.paths.TaskPath;
import ca.aquiletour.core.models.dates.AquiletourDate;
import ca.aquiletour.core.models.dates.CalendarWeek;
import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.course.messages.AddNextTaskMessage;
import ca.aquiletour.core.pages.course.messages.AddPreviousTaskMessage;
import ca.aquiletour.core.pages.course.messages.AddSubTaskMessage;
import ca.aquiletour.core.pages.course.messages.DeleteTaskMessage;
import ca.aquiletour.core.pages.course.messages.RemoveNextTaskMessage;
import ca.aquiletour.core.pages.course.messages.RemovePreviousTaskMessage;
import ca.aquiletour.core.pages.course.messages.RemoveSubTaskMessage;
import ca.aquiletour.core.pages.course.messages.AtomicTaskCompletedMessage;
import ca.aquiletour.core.pages.course.messages.UpdateTaskInfoMessage;
import ca.aquiletour.core.pages.course.student.messages.StudentDeletesRepoMessage;
import ca.aquiletour.core.pages.course.student.messages.StudentRegistersRepoMessage;
import ca.aquiletour.core.pages.course.student.messages.AquiletourGitMessage;
import ca.aquiletour.core.pages.course_list.messages.AddCourseMessage;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.dashboard.teacher.messages.DeleteCourseMessage;
import ca.aquiletour.core.pages.queue.messages.ModifyAppointmentDurationsMessage;
import ca.aquiletour.core.pages.queue.messages.ModifyAppointmentTimesMessage;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.student.messages.ModifyAppointmentCommentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.MoveAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.aquiletour.core.pages.semester_list.messages.AddSemesterWeekMessage;
import ca.aquiletour.core.pages.semester_list.messages.DeleteSemesterMessage;
import ca.aquiletour.core.pages.semester_list.messages.SetActiveSemesterMessage;
import ca.aquiletour.core.pages.semester_list.models.CourseGroup;
import ca.aquiletour.core.pages.semester_list.messages.AddScheduleItemMessage;
import ca.aquiletour.core.pages.semester_list.messages.AddSemesterMessage;
import ca.ntro.backend.UserInputError;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.models.NtroDate;
import ca.ntro.models.NtroDayOfWeek;
import ca.ntro.models.NtroTimeOfDay;
import ca.ntro.services.Ntro;
import ca.ntro.users.NtroSession;

public class AquiletourBackendRequestHandler {
	
	public static void sendMessages(NtroContext<User, SessionData> context, Path path, Map<String, String[]> parameters) throws UserInputError {
		T.call(AquiletourBackendRequestHandler.class);
		
		sendRootMessages(context, path, parameters);

		if(path.startsWith(Constants.LOGOUT_URL_SEGMENT)) {

			Ntro.messages().send(Ntro.messages().create(UserLogsOutMessage.class));

		} else if(path.startsWith(Constants.DASHBOARD_URL_SEGMENT)) {

			sendDashboardMessages(path.subPath(1), parameters, context.user());

		} else if(path.startsWith(Constants.QUEUES_URL_SEGMENT)) {
			
			sendQueuesMessages(path.subPath(1), parameters);

		}else if(path.startsWith(Constants.QUEUE_URL_SEGMENT)) {
			
			sendQueueMessages(path.subPath(1), parameters, context.user());

		}else if(path.startsWith(Constants.COURSE_URL_SEGMENT)) {
			
			sendCourseMessages(path.subPath(1), parameters, context.sessionData());
			
		}else if(path.startsWith(Constants.LOGIN_URL_SEGMENT)) {
			
			sendLoginMessages(path.subPath(1), parameters);
	
		}else if(path.startsWith(Constants.HOME_URL_SEGMENT)) {

			sendHomeMessages(path.subPath(1), parameters);

		}else if(path.startsWith(Constants.SEMESTER_LIST_URL_SEGMENT)) {

			sendSemesterListMessages(path.subPath(1), parameters);

		}else if(path.startsWith(Constants.COURSE_LIST_URL_SEGMENT)) {

			sendCourseListMessages(path.subPath(1), parameters, context.user());

		}
	}

	public static void sendRootMessages(NtroContext<User, SessionData> context, Path path, Map<String, String[]> parameters) throws UserInputError {
		T.call(AquiletourBackendRequestHandler.class);

		if(parameters.containsKey("loginStep01")
				&& parameters.containsKey("studentId")) {
			
			String userId = parameters.get("studentId")[0].trim();
			
			sendLoginMessage(userId, parameters);

		} else if(parameters.containsKey("loginStep01")
				&& parameters.containsKey("teacherId") 
				&& !parameters.get("teacherId")[0].isEmpty()) {
			
			String userId = parameters.get("teacherId")[0].trim();
			
			sendLoginMessage(userId, parameters);

		} else if(parameters.containsKey("itsNotMe")) {
			
			ItsNotMeMessage itsNotMeMessage = Ntro.messages().create(ItsNotMeMessage.class);
			itsNotMeMessage.setDelayedMessages(delayedMessages(parameters));
			Ntro.messages().send(itsNotMeMessage);

		} else if(parameters.containsKey("loginCode")) {

			UserSendsLoginCodeMessage userSendsLoginCodeMessage = Ntro.messages().create(UserSendsLoginCodeMessage.class);
			userSendsLoginCodeMessage.setLoginCode(parameters.get("loginCode")[0]);
			userSendsLoginCodeMessage.setDelayedMessages(delayedMessages(parameters));
			Ntro.messages().send(userSendsLoginCodeMessage);

		} else if(parameters.containsKey("userName")) {
			
			String screenName = parameters.get("userName")[0];
			
			UpdateUserInfoMessage updateUserInfoMessage = Ntro.messages().create(UpdateUserInfoMessage.class);
			updateUserInfoMessage.setScreenName(screenName);
			Ntro.messages().send(updateUserInfoMessage); // XXX: must be Ntro.message(), in JSweet the frontend handles it

		} else if(parameters.containsKey("newPasswordA")) {

			String currentPassword = null;
			if(parameters.containsKey("currentPassword")) {
				currentPassword = parameters.get("currentPassword")[0];
			}
			String newPasswordA = parameters.get("newPasswordA")[0];
			String newPasswordB = parameters.get("newPasswordB")[0];
			
			UserChangesPasswordMessage userChangesPassword = Ntro.messages().create(UserChangesPasswordMessage.class);
			userChangesPassword.setCurrentPassword(currentPassword);
			userChangesPassword.setNewPasswordA(newPasswordA);
			userChangesPassword.setNewPasswordB(newPasswordB);
			
			Ntro.messages().send(userChangesPassword);

		} else if(parameters.containsKey("password")) {

			String password = parameters.get("password")[0];
			
			UserSendsPasswordMessage userSendsPassword = Ntro.messages().create(UserSendsPasswordMessage.class);
			userSendsPassword.setPassword(password);
			userSendsPassword.setDelayedMessages(delayedMessages(parameters));
			
			Ntro.messages().send(userSendsPassword);

		} else if(parameters.containsKey("toggleStudentMode")) {

			ToggleStudentModeMessage toggleStudentModeMessage = Ntro.messages().create(ToggleStudentModeMessage.class);
			Ntro.messages().send(toggleStudentModeMessage);

		} else if(parameters.containsKey("toggleAdminMode")) {

			ToggleAdminModeMessage toggleAdminModeMessage = Ntro.messages().create(ToggleAdminModeMessage.class);
			Ntro.messages().send(toggleAdminModeMessage);
		}
	}

	@SuppressWarnings("unchecked")
	private static List<NtroMessage> delayedMessages(Map<String, String[]> parameters) {
		T.call(AquiletourBackendRequestHandler.class);
		
		List<NtroMessage> delayedMessages = null;

		String delayedMessagesText = parameters.get("delayedMessages")[0];
		
		if(delayedMessagesText.isEmpty()) {
			
			delayedMessages = new ArrayList<>();
			
		}else {

			delayedMessagesText = delayedMessagesText.replaceAll("\\\"","\"");
			delayedMessages = (List<NtroMessage>) Ntro.jsonService().fromString(List.class, delayedMessagesText);
		}

		return delayedMessages;
	}

	private static void sendLoginMessage(String userId, Map<String, String[]> parameters) {
		T.call(AquiletourBackendRequestHandler.class);
		
		UserInitiatesLoginMessage userInitiatesLoginMessage = Ntro.messages().create(UserInitiatesLoginMessage.class);
		userInitiatesLoginMessage.setRegistrationId(userId);
		userInitiatesLoginMessage.setDelayedMessages(delayedMessages(parameters));
		Ntro.messages().send(userInitiatesLoginMessage);
	}


	private static void sendCourseListMessages(Path subPath, Map<String, String[]> parameters, User user) {
		T.call(AquiletourBackendRequestHandler.class);
		
		if(parameters.containsKey("addCourse")) {
			
			String semesterId = parameters.get("semesterId")[0];
			String courseTitle = parameters.get("courseTitle")[0];
			String courseId = parameters.get("newCourseId")[0];
			
			CourseListItem courseListItem = new CourseListItem(user.getId(), semesterId, courseId, courseTitle);
			
			AddCourseMessage addCourseMessage = Ntro.messages().create(AddCourseMessage.class);
			addCourseMessage.setSemesterId(semesterId);
			addCourseMessage.setCourseListItem(courseListItem);
			Ntro.messages().send(addCourseMessage);

		} else if(parameters.containsKey("duplicateCourse")) {
			
			String duplicateCourseId = parameters.get("duplicateCourseId")[0];
			
			System.out.println("TODO: " + duplicateCourseId);


		} else if(parameters.containsKey("taskId")) {
			
			String teacherId = user.getId();
			String semesterId = parameters.get("semesterId")[0];
			String courseId = parameters.get("courseId")[0];
			
			String taskId = parameters.get("taskId")[0];
			String taskTitle = parameters.get("taskId")[0];
			
			Path parentPath = Path.fromRawPath("/");
			TaskPath taskPath = TaskPath.fromRawPath("/" + taskId);

			Task task = new Task();
			task.updateTitle(taskTitle);
			task.setPath(taskPath);
			
			AddSubTaskMessage addSubTaskMessage = Ntro.messages().create(AddSubTaskMessage.class);
			addSubTaskMessage.setTeacherId(teacherId);
			addSubTaskMessage.setSemesterId(semesterId);
			addSubTaskMessage.setCourseId(courseId);
			addSubTaskMessage.setParentPath(parentPath);
			addSubTaskMessage.setSubTask(task);
			Ntro.messages().send(addSubTaskMessage);

		} else if(parameters.containsKey("openQueueCourseId")) {
			
			String courseId = parameters.get("openQueueCourseId")[0];
			
			if(parameters.containsKey("ifQueueOpen")
					&& parameters.get("ifQueueOpen")[0].equals("on")) {
				
				TeacherUsesQueueMessage teacherUsesQueueMessage = Ntro.messages().create(TeacherUsesQueueMessage.class);
				teacherUsesQueueMessage.setCourseId(courseId);
				Ntro.messages().send(teacherUsesQueueMessage);
				
			}else {
				
				TeacherClosesQueueMessage teacherClosesQueueMessage = Ntro.messages().create(TeacherClosesQueueMessage.class);
				teacherClosesQueueMessage.setCourseId(courseId);
				Ntro.messages().send(teacherClosesQueueMessage);
			}
		}
	}

	private static void sendSemesterListMessages(Path subPath, Map<String, String[]> parameters) throws UserInputError {
		T.call(AquiletourBackendRequestHandler.class);

		if(parameters.containsKey(Constants.ADD_SEMESTER_URL_PARAM)) {
			
			String semesterId = parameters.get(Constants.ADD_SEMESTER_URL_PARAM)[0];
			
			AddSemesterMessage addSemesterMessage = Ntro.messages().create(AddSemesterMessage.class);
			addSemesterMessage.setSemesterId(semesterId);
			
			Ntro.messages().send(addSemesterMessage);

		} else if(parameters.containsKey("semesterId") && parameters.containsKey("weekOf")) {

			String semesterId = parameters.get("semesterId")[0];
			String weekOf = parameters.get("weekOf")[0];
			
			CalendarWeek semesterWeek = new CalendarWeek();
			
			NtroDate mondayDate = Ntro.calendar().fromString(weekOf, "dd/MM/yyyy");
			semesterWeek.setMondayDate(mondayDate);
			
			for(int dayOfWeek = NtroDayOfWeek.MONDAY; dayOfWeek <= NtroDayOfWeek.FRIDAY; dayOfWeek++) {
				String semesterWeekId = parameters.get("day" + dayOfWeek)[0];
				String scheduleOf = parameters.get("day" + dayOfWeek + "ScheduleOf")[0];
				
				SemesterDate semesterDate = new SemesterDate();
				semesterDate.setCalendarDate(mondayDate.deltaDays(dayOfWeek));
				try {

					semesterDate.setSemesterWeek(Integer.valueOf(semesterWeekId));

				}catch(NumberFormatException e) {
					
					throw new UserInputError("SVP entrer un nombre pour chaque jour (p.ex. 4 indique qu'il s'agit du lundi de la semaine 4)");
				}

				semesterDate.setSemesterDay(new NtroDayOfWeek(dayOfWeek));
				semesterDate.setScheduleOf(NtroDayOfWeek.fromString(scheduleOf));

				semesterWeek.addDate(semesterDate);
			}

			AddSemesterWeekMessage addSemesterWeek = Ntro.messages().create(AddSemesterWeekMessage.class);
			addSemesterWeek.setSemesterId(semesterId);
			addSemesterWeek.setSemesterWeek(semesterWeek);
			Ntro.messages().send(addSemesterWeek);

			
		} else if(parameters.containsKey("semesterId") && parameters.containsKey("scheduleItemDay")) {

			String semesterId = parameters.get("semesterId")[0];
			String itemDayString = parameters.get("scheduleItemDay")[0];
			String startTimeString = parameters.get("startTime")[0];
			String endTimeString = parameters.get("endTime")[0];
			String courseGroupPath = parameters.get("courseGroup")[0];
			String scheduleItemId = parameters.get("scheduleItemId")[0];
			
			CourseGroup courseGroup = CourseGroup.fromString(courseGroupPath);
			NtroDayOfWeek itemDay = NtroDayOfWeek.fromString(itemDayString);
			NtroTimeOfDay startTime = NtroTimeOfDay.fromString(startTimeString);
			NtroTimeOfDay endTime = NtroTimeOfDay.fromString(endTimeString);
			
			ScheduleItem scheduleItem = new ScheduleItem(scheduleItemId,
														 courseGroup,
														 itemDay,
														 startTime,
														 endTime);
			
			AddScheduleItemMessage addScheduleItemMessage = Ntro.messages().create(AddScheduleItemMessage.class);
			addScheduleItemMessage.setSemesterId(semesterId);
			addScheduleItemMessage.setScheduleItem(scheduleItem);
			Ntro.messages().send(addScheduleItemMessage);
			
		} else if(parameters.containsKey("currentSemesterId")) {

			String semesterId = parameters.get("currentSemesterId")[0];
			boolean currentSemester = false;
			
			if(parameters.containsKey("ifSemesterSelected")) {
				currentSemester = true;
			}

			SetActiveSemesterMessage selectCurrentSemester = Ntro.messages().create(SetActiveSemesterMessage.class);
			selectCurrentSemester.setSemesterId(semesterId);
			selectCurrentSemester.setIsActive(currentSemester);
			Ntro.messages().send(selectCurrentSemester);

		} else if(parameters.containsKey("semesterId")) {
			// add availibility dates

		} else if(parameters.containsKey("deleteSemesterId")) {
			
			String semesterIdA = parameters.get("deleteSemesterId")[0];
			String semesterIdB = parameters.get("confirmSemesterId")[0];
			
			if(semesterIdA.equals(semesterIdB)) {
				
				DeleteSemesterMessage deleteSemester = Ntro.messages().create(DeleteSemesterMessage.class);
				deleteSemester.setSemesterId(semesterIdA);
				Ntro.messages().send(deleteSemester);

			}else {
				
				throw new UserInputError("Les codes de session ne correspondent pas: '" + semesterIdA + "' Vs '" + semesterIdB + "'");
			}
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


		} else if(parameters.containsKey(Constants.CLOSE_QUEUE_URL_PARAM)) {

			TeacherClosesQueueMessage teacherClosesQueueMessage = Ntro.messages().create(TeacherClosesQueueMessage.class);
			
			String courseId = parameters.get(Constants.CLOSE_QUEUE_URL_PARAM)[0];
			teacherClosesQueueMessage.setCourseId(courseId);
			Ntro.messages().send(teacherClosesQueueMessage);

		} else if(parameters.containsKey(Constants.DELETE_QUEUE_URL_PARAM)) {

			DeleteCourseMessage deleteCourseMessage = Ntro.messages().create(DeleteCourseMessage.class);
			
			String courseId = parameters.get(Constants.DELETE_QUEUE_URL_PARAM)[0];
			deleteCourseMessage.setCourseId(courseId);
			Ntro.messages().send(deleteCourseMessage);
		}
	}

	private static void sendQueuesMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourBackendRequestHandler.class);

	}
		

	private static void sendQueueMessages(Path path, Map<String, String[]> parameters, User user) {
		T.call(AquiletourBackendRequestHandler.class);
		
		if(path.nameCount() >= 1) {
			sendQueueMenuMessages(parameters, user, path.name(0));
			sendAppointmentMessages(parameters, user, path.name(0));
		}
	}

	private static void sendQueueMenuMessages(Map<String, String[]> parameters, User user, String teacherId) {
		T.call(AquiletourBackendRequestHandler.class);
		
		if(parameters.containsKey("openQueueCourseId")) {
			
			String semesterId  = parameters.get("semesterId")[0];
			String courseId  = parameters.get("openQueueCourseId")[0];
			boolean isQueueOpen = false;
			if(parameters.containsKey("isQueueOpen")
					&& parameters.get("isQueueOpen")[0].equals("on")) {
				isQueueOpen = true;
			}

			UpdateIsQueueOpenMessage updateIsQueueOpenMessage = Ntro.messages().create(UpdateIsQueueOpenMessage.class);
			updateIsQueueOpenMessage.setSemesterId(semesterId);
			updateIsQueueOpenMessage.setTeacherId(teacherId);
			updateIsQueueOpenMessage.setCourseId(courseId);
			updateIsQueueOpenMessage.setIsQueueOpen(isQueueOpen);
			Ntro.messages().send(updateIsQueueOpenMessage);
			
		}else if(parameters.containsKey("openQueueGroupId")) {
			
		}
	}

	private static void sendAppointmentMessages(Map<String, String[]> parameters, User user, String queueId) {
		T.call(AquiletourBackendRequestHandler.class);

		if(parameters.containsKey("makeAppointment")) {
			
			CoursePath coursePath = null;
			TaskPath taskPath = null;
			String taskTitle = null;
			
			if(parameters.containsKey("coursePath")) {
				coursePath = CoursePath.fromKey(parameters.get("coursePath")[0]);
			}

			if(parameters.containsKey("taskPath")) {
				taskPath = TaskPath.fromKey(parameters.get("taskPath")[0]);
			}

			if(parameters.containsKey("taskTitle")) {
				taskTitle = parameters.get("taskTitle")[0];
			}

			AddAppointmentMessage addAppointmentMessage = Ntro.messages().create(AddAppointmentMessage.class);
			addAppointmentMessage.setQueueId(queueId);
			addAppointmentMessage.setCoursePath(coursePath);
			addAppointmentMessage.setTaskPath(taskPath);
			addAppointmentMessage.setTaskTitle(taskTitle);
			Ntro.messages().send(addAppointmentMessage);
			
		} else if(parameters.containsKey("deleteAppointment")){
			
			// FIXME TODO: check for permissions
			
			DeleteAppointmentMessage deleteAppointmentMessage = Ntro.messages().create(DeleteAppointmentMessage.class);
			String appointmentId = parameters.get("deleteAppointment")[0];
			deleteAppointmentMessage.setAppointmentId(appointmentId);
			deleteAppointmentMessage.setCourseId(queueId);
			Ntro.messages().send(deleteAppointmentMessage);

		} else if(parameters.containsKey("move")) {

			String appointmentId = parameters.get("move")[0];
			String destinationId = null;
			String beforeOrAfter = null;
			if(parameters.containsKey("after")) {
				destinationId = parameters.get("after")[0];
				beforeOrAfter = "after";
			}else if(parameters.containsKey("before")) {
				destinationId = parameters.get("before")[0];
				beforeOrAfter = "before";
			}

			MoveAppointmentMessage moveAppointmentMessage = Ntro.messages().create(MoveAppointmentMessage.class);
			moveAppointmentMessage.setCourseId(queueId);
			moveAppointmentMessage.setAppointmentId(appointmentId);
			moveAppointmentMessage.setDestinationId(destinationId);
			moveAppointmentMessage.setBeforeOrAfter(beforeOrAfter);
			Ntro.messages().send(moveAppointmentMessage);

		} else if(parameters.containsKey("decrementAppointmentTimes")) {
			
			sendModifyAppointmentTimesMessage(-Constants.APPOINTMENT_TIME_INCREMENT_SECONDS);
			
		} else if(parameters.containsKey("incrementAppointmentTimes")) {

			sendModifyAppointmentTimesMessage(+Constants.APPOINTMENT_TIME_INCREMENT_SECONDS);

		} else if(parameters.containsKey("decreaseAppointmentDuration")) {
			
			sendModifyAppointmentDurationsMessage(-Constants.APPOINTMENT_DURATION_INCREMENT_SECONDS);

		} else if(parameters.containsKey("increaseAppointmentDuration")) {

			sendModifyAppointmentDurationsMessage(+Constants.APPOINTMENT_DURATION_INCREMENT_SECONDS);

		} else if(parameters.containsKey("modifyCommentForQueueId")) {
			
			String comment = parameters.get("comment")[0];

			ModifyAppointmentCommentMessage modifyAppointmentComment = Ntro.messages().create(ModifyAppointmentCommentMessage.class);
			modifyAppointmentComment.setQueueId(queueId);
			modifyAppointmentComment.setComment(comment);
			Ntro.messages().send(modifyAppointmentComment);
		}
	}


	private static void sendCourseMessages(Path path, Map<String, String[]> parameters, SessionData sessionData) throws UserInputError {
		T.call(AquiletourBackendRequestHandler.class);

		if(parameters.containsKey("newSubTaskId")) {

			AddSubTaskMessage addSubTaskMessage = AquiletourRequestHandler.createCourseTaskMessage(AddSubTaskMessage.class,
																							       path,
																							       parameters,
																							       sessionData);

			String parentTaskId = parameters.get("taskId")[0];
			String newSubTaskId = parameters.get("newSubTaskId")[0];
			String taskTitle = parameters.get("taskTitle")[0];
			TaskPath newTaskPath = TaskPath.fromRawPath(parentTaskId + "/" + newSubTaskId);
			
			Task task = new Task();
			task.setPath(newTaskPath);
			task.updateTitle(taskTitle);

			addSubTaskMessage.setParentPath(Path.fromRawPath(parentTaskId));
			addSubTaskMessage.setSubTask(task);
			Ntro.messages().send(addSubTaskMessage);

		}else if(parameters.containsKey("newPreviousTaskId")
				&& parameters.get("newPreviousTaskId")[0].length() > 0) {

			AddPreviousTaskMessage addPreviousTaskMessage = AquiletourRequestHandler.createCourseTaskMessage(AddPreviousTaskMessage.class,
																							             path,
																							             parameters,
																							             sessionData);

			String nextTaskId = parameters.get("taskId")[0];
			String newPreviousTaskId = parameters.get("newPreviousTaskId")[0];
			String taskTitle = parameters.get("previousTaskTitle")[0];
			
			Path nextPath = Path.fromRawPath(nextTaskId);
			Path parentPath = nextPath.parent();

			TaskPath newPreviousTaskPath = TaskPath.fromRawPath(parentPath.toString() + "/" + newPreviousTaskId);

			Task newPreviousTask = new Task();
			newPreviousTask.setPath(newPreviousTaskPath);
			newPreviousTask.updateTitle(taskTitle);

			addPreviousTaskMessage.setNextPath(nextPath);
			addPreviousTaskMessage.setPreviousTask(newPreviousTask);
			
			Ntro.messages().send(addPreviousTaskMessage);

		}else if(parameters.containsKey("linkToPreviousTaskPath")
				&& parameters.get("linkToPreviousTaskPath")[0].length() > 0) {

			AddPreviousTaskMessage addPreviousTaskMessage = AquiletourRequestHandler.createCourseTaskMessage(AddPreviousTaskMessage.class,
																							             path,
																							             parameters,
																							             sessionData);

			String nextTaskId = parameters.get("taskId")[0];
			String existingTaskPathString = parameters.get("linkToPreviousTaskPath")[0];
			TaskPath existingTaskPath = TaskPath.fromRawPath(existingTaskPathString);

			Task previousTask = new Task();
			previousTask.setPath(existingTaskPath);
			
			addPreviousTaskMessage.setNextPath(Path.fromRawPath(nextTaskId));
			addPreviousTaskMessage.setPreviousTask(previousTask);
			
			Ntro.messages().send(addPreviousTaskMessage);

		}else if(parameters.containsKey("newNextTaskId")
				&& parameters.get("newNextTaskId")[0].length() > 0) {

			AddNextTaskMessage addNextTaskMessage = AquiletourRequestHandler.createCourseTaskMessage(AddNextTaskMessage.class,
																							     path,
																							     parameters,
																							     sessionData);

			String previousTaskId = parameters.get("taskId")[0];
			String newNextTaskId = parameters.get("newNextTaskId")[0];
			String taskTitle = parameters.get("nextTaskTitle")[0];
			
			Path previousPath = Path.fromRawPath(previousTaskId);
			Path parentPath = previousPath.parent();

			TaskPath newNextTaskPath = TaskPath.fromRawPath(parentPath.toString() + "/" + newNextTaskId);

			Task newNextTask = new Task();
			newNextTask.setPath(newNextTaskPath);
			newNextTask.updateTitle(taskTitle);

			addNextTaskMessage.setPreviousPath(previousPath);
			addNextTaskMessage.setNextTask(newNextTask);
			
			Ntro.messages().send(addNextTaskMessage);

		}else if(parameters.containsKey("linkToNextTaskPath")
				&& parameters.get("linkToNextTaskPath")[0].length() > 0) {

			AddNextTaskMessage addNextTaskMessage = AquiletourRequestHandler.createCourseTaskMessage(AddNextTaskMessage.class,
																							     path,
																							     parameters,
																							     sessionData);
			String previousTaskId = parameters.get("taskId")[0];
			String existingTaskPathSting = parameters.get("linkToNextTaskPath")[0];
			TaskPath existingTaskPath = TaskPath.fromRawPath(existingTaskPathSting);
			
			Task nextTask = new Task();
			nextTask.setPath(existingTaskPath);

			addNextTaskMessage.setPreviousPath(Path.fromRawPath(previousTaskId));
			addNextTaskMessage.setNextTask(nextTask);

			Ntro.messages().send(addNextTaskMessage);

		}else if(parameters.containsKey("delete")) {

			DeleteTaskMessage deleteTaskMessage = AquiletourRequestHandler.createCourseTaskMessage(DeleteTaskMessage.class,
																							   path,
																							   parameters,
																							   sessionData);
			String taskId = parameters.get("delete")[0];
			Path taskPath = Path.fromFileName(taskId);

			deleteTaskMessage.setTaskToDelete(taskPath);

			Ntro.messages().send(deleteTaskMessage);

		}else if(parameters.containsKey("removePreviousTask")) {

			RemovePreviousTaskMessage removePreviousTaskMessage = AquiletourRequestHandler.createCourseTaskMessage(RemovePreviousTaskMessage.class,
																							                   path,
																							                   parameters,
																							                   sessionData);
			String toRemoveId = parameters.get("removePreviousTask")[0];
			Path toRemovePath = Path.fromFileName(toRemoveId);

			String toModifyId = parameters.get("from")[0];
			Path toModifyPath = Path.fromFileName(toModifyId);
			
			removePreviousTaskMessage.setTaskToModify(toModifyPath);
			removePreviousTaskMessage.setTaskToRemove(toRemovePath);

			Ntro.messages().send(removePreviousTaskMessage);

		}else if(parameters.containsKey("removeSubTask")) {

			RemoveSubTaskMessage removeSubTaskMessage = AquiletourRequestHandler.createCourseTaskMessage(RemoveSubTaskMessage.class,
																							         path,
																							         parameters,
																							         sessionData);

			String toRemoveId = parameters.get("removeSubTask")[0];
			Path toRemovePath = Path.fromFileName(toRemoveId);

			String toModifyId = parameters.get("from")[0];
			Path toModifyPath = Path.fromFileName(toModifyId);
			
			removeSubTaskMessage.setTaskToModify(toModifyPath);
			removeSubTaskMessage.setTaskToRemove(toRemovePath);

			Ntro.messages().send(removeSubTaskMessage);

		}else if(parameters.containsKey("removeNextTask")) {

			RemoveNextTaskMessage removeNextTaskMessage = AquiletourRequestHandler.createCourseTaskMessage(RemoveNextTaskMessage.class,
																							           path,
																							           parameters,
																							           sessionData);

			String toRemoveId = parameters.get("removeNextTask")[0];
			Path toRemovePath = Path.fromFileName(toRemoveId);

			String toModifyId = parameters.get("from")[0];
			Path toModifyPath = Path.fromFileName(toModifyId);
			
			removeNextTaskMessage.setTaskToModify(toModifyPath);
			removeNextTaskMessage.setTaskToRemove(toRemovePath);

			Ntro.messages().send(removeNextTaskMessage);

		}else if(parameters.containsKey("endTimeWeek")) {

			UpdateTaskInfoMessage updateTaskInfo = AquiletourRequestHandler.createCourseTaskMessage(UpdateTaskInfoMessage.class,
																					            path,
																					            parameters,
																					            sessionData);
			String title = parameters.get("taskTitle")[0];
			String description = parameters.get("taskDescription")[0];
			String endTimeWeekText = parameters.get("endTimeWeek")[0];
			String endTimeScheduleItemId = parameters.get("endTimeScheduleItem")[0];
			String endTimeStartOrEnd = parameters.get("endTimeStartOrEnd")[0];

			updateTaskInfo.setTaskTitle(title);
			updateTaskInfo.setTaskDescription(description);
			
			AquiletourDate endTime = null;
			
			if(endTimeWeekText.isEmpty() 
					&& endTimeScheduleItemId.isEmpty()
					&& endTimeStartOrEnd.isEmpty()) {
				
				endTime = AquiletourDate.undefined();
				
			}else {
				int endTimeWeek = 0;
				
				try {

					endTimeWeek = Integer.parseInt(endTimeWeekText);
					
				}catch(NumberFormatException e) {
					throw new UserInputError("SVP entrer un nombre pour la semaine");
				}

				endTime = new CourseDateScheduleItem(endTimeWeek, endTimeScheduleItemId, endTimeStartOrEnd);
				
			}
			
			updateTaskInfo.setEndTime(endTime);
			
			Ntro.messages().send(updateTaskInfo);

		} else if(parameters.containsKey("StudentRegistersRepo")) {

			StudentRegistersRepoMessage studentRegistersRepo = createAquiletourGitMessage(StudentRegistersRepoMessage.class,
																					      path,
																					      parameters,
																					      sessionData);


			Ntro.messages().send(studentRegistersRepo);

		} else if(parameters.containsKey("StudentDeletesRepo")) {

			StudentDeletesRepoMessage studentDeletesRepoMessage = createAquiletourGitMessage(StudentDeletesRepoMessage.class,
																					         path,
																					         parameters,
																					         sessionData);
			Ntro.messages().send(studentDeletesRepoMessage);

		} else if(parameters.containsKey("atomicTaskCompletedId")) {
				
				String atomicTaskId = parameters.get("atomicTaskCompletedId")[0];
				String groupId = parameters.get("groupId")[0];

				AtomicTaskCompletedMessage taskCompletedMessage = AquiletourRequestHandler.createCourseTaskMessage(AtomicTaskCompletedMessage.class,
																						                           path,
																							                       parameters,
             																							           sessionData);
				taskCompletedMessage.setGroupId(groupId);
				taskCompletedMessage.setCompletion(new DefaultCompletion());
				taskCompletedMessage.setAtomicTaskId(atomicTaskId);
				
				Ntro.messages().send(taskCompletedMessage);
			}
	}

	static <MSG extends AquiletourGitMessage> MSG createAquiletourGitMessage(Class<MSG> messageClass, 
			                                                                 Path path, 
			                                                                 Map<String, String[]> parameters,
			                                                                 SessionData sessionData) {
		T.call(AquiletourBackendRequestHandler.class);

		MSG message = createAtomicTaskMessage(messageClass,
										      path,
											  parameters,
											  sessionData);

		String studentId = null;
		String groupId = null;
		Path repoPath = null;
		String repoUrl = null;
		
		if(parameters.containsKey(Constants.USER_URL_PARAM)) {
			studentId = parameters.get(Constants.USER_URL_PARAM)[0];
		}else if(parameters.containsKey("studentId")) {
			studentId = parameters.get("studentId")[0];
		}
		
		if(parameters.containsKey(Constants.GROUP_URL_PARAM)) {
			groupId = parameters.get(Constants.GROUP_URL_PARAM)[0];
		}else if(parameters.containsKey("groupId")) {
			groupId = parameters.get("groupId")[0];
		}
		
		if(parameters.containsKey("repoPath")) {
			repoPath = Path.fromRawPath(parameters.get("repoPath")[0]);
		}

		if(parameters.containsKey("repoUrl")) {
			repoUrl = parameters.get("repoUrl")[0];
		}

		message.setStudentId(studentId);
		message.setGroupId(groupId);
		message.setRepoPath(repoPath);
		message.setRepoUrl(repoUrl);
		
		return message;
	}

	static <MSG extends AtomicTaskMessage> MSG createAtomicTaskMessage(Class<MSG> messageClass, 
			                                                           Path path, 
			                                                           Map<String, String[]> parameters,
			                                                           SessionData sessionData) {
		T.call(AquiletourBackendRequestHandler.class);

		MSG message = AquiletourRequestHandler.createCourseTaskMessage(messageClass,
																   path,
																   parameters,
																   sessionData);
		
		if(parameters.containsKey("atomicTaskId")) {

			message.setAtomicTaskId(parameters.get("atomicTaskId")[0]);
		}

		return message;
	}


	private static void sendModifyAppointmentDurationsMessage(int incrementSeconds) {
		T.call(AquiletourBackendRequestHandler.class);

		ModifyAppointmentDurationsMessage modifyAppointmentDurations = Ntro.messages().create(ModifyAppointmentDurationsMessage.class);
		modifyAppointmentDurations.setDurationIncrementSeconds(incrementSeconds);
		Ntro.messages().send(modifyAppointmentDurations);
	}

	private static void sendModifyAppointmentTimesMessage(int incrementSeconds) {
		T.call(AquiletourBackendRequestHandler.class);

		ModifyAppointmentTimesMessage modifyAppointmentTimes = Ntro.messages().create(ModifyAppointmentTimesMessage.class);
		modifyAppointmentTimes.setTimeIncrementSeconds(incrementSeconds);
		Ntro.messages().send(modifyAppointmentTimes);
	}

}
