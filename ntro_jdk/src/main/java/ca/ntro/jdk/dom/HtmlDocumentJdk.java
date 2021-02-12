package ca.ntro.jdk.dom;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.dom.HtmlDocument;


public class HtmlDocumentJdk implements HtmlDocument, Cloneable {
	
	private Document document;
	
	public HtmlDocumentJdk(Document document) {
		this.document = document;
	}

	@Override
	public HtmlElements select(String cssQuery) {
		T.call(this);

		Elements elements = document.select(cssQuery);
		
		return new HtmlElementsJdk(elements);
	}

	@Override
	public void writeHtml(StringBuilder out) {
		T.call(this);
		
		out.append(document.outerHtml());
	}

	@Override
	public HtmlDocumentJdk clone() {
		return new HtmlDocumentJdk(document.clone());
	}
	
	@Override 
	public String toString() {
		return "document@" + System.identityHashCode(document);
	}

}
