package ca.ntro;

import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.messages.NtroMessage;

public interface HandlerRegistrar {

	void registerHandler(Class<? extends NtroMessage> messageClass, Class<? extends BackendMessageHandler<?>> handlerClass);

}
