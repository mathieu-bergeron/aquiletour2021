package ca.aquiletour.core.pages.dashboard;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.dashboard.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboard.messages.AddCourseReceptor;
import ca.aquiletour.core.pages.dashboard.messages.ShowDashboardMessage;
import ca.aquiletour.core.pages.dashboard.messages.ShowDashboardReceptor;
import ca.aquiletour.core.pages.dashboard.values.CourseSummary;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.services.stores.LocalStore;
import ca.ntro.core.services.stores.MemoryStore;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageFactory;

public abstract class DashboardController extends NtroController {

	private RootController parentController;

	private ViewLoader viewLoader;
	private DashboardView view;

	private ModelLoader modelLoader;
	private DashboardModel model;
	
	private DashboardViewModel viewModel;

	private ViewLoader courseSummaryViewLoader;

	public DashboardController(RootController parentController) {
		super();
		T.call(this);

		this.parentController = parentController;
	}

	@Override
	protected void initializeTask() {
		T.call(this);
		
		viewModel = new DashboardViewModel();

		viewLoader = createViewLoader(Constants.LANG);
		addSubTask(viewLoader);
		
		modelLoader = LocalStore.getLoader(DashboardModel.class, "testId");
		addSubTask(modelLoader);

		courseSummaryViewLoader = createCourseSummaryViewLoader(Constants.LANG);
		courseSummaryViewLoader.setTaskId("courseSummaryViewLoader");             // XXX: must have taskId as there
		addSubTask(courseSummaryViewLoader);                                      //      is 2 ViewLoader subTask

		MessageFactory.addMessageReceptor(ShowDashboardMessage.class, new ShowDashboardReceptor(this));
		MessageFactory.addMessageReceptor(AddCourseMessage.class, new AddCourseReceptor(this));
	}


	@Override
	protected void runTaskAsync() {
		T.call(this);

		view = (DashboardView) viewLoader.createView();
		view.setCourseSummaryViewLoader(courseSummaryViewLoader);

		model  = (DashboardModel) modelLoader.getModel();
		
		viewModel.observeAndDisplay(model, view);

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

	public void addCourse(CourseSummary course) {
		T.call(this);
		
		model.addCourse(course);
		model.save();
	}

	protected abstract ViewLoader createCourseSummaryViewLoader(String lang);

}
