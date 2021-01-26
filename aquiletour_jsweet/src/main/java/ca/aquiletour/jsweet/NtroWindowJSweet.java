package ca.aquiletour.jsweet;

import ca.ntro.core.system.assertions.MustNot;

import ca.ntro.core.system.trace.T;
import ca.ntro.web.NtroWindowWeb;
import ca.ntro.web.dom.HtmlDocument;
import ca.ntro.jsweet.dom.HtmlDocumentJSweet;

public class NtroWindowJSweet extends NtroWindowWeb {
	
	private HtmlDocumentJSweet document = new HtmlDocumentJSweet();

	@Override
	protected HtmlDocument getDocument() {
		T.call(this);
		
		// FIXME: why is it when called??
		// MustNot.beNull(document);
		
		return new HtmlDocumentJSweet();
	}

}
