package ca.ntro.jsweet.services;

import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.jsweet.dom.HtmlElementJSweet;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.ViewLoaderWeb;
import def.dom.Globals;
import def.jquery.JQuery;


import static def.dom.Globals.document;
import static def.jquery.Globals.$;

public class ViewLoaderWebJSweet extends ViewLoaderWeb {

	public ViewLoaderWebJSweet() {
		super();
		T.call(this);
	}

	@Override
	protected ViewLoader clone() {
		T.call(this);
		
		ViewLoaderWebJSweet clone = new ViewLoaderWebJSweet();
		clone.setHtmlUrl(getHtmlUrl());
		clone.setTargetClass(getTargetClass());
		
		return clone;
	}

	@Override
	protected HtmlElement parseHtml(String html) {
		T.call(this);

		Object[] parsedHtml = $.parseHTML(html, Globals.document, false);
		
		JQuery rootDiv = $(document.createElement("div"));
		
		for(Object parsedElement : parsedHtml) {
			rootDiv.append($(parsedElement));
		}
		
		return new HtmlElementJSweet(rootDiv);
	}


}
