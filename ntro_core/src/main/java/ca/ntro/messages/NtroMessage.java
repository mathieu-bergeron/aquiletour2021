package ca.ntro.messages;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskAsync;

public abstract class NtroMessage extends NtroTaskAsync {
	
	public void sendMessage() {
		T.call(this);
		
		notifyTaskFinished();
		reset();
		execute();
	}

	@Override
	protected void initializeTask() {
		T.call(this);
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);
	}

}
