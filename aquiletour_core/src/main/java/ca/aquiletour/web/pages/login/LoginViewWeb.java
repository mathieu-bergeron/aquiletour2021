package ca.aquiletour.web.pages.login;

import ca.aquiletour.core.messages.UserInitiatesLoginMessage;
import ca.aquiletour.core.messages.UserSendsLoginCodeMessage;
import ca.aquiletour.core.models.users.Guest;
import ca.aquiletour.core.models.users.StudentGuest;
import ca.aquiletour.core.models.users.TeacherGuest;
import ca.aquiletour.core.pages.login.LoginView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.mvc.NtroViewWeb;

public class LoginViewWeb extends NtroViewWeb implements LoginView {

	private HtmlElement loginButtonStep1;
	private HtmlElement loginButtonStep2;
	private HtmlElement loginInputStep1;
	private HtmlElement loginInputStep2;
	private HtmlElement loginFormStep1;
	private HtmlElement loginFormStep2;
	private HtmlElement loginMessage;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

		loginFormStep1 = getRootElement().find("#login-form-step1").get(0);
		loginInputStep1 = getRootElement().find("#login-input-step1").get(0);
		loginButtonStep1 = getRootElement().find("#login-button-step1").get(0);

		loginFormStep2 = getRootElement().find("#login-form-step2").get(0);
		loginInputStep2 = getRootElement().find("#login-input-step2").get(0);
		loginButtonStep2 = getRootElement().find("#login-button-step2").get(0);

	    loginMessage = getRootElement().find("#login-message").get(0);
		
		MustNot.beNull(loginFormStep1);
		MustNot.beNull(loginInputStep1);
		MustNot.beNull(loginButtonStep1);

		MustNot.beNull(loginFormStep2);
		MustNot.beNull(loginInputStep2);
		MustNot.beNull(loginButtonStep2);

		MustNot.beNull(loginMessage);
		
		addListeners();

		selectLoginStep(context);
	}

	private void addListeners() {
		T.call(this);

		loginButtonStep1.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);
				
				String userId = loginInputStep1.value();

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
				
				UserSendsLoginCodeMessage userSendsLoginCodeMessage = Ntro.messages().create(UserSendsLoginCodeMessage.class);
				userSendsLoginCodeMessage.setLoginCode(loginCode);
				Ntro.messages().send(userSendsLoginCodeMessage);
			}
		});
	}

	// FIXME: we would prefer that logic in the controller
	@Override
	public void selectLoginStep(NtroContext<?> context) {
		T.call(this);

		if(context.user() instanceof Guest) {
			
			displayStep1();
			
		}else if(context.user() instanceof TeacherGuest
				|| context.user() instanceof StudentGuest) {
			
			displayStep2();

		}else {

			hideSteps();
		}
	}

	@Override
	public void displayLoginMessage(String message) {
		T.call(this);

		loginMessage.html(message);
		loginMessage.show();
	}

	@Override
	public void hideLoginMessage() {
		T.call(this);

		loginMessage.hide();
	}

	@Override
	public void displayStep1() {
		T.call(this);

		loginFormStep1.show();
		loginFormStep2.hide();
	}

	@Override
	public void displayStep2() {
		T.call(this);

		loginFormStep1.hide();
		loginFormStep2.show();
	}

	@Override
	public void hideSteps() {
		T.call(this);

		loginFormStep1.hide();
		loginFormStep2.hide();
	}
}
