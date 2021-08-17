package ca.aquiletour.core.utils;

import ca.ntro.core.system.trace.T;

public class TextProcessing {

	private static String[] wordsToIgnore = new String[] 
			{
				"de", 
				"du", 
				"a", 
				"au", 
				"la", 
				"le",
			};

	private static boolean shouldIngoreWord(String word) {
		T.call(TextProcessing.class);
		
		boolean ignore = false;
		
		for(String wordToIgnore : wordsToIgnore) {
			if(wordToIgnore.equalsIgnoreCase(word)) {
				ignore = true;
				break;
			}
		}
		
		return ignore;
	}

	private static String shortenWord(String word) {
		T.call(TextProcessing.class);
		
		String shortWord = null;
		
		if(word.length() <= 3) {
			
			shortWord =  word;
			
		}else {
			
			shortWord = word.substring(0,3);
		}
		
		return shortWord;
	}

	public static String generateIdFromTitle(String courseTitle) {
		T.call(TextProcessing.class);

		courseTitle = Validator.deAccent(courseTitle);

		StringBuilder builder = new StringBuilder();
		
		String[] words = courseTitle.split(" ");
		for(String word : words) {
			if(!shouldIngoreWord(word)) {
				
				builder.append(capitalizeWord(shortenWord(word)));
			}
		}
		
		return Validator.normalizeId(builder.toString());
	}

	private static String capitalizeWord(String word) {
		T.call(TextProcessing.class);

		String firstLetter = "";
		String remainder = "";
		
		if(word.length() > 0) {
			firstLetter = word.substring(0,1);
			firstLetter = firstLetter.toUpperCase();
		}
		
		if(word.length() > 1) {
			remainder = word.substring(1);
		}

		return firstLetter + remainder;
	}

	public static boolean isValidName(String name) {
		T.call(TextProcessing.class);
		
		boolean isValid = true;
		
		if(name.length() <= 2) {
			isValid = false;
		}

		return isValid;
	}

}
