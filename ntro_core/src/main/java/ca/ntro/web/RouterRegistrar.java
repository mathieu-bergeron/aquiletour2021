package ca.ntro.web;

import ca.ntro.messages.NtroMessage;
import ca.ntro.users.NtroUser;

public interface RouterRegistrar {

	void registerRouter(String string, Class<? extends NtroUser> userClass, Class<? extends NtroMessage> messageClass);
	
	void registerRouter(NtroRouter<?> router);

}
