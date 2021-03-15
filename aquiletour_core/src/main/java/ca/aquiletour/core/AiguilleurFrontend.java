package ca.aquiletour.core;

import ca.ntro.RootControllerRegistrar;
import ca.aquiletour.core.pages.root.AiguilleurRootController;
import ca.ntro.MessageRegistrar;
import ca.ntro.ModelRegistrar;
import ca.ntro.NtroFrontend;

public abstract class AiguilleurFrontend implements NtroFrontend {

	@Override
	public void registerModels(ModelRegistrar registrar) {
	}

	@Override
	public void registerMessages(MessageRegistrar registrar) {
	}

	@Override
	public void registerRootController(RootControllerRegistrar registrar) {

		registrar.registerRootController(AiguilleurRootController.class);
	}

}
