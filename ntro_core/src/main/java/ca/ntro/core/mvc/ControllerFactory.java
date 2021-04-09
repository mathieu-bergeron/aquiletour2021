package ca.ntro.core.mvc;

import ca.ntro.core.Path;
import ca.ntro.core.introspection.Factory;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class ControllerFactory {

	public static <RC extends NtroRootController> RC createRootController(Class<RC> controllerClass, String path, NtroWindow window, NtroContext context) {
		T.call(ControllerFactory.class);
		
		return createRootController(controllerClass, new Path(path), window, context);
	}

	public static <RC extends NtroRootController> RC createRootController(Class<RC> controllerClass, Path path, NtroWindow window, NtroContext context) {
		T.call(ControllerFactory.class);
		
		RC rootController = Ntro.factory().newInstance(controllerClass);
		
		rootController.setWindow(window);

		rootController.setPath(path);
		
		rootController.setContext(context);
		
		rootController.onCreate(context);
		
		return rootController;
		
	}

	static <C extends NtroController> C createController(Class<C> controllerClass, Path path, NtroAbstractController parentController, NtroContext context) {
		T.call(ControllerFactory.class);
		
		C controller = Ntro.factory().newInstance(controllerClass);

		controller.setParentController(parentController);

		controller.setPath(path);
		
		controller.setContext(context);
		
		controller.onCreate(context);
		
		return controller;
	}


}
