package ca.aquiletour.core.pages.login;

import ca.ntro.core.mvc.NtroView;

public interface LoginView extends NtroView  {
	
	void displayLoginMessage(String message);
	void displayStep1();
	void displayStep2();
	

}
