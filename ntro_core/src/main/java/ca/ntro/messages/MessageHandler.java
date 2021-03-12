package ca.ntro.messages;

public abstract class MessageHandler<M extends NtroMessage> {
	
	public void handleUntyped(NtroMessage message) {
		handle((M) message);
	}

	public abstract void handle(M message);

}
