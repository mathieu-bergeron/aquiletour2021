package ca.ntro.jdk.services;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.ntro_messages.NtroErrorMessage;
import ca.ntro.services.BackendService;
import ca.ntro.services.ModelStore;
import ca.ntro.services.Ntro;

public abstract class BackendServiceServer extends BackendService {

	private Map<Class<? extends NtroMessage>, BackendMessageHandler> handlers = new HashMap<>();
	
	public BackendServiceServer() {
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

			Log.warning("No BackendMessageHandler for " + message.getClass().getSimpleName());

		}else {

			try {
				
				handler.handleNow(new ModelStoreSync(Ntro.modelStore()), message);

				Ntro.threadService().executeLater(new NtroTaskSync() {
					@Override
					protected void runTask() {
						T.call(this);

						handler.handleLater(new ModelStoreSync(Ntro.modelStore()), message);
					}

					@Override
					protected void onFailure(Exception e) {
					}
				});
				
			}catch(BackendMessageHandlerError e) {
				
				NtroErrorMessage displayErrorMessage = Ntro.messages().create(NtroErrorMessage.class);
				displayErrorMessage.setMessage(e.getMessage());
				Ntro.messages().send(displayErrorMessage);
			}
		}
	}

	@Override
	public <M extends NtroMessage> void handleMessageFromBackend(Class<M> messageClass, MessageHandler<M> handler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <MSG extends NtroMessage> boolean handlerExistsFor(MSG message) {
		return handlers.containsKey(message.getClass());
	}
}
