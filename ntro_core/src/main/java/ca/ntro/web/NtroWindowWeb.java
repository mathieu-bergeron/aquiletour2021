package ca.ntro.web;

import ca.ntro.core.Path;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.system.trace.T;
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
	public void installRootView(NtroView rootView) {
		T.call(this);

		HtmlElement body = getDocument().select("body").get(0);

		NtroViewWeb rootViewWeb = (NtroViewWeb) rootView;
		
		body.deleteChildrenForever();
		body.appendElement(rootViewWeb.getRootElement());
		
		/*
		if(body.children("*").size() == 0) {

			body.appendElement(rootViewWeb.getRootElement());

		}else if(body.children("*").size() == 1){
			
			// TODO: initialize rootViewWeb with existing
			//       body child
			
		}else {
			
			body.removeChildrenFromDocument();
			body.appendElement(rootViewWeb.getRootElement());
		}*/
	}

	public void setCurrentPath(Path path) {
		T.call(this);

		HtmlElement body = getDocument().select("body").get(0);

		//body.setAttribute("current-path", path.toString());
	}


}
