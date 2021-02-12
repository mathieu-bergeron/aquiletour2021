package ca.ntro.messages;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskAsync;

public abstract class MessageHandler extends NtroTaskAsync {
	
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
