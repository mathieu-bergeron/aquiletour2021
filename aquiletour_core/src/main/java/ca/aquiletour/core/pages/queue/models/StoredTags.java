package ca.aquiletour.core.pages.queue.models;

import java.util.List;

import ca.ntro.core.models.StoredList;
import ca.ntro.core.regex.Matcher;
import ca.ntro.core.regex.Pattern;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class StoredTags extends StoredList<String> {

	private static Pattern tagPattern = null;
	
	private static Pattern tagPattern() {
		T.call(StoredTags.class);

		if(tagPattern == null) {
			tagPattern = Ntro.regEx().compilePattern("#\\p{IsLatin}+\\s?");
		}

		return tagPattern;
	}

	public void addTagsFromComment(String comment) {
		T.call(this);

		Matcher matcher = tagPattern().matcher(comment);

		List<String> tags = matcher.allMatches();
		
		for(String tag : tags) {
			if(!contains(tag)) {
				String trimmedTag = tag.replace("#", "");
				trimmedTag = trimmedTag.trim();
				addItem(trimmedTag);
			}
		}
	}

	public static String removeTags(String comment) {
		T.call(StoredTags.class);
		
		Matcher matcher = tagPattern().matcher(comment);
		
		return matcher.replaceAll(" ");
	}

}
