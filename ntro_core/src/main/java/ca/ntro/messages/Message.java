package ca.ntro.messages;

import ca.ntro.core.system.trace.T;

public abstract class Message {
	
	
	public void sendMessage() {
		T.call(this);
		
		MessageFactory.sendMessage(this);
	}

}
