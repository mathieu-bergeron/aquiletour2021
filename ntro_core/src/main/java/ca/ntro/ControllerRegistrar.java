package ca.ntro;

import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.mvc.NtroRootController;

public interface ControllerRegistrar {

	void registerRootController(Class<? extends NtroRootController> controllerClass);
	void registerSubController(Class<? extends NtroController<?>> controllerClass);

}
