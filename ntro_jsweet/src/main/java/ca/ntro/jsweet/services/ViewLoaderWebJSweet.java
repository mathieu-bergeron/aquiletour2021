package ca.ntro.jsweet.services;

import ca.ntro.jsweet.dom.HtmlElementJSweet;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.ViewLoaderWeb;
import def.dom.Globals;
import def.jquery.JQuery;

import static def.dom.Globals.console;
import static def.jquery.Globals.$;

public class ViewLoaderWebJSweet extends ViewLoaderWeb {

	@Override
	protected HtmlElement parseHtml(String html) {
		Object[] parsedHtml = $.parseHTML(html, Globals.document, false);

		if (parsedHtml.length > 1) {
			console.warn("[ViewLoader] Root HTML contains more than one root node. First non-text node will be used.");
		}

		JQuery firstNonTextNode = $(parsedHtml).filter((index, element) -> element.nodeType != 3);

		return new HtmlElementJSweet($(firstNonTextNode));
	}

}
