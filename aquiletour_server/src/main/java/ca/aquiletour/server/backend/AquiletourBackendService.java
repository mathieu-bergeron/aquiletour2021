package ca.aquiletour.server.backend;


import ca.aquiletour.core.messages.AddStudentCsvMessage;
import ca.aquiletour.core.messages.InitializeSessionMessage;
import ca.aquiletour.core.messages.UserInitiatesLoginMessage;
import ca.aquiletour.core.messages.UserLogsOutMessage;
import ca.aquiletour.core.messages.UserSendsLoginCodeMessage;
import ca.aquiletour.core.messages.git.RegisterRepo;
import ca.aquiletour.core.pages.course.messages.AddNextTaskMessage;
import ca.aquiletour.core.pages.course.messages.AddPreviousTaskMessage;
import ca.aquiletour.core.pages.course.messages.AddSubTaskMessage;
import ca.aquiletour.core.pages.course.messages.DeleteTaskMessage;
import ca.aquiletour.core.pages.course.messages.RemoveNextTaskMessage;
import ca.aquiletour.core.pages.course.messages.RemovePreviousTaskMessage;
import ca.aquiletour.core.pages.course.messages.RemoveSubTaskMessage;
import ca.aquiletour.core.pages.course_list.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboards.teacher.messages.DeleteCourseMessage;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.MoveAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.aquiletour.core.pages.semester_list.messages.AddSemesterMessage;
import ca.aquiletour.core.pages.semester_list.messages.AddSemesterWeekMessage;
import ca.aquiletour.core.pages.semester_list.messages.SelectCurrentSemester;
import ca.aquiletour.server.backend.course.AddNextTaskHandler;
import ca.aquiletour.server.backend.course.AddPreviousTaskHandler;
import ca.aquiletour.server.backend.course.AddSubTaskHandler;
import ca.aquiletour.server.backend.course.DeleteTaskHandler;
import ca.aquiletour.server.backend.course.RemoveNextTaskHandler;
import ca.aquiletour.server.backend.course.RemovePreviousTaskHandler;
import ca.aquiletour.server.backend.course.RemoveSubTaskHandler;
import ca.aquiletour.server.backend.course_list.AddCourseHandler;
import ca.aquiletour.server.backend.dashboard.DeleteCourseHandler;
import ca.aquiletour.server.backend.git.RegisterRepoHandler;
import ca.aquiletour.server.backend.login.InitializeSessionHandler;
import ca.aquiletour.server.backend.login.UserInitiatesLoginHandler;
import ca.aquiletour.server.backend.login.UserSendsLoginCodeHandler;
import ca.aquiletour.server.backend.login.UserLogsOutHandler;
import ca.aquiletour.server.backend.queue.AddAppointmentHandler;
import ca.aquiletour.server.backend.queue.DeleteAppointmentHandler;
import ca.aquiletour.server.backend.queue.MoveAppointmentHandler;
import ca.aquiletour.server.backend.queue.TeacherClosesQueueHandler;
import ca.aquiletour.server.backend.queue.TeacherUsesQueueHandler;
import ca.aquiletour.server.backend.semester_list.AddSemesterHandler;
import ca.aquiletour.server.backend.semester_list.AddSemesterWeekHandler;
import ca.aquiletour.server.backend.semester_list.SelectCurrentSemesterHandler;
import ca.aquiletour.server.backend.users.AddStudentCsvHandler;
import ca.ntro.jdk.services.BackendServiceServer;
import ca.ntro.messages.NtroMessage;

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
		addBackendMessageHandler(RegisterRepo.class, new RegisterRepoHandler());
		addBackendMessageHandler(AddSemesterMessage.class, new AddSemesterHandler());
		addBackendMessageHandler(AddSemesterWeekMessage.class, new AddSemesterWeekHandler());
		addBackendMessageHandler(SelectCurrentSemester.class, new SelectCurrentSemesterHandler());
	}

}
