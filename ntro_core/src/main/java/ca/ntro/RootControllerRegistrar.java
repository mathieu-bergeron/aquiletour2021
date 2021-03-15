package ca.ntro;

import ca.ntro.core.mvc.NtroRootController;

public interface RootControllerRegistrar {

	void registerRootController(Class<? extends NtroRootController> rootControllerClass);

}
