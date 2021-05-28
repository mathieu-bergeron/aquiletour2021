package ca.aquiletour.core.validator;

public class ValidationError extends Exception {
	private static final long serialVersionUID = -4060861976068530773L;
	
	public ValidationError() {
		super();
	}

	public ValidationError(String message) {
		super(message);
	}
}
