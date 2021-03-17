package ca.aquiletour.web.pages.login;

import ca.aquiletour.core.messages.UserInitiatesLoginMessage;
import ca.aquiletour.core.messages.UserSendsLoginCodeMessage;
import ca.aquiletour.core.pages.login.LoginView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.mvc.NtroViewWeb;

public class LoginViewWeb extends NtroViewWeb implements LoginView {

	@Override
	public void initializeViewWeb(NtroContext<?> context) {

		HtmlElement loginButtonStep1 = getRootElement().find("#login-button-step1").get(0);
		HtmlElement loginButtonStep2 = getRootElement().find("#login-button-step2").get(0);
		HtmlElement loginInputStep1 = getRootElement().find("#login-input-step1").get(0);
		HtmlElement loginInputStep2 = getRootElement().find("#login-input-step2").get(0);

		MustNot.beNull(loginButtonStep1);
		MustNot.beNull(loginButtonStep2);
		MustNot.beNull(loginInputStep1);
		MustNot.beNull(loginInputStep2);
		
		loginButtonStep1.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);
				
				String userId = loginInputStep1.value();
				System.out.println("userId: " + userId);

				UserInitiatesLoginMessage userInitiatesLoginMessage = Ntro.messages().create(UserInitiatesLoginMessage.class);
				userInitiatesLoginMessage.setProvidedId(userId);
				Ntro.messages().send(userInitiatesLoginMessage);
			}
		});

		loginButtonStep2.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);

				String loginCode = loginInputStep2.value();
				System.out.println("loginCode: " + loginCode);
				
				UserSendsLoginCodeMessage userSendsLoginCodeMessage = Ntro.messages().create(UserSendsLoginCodeMessage.class);
				userSendsLoginCodeMessage.setLoginCode(loginCode);
				Ntro.messages().send(userSendsLoginCodeMessage);
			}
		});
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
