package ca.ntro.jsweet.regex;

import ca.ntro.core.regex.Matcher;
import ca.ntro.core.regex.Pattern;

public class PatternJSweet extends Pattern {

	@Override
	public Matcher matcher(String text) {
		return new MatcherJSweet(text);
	}

}
