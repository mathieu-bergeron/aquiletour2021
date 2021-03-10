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
}
