package ca.aquiletour.core.pages.queue;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.dashboard.DashboardModel;
import ca.aquiletour.core.pages.dashboard.DashboardView;
import ca.aquiletour.core.pages.dashboard.DashboardViewModel;
import ca.aquiletour.core.pages.dashboard.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboard.messages.AddCourseReceptor;
import ca.aquiletour.core.pages.dashboard.messages.ShowDashboardMessage;
import ca.aquiletour.core.pages.dashboard.messages.ShowDashboardReceptor;
import ca.aquiletour.core.pages.dashboard.values.CourseSummary;
import ca.aquiletour.core.pages.queue.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.messages.AddAppointmentReceptor;
import ca.aquiletour.core.pages.queue.messages.ShowQueueMessage;
import ca.aquiletour.core.pages.queue.messages.ShowQueueReceptor;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.services.stores.LocalStore;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageFactory;

public abstract class QueueController extends NtroController {

	
	private RootController parentController;

	private ViewLoader viewLoader;
	private QueueView view;

	private ModelLoader modelLoader;
	private QueueModel model;
	
	private QueueViewModel viewModel;

	private ViewLoader appointmentViewLoader;

	public QueueController(RootController parentController) {
		super();
		T.call(this);

		this.parentController = parentController;
	}
	
	@Override
	protected void initializeTask() {
		// TODO Auto-generated method stub
		T.here();
		T.call(this);
		
		viewModel = new QueueViewModel();

		viewLoader = createViewLoader(Constants.LANG);
		addSubTask(viewLoader);
		
		modelLoader = LocalStore.getLoader(QueueModel.class, "testId1");
		addSubTask(modelLoader);

		appointmentViewLoader = createAppointmentViewLoader(Constants.LANG);
		appointmentViewLoader.setTaskId("appointmentViewLoader");             // XXX: must have taskId as there
		addSubTask(appointmentViewLoader);                                      //      is 2 ViewLoader subTask

		MessageFactory.addMessageReceptor(ShowQueueMessage.class, new ShowQueueReceptor(this)); 
		MessageFactory.addMessageReceptor(AddAppointmentMessage.class, new AddAppointmentReceptor(this));
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		// TODO Auto-generated method stub
		view = (QueueView) viewLoader.createView();
		view.setAppointmentViewLoader(appointmentViewLoader);

		model  = (QueueModel) modelLoader.getModel();
		
		viewModel.observeAndDisplay(model, view);

		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}
	
	public void showQueue() {
		T.call(this);

		parentController.installSubView(view);
	}

	public ShowQueueReceptor createShowQueueTask() {
		T.call(this);

		return new ShowQueueReceptor(this);
	}

	public void addAppointement(Appointment appointment) {
		T.call(this);
		
		model.addAppointment(appointment);
		model.save();
	}
	public void deleteAppointement(int appointmentId) {
		T.call(this);
		
		model.deleteAppointment(appointmentId);
		model.save();
	}
	
	protected abstract ViewLoader createAppointmentViewLoader(String lang);

}
