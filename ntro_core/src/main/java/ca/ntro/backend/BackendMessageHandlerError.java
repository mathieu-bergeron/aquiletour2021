package ca.ntro.backend;

public class BackendMessageHandlerError extends Exception {
	private static final long serialVersionUID = -3059766814364821755L;

	public BackendMessageHandlerError() {
		super();
	}

	public BackendMessageHandlerError(String message) {
		super(message);
	}
}
