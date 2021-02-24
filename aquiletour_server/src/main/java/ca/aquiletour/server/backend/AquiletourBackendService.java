package ca.aquiletour.server.backend;


import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.ntro.jdk.models.ModelStoreSync;
import ca.ntro.jdk.services.BackendServiceServer;

public class AquiletourBackendService extends BackendServiceServer {
	
	public AquiletourBackendService(ModelStoreSync modelStore) {
		super(modelStore);
	}

	@Override
	protected void addBackendMessageHandlers() {

		addBackendMessageHandler(AddCourseMessage.class, new AddCourseHandler());
	}
}
