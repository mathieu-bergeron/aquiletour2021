package ca.aquiletour.web;

import java.util.Map;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.UserInitiatesLoginMessage;
import ca.aquiletour.core.messages.UserLogsOutMessage;
import ca.aquiletour.core.messages.UserSendsLoginCodeMessage;
import ca.aquiletour.core.messages.git.RegisterRepo;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course.messages.AddNextTaskMessage;
import ca.aquiletour.core.pages.course.messages.AddPreviousTaskMessage;
import ca.aquiletour.core.pages.course.messages.AddSubTaskMessage;
import ca.aquiletour.core.pages.course.messages.DeleteTaskMessage;
import ca.aquiletour.core.pages.course.messages.RemoveNextTaskMessage;
import ca.aquiletour.core.pages.course.messages.RemovePreviousTaskMessage;
import ca.aquiletour.core.pages.course.messages.RemoveSubTaskMessage;
import ca.aquiletour.core.pages.course.models.Task;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboards.teacher.messages.DeleteCourseMessage;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.MoveAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
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

		} else if(path.startsWith(Constants.LOGOUT_URL_SEGMENT)) {

			Ntro.backendService().sendMessageToBackend(Ntro.messages().create(UserLogsOutMessage.class));

		} else if(path.startsWith(Constants.DASHBOARD_URL_SEGMENT)) {

			sendDashboardMessages(path.subPath(1), parameters, (User) Ntro.userService().user());

		} else if(path.startsWith(Constants.QUEUES_URL_SEGMENT)) {
			
			sendQueuesMessages(path.subPath(1), parameters);

		}else if(path.startsWith(Constants.QUEUE_URL_SEGMENT)) {
			
			sendQueueMessages(path.subPath(1), parameters , (User) Ntro.userService().user());

		}else if(path.startsWith(Constants.COURSE_URL_SEGMENT)) {
			
			sendCourseMessages(path.subPath(1), parameters , (User) Ntro.userService().user());
			
		}else if(path.startsWith(Constants.LOGIN_URL_SEGMENT)) {
			
			sendLoginMessages(path.subPath(1), parameters);
	
		}else if(path.startsWith(Constants.HOME_URL_SEGMENT)) {

			sendHomeMessages(path.subPath(1), parameters);
			
		}else if(path.startsWith(Constants.GIT_PROGRESS_URL_SEGMENT)) {

			sendGitMessages(path.subPath(1), parameters);
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

			String courseTitle = parameters.get("addQueue")[0];
			String courseId = courseTitle;  // FIXME: we need a real id

			AddCourseMessage addCourseMessage = Ntro.messages().create(AddCourseMessage.class);
			addCourseMessage.setCourse(new CourseSummary(courseTitle, courseId));
			addCourseMessage.setUser(user);
			Ntro.backendService().sendMessageToBackend(addCourseMessage);

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

	private static void sendCourseMessages(Path path, Map<String, String[]> parameters, User user) {
		T.call(AquiletourBackendRequestHandler.class);
		
		if(path.nameCount() >= 1) {
			sendTaskMessages(parameters, user, path.name(0));
		}
	}

	private static void sendTaskMessages(Map<String, String[]> parameters, User user, String courseId) {
		T.call(AquiletourBackendRequestHandler.class);
		
		if(parameters.containsKey("newSubTask")) {
			
			String parentTaskId = parameters.get("taskId")[0];
			String newSubTaskId = parameters.get("newSubTask")[0];
			String taskTitle = newSubTaskId;
			Path newTaskPath = new Path(parentTaskId + "/" + newSubTaskId);

			Task task = new Task();
			task.setPath(newTaskPath);
			task.setTitle(taskTitle);

			AddSubTaskMessage addSubTaskMessage = Ntro.messages().create(AddSubTaskMessage.class);
			addSubTaskMessage.setCourseId(courseId);
			addSubTaskMessage.setParentPath(new Path(parentTaskId));
			addSubTaskMessage.setSubTask(task);
			Ntro.messages().send(addSubTaskMessage);

		}else if(parameters.containsKey("newPreviousTask")
				&& parameters.get("newPreviousTask")[0].length() > 0) {

			String nextTaskId = parameters.get("taskId")[0];
			String newPreviousTaskId = parameters.get("newPreviousTask")[0];
			String taskTitle = newPreviousTaskId;
			
			Path nextPath = new Path(nextTaskId);
			Path parentPath = nextPath.parent();

			Path newPreviousTaskPath = new Path(parentPath.toString() + "/" + newPreviousTaskId);

			Task newPreviousTask = new Task();
			newPreviousTask.setPath(newPreviousTaskPath);
			newPreviousTask.setTitle(taskTitle);

			AddPreviousTaskMessage addPreviousTaskMessage = Ntro.messages().create(AddPreviousTaskMessage.class);
			addPreviousTaskMessage.setCourseId(courseId);
			addPreviousTaskMessage.setNextPath(nextPath);
			addPreviousTaskMessage.setPreviousTask(newPreviousTask);
			
			Ntro.messages().send(addPreviousTaskMessage);

		}else if(parameters.containsKey("previousTask")) {

			String nextTaskId = parameters.get("taskId")[0];
			String existingTaskId = parameters.get("previousTask")[0];
			Path existingTaskPath = new Path(existingTaskId);

			Task previousTask = new Task();
			previousTask.setPath(existingTaskPath);
			
			AddPreviousTaskMessage addPreviousTaskMessage = Ntro.messages().create(AddPreviousTaskMessage.class);
			addPreviousTaskMessage.setCourseId(courseId);
			addPreviousTaskMessage.setNextPath(new Path(nextTaskId));
			addPreviousTaskMessage.setPreviousTask(previousTask);
			
			Ntro.messages().send(addPreviousTaskMessage);

		}else if(parameters.containsKey("newNextTask")
				&& parameters.get("newNextTask")[0].length() > 0) {

			String previousTaskId = parameters.get("taskId")[0];
			String newNextTaskId = parameters.get("newNextTask")[0];
			String taskTitle = newNextTaskId;
			
			Path previousPath = new Path(previousTaskId);
			Path parentPath = previousPath.parent();

			Path newNextTaskPath = new Path(parentPath.toString() + "/" + newNextTaskId);

			Task newNextTask = new Task();
			newNextTask.setPath(newNextTaskPath);
			newNextTask.setTitle(taskTitle);

			AddNextTaskMessage addNextTaskMessage = Ntro.messages().create(AddNextTaskMessage.class);
			addNextTaskMessage.setCourseId(courseId);
			addNextTaskMessage.setPreviousPath(previousPath);
			addNextTaskMessage.setNextTask(newNextTask);
			
			Ntro.messages().send(addNextTaskMessage);

		}else if(parameters.containsKey("nextTask")) {

			String previousTaskId = parameters.get("taskId")[0];
			String existingTaskId = parameters.get("nextTask")[0];
			Path existingTaskPath = new Path(existingTaskId);
			
			Task nextTask = new Task();
			nextTask.setPath(existingTaskPath);

			AddNextTaskMessage addNextTaskMessage = Ntro.messages().create(AddNextTaskMessage.class);
			addNextTaskMessage.setCourseId(courseId);
			addNextTaskMessage.setPreviousPath(new Path(previousTaskId));
			addNextTaskMessage.setNextTask(nextTask);

			Ntro.messages().send(addNextTaskMessage);

		}else if(parameters.containsKey("delete")) {

			String taskId = parameters.get("delete")[0];
			Path taskPath = new Path();
			taskPath.parseFileName(taskId);
			
			DeleteTaskMessage deleteTaskMessage = Ntro.messages().create(DeleteTaskMessage.class);
			deleteTaskMessage.setCourseId(courseId);
			deleteTaskMessage.setTaskToDelete(taskPath);

			Ntro.messages().send(deleteTaskMessage);

		}else if(parameters.containsKey("removePreviousTask")) {

			String toRemoveId = parameters.get("removePreviousTask")[0];
			Path toRemovePath = new Path();
			toRemovePath.parseFileName(toRemoveId);

			String toModifyId = parameters.get("from")[0];
			Path toModifyPath = new Path();
			toModifyPath.parseFileName(toModifyId);
			
			RemovePreviousTaskMessage removePreviousTaskMessage = Ntro.messages().create(RemovePreviousTaskMessage.class);
			removePreviousTaskMessage.setCourseId(courseId);
			removePreviousTaskMessage.setTaskToModify(toModifyPath);
			removePreviousTaskMessage.setTaskToRemove(toRemovePath);

			Ntro.messages().send(removePreviousTaskMessage);

		}else if(parameters.containsKey("removeSubTask")) {

			String toRemoveId = parameters.get("removeSubTask")[0];
			Path toRemovePath = new Path();
			toRemovePath.parseFileName(toRemoveId);

			String toModifyId = parameters.get("from")[0];
			Path toModifyPath = new Path();
			toModifyPath.parseFileName(toModifyId);
			
			RemoveSubTaskMessage removeSubTaskMessage = Ntro.messages().create(RemoveSubTaskMessage.class);
			removeSubTaskMessage.setCourseId(courseId);
			removeSubTaskMessage.setTaskToModify(toModifyPath);
			removeSubTaskMessage.setTaskToRemove(toRemovePath);

			Ntro.messages().send(removeSubTaskMessage);

		}else if(parameters.containsKey("removeNextTask")) {

			String toRemoveId = parameters.get("removeNextTask")[0];
			Path toRemovePath = new Path();
			toRemovePath.parseFileName(toRemoveId);

			String toModifyId = parameters.get("from")[0];
			Path toModifyPath = new Path();
			toModifyPath.parseFileName(toModifyId);
			
			RemoveNextTaskMessage removeNextTaskMessage = Ntro.messages().create(RemoveNextTaskMessage.class);
			removeNextTaskMessage.setCourseId(courseId);
			removeNextTaskMessage.setTaskToModify(toModifyPath);
			removeNextTaskMessage.setTaskToRemove(toRemovePath);

			Ntro.messages().send(removeNextTaskMessage);

		} else if(parameters.containsKey("registerGitRepo")) {
				
				String taskId = parameters.get("taskId")[0];
				String repoUrl = parameters.get("registerGitRepo")[0];
				RegisterRepo registerRepoMessage = Ntro.messages().create(RegisterRepo.class);
				registerRepoMessage.setCourseId(courseId);
				registerRepoMessage.setStudentId(Ntro.userService().user().getId());
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
