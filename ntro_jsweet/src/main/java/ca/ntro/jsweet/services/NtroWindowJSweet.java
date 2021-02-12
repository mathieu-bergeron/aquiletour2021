package ca.ntro.jsweet.services;

import ca.ntro.core.system.trace.T;
import ca.ntro.jsweet.dom.HtmlDocumentJSweet;
import ca.ntro.web.NtroWindowWeb;
import ca.ntro.web.dom.HtmlDocument;

public class NtroWindowJSweet extends NtroWindowWeb {
	
	private HtmlDocument document = new HtmlDocumentJSweet();

	@Override
	protected HtmlDocument getDocument() {
		T.call(this);
		
		return document;
	}
}
