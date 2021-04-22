package ca.aquiletour.web;

import java.util.Map;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.UserInitiatesLoginMessage;
import ca.aquiletour.core.messages.UserLogsOutMessage;
import ca.aquiletour.core.messages.UserSendsLoginCodeMessage;
import ca.aquiletour.core.messages.git.RegisterRepo;
import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.dates.SemesterDate;
import ca.aquiletour.core.models.dates.SemesterWeek;
import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course.messages.AddNextTaskMessage;
import ca.aquiletour.core.pages.course.messages.AddPreviousTaskMessage;
import ca.aquiletour.core.pages.course.messages.AddSubTaskMessage;
import ca.aquiletour.core.pages.course.messages.DeleteTaskMessage;
import ca.aquiletour.core.pages.course.messages.RemoveNextTaskMessage;
import ca.aquiletour.core.pages.course.messages.RemovePreviousTaskMessage;
import ca.aquiletour.core.pages.course.messages.RemoveSubTaskMessage;
import ca.aquiletour.core.pages.course_list.messages.AddCourseMessage;
import ca.aquiletour.core.pages.course_list.models.CourseItem;
import ca.aquiletour.core.pages.dashboards.teacher.messages.DeleteCourseMessage;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.MoveAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.aquiletour.core.pages.semester_list.messages.AddSemesterWeekMessage;
import ca.aquiletour.core.pages.semester_list.messages.SelectCurrentSemester;
import ca.aquiletour.core.pages.semester_list.models.CourseGroup;
import ca.aquiletour.core.pages.semester_list.messages.AddScheduleItemMessage;
import ca.aquiletour.core.pages.semester_list.messages.AddSemesterMessage;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.backend.UserInputError;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.models.NtroDayOfWeek;
import ca.ntro.models.NtroTimeOfDay;
import ca.ntro.services.Ntro;

public class AquiletourBackendRequestHandler {
	
	public static void sendMessages(NtroContext<User, SessionData> context, Path path, Map<String, String[]> parameters) throws UserInputError {
		T.call(AquiletourBackendRequestHandler.class);

		if(parameters.containsKey("userId")) {
			
			UserInitiatesLoginMessage userInitiatesLoginMessage = Ntro.messages().create(UserInitiatesLoginMessage.class);
			userInitiatesLoginMessage.setProvidedId(parameters.get("userId")[0]);
			Ntro.backendService().sendMessageToBackend(userInitiatesLoginMessage);

		} else if(parameters.containsKey("loginCode")) {
			
			UserSendsLoginCodeMessage userSendsLoginCodeMessage = Ntro.messages().create(UserSendsLoginCodeMessage.class);
			userSendsLoginCodeMessage.setLoginCode(parameters.get("loginCode")[0]);
			Ntro.backendService().sendMessageToBackend(userSendsLoginCodeMessage);

		} else if(path.startsWith(Constants.LOGOUT_URL_SEGMENT)) {

			Ntro.backendService().sendMessageToBackend(Ntro.messages().create(UserLogsOutMessage.class));

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
			
		}else if(path.startsWith(Constants.GIT_PROGRESS_URL_SEGMENT)) {

			sendGitMessages(path.subPath(1), parameters);

		}else if(path.startsWith(Constants.SEMESTER_LIST_URL_SEGMENT)) {

			sendSemesterListMessages(path.subPath(1), parameters);

		}else if(path.startsWith(Constants.COURSE_LIST_URL_SEGMENT)) {

			sendCourseListMessages(path.subPath(1), parameters, context.user());
		}
	}

