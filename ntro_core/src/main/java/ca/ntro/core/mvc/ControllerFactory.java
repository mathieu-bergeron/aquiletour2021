package ca.ntro.core.mvc;

import ca.ntro.core.introspection.Factory;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.Path;

public class ControllerFactory {

	public static <C extends NtroController> C createController(Class<C> controllerClass, Path path) {
		T.call(ControllerFactory.class);
		
		C controller = Factory.newInstance(controllerClass);
		
		controller.initialize();
		
		
		// TODO: automate this according to path and registered controllers
		if(path.startsWith("settings")) {
			
			controller.addSubController(ControllerFactory.createController(SettingsController.class, path.subPath(1)));
			
		}else if(path.startsWith("dashboard")) {

			controller.addSubController(ControllerFactory.createController(DashboardController.class, path.subPath(1)));

		}else if(path.startsWith("*")) {

			// XXX: add every subcontroller
			controller.addSubController(ControllerFactory.createController(SettingsController.class, path));
			controller.addSubController(ControllerFactory.createController(DashboardController.class, path));
		}
		
		
		return controller;
	}
	
	public static <C extends NtroController> C createController(Class<C> controllerClass, String path) {
		T.call(ControllerFactory.class);

		return createController(controllerClass, new Path(path));
	}

	public static void registerController(String string, Class<? extends NtroController> controllerClass) {
		// TODO Auto-generated method stub
		
	}

}
