package ca.ntro.messages;

public abstract class MessageHandler<M extends NtroMessage> {
	
	public abstract void handle(M message);

}
