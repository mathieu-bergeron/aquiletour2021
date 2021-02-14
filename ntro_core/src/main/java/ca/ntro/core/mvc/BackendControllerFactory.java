package ca.ntro.core.mvc;

import ca.ntro.core.introspection.Factory;
import ca.ntro.core.models.ModelStore;
import ca.ntro.core.system.trace.T;

public class BackendControllerFactory {

	public static <RC extends BackendRootController> RC createBackendRootController(Class<RC> controllerClass, ModelStore modelStore) {
		T.call(BackendControllerFactory.class);
		
		RC rootController = Factory.newInstance(controllerClass);

		rootController.setModelStore(modelStore);

		rootController.onCreate();
		
		return rootController;
		
	}

	public static <C extends BackendController<?>> C createBackendController(Class<C> controllerClass, BackendAbstractController parentController) {
		T.call(BackendControllerFactory.class);
		
		C controller = Factory.newInstance(controllerClass);
		
		controller.setParentController(parentController);
		
		controller.setModelStore(parentController.getModelStore());

		controller.onCreate();
		
		return controller;
	}


}
