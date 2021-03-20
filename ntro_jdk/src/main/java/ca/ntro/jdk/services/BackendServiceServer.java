package ca.ntro.jdk.services;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;
import ca.ntro.services.BackendService;
import ca.ntro.services.ModelStore;
import ca.ntro.services.Ntro;

public abstract class BackendServiceServer extends BackendService {

	private ModelStoreSync modelStore;
	
	private Map<Class<? extends NtroMessage>, BackendMessageHandler> handlers = new HashMap<>();
	
	public BackendServiceServer() {
		addBackendMessageHandlers();
	}

	public void setModelStore(ModelStore modelStore) {
		T.call(this);

		this.modelStore = new ModelStoreSync(modelStore);
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

			Log.warning("No BackendMessageHandler for " + message.getClass().getSimpleName());

		}else {
			
			handler.handleNow(modelStore, message);
			
			Ntro.threadService().executeLater(new NtroTaskSync() {
				@Override
				protected void runTask() {
					T.call(this);
					
					ModelStoreSync modelStore = new ModelStoreSync(Ntro.modelStore());
					
					handler.handleLater(modelStore, message);
				}

				@Override
				protected void onFailure(Exception e) {
				}
			});
		}
	}

	@Override
	public <M extends NtroMessage> void handleMessageFromBackend(Class<M> messageClass, MessageHandler<M> handler) {
		// TODO Auto-generated method stub
		
	}


}
