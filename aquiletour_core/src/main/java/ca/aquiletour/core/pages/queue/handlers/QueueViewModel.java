package ca.aquiletour.core.pages.queue.handlers;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.aquiletour.core.pages.queue.models.QueueModel;
import ca.aquiletour.core.pages.queue.teacher.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.views.AppointmentView;
import ca.aquiletour.core.pages.queue.views.QueueView;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageHandler;
import ca.ntro.services.ModelObserver;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;

public abstract class QueueViewModel<V extends QueueView> extends ModelViewSubViewHandler<QueueModel, V>  {
	
	private ModelObserver currentObserver = null;
	private QueueModel currentModel = null;

	@Override
	protected void handle(QueueModel model, V view, ViewLoader subViewLoader) {
		T.call(this);
		
		ModelStoreSync modelStore = new ModelStoreSync(Ntro.modelStore());
		
		if(currentObserver != null && currentModel != null) {
			modelStore.removeObserver(currentModel, currentObserver);
			currentModel = model;
		}
		
		currentObserver = modelStore.observeModel(model, new ModelObserver() {
			@Override
			public void onModelUpdate(NtroModel updatedModel) {
				T.call(this);
				
				QueueModel queueModel = (QueueModel) updatedModel;

				observeQueueModel(view, subViewLoader, queueModel);
			}
		});
		
		// XXX: hide appointment as soon as user clicks 
		//      delete appointment when we get confirmation from server
		Ntro.messages().registerHandler(DeleteAppointmentMessage.class, new MessageHandler<DeleteAppointmentMessage>() {

			@Override
			public void handle(DeleteAppointmentMessage message) {
				T.call(this);

				String appointementId = message.getAppointmentId();
				String subViewId = Appointment.subViewId(appointementId);

				view.hideSubView(subViewId);

				Ntro.backendService().sendMessageToBackend(message);
			}
		});
	}

	protected void observeQueueModel(V view, ViewLoader subViewLoader, QueueModel queueModel) {
		T.call(this);
		
		List<String> subViewsToShow = new ArrayList<>();
		
		queueModel.getAppointmentById().forEachEntry((appointmentId, appointment) -> {
			
			int index = queueModel.appointmentIndexById(appointmentId);
			
			String appointmentViewId = appointment.subViewId();

			subViewsToShow.add(appointmentViewId);
			
			displayOrUpdateAppointment(queueModel, 
									   view, 
									   subViewLoader, 
									   appointmentViewId,
									   index, 
									   appointment);
		});

		view.deleteSubViewsNotInList(subViewsToShow);
		
		queueModel.getAppointmentsInOrder().forEachItem((index, appointmentId) -> {

			String subViewId = Appointment.subViewId(appointmentId);
			view.moveAppointment(index, subViewId);
		});
	}

	private void displayOrUpdateAppointment(QueueModel model, 
			                                V view, 
			                                ViewLoader subViewLoader, 
			                                String appointmentViewId,
			                                int appointmentIndex, 
			                                Appointment appointment) {
		T.call(this);
		
		String currentUserId = Ntro.currentUser().getId();
		boolean displayTime = model.shouldShowAppointmentTimes();
		
		AppointmentView appointmentView = (AppointmentView) view.findSubView(view.appointmentViewClass(), appointmentViewId);
		
		if(appointmentView != null) {

			appointmentView.initializeView(AquiletourMain.createNtroContext());
			appointmentView.updateAppointment(appointmentIndex, displayTime, appointment);
			
		}else {

			appointmentView = (AppointmentView) subViewLoader.createView(AquiletourMain.createNtroContext());

			appointmentView.displayAppointement(model.getQueueId(), 
					                            currentUserId, 
					                            appointmentViewId,
					                            appointmentIndex,
					                            displayTime,
					                            appointment);

			view.insertAppointment(appointmentIndex, appointmentView);
			
			onNewAppointment(view, appointment);
			
		}
	}

	protected abstract void onNewAppointment(V view, Appointment appointment);
}