	private static void sendCourseListMessages(Path subPath, Map<String, String[]> parameters, User user) {
		T.call(AquiletourBackendRequestHandler.class);
		
		if(parameters.containsKey("newCourseId")) {
			
			String semesterId = parameters.get("semesterId")[0];
			String courseTitle = parameters.get("courseTitle")[0];
			String courseId = parameters.get("newCourseId")[0];
			
			CourseItem courseDescription = new CourseItem(user.getId(), semesterId, courseId, courseTitle);
			
			AddCourseMessage addCourseMessage = Ntro.messages().create(AddCourseMessage.class);
			addCourseMessage.setSemesterId(semesterId);
			addCourseMessage.setCourseDescription(courseDescription);
			Ntro.backendService().sendMessageToBackend(addCourseMessage);
		}

		else if(parameters.containsKey("taskId")) {
			
			String teacherId = user.getId();
			String semesterId = parameters.get("semesterId")[0];
			String courseId = parameters.get("courseId")[0];
			
			String taskId = parameters.get("taskId")[0];
			String taskTitle = parameters.get("taskId")[0];
			
			Path parentPath = new Path("/");
			Path taskPath = new Path("/" + taskId);

			Task task = new Task();
			task.setTitle(taskTitle);
			task.setPath(taskPath);
			
			AddSubTaskMessage addSubTaskMessage = Ntro.messages().create(AddSubTaskMessage.class);
			addSubTaskMessage.setTeacherId(teacherId);
			addSubTaskMessage.setSemesterId(semesterId);
			addSubTaskMessage.setCourseId(courseId);
			addSubTaskMessage.setParentPath(parentPath);
			addSubTaskMessage.setSubTask(task);
			Ntro.backendService().sendMessageToBackend(addSubTaskMessage);
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
			
			SemesterWeek semesterWeek = new SemesterWeek();
			
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
			Ntro.backendService().sendMessageToBackend(addScheduleItemMessage);
			
		} else if(parameters.containsKey("currentSemesterId")) {

			String semesterId = parameters.get("currentSemesterId")[0];
			boolean currentSemester = false;
			
			if(parameters.containsKey("ifSemesterSelected")) {
				currentSemester = true;
			}

			SelectCurrentSemester selectCurrentSemester = Ntro.messages().create(SelectCurrentSemester.class);
			selectCurrentSemester.setSemesterId(semesterId);
			selectCurrentSemester.setCurrentSemester(currentSemester);
			Ntro.messages().send(selectCurrentSemester);

		} else if(parameters.containsKey("semesterId")) {
			// add availibility dates
		}
	}
	
	
	
