package ca.ntro.messages;

import ca.ntro.core.tasks.NtroTaskAsync;

public class MessageHandlerTask<MSG extends NtroMessage> extends NtroTaskAsync {
	
	private MSG message;
	
	public MSG getMessage() {
		return message;
	}

	public void setMessage(MSG message) {
		this.message = message;
	}

	@Override
	protected void runTaskAsync() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

	public void triggerHandlerOnce() {
		System.out.println("triggerHandlerOnce");
		
		// FIXME: as implemented below
		//        there is an issue with two message
		//        handlers one after the other
		notifyTaskFinished();  // unblock the handler
		execute();             // execute tasks that are now unblocked
		resetNodeTransitive(); // reblock tasks that depend on this handler
		execute();             // get ready for next trigger

	}
}
