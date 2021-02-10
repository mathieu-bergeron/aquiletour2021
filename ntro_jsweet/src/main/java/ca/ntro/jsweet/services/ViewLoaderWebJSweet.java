package ca.ntro.jsweet.services;

import ca.ntro.jsweet.dom.HtmlElementJSweet;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.ViewLoaderWeb;
import def.dom.Globals;
import def.jquery.JQuery;

import static def.jquery.Globals.$;

public class ViewLoaderWebJSweet extends ViewLoaderWeb {

	@Override
	protected HtmlElement parseHtml(String html) {
		Object[] parsedHtml = $.parseHTML(html, Globals.document, false);

		// TODO améliorer ceci
		return new HtmlElementJSweet($(parsedHtml[0]));
	}

}
