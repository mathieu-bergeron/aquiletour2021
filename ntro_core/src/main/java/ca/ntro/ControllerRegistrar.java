package ca.ntro;

import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.messages.NtroMessage;

public interface ControllerRegistrar {

	void registerRootView(Class<? extends NtroView> viewClass);
	void registerController(Class<? extends NtroController<?>> controllerClass, String pathName, Class<? extends NtroMessage> showMessage);

}
