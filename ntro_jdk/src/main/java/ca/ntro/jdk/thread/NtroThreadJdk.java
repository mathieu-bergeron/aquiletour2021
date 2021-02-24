package ca.ntro.jdk.thread;

import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;
import ca.ntro.threads.NtroThread;

public class NtroThreadJdk extends Thread implements NtroThread {
	
	private Thread javaThread;
	
	public NtroThreadJdk(Thread javaThread) {
		this.javaThread = javaThread;
	}
	
	@Override
	public int hashCode() {
		return javaThread.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other == this) return true;
		if(other instanceof NtroThreadJdk) {
			NtroThreadJdk otherThread = (NtroThreadJdk) other;
			return otherThread.javaThread.equals(javaThread);
		}
		return false;
	}
	

	@Override
	public void sendMessageToThread(NtroMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <M extends NtroMessage> void handleMessageFromThread(Class<M> messageClass, MessageHandler<M> handler) {
		// TODO Auto-generated method stub
		
	}
	

}
