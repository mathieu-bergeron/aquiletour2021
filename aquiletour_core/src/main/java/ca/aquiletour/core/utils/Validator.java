package ca.aquiletour.core.utils;

import java.util.HashMap;
import java.util.Map;

import ca.aquiletour.core.Constants;
import ca.ntro.core.system.trace.T;

public class Validator {
	
	private static final int MIN_ID_LENGTH = 1;
	private static final int MAX_ID_LENGTH = 100;

	private static final String[] acceptableChars = new String[] 
			{
				"a","b","c","d","e",
				"f","g","h","i","j",
				"k","l","m","n","o",
				"p","q","r","s","t",
				"u","v","w","x","y",
				"z",
				"A","B","C","D","E",
				"F","G","H","I","J",
				"K","L","M","N","O",
				"P","Q","R","S","T",
				"U","V","W","X","Y",
				"Z",
				"_",
				"-",
				".",
				"0","1","2","3","4",
				"5","6","7","8","9"
			};

	private static final Map<String, String> accentReplacements = new HashMap<>();
	static {

		accentReplacements.put("à", "a");
		accentReplacements.put("â", "a");
		accentReplacements.put("ä", "a");
		
		accentReplacements.put("é", "e");
		accentReplacements.put("è", "e");
		accentReplacements.put("ê", "e");
		accentReplacements.put("ë", "e");

		accentReplacements.put("ì", "i");
		accentReplacements.put("î", "i");
		accentReplacements.put("ï", "i");

		accentReplacements.put("ò", "o");
		accentReplacements.put("ô", "o");
		accentReplacements.put("ö", "o");

		accentReplacements.put("ù", "u");
		accentReplacements.put("ü", "u");
		accentReplacements.put("û", "u");

		accentReplacements.put("À", "A");
		accentReplacements.put("Â", "A");
		accentReplacements.put("Ä", "A");
		
		accentReplacements.put("É", "E");
		accentReplacements.put("È", "E");
		accentReplacements.put("Ê", "E");
		accentReplacements.put("Ë", "E");

		accentReplacements.put("Ì", "I");
		accentReplacements.put("Î", "I");
		accentReplacements.put("Ï", "I");

		accentReplacements.put("Ò", "O");
		accentReplacements.put("Ô", "O");
		accentReplacements.put("Ö", "O");

		accentReplacements.put("Ù", "U");
		accentReplacements.put("Ü", "U");
		accentReplacements.put("Û", "U");
	}

	private static boolean isAcceptableChar(String c) {
		T.call(Validator.class);
		
		boolean isAcceptable = false;
		
		for(String acceptable : acceptableChars) {
			if(acceptable.equals(c)) {
				isAcceptable = true;
				break;
			}
		}
		
		return isAcceptable;
	}

	public static String deAccent(String id) {
		T.call(Validator.class);

		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < id.length(); i++) {
			String charI = id.substring(i, i+1);
			
			if(accentReplacements.containsKey(charI)) {
				charI = accentReplacements.get(charI);
			}
			
			builder.append(charI);
		}
		
		return builder.toString();
	}

	public static String normalizeId(String id) {
		T.call(Validator.class);
		
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < id.length(); i++) {
			String charI = id.substring(i, i+1);

			if(!isAcceptableChar(charI)) {
				charI = "_";
			}
			
			builder.append(charI);
		}
		
		return builder.toString();
	}

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
		
		for(int i = 0; i < id.length(); i++) {
			String charI = id.substring(i,i+1);
			if(!isAcceptableChar(charI)) {
				throw new ValidationError("Ne peut contenir le caractère " + charI);
			}
		}

		for(String reservedId : Constants.RESERVED_IDS) {
			if(id.equals(reservedId)) {
				throw new ValidationError("Le code " + id + " est réservé pour le système");
			}
		}
	}

}
