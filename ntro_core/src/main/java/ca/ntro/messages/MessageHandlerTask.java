package ca.ntro.messages;

import ca.ntro.core.tasks.NtroTask;
import ca.ntro.core.tasks.NtroTaskImpl;

public class MessageHandlerTask<MSG extends NtroMessage> extends NtroTaskImpl {
	
	private MSG message;
	
	public MSG getMessage() {
		return message;
	}

	public void setMessage(MSG message) {
		this.message = message;
	}

	@Override
	protected void runEntryTaskAsync() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void runExitTaskAsync() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onSomePreviousTaskFinished(String taskId, NtroTask previousTask) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onSomeSubTaskFinished(String taskId, NtroTask subTask) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}


}
