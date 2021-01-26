package ca.ntro.messages;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;

public abstract class MessageReceptionTask extends NtroTask {
	
	private Message message;
	
	void setMessage(Message message) {
		T.call(this);
		
		this.message = message;
	}
	
	public Message getMessage() {
		T.call(this);

		return message;
	}


}
