package ca.aquiletour.jsweet;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.Constants;
import ca.aquiletour.web.ViewLoaderRegistrationWeb;
import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.system.trace.T;
import ca.ntro.jsweet.services.NtroWindowJSweet;
import ca.ntro.services.Ntro;
import def.dom.PopStateEvent;

import static def.dom.Globals.window;

import java.util.function.Function;

public class AquiletourMainJSweet extends AquiletourMain {
	
	public static void callRouterForCurrentLocation() {
		T.call(AquiletourMainJSweet.class);

		String rawPath = window.location.pathname;
		String rawParameters = window.location.search;
		Ntro.router().sendMessagesFor(Ntro.context(), rawPath, rawParameters);
	}

	@Override
	protected void runTask() {
		T.call(this);

		super.runTask();
		
		if(window.location.pathname.isEmpty()
				|| window.location.pathname.equals("/")) {
			Ntro.router().sendMessagesFor(Ntro.context(), Constants.DASHBOARD_URL_SEGMENT, "");
		}else {
			callRouterForCurrentLocation();
		}
		
		window.onpopstate = new Function<PopStateEvent, Object>() {
			@Override
			public Object apply(PopStateEvent t) {
				T.call(this);

				callRouterForCurrentLocation();

				return null;
			}
		};
	}

	@Override
	protected void registerViewLoaders() {
		T.call(this);
		
		ViewLoaderRegistrationWeb.registerViewLoaders();
	}

	@Override
	protected NtroWindow getWindow() {
		T.call(this);

		return new NtroWindowJSweet();
	}
}
