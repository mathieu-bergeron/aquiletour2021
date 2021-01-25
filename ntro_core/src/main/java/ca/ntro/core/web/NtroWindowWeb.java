package ca.ntro.core.web;

import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.mvc.view.NtroView;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.web.dom.HtmlDocument;

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
	public void installRootView(NtroView view) {
		T.call(this);
		
	}

}
