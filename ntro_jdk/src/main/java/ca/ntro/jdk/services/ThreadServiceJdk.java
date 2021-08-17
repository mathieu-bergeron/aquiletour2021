package ca.ntro.jdk.services;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.jdk.thread.NtroThreadJdk;
import ca.ntro.messages.NtroMessage;
import ca.ntro.services.Ntro;
import ca.ntro.services.ThreadService;
import ca.ntro.threads.NtroThread;

public class ThreadServiceJdk extends ThreadService {

	private final ExecutorService executor;
	
	public ThreadServiceJdk() {
		super();
		
		if(Ntro.config().isProd()) {

			executor = Executors.newFixedThreadPool(1000);

		}else {

			executor = Executors.newFixedThreadPool(5);

		}
	}

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
		T.call(this);

		executor.execute(new Runnable() {
			@Override
			public void run() {
				task.execute();
			}
		});
	}
}
