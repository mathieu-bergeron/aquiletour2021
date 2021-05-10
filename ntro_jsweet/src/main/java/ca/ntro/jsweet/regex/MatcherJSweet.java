package ca.ntro.jsweet.regex;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.regex.Matcher;
import ca.ntro.core.system.trace.T;

public class MatcherJSweet extends Matcher {
	
	private final String text;
	
	public MatcherJSweet(String text) {
		super();
		T.call(this);
		
		this.text = text;
	}

	@Override
	public boolean matches() {
		return false;
	}

	@Override
	public String replaceAll(String replacement) {
		return text;
	}

	@Override
	public List<String> allMatches() {
		return new ArrayList<String>();
	}

}
