package ca.aquiletour.jsweet;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.web.ViewLoaderRegistrationWeb;
import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.jsweet.services.NtroWindowJSweet;

public class AquiletourMainJSweetThin extends AquiletourMain {

	@Override
	protected void runTask() {
		T.call(this);
		
		Log.info("AquiletourMainJSweetThin");
		
		/* TODO: 
		 * if we are on a queue page, connect a WebSocket
		 * and register a model observer for that queue
		 * 
		 */
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
