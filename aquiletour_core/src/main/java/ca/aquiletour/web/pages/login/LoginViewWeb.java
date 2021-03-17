package ca.aquiletour.web.pages.login;

import ca.aquiletour.core.pages.login.LoginView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
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

	@Override
	public void displayStep1() {
		T.call(this);

		HtmlElement loginFormStep1 = getRootElement().find("#login-step1").get(0);
		HtmlElement loginFormStep2 = getRootElement().find("#login-step2").get(0);

		MustNot.beNull(loginFormStep1);
		MustNot.beNull(loginFormStep2);
		
		loginFormStep1.show();
		loginFormStep2.hide();
	}

	@Override
	public void displayStep2() {
		T.call(this);

		HtmlElement loginFormStep1 = getRootElement().find("#login-step1").get(0);
		HtmlElement loginFormStep2 = getRootElement().find("#login-step2").get(0);

		MustNot.beNull(loginFormStep1);
		MustNot.beNull(loginFormStep2);
		
		loginFormStep1.hide();
		loginFormStep2.show();
	}
}
