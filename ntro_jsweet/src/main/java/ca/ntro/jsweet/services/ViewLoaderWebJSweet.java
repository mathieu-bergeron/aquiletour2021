package ca.ntro.jsweet.services;

import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.services.ResourceLoaderTask;
import ca.ntro.core.system.trace.T;
import ca.ntro.jsweet.dom.HtmlElementJSweet;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.ViewLoaderWeb;
import def.dom.Globals;
import def.jquery.JQuery;

import static def.dom.Globals.console;
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

		if (parsedHtml.length > 1) {
			console.warn("[ViewLoader] Root HTML contains more than one root node. First non-text node will be used.");
		}

		JQuery firstNonTextNode = $(parsedHtml).filter((index, element) -> element.nodeType != 3);

		return new HtmlElementJSweet($(firstNonTextNode));
	}


}
