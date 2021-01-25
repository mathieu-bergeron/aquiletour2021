package ca.aquiletour.jsweet;

import ca.ntro.core.web.NtroWindowWeb;
import ca.ntro.core.web.dom.HtmlDocument;
import ca.ntro.jsweet.dom.HtmlDocumentJSweet;

public class NtroWindowJSweet extends NtroWindowWeb {
	
	private HtmlDocumentJSweet document = new HtmlDocumentJSweet();

	@Override
	protected HtmlDocument getDocument() {
		return document;
	}

}
