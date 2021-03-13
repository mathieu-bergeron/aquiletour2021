package ca.aquiletour.core;

import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.ControllerRegistrar;
import ca.ntro.MessageRegistrar;
import ca.ntro.ModelRegistrar;
import ca.ntro.NtroApp;

public abstract class AiguilleurApp implements NtroApp {

	@Override
	public void registerModels(ModelRegistrar registrar) {
	}

	@Override
	public void registerMessages(MessageRegistrar registrar) {
	}

	@Override
	public void registerControllers(ControllerRegistrar registrar) {
		registrar.registerRootController(RootController.class);
	}

}
