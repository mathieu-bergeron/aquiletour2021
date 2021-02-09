package ca.aquiletour.core.pages.login;

import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class LoginController extends NtroController<RootController> {

	@Override
	protected void initialize() {
		T.call(this);

		setViewLoader(LoginView.class, "fr");

		addParentViewMessageHandler(ShowLoginMessage.class, new ShowLoginHandler());
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);
		
	}

}
