package ca.ntro.core.services;

import ca.ntro.messages.NtroMessage;

public abstract class BackendService {

	public abstract void sendMessageToBackend(NtroMessage message);

}
