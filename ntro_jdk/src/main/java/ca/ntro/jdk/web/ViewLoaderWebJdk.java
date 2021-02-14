package ca.ntro.jdk.web;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.services.ResourceLoaderTask;
import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.dom.HtmlElementJdk;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;
import ca.ntro.web.mvc.ViewLoaderWeb;

public class ViewLoaderWebJdk extends ViewLoaderWeb {
	
	public ViewLoaderWebJdk() {
		super();
		T.call(this);
	}

	@Override
	protected ViewLoader clone() {
		T.call(this);
		
		ViewLoaderWebJdk clone = new ViewLoaderWebJdk();
		clone.setHtmlUrl(getHtmlUrl());
		clone.setTargetClass(getTargetClass());
		
		return clone;
	}

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
