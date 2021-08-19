package ca.aquiletour.server.backend;


import ca.aquiletour.core.messages.AddStudentCsvMessage;
import ca.aquiletour.core.messages.InitializeSessionMessage;
import ca.aquiletour.core.messages.git.OnClone;
import ca.aquiletour.core.messages.git.OnExerciseCompleted;
import ca.aquiletour.core.messages.git.OnNewCommits;
import ca.aquiletour.core.messages.queue.UpdateIsQueueOpenMessage;
import ca.aquiletour.core.messages.queue.UpdateQueueInfoMessage;
import ca.aquiletour.core.messages.time.TimePassesMessage;
import ca.aquiletour.core.messages.user.ItsNotMeMessage;
import ca.aquiletour.core.messages.user.RenameUserMessage;
import ca.aquiletour.core.messages.user.ToggleAdminModeMessage;
import ca.aquiletour.core.messages.user.ToggleStudentModeMessage;
import ca.aquiletour.core.messages.user.UpdateUserInfoMessage;
import ca.aquiletour.core.messages.user.UserChangesPasswordMessage;
import ca.aquiletour.core.messages.user.UserInitiatesLoginMessage;
import ca.aquiletour.core.messages.user.UserIsActiveMessage;
import ca.aquiletour.core.messages.user.UserLogsOutMessage;
import ca.aquiletour.core.messages.user.UserSendsLoginCodeMessage;
import ca.aquiletour.core.messages.user.UserSendsPasswordMessage;
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
import ca.aquiletour.core.pages.course_list.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboard.teacher.messages.DeleteCourseMessage;
import ca.aquiletour.core.pages.queue.messages.ModifyAppointmentDurationsMessage;
import ca.aquiletour.core.pages.queue.messages.ModifyAppointmentTimesMessage;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.student.messages.ModifyAppointmentCommentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.MoveAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.aquiletour.core.pages.semester_list.messages.AddScheduleItemMessage;
import ca.aquiletour.core.pages.semester_list.messages.AddSemesterMessage;
import ca.aquiletour.core.pages.semester_list.messages.AddSemesterWeekMessage;
import ca.aquiletour.core.pages.semester_list.messages.DeleteSemesterMessage;
import ca.aquiletour.core.pages.semester_list.messages.SetActiveSemesterMessage;
import ca.aquiletour.server.backend.course.AddNextTaskHandler;
import ca.aquiletour.server.backend.course.AddPreviousTaskHandler;
import ca.aquiletour.server.backend.course.AddSubTaskHandler;
import ca.aquiletour.server.backend.course.DeleteTaskHandler;
import ca.aquiletour.server.backend.course.RemoveNextTaskHandler;
import ca.aquiletour.server.backend.course.RemovePreviousTaskHandler;
import ca.aquiletour.server.backend.course.RemoveSubTaskHandler;
import ca.aquiletour.server.backend.course.AtomicTaskCompletedHandler;
import ca.aquiletour.server.backend.course.UpdateTaskInfoHandler;
import ca.aquiletour.server.backend.course_list.AddCourseHandler;
import ca.aquiletour.server.backend.dashboard.DeleteCourseHandler;
import ca.aquiletour.server.backend.git.OnCloneHandler;
import ca.aquiletour.server.backend.git.OnExerciseCompletedHandler;
import ca.aquiletour.server.backend.git.OnNewCommitsHandler;
import ca.aquiletour.server.backend.git.StudentDeletesRepoHandler;
import ca.aquiletour.server.backend.git.StudentRegistersRepoHandler;
import ca.aquiletour.server.backend.login.InitializeSessionHandler;
import ca.aquiletour.server.backend.login.ItsNotMeHandler;
import ca.aquiletour.server.backend.login.UserInitiatesLoginHandler;
import ca.aquiletour.server.backend.login.UserSendsLoginCodeHandler;
import ca.aquiletour.server.backend.login.UserSendsPasswordHandler;
import ca.aquiletour.server.backend.login.UserLogsOutHandler;
import ca.aquiletour.server.backend.queue.AddAppointmentHandler;
import ca.aquiletour.server.backend.queue.DeleteAppointmentHandler;
import ca.aquiletour.server.backend.queue.ModifyAppointmentCommentHandler;
import ca.aquiletour.server.backend.queue.ModifyAppointmentDurationsHandler;
import ca.aquiletour.server.backend.queue.ModifyAppointmentTimesHandler;
import ca.aquiletour.server.backend.queue.MoveAppointmentHandler;
import ca.aquiletour.server.backend.queue.TeacherClosesQueueHandler;
import ca.aquiletour.server.backend.queue.TeacherUsesQueueHandler;
import ca.aquiletour.server.backend.queue.UpdateIsQueueOpenHandler;
import ca.aquiletour.server.backend.queue.UpdateQueueInfoHandler;
import ca.aquiletour.server.backend.semester_list.AddScheduleItemHandler;
import ca.aquiletour.server.backend.semester_list.AddSemesterHandler;
import ca.aquiletour.server.backend.semester_list.AddSemesterWeekHandler;
import ca.aquiletour.server.backend.semester_list.DeleteSemesterHandler;
import ca.aquiletour.server.backend.semester_list.SetActiveSemesterHanlder;
import ca.aquiletour.server.backend.time.TimePassesHandler;
import ca.aquiletour.server.backend.users.AddStudentCsvHandler;
import ca.aquiletour.server.backend.users.RenameUserHandler;
import ca.aquiletour.server.backend.users.ToggleAdminModeHandler;
import ca.aquiletour.server.backend.users.ToggleStudentModeHandler;
import ca.aquiletour.server.backend.users.UpdateUserInfoHandler;
import ca.aquiletour.server.backend.users.UserChangesPasswordHandler;
import ca.aquiletour.server.backend.users.UserIsActiveHandler;
import ca.aquiletour.server.registered_sockets.RegisteredSocketsSockJS;
import ca.ntro.backend.BackendError;
import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.services.BackendServiceServer;
import ca.ntro.messages.NtroMessage;
import ca.ntro.services.Ntro;
import ca.ntro.users.NtroUser;

