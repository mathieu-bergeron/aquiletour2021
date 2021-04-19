package ca.ntro.web.dom;

import ca.ntro.core.system.trace.T;

public class HtmlUtils {

	public static void addIdTo(HtmlElements elements, String attributeName, String idToAdd) {
		T.call(HtmlUtils.class);

		elements.forEach(e -> {
			String value = e.getAttribute(attributeName);
			value += idToAdd;
			e.setAttribute(attributeName, value);
		});
	}

}
