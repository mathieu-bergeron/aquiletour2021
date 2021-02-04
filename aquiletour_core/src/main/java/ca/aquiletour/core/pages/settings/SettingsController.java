package ca.aquiletour.core.pages.settings;

import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class SettingsController extends NtroController<RootController> {

<<<<<<< HEAD
=======
	private RootController parentController;
	private ViewLoader viewLoader;
	private SettingsView view;

	public SettingsController(RootController parentController) {
		super();
		T.call(this);

		this.parentController = parentController;
	}

>>>>>>> main
	@Override
	protected void initialize() {
		T.call(this);

		setViewLoader(SettingsView.class, "fr");

<<<<<<< HEAD
		addParentViewMessageHandler(ShowSettingsMessage.class, new ShowSettingsHandler());
=======
		MessageFactory.addMessageReceptor(ShowSettingsMessage.class, new ShowSettingsReceptor(this));
	}

	@Override
	protected void runTask() {
		T.call(this);

		view = (SettingsView) viewLoader.createView();
>>>>>>> main
	}

	@Override
	protected void onFailure(Exception e) {
<<<<<<< HEAD
		T.call(this);
		
	}

=======

	}

	public ShowSettingsReceptor createShowSettingsTask() {
		T.call(this);

		return new ShowSettingsReceptor(this);
	}

	public void showSettings() {
		T.call(this);

		parentController.installSubView(view);
	}
>>>>>>> main
}
