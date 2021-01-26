package ca.ntro.jdk.dom;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.dom.HtmlDocument;


public class HtmlDocumentJava implements HtmlDocument {
	
	private Document document;
	
	public HtmlDocumentJava(Document document) {
		this.document = document;
	}

	@Override
	public HtmlElements select(String cssQuery) {
		T.call(this);

		Elements elements = document.select(cssQuery);
		
		return new HtmlElementsJava(elements);
	}

	@Override
	public void writeHtml(StringBuilder out) {
		T.call(this);
		
		out.append(document.outerHtml());
	}

}
