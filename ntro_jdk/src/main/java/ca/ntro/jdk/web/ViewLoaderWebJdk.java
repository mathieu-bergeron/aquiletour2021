package ca.ntro.jdk.web;

import java.nio.charset.StandardCharsets;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.dom.HtmlElementJava;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.ViewLoaderWeb;

public class ViewLoaderWebJdk extends ViewLoaderWeb {

	@Override
	protected HtmlElement parseHtml(String html) {
		T.call(this);
		
		HtmlElement rootElement = null;

		Document jsoupDocument = Jsoup.parse(html, StandardCharsets.UTF_8.name());
		rootElement = new HtmlElementJava(jsoupDocument.root());
        
        return rootElement;
	}

}
