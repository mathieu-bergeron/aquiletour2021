package ca.ntro.core.services;

import ca.ntro.threads.NtroThread;

public abstract class ThreadService {
	
	public abstract NtroThread currentThread();
	public abstract NtroThread parentThreadOf(NtroThread thread);
	public abstract NtroThread newSubThreadFor(NtroThread thread);
	public abstract NtroThread newSubThread();

}
