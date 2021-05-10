package ca.ntro.backend;

public class UserInputError extends Exception {
	private static final long serialVersionUID = 4171333382159903592L;

	public UserInputError() {
		super();
	}

	public UserInputError(String message) {
		super(message);
	}
}
