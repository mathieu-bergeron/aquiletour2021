package ca.aquiletour.core.validator;

import ca.aquiletour.core.Constants;
import ca.ntro.core.system.trace.T;

public class Validator {
	
	private static final int MIN_ID_LENGTH = 1;
	private static final int MAX_ID_LENGTH = 100;

	private static final String[] illegalChars = new String[] {"¤","/","?","*", "@"};

	public static boolean isValidId(String id) {
		T.call(Validator.class);
		
		boolean isValid = false;
		
		try {
			
			mustBeValidId(id);
			isValid = true;
			
		}catch(ValidationError e) {}

		return isValid;
	}

	public static void mustBeValidId(String id) throws ValidationError {
		T.call(Validator.class);

		if(id.length() < MIN_ID_LENGTH) {
			throw new ValidationError("Doit au moins contenir" + MIN_ID_LENGTH + " caractère");
		}

		if(id.length() > MAX_ID_LENGTH) {
			throw new ValidationError("Ne peut contenir plus de " + MAX_ID_LENGTH + " caractères");
		}
		
		if(id.contains(" ")) {
			throw new ValidationError("Ne peut contenir d'espace");
		}

		for(String illegalChar : illegalChars) {
			if(id.contains(illegalChar)) {
				throw new ValidationError("Ne peut contenir le caractère " + illegalChar);
			}
		}

		for(String reservedId : Constants.RESERVED_IDS) {
			if(id.equals(reservedId)) {
				throw new ValidationError("Le code " + id + " est réservé pour le système");
			}
		}
	}
}
