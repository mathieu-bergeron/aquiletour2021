package ca.aquiletour.core.pages.login;


import java.io.File;
import java.io.IOException;

import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class LoginController extends NtroController<RootController> {

	@Override
	protected void onCreate() {
		T.call(this);

		setViewLoader(LoginView.class, "fr");
		
		addParentViewMessageHandler(ShowLoginMessage.class, new ShowLoginHandler());
	}

	@Override
	protected void onChangeContext(NtroContext<?> previousContext) {
		
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);
		
	}


}
