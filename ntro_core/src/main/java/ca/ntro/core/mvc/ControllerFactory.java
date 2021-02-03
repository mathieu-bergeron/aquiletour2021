package ca.ntro.core.mvc;

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
		
		initializeController(rootController, path);
		
		rootController.setWindow(window);
		
		return rootController;
		
	}

	public static <C extends NtroController> C createController(Class<C> controllerClass, Path path) {
		T.call(ControllerFactory.class);
		
		C controller = Factory.newInstance(controllerClass);
		
		initializeController(controller, path);
		
		return controller;
	}

	private static <CB extends ControllerBase> void initializeController(CB controller, Path path) {
		T.call(ControllerFactory.class);

		controller.setPath(path);
		
		controller.initialize();
	}
	
	public static <C extends NtroController> C createController(Class<C> controllerClass, String path) {
		T.call(ControllerFactory.class);

		return createController(controllerClass, new Path(path));
	}


}
