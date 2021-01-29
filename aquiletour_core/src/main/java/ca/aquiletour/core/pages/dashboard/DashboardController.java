package ca.aquiletour.core.pages.dashboard;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageFactory;

public abstract class DashboardController extends NtroController {

	private RootController parentController;
	private ViewLoader viewLoader;
	private DashboardView view;

	public DashboardController(RootController parentController) {
		super();
		T.call(this);

		this.parentController = parentController;
	}

	@Override
	protected void initializeTask() {
		T.call(this);

		viewLoader = createViewLoader(Constants.LANG);
		addSubTask(viewLoader);

		MessageFactory.addMessageReceptor(ShowDashboardMessage.class, new ShowDashboardReceptor(this));
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);

		view = (DashboardView) viewLoader.getView();

		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub

	}

	public void showDashboard() {
		T.call(this);

		parentController.installSubView(view);
	}

	public ShowDashboardReceptor createShowDashboardTask() {
		T.call(this);

		return new ShowDashboardReceptor(this);
	}


}
