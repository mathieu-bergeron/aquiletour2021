package ca.aquiletour.web.pages.login;

import ca.aquiletour.core.pages.login.LoginView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class LoginViewWeb extends NtroViewWeb implements LoginView {

	@Override
	public void initializeViewWeb(NtroContext<?> context) {

	}

	@Override
	public void displayLoginMessage(String message) {
		
		HtmlElement loginMessage = getRootElement().find("#login-message").get(0);
		
		MustNot.beNull(loginMessage);

		loginMessage.html(message);
	}
}
