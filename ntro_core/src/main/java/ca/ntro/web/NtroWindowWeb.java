package ca.ntro.web;

import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlDocument;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.ViewLoaderWeb;

public abstract class NtroWindowWeb extends NtroWindow<ViewLoaderWeb> {
	
	private HtmlDocument document;
	
	public NtroWindowWeb() {
		T.call(this);
		
		document = getDocument();
	}
	
	protected abstract HtmlDocument getDocument();
	
	public void writeHtml(StringBuilder out) {
		T.call(this);

		document.writeHtml(out);
	}

	@Override
	public void installRootView(ViewLoaderWeb viewLoader) {
		T.call(this);
		
		HtmlElement body = document.select("body").get(0);
		
		body.appendHtml(viewLoader.getHtml());
	}

	
}
