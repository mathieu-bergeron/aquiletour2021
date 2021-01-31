package ca.ntro.web;

import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.mvc.view.NtroView;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlDocument;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public abstract class NtroWindowWeb extends NtroWindow {
	
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
	public void installRootView(NtroView rootView) {
		T.call(this);
		
		HtmlElement body = document.select("body").get(0);
		
		NtroViewWeb rootViewWeb = (NtroViewWeb) rootView;

		body.appendElement(rootViewWeb.getRootElement());
	}

	public void setCurrentPath(Path path) {
		T.call(this);

		HtmlElement body = document.select("body").get(0);
		
		body.setAttribute("current-path", path.toString());
	}

	
}
