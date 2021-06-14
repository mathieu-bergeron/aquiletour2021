package ca.aquiletour.core.pages.root.handlers;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.mvc.WindowViewHandler;
import ca.ntro.core.system.trace.T;

public class RootViewHandler extends WindowViewHandler<RootView> {
	
	
	@Override
	public void handle(NtroWindow window, RootView view) {
		T.call(this);
		
		NtroContext<?,?> context = AquiletourMain.createNtroContext();
		
		window.installRootView(context, view);
	}
}
