package ca.ntro.web.mvc;

import ca.ntro.core.Constants;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.mvc.StringFilter;
import ca.ntro.core.mvc.ViewLoaders;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;

public abstract class NtroViewWeb implements NtroView {

	private HtmlElement rootElement;

	protected abstract void initializeViewWeb(NtroContext<?,?> context);

	@Override
	public void initializeView(NtroContext<?,?> context) {
		T.call(this);

		initializeViewWeb(context);

		getRootElement().initializeJs(Ntro.introspector().getSimpleNameForClass(getClass()));
	}

	public void setRootElement(HtmlElement rootElement) {
		T.call(this);

		this.rootElement = rootElement;
	}

	public HtmlElement getRootElement() {
		T.call(this);

		return rootElement;
	}

	@Override
	public NtroView findSubView(Class<? extends NtroView> subViewClass, String subViewId) {
		T.call(this);

		NtroView subView = null;
		
		String lang = Ntro.currentSession().getLang();
		if(lang == null || lang.isEmpty()) {
			lang = Constants.LANG;
		}
		
		Class<? extends NtroViewWeb> targetClass = ViewLoaders.getViewTargetClassWeb(subViewClass, lang);
		
		if(targetClass != null) {

			HtmlElement subViewElement = getRootElement().find("#" + subViewId).get(0);
			
			if(subViewElement != null) {
				
				NtroViewWeb subViewWeb = Ntro.factory().newInstance(targetClass);
				subViewWeb.setRootElement(subViewElement);

				subView = subViewWeb;
			}
		}

		return subView;
	}

	@Override
	public void displayOrHideSubView(Class<? extends NtroView> subViewClass, StringFilter filter) {
		T.call(this);

		String subViewStyleClass = Ntro.introspector().getSimpleNameForClass(subViewClass);

		getRootElement().find("." + subViewStyleClass).forEach(e -> {
			
			String subViewId = e.getAttribute("id");

			e.display(filter.select(subViewId));
		});
	}
}
