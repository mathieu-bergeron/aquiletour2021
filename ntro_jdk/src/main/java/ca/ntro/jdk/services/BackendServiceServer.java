package ca.ntro.jdk.services;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.services.BackendService;
import ca.ntro.core.system.log.Log;
import ca.ntro.jdk.messages.BackendMessageHandler;
import ca.ntro.jdk.models.ModelStoreSync;
import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;

public abstract class BackendServiceServer extends BackendService {

	private ModelStoreSync modelStore;
	
	private Map<Class<? extends NtroMessage>, BackendMessageHandler> handlers = new HashMap<>();
	
	public BackendServiceServer(ModelStoreSync modelStore) {
		
		this.modelStore = modelStore;
		
		addBackendMessageHandlers();
	}

	protected abstract void addBackendMessageHandlers();

	protected void addBackendMessageHandler(Class<? extends NtroMessage> messageClass, BackendMessageHandler handler) {
		handlers.put(messageClass, handler);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void sendMessageToBackend(NtroMessage message) {
		BackendMessageHandler handler = handlers.get(message.getClass());
		
		if(handler == null) {
			Log.fatalError("No BackendMessageHandler for " + message.getClass().getSimpleName());
		}
		
		handler.handle(modelStore, message);
	}

	@Override
	public <M extends NtroMessage> void handleMessageFromBackend(Class<M> messageClass, MessageHandler<M> handler) {
		// TODO Auto-generated method stub
		
	}

}
