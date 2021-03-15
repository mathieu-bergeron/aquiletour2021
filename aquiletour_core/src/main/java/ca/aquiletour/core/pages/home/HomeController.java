package ca.aquiletour.core.pages.home;

import ca.aquiletour.core.pages.root.AiguilleurRootController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class HomeController extends NtroController<AiguilleurRootController> {

	@Override
	protected void onCreate() {
		T.call(this);

		setViewLoader(HomeView.class, "fr");

		addParentViewMessageHandler(ShowHomeMessage.class, new ShowHomeHandler());
	}

	@Override
	protected void onChangeContext(NtroContext<?> previousContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);
		
	}


}
