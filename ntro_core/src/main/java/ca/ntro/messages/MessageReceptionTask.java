package ca.ntro.messages;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;

public abstract class MessageReceptionTask extends NtroTaskImpl {
	
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
