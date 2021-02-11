package ca.ntro.jdk.web;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.dom.HtmlElementJdk;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.ViewLoaderWeb;

public class ViewLoaderWebJdk extends ViewLoaderWeb {

	@Override
	protected HtmlElement parseHtml(String html) {
		T.call(this);
		
		HtmlElement rootElement = null;

		Document jsoupDocument = Jsoup.parse(html, StandardCharsets.UTF_8.name());
		
		Element jsoupRootElement = new Element("div");

		List<Node> children = jsoupDocument.body().childNodes();
		
		children.forEach(c -> jsoupRootElement.appendChild(c.clone()));
		
		rootElement = new HtmlElementJdk(jsoupRootElement);
        
        return rootElement;
	}

}