	private static void sendGitMessages(Path subPath, Map<String, String[]> parameters) {
		T.call(AquiletourBackendRequestHandler.class);
		
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
			Ntro.backendService().sendMessageToBackend(teacherClosesQueueMessage);

		} else if(parameters.containsKey(Constants.DELETE_QUEUE_URL_PARAM)) {

			DeleteCourseMessage deleteCourseMessage = Ntro.messages().create(DeleteCourseMessage.class);
			
			String courseId = parameters.get(Constants.DELETE_QUEUE_URL_PARAM)[0];
			deleteCourseMessage.setCourseId(courseId);
			Ntro.backendService().sendMessageToBackend(deleteCourseMessage);
		}
	}

	private static void sendQueuesMessages(Path path, Map<String, String[]> parameters) {
		T.call(AquiletourBackendRequestHandler.class);

	}
		

	private static void sendQueueMessages(Path path, Map<String, String[]> parameters, User user) {
		T.call(AquiletourBackendRequestHandler.class);
		
		if(path.nameCount() >= 1) {
			sendAppointmentMessages(parameters, user, path.name(0));
		}
	}

	private static void sendCourseMessages(Path path, Map<String, String[]> parameters, SessionData sessionData) {
		T.call(AquiletourBackendRequestHandler.class);

		if(parameters.containsKey("newSubTask")) {

			AddSubTaskMessage addSubTaskMessage = AquiletourRequestHandler.createCourseMessage(AddSubTaskMessage.class,
																							   path,
																							   parameters,
																							   sessionData);

			String parentTaskId = parameters.get("taskId")[0];
			String newSubTaskId = parameters.get("newSubTask")[0];
			String taskTitle = newSubTaskId;
			Path newTaskPath = new Path(parentTaskId + "/" + newSubTaskId);
			
			Task task = new Task();
			task.setPath(newTaskPath);
			task.setTitle(taskTitle);

			addSubTaskMessage.setParentPath(new Path(parentTaskId));
			addSubTaskMessage.setSubTask(task);
			Ntro.messages().send(addSubTaskMessage);

		}else if(parameters.containsKey("newPreviousTask")
				&& parameters.get("newPreviousTask")[0].length() > 0) {

			AddPreviousTaskMessage addPreviousTaskMessage = AquiletourRequestHandler.createCourseMessage(AddPreviousTaskMessage.class,
																							             path,
																							             parameters,
																							             sessionData);

			String nextTaskId = parameters.get("taskId")[0];
			String newPreviousTaskId = parameters.get("newPreviousTask")[0];
			String taskTitle = newPreviousTaskId;
			
			Path nextPath = new Path(nextTaskId);
			Path parentPath = nextPath.parent();

			Path newPreviousTaskPath = new Path(parentPath.toString() + "/" + newPreviousTaskId);

			Task newPreviousTask = new Task();
			newPreviousTask.setPath(newPreviousTaskPath);
			newPreviousTask.setTitle(taskTitle);

			addPreviousTaskMessage.setNextPath(nextPath);
			addPreviousTaskMessage.setPreviousTask(newPreviousTask);
			
			Ntro.messages().send(addPreviousTaskMessage);

		}else if(parameters.containsKey("previousTask")) {

			AddPreviousTaskMessage addPreviousTaskMessage = AquiletourRequestHandler.createCourseMessage(AddPreviousTaskMessage.class,
																							             path,
																							             parameters,
																							             sessionData);

			String nextTaskId = parameters.get("taskId")[0];
			String existingTaskId = parameters.get("previousTask")[0];
			Path existingTaskPath = new Path(existingTaskId);

			Task previousTask = new Task();
			previousTask.setPath(existingTaskPath);
			
			addPreviousTaskMessage.setNextPath(new Path(nextTaskId));
			addPreviousTaskMessage.setPreviousTask(previousTask);
			
			Ntro.messages().send(addPreviousTaskMessage);

		}else if(parameters.containsKey("newNextTask")
				&& parameters.get("newNextTask")[0].length() > 0) {

			AddNextTaskMessage addNextTaskMessage = AquiletourRequestHandler.createCourseMessage(AddNextTaskMessage.class,
																							     path,
																							     parameters,
																							     sessionData);

			String previousTaskId = parameters.get("taskId")[0];
			String newNextTaskId = parameters.get("newNextTask")[0];
			String taskTitle = newNextTaskId;
			
			Path previousPath = new Path(previousTaskId);
			Path parentPath = previousPath.parent();

			Path newNextTaskPath = new Path(parentPath.toString() + "/" + newNextTaskId);

			Task newNextTask = new Task();
			newNextTask.setPath(newNextTaskPath);
			newNextTask.setTitle(taskTitle);

			addNextTaskMessage.setPreviousPath(previousPath);
			addNextTaskMessage.setNextTask(newNextTask);
			
			Ntro.messages().send(addNextTaskMessage);

		}else if(parameters.containsKey("nextTask")) {

			AddNextTaskMessage addNextTaskMessage = AquiletourRequestHandler.createCourseMessage(AddNextTaskMessage.class,
																							     path,
																							     parameters,
																							     sessionData);
			String previousTaskId = parameters.get("taskId")[0];
			String existingTaskId = parameters.get("nextTask")[0];
			Path existingTaskPath = new Path(existingTaskId);
			
			Task nextTask = new Task();
			nextTask.setPath(existingTaskPath);

			addNextTaskMessage.setPreviousPath(new Path(previousTaskId));
			addNextTaskMessage.setNextTask(nextTask);

			Ntro.messages().send(addNextTaskMessage);

		}else if(parameters.containsKey("delete")) {

			DeleteTaskMessage deleteTaskMessage = AquiletourRequestHandler.createCourseMessage(DeleteTaskMessage.class,
																							   path,
																							   parameters,
																							   sessionData);
			String taskId = parameters.get("delete")[0];
			Path taskPath = new Path();
			taskPath.parseFileName(taskId);
			
			deleteTaskMessage.setTaskToDelete(taskPath);

			Ntro.messages().send(deleteTaskMessage);

		}else if(parameters.containsKey("removePreviousTask")) {

			RemovePreviousTaskMessage removePreviousTaskMessage = AquiletourRequestHandler.createCourseMessage(RemovePreviousTaskMessage.class,
																							                   path,
																							                   parameters,
																							                   sessionData);
			String toRemoveId = parameters.get("removePreviousTask")[0];
			Path toRemovePath = new Path();
			toRemovePath.parseFileName(toRemoveId);

			String toModifyId = parameters.get("from")[0];
			Path toModifyPath = new Path();
			toModifyPath.parseFileName(toModifyId);
			
			removePreviousTaskMessage.setTaskToModify(toModifyPath);
			removePreviousTaskMessage.setTaskToRemove(toRemovePath);

			Ntro.messages().send(removePreviousTaskMessage);

		}else if(parameters.containsKey("removeSubTask")) {

			RemoveSubTaskMessage removeSubTaskMessage = AquiletourRequestHandler.createCourseMessage(RemoveSubTaskMessage.class,
																							         path,
																							         parameters,
																							         sessionData);

			String toRemoveId = parameters.get("removeSubTask")[0];
			Path toRemovePath = new Path();
			toRemovePath.parseFileName(toRemoveId);

			String toModifyId = parameters.get("from")[0];
			Path toModifyPath = new Path();
			toModifyPath.parseFileName(toModifyId);
			
			removeSubTaskMessage.setTaskToModify(toModifyPath);
			removeSubTaskMessage.setTaskToRemove(toRemovePath);

			Ntro.messages().send(removeSubTaskMessage);

		}else if(parameters.containsKey("removeNextTask")) {

			RemoveNextTaskMessage removeNextTaskMessage = AquiletourRequestHandler.createCourseMessage(RemoveNextTaskMessage.class,
																							           path,
																							           parameters,
																							           sessionData);

			String toRemoveId = parameters.get("removeNextTask")[0];
			Path toRemovePath = new Path();
			toRemovePath.parseFileName(toRemoveId);

			String toModifyId = parameters.get("from")[0];
			Path toModifyPath = new Path();
			toModifyPath.parseFileName(toModifyId);
			
			removeNextTaskMessage.setTaskToModify(toModifyPath);
			removeNextTaskMessage.setTaskToRemove(toRemovePath);

			Ntro.messages().send(removeNextTaskMessage);

		} else if(parameters.containsKey("registerGitRepo")) {
				
				String taskId = parameters.get("taskId")[0];
				String repoUrl = parameters.get("registerGitRepo")[0];
				RegisterRepo registerRepoMessage = Ntro.messages().create(RegisterRepo.class);
				registerRepoMessage.setCourseId("FIXME");
				registerRepoMessage.setStudentId(Ntro.currentUser().getId());
				registerRepoMessage.setSemesterId("H2021"); // FIXME
				registerRepoMessage.setGroupId("01"); // FIXME
				registerRepoMessage.setRepoUrl(repoUrl);
				registerRepoMessage.setExercisePath(taskId);

				Ntro.backendService().sendMessageToBackend(registerRepoMessage);
			}
	}

	private static void sendAppointmentMessages(Map<String, String[]> parameters, User user, String courseId) {
		T.call(AquiletourBackendRequestHandler.class);

		if(parameters.containsKey("makeAppointment")) {
			
			// FIXME: we need a Ntro service for dates
			/*
			Calendar rightNow = Calendar.getInstance();
			int hour = rightNow.get(Calendar.HOUR_OF_DAY);
			int minute = rightNow.get(Calendar.MINUTE);
			String time = hour + ":" + minute;
			 */
			AddAppointmentMessage addAppointmentMessage = Ntro.messages().create(AddAppointmentMessage.class);
			addAppointmentMessage.setCourseId(courseId);
			Ntro.backendService().sendMessageToBackend(addAppointmentMessage);
			
		} else if(parameters.containsKey("deleteAppointment")){
			
			// FIXME TODO: check for permissions
			
			DeleteAppointmentMessage deleteAppointmentMessage = Ntro.messages().create(DeleteAppointmentMessage.class);
			String appointmentId = parameters.get("deleteAppointment")[0];
			deleteAppointmentMessage.setAppointmentId(appointmentId);
			deleteAppointmentMessage.setCourseId(courseId);
			Ntro.backendService().sendMessageToBackend(deleteAppointmentMessage);

		} else if(parameters.containsKey("move")) { // /billetterie/IdDuCours?move=Id1&before=Id2

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
			moveAppointmentMessage.setCourseId(courseId);
			moveAppointmentMessage.setAppointmentId(appointmentId);
			moveAppointmentMessage.setDestinationId(destinationId);
			moveAppointmentMessage.setBeforeOrAfter(beforeOrAfter);
			Ntro.messages().send(moveAppointmentMessage);

		} 
	}
}
