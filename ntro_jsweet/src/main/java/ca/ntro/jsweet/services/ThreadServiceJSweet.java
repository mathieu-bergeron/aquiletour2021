package ca.ntro.jsweet.services;

import java.util.HashSet;
import java.util.Set;

import ca.ntro.core.services.ThreadService;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.jsweet.thread.NtroThreadJSweet;
import ca.ntro.messages.NtroMessage;
import ca.ntro.threads.NtroThread;

public class ThreadServiceJSweet extends ThreadService {
	
	private long ROOT_THREAD_ID = 0;
	
	@Override
	public NtroThread currentThread() {
		return new NtroThreadJSweet(ROOT_THREAD_ID);
	}

	@Override
	public boolean hasParentThread() {
		return false;
	}

	@Override
	public void sendMessageToParentThread(NtroMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NtroThread newSubThread() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<NtroThread> subThreads() {
		// FIXME
		return new HashSet<>();
	}

	@Override
	public void executeLater(NtroTask task) {
		Log.warning("threadService().executeLater not supported in JSweet");
	}

}
