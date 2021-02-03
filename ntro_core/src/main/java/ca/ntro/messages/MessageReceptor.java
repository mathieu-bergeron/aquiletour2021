package ca.ntro.messages;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;

public abstract class MessageReceptor extends NtroTaskImpl {
	
	private NtroMessage message;
	
	void setMessage(NtroMessage message) {
		T.call(this);
		
		this.message = message;
	}
	
	public NtroMessage getMessage() {
		T.call(this);

		return message;
	}


}
