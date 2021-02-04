package ca.ntro.messages;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;

public abstract class NtroMessage extends NtroTaskImpl {
	
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
