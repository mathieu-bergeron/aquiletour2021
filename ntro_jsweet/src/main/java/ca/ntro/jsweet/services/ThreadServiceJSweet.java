package ca.ntro.jsweet.services;

import java.util.Set;

import ca.ntro.core.services.ThreadService;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.messages.NtroMessage;
import ca.ntro.threads.NtroThread;

public class ThreadServiceJSweet extends ThreadService {

	@Override
	public NtroThread currentThread() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasParentThread() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executeLater(NtroTask task) {
		// TODO Auto-generated method stub
		
	}

}
