package ca.ntro.core.mvc;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.Path;
import ca.ntro.core.introspection.Factory;
import ca.ntro.core.system.trace.T;

public class ControllerFactory {

	public static <RC extends NtroRootController> RC createRootController(Class<RC> controllerClass, String path, NtroWindow window) {
		T.call(ControllerFactory.class);
		
		return createRootController(controllerClass, new Path(path), window);
	}

	public static <RC extends NtroRootController> RC createRootController(Class<RC> controllerClass, Path path, NtroWindow window) {
		T.call(ControllerFactory.class);
		
		RC rootController = Factory.newInstance(controllerClass);

		rootController.setWindow(window);

		rootController.setPath(path);
		
		rootController.initialize();
		
		return rootController;
		
	}

	static <C extends NtroController> C createController(Class<C> controllerClass, Path path, NtroAbstractController parentController) {
		T.call(ControllerFactory.class);
		
		C controller = Factory.newInstance(controllerClass);

		controller.setPath(path);
		
		controller.setParentController(parentController);

		controller.initialize();
		
		return controller;
	}


}
