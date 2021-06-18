package ca.ntro.jsweet.services;

import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.jsweet.dom.HtmlElementJSweet;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.ViewLoaderWeb;

public class ViewLoaderWebJSweet extends ViewLoaderWeb {

	public ViewLoaderWebJSweet() {
		super();
		T.call(this);
	}

	@Override
	protected ViewLoader clone() {
		T.call(this);
		
		ViewLoaderWebJSweet clone = new ViewLoaderWebJSweet();
		clone.setHtmlUrl(getHtmlUrl());
		clone.setTargetClass(getTargetClass());
		
		return clone;
	}

	@Override
	protected HtmlElement parseHtml(String html) {
		T.call(this);

		return new HtmlElementJSweet(HtmlElementJSweet.parseHtml(html));
	}
}
