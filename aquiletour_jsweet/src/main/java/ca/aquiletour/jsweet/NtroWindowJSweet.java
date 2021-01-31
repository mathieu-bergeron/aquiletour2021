package ca.aquiletour.jsweet;

import ca.ntro.core.system.assertions.MustNot;

import ca.ntro.core.system.trace.T;
import ca.ntro.web.NtroWindowWeb;
import ca.ntro.web.dom.HtmlDocument;
import ca.ntro.jsweet.dom.HtmlDocumentJSweet;

public class NtroWindowJSweet extends NtroWindowWeb {
	
	private HtmlDocumentJSweet document;
	
	public NtroWindowJSweet() {
		super();
		T.call(this);
		
		document = new HtmlDocumentJSweet();
	}

	@Override
	protected HtmlDocument getDocument() {
		T.call(this);
		
		// FIXME: why is this null???
		MustNot.beNull(document);
		document = new HtmlDocumentJSweet();
		
		return document;
	}

}
