package ca.aquiletour.core.pages.dashboard.messages;

import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;

public class AddCourseMessage extends NtroMessage {
	
	private String text;

	public void setText(String text) {
		T.call(this);
		
		this.text = text;
	}
	
	public String getText() {
		T.call(this);
		
		return text;
	}

}
