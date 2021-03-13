package ca.aquiletour.server;

import ca.aquiletour.core.AiguilleurApp;
import ca.aquiletour.core.messages.AddStudentCsvMessage;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboards.teacher.messages.DeleteCourseMessage;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.MoveAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.aquiletour.core.pages.users.messages.AddUserMessage;
import ca.aquiletour.core.pages.users.messages.AddUserToCourseMessage;
import ca.aquiletour.core.pages.users.messages.DeleteUserFromCourseMessage;
import ca.aquiletour.core.pages.users.messages.DeleteUserMessage;
import ca.aquiletour.server.backend.MoveAppointmentHandler;
import ca.aquiletour.server.backend.dashboard.AddCourseHandler;
import ca.aquiletour.server.backend.dashboard.DeleteCourseHandler;
import ca.aquiletour.server.backend.queue.AddAppointmentHandler;
import ca.aquiletour.server.backend.queue.DeleteAppointmentHandler;
import ca.aquiletour.server.backend.queues.TeacherClosesQueueHandler;
import ca.aquiletour.server.backend.queues.TeacherUsesQueueHandler;
import ca.aquiletour.server.backend.users.AddStudentCsvHandler;
import ca.aquiletour.server.backend.users.AddUserHandler;
import ca.aquiletour.server.backend.users.AddUserToCourseHandler;
import ca.aquiletour.server.backend.users.DeleteUserFromCourseHandler;
import ca.aquiletour.server.backend.users.DeleteUserHandler;
import ca.ntro.HandlerRegistrar;
import ca.ntro.NtroBackend;

public class AiguilleurBackend extends AiguilleurApp implements NtroBackend {

	@Override
	public void registerHandlers(HandlerRegistrar registrar) {

		registrar.registerHandler(AddCourseMessage.class,            AddCourseHandler.class);
		registrar.registerHandler(AddAppointmentMessage.class,       AddAppointmentHandler.class);
		registrar.registerHandler(AddStudentCsvMessage.class,        AddStudentCsvHandler.class);
		registrar.registerHandler(AddUserToCourseMessage.class,      AddUserToCourseHandler.class);
		registrar.registerHandler(DeleteUserFromCourseMessage.class, DeleteUserFromCourseHandler.class);
		registrar.registerHandler(AddUserMessage.class,              AddUserHandler.class);
		registrar.registerHandler(DeleteUserMessage.class,           DeleteUserHandler.class);
		registrar.registerHandler(TeacherUsesQueueMessage.class,     TeacherUsesQueueHandler.class);
		registrar.registerHandler(DeleteCourseMessage.class,         DeleteCourseHandler.class);
		registrar.registerHandler(DeleteAppointmentMessage.class,    DeleteAppointmentHandler.class);
		registrar.registerHandler(MoveAppointmentMessage.class,      MoveAppointmentHandler.class);
		registrar.registerHandler(TeacherClosesQueueMessage.class,   TeacherClosesQueueHandler.class);
	}

}
