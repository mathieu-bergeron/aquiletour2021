package ca.ntro.messages;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskAsync;

public abstract class NtroMessage extends NtroTaskAsync {
	
	public void sendMessage() {
		T.call(this);
		
		notifyTaskFinished(); // XXX: this re-executes the graph
		resetTask();		  // FIXME: should not reset right away
		execute();            // 
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
