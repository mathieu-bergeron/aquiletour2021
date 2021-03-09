package ca.aquiletour.server.backend;


import ca.aquiletour.core.messages.AddStudentCsvMessage;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboards.teacher.messages.DeleteCourseMessage;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.MoveAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.aquiletour.core.pages.users.messages.AddUserMessage;
import ca.aquiletour.core.pages.users.messages.AddUserToCourseMessage;
import ca.aquiletour.core.pages.users.messages.DeleteUserFromCourseMessage;
import ca.aquiletour.core.pages.users.messages.DeleteUserMessage;
import ca.ntro.jdk.models.ModelStoreSync;
import ca.ntro.jdk.services.BackendServiceServer;

public class AquiletourBackendService extends BackendServiceServer {
	
	public AquiletourBackendService(ModelStoreSync modelStore) {
		super(modelStore);
	}

	@Override
	protected void addBackendMessageHandlers() {

		addBackendMessageHandler(AddCourseMessage.class, new AddCourseHandler());
		addBackendMessageHandler(AddAppointmentMessage.class, new AddAppointmentHandler());
		addBackendMessageHandler(AddStudentCsvMessage.class, new AddStudentCsvHandler());
		addBackendMessageHandler(AddUserToCourseMessage.class, new AddUserToCourseHandler());
		addBackendMessageHandler(DeleteUserFromCourseMessage.class, new DeleteUserFromCourseHandler());
		addBackendMessageHandler(AddUserMessage.class, new AddUserHandler());
		addBackendMessageHandler(DeleteUserMessage.class, new DeleteUserHandler());
		addBackendMessageHandler(TeacherUsesQueueMessage.class, new TeacherUsesQueueHandler());
		addBackendMessageHandler(DeleteCourseMessage.class, new DeleteCourseHandler());
		addBackendMessageHandler(DeleteAppointmentMessage.class, new DeleteAppointmentHandler());
		addBackendMessageHandler(MoveAppointmentMessage.class, new MoveAppointmentHandler());
	}
}
