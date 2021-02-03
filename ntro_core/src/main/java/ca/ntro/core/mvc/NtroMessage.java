package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;

public abstract class NtroMessage extends NtroTaskImpl {
	
	
	public void sendMessage() {
		T.call(this);
		
		MessageFactory.sendMessage(this);
	}

	@Override
	protected void initializeTask() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);

		MessageFactory.sendMessage(this);
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
