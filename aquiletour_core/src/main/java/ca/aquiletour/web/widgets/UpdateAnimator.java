package ca.aquiletour.web.widgets;

import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;

public class UpdateAnimator {
	
	public static void updateText(HtmlElement element, String text) {
		T.call(UpdateAnimator.class);
		
		// TODO: animate when we update
		if(element != null 
				&& text != null
				&& !element.text().equals(text)) {
			
			element.text(text);
		}
	}
}
