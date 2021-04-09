package ca.ntro.jdk.services;

import java.util.HashSet;
import java.util.Set;

import ca.ntro.core.tasks.NtroTask;
import ca.ntro.jdk.thread.NtroThreadJdk;
import ca.ntro.messages.NtroMessage;
import ca.ntro.services.ThreadService;
import ca.ntro.threads.NtroThread;

public class ThreadServiceJdk extends ThreadService {

	@Override
	public NtroThread currentThread() {
		return new NtroThreadJdk(Thread.currentThread());
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
		// FIXME: on the server
		//        use a thread from Jetty thread pool
		new Thread() {
			@Override
			public void run() {
				task.execute();
			}
		}.start();
	}
}
