package ca.aquiletour.server.backend;


import ca.aquiletour.core.messages.AddStudentCsvMessage;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.users.messages.AddUserToCourseMessage;
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
	}
}
