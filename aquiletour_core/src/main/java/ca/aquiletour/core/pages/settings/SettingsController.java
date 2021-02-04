package ca.aquiletour.core.pages.settings;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageFactory;

public abstract class SettingsController extends NtroController {

	private RootController parentController;
	private ViewLoader viewLoader;
	private SettingsView view;

	public SettingsController(RootController parentController) {
		super();
		T.call(this);

		this.parentController = parentController;
	}

	@Override
	protected void initializeTask() {
		T.call(this);

		viewLoader = createViewLoader(Constants.LANG);
		addSubTask(viewLoader);

		MessageFactory.addMessageReceptor(ShowSettingsMessage.class, new ShowSettingsReceptor(this));
	}

	@Override
	protected void runTask() {
		T.call(this);

		view = (SettingsView) viewLoader.createView();
	}

	@Override
	protected void onFailure(Exception e) {

	}

	public ShowSettingsReceptor createShowSettingsTask() {
		T.call(this);

		return new ShowSettingsReceptor(this);
	}

	public void showSettings() {
		T.call(this);

		parentController.installSubView(view);
	}
}
