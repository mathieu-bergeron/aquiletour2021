package ca.ntro.core.mvc;

import ca.ntro.core.introspection.Factory;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.Path;

public class ControllerFactory {

	public static <C extends NtroController> C createController(Class<C> controllerClass, Path path) {
		T.call(ControllerFactory.class);
		
		C controller = Factory.newInstance(controllerClass);
		
		controller.initialize(path);
		
		return controller;
	}
	
	public static <C extends NtroController> C createController(Class<C> controllerClass, String path) {
		T.call(ControllerFactory.class);

		return createController(controllerClass, new Path(path));
	}

}
