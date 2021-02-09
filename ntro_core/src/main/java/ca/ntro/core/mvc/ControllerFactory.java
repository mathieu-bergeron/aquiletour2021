package ca.ntro.core.mvc;

import ca.ntro.core.Path;
import ca.ntro.core.introspection.Factory;
import ca.ntro.core.system.trace.T;

public class ControllerFactory {

	public static <RC extends NtroRootController> RC createRootController(Class<RC> controllerClass, String path, NtroWindow window, NtroContext context) {
		T.call(ControllerFactory.class);
		
		return createRootController(controllerClass, new Path(path), window, context);
	}

	public static <RC extends NtroRootController> RC createRootController(Class<RC> controllerClass, Path path, NtroWindow window, NtroContext context) {
		T.call(ControllerFactory.class);
		
		RC rootController = Factory.newInstance(controllerClass);

		rootController.setWindow(window);

		rootController.setPath(path);
		
		rootController.setContext(context);
		
		rootController.onCreate();
		
		return rootController;
		
	}

	static <C extends NtroController> C createController(Class<C> controllerClass, Path path, NtroAbstractController parentController, NtroContext context) {
		T.call(ControllerFactory.class);
		
		C controller = Factory.newInstance(controllerClass);

		controller.setParentController(parentController);

		controller.setPath(path);
		
		controller.setContext(context);
		
		controller.onCreate();
		
		return controller;
	}


}
