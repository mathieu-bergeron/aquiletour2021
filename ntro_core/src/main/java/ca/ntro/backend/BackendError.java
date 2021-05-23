package ca.ntro.backend;

public class BackendError extends Exception {
	private static final long serialVersionUID = -3059766814364821755L;

	public BackendError() {
		super();
	}

	public BackendError(String message) {
		super(message);
	}
}
