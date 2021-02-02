package ca.ntro.core.mvc;

import ca.ntro.core.Path;
import ca.ntro.core.introspection.Factory;
import ca.ntro.core.system.trace.T;

public class ControllerFactory {

	public static <C extends NtroController> C createController(Class<C> controllerClass, Path path) {
		T.call(ControllerFactory.class);
		
		C controller = Factory.newInstance(controllerClass);
		
		controller.setPath(path);
		
		controller.initialize();
		
		return controller;
	}
	
	public static <C extends NtroController> C createController(Class<C> controllerClass, String path) {
		T.call(ControllerFactory.class);

		return createController(controllerClass, new Path(path));
	}


}
