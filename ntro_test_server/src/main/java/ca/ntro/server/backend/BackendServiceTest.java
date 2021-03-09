package ca.ntro.server.backend;

import ca.ntro.core.services.BackendService;
import ca.ntro.jdk.models.ModelStoreSync;
import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;

public class BackendServiceTest extends BackendService  {
	
	private ModelStoreSync modelStore;

	public BackendServiceTest(ModelStoreSync modelStore) {
		this.modelStore = modelStore;
	}

	@Override
	public void sendMessageToBackend(NtroMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <M extends NtroMessage> void handleMessageFromBackend(Class<M> messageClass, MessageHandler<M> handler) {
		// TODO Auto-generated method stub
		
	}

}
