package ca.ntro.web;

import ca.ntro.core.Path;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlDocument;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public abstract class NtroWindowWeb extends NtroWindow {

	protected abstract HtmlDocument getDocument();

	public void writeHtml(StringBuilder out) {
		T.call(this);

		getDocument().writeHtml(out);
	}

	@Override
	public void installRootView(NtroContext<?,?> context, NtroView rootView) {
		T.call(this);

		HtmlElement body = getDocument().select("body").get(0);

		NtroViewWeb rootViewWeb = (NtroViewWeb) rootView;
		
		if(body.children("*").size() == 0) {

			rootViewWeb.initializeView(context);
			
			rootViewWeb.getRootElement().setAttribute("id", Ntro.introspector().getSimpleNameForClass(rootView.getClass()));
			
			body.appendElement(rootViewWeb.getRootElement());

			Log.info("[installRootview] adding rootView: " + rootViewWeb.getRootElement().getAttribute("id"));

		} else if(body.children("*").size() == 1) {
			
			HtmlElement rootElement = body.children("*").get(0);

			rootViewWeb.setRootElement(rootElement);

			rootViewWeb.initializeView(context);
			
			Log.info("[installRootView] using existing rootView: " + rootViewWeb);

		}else {
			
			Log.warning("[installRootView] body must have at most 1 child node");
		}
	}

	public void setCurrentPath(Path path) {
		T.call(this);

		HtmlElement body = getDocument().select("body").get(0);

		//body.setAttribute("current-path", path.toString());
	}


}