public class AquiletourBackendService extends BackendServiceServer {
	
	@Override
	protected void addBackendMessageHandlers() {

		addBackendMessageHandler(AddCourseMessage.class, new AddCourseHandler());
		addBackendMessageHandler(AddAppointmentMessage.class, new AddAppointmentHandler());
		addBackendMessageHandler(AddStudentCsvMessage.class, new AddStudentCsvHandler());
		addBackendMessageHandler(TeacherUsesQueueMessage.class, new TeacherUsesQueueHandler());
		addBackendMessageHandler(DeleteCourseMessage.class, new DeleteCourseHandler());
		addBackendMessageHandler(DeleteAppointmentMessage.class, new DeleteAppointmentHandler());
		addBackendMessageHandler(MoveAppointmentMessage.class, new MoveAppointmentHandler());
		addBackendMessageHandler(TeacherClosesQueueMessage.class, new TeacherClosesQueueHandler());
		addBackendMessageHandler(UserInitiatesLoginMessage.class, new UserInitiatesLoginHandler());
		addBackendMessageHandler(InitializeSessionMessage.class, new InitializeSessionHandler());
		addBackendMessageHandler(UserSendsLoginCodeMessage.class, new UserSendsLoginCodeHandler());
		addBackendMessageHandler(UserLogsOutMessage.class, new UserLogsOutHandler());
		addBackendMessageHandler(AddSubTaskMessage.class, new AddSubTaskHandler());
		addBackendMessageHandler(AddPreviousTaskMessage.class, new AddPreviousTaskHandler());
		addBackendMessageHandler(AddNextTaskMessage.class, new AddNextTaskHandler());
		addBackendMessageHandler(DeleteTaskMessage.class, new DeleteTaskHandler());
		addBackendMessageHandler(RemovePreviousTaskMessage.class, new RemovePreviousTaskHandler());
		addBackendMessageHandler(RemoveSubTaskMessage.class, new RemoveSubTaskHandler());
		addBackendMessageHandler(RemoveNextTaskMessage.class, new RemoveNextTaskHandler());
		addBackendMessageHandler(StudentRegistersRepoMessage.class, new StudentRegistersRepoHandler());
		addBackendMessageHandler(AddSemesterMessage.class, new AddSemesterHandler());
		addBackendMessageHandler(AddSemesterWeekMessage.class, new AddSemesterWeekHandler());
		addBackendMessageHandler(SetActiveSemesterMessage.class, new SetActiveSemesterHanlder());
		addBackendMessageHandler(AddScheduleItemMessage.class, new AddScheduleItemHandler());
		addBackendMessageHandler(UpdateTaskInfoMessage.class, new UpdateTaskInfoHandler());
		addBackendMessageHandler(UpdateUserInfoMessage.class, new UpdateUserInfoHandler());
		addBackendMessageHandler(AtomicTaskCompletedMessage.class, new AtomicTaskCompletedHandler());
		addBackendMessageHandler(TimePassesMessage.class, new TimePassesHandler());
		addBackendMessageHandler(ToggleStudentModeMessage.class, new ToggleStudentModeHandler());
		addBackendMessageHandler(ModifyAppointmentDurationsMessage.class, new ModifyAppointmentDurationsHandler());
		addBackendMessageHandler(ModifyAppointmentTimesMessage.class, new ModifyAppointmentTimesHandler());
		addBackendMessageHandler(ModifyAppointmentCommentMessage.class, new ModifyAppointmentCommentHandler());
		addBackendMessageHandler(ItsNotMeMessage.class, new ItsNotMeHandler());
		addBackendMessageHandler(OnNewCommits.class, new OnNewCommitsHandler());
		addBackendMessageHandler(UserChangesPasswordMessage.class, new UserChangesPasswordHandler());
		addBackendMessageHandler(UserSendsPasswordMessage.class, new UserSendsPasswordHandler());
		addBackendMessageHandler(ToggleAdminModeMessage.class, new ToggleAdminModeHandler());
		addBackendMessageHandler(DeleteSemesterMessage.class, new DeleteSemesterHandler());
		addBackendMessageHandler(StudentDeletesRepoMessage.class, new StudentDeletesRepoHandler());
		addBackendMessageHandler(OnClone.class, new OnCloneHandler());
		addBackendMessageHandler(UpdateIsQueueOpenMessage.class, new UpdateIsQueueOpenHandler());
		addBackendMessageHandler(OnExerciseCompleted.class, new OnExerciseCompletedHandler());
		addBackendMessageHandler(UpdateQueueInfoMessage.class, new UpdateQueueInfoHandler());
		addBackendMessageHandler(UserIsActiveMessage.class, new UserIsActiveHandler());
		addBackendMessageHandler(RenameUserMessage.class, new RenameUserHandler());
	}

	@Override
	protected void beforeCallingHandler(NtroUser requestingUser) {
		T.call(this);

		RegisteredSocketsSockJS.createInvokeValueMessageQueue(Ntro.threadService().currentThread().threadId(), requestingUser);
	}

	@Override
	protected void afterCallingHandler(NtroUser requestingUser) {
		T.call(this);

		RegisteredSocketsSockJS.flushInvokeValueMessageQueue(Ntro.threadService().currentThread().threadId(), requestingUser);
	}
}
