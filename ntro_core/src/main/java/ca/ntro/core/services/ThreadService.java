package ca.ntro.core.services;

import java.util.Set;

import ca.ntro.core.tasks.NtroTask;
import ca.ntro.messages.NtroMessage;
import ca.ntro.threads.NtroThread;

public abstract class ThreadService {
	
	public abstract NtroThread currentThread();
	public abstract boolean hasParentThread();
	public abstract void sendMessageToParentThread(NtroMessage message);
	public abstract NtroThread newSubThread();
	public abstract Set<NtroThread> subThreads();
	
	public abstract void executeLater(NtroTask task);

}
