package ca.ntro.jdk.web;



import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.dom.HtmlElementJdk;
import ca.ntro.web.dom.HtmlElement;
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

		return HtmlElementJdk.parseHtml(html);
	}
}
