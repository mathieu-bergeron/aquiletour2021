package ca.ntro.jsweet.dom;

import static def.jquery.Globals.$;

import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlDocument;
import ca.ntro.web.dom.HtmlElements;

import static def.dom.Globals.document;

public class HtmlDocumentJSweet implements HtmlDocument {
	
	@Override
	public HtmlElements select(String cssQuery) {
		return new HtmlElementsJSweet($(cssQuery));
	}

	@Override
	public void writeHtml(StringBuilder out) {
		T.call(this);

		out.append($(document).html());
	}

}
