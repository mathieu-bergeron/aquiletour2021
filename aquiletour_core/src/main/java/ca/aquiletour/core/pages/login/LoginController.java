package ca.aquiletour.core.pages.login;


import java.io.File;
import java.io.IOException;

import ca.aquiletour.core.pages.root.AiguilleurRootController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class LoginController extends NtroController<AiguilleurRootController> {

	@Override
	protected void onCreate() {
		T.call(this);

		setViewLoader(LoginView.class, "fr");
		
		addParentViewMessageHandler(ShowLoginMessage.class, new ShowLoginHandler());
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
