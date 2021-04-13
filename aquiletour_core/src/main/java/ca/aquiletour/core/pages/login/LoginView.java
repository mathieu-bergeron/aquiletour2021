package ca.aquiletour.core.pages.login;

import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;

public interface LoginView extends NtroView  {
	
	void displayLoginMessage(String message);
	void hideLoginMessage();
	void displayStep1();
	void displayStep2();
	void hideSteps();
	void selectLoginStep(NtroContext<?,?> context);
}
