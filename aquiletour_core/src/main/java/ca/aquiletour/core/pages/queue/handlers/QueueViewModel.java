package ca.aquiletour.core.pages.queue.handlers;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.aquiletour.core.pages.queue.models.QueueModel;
import ca.aquiletour.core.pages.queue.views.AppointmentView;
import ca.aquiletour.core.pages.queue.views.QueueView;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.listeners.MapObserver;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelObserver;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;

public abstract class QueueViewModel<V extends QueueView> extends ModelViewSubViewHandler<QueueModel, V>  {
	
	//private ModelObserver currentObserver = null;
	//private QueueModel currentModel = null;

	@Override
	protected void handle(QueueModel model, V view, ViewLoader subViewLoader) {
		T.call(this);
		
		model.getAppointmentById().removeObservers();
		model.getAppointmentById().observe(new MapObserver<Appointment>() {
			
			@Override
			public void onEntryAdded(String appointmentId, Appointment appointment) {
				T.call(this);

				int index = model.appointmentIndexById(appointmentId);
				
				String appointmentViewId = appointment.subViewId();

				displayOrUpdateAppointment(model, 
										   view, 
										   subViewLoader, 
										   appointmentViewId,
										   index, 
										   appointment);
			}

			@Override
			public void onEntryRemoved(String appointmentId, Appointment appointment) {
				T.call(this);

				String appointmentViewId = appointment.subViewId();
				
				view.deleteSubView(appointmentViewId);
			}
			
			@Override
			public void onClearEntries() {
				T.call(this);

				view.deleteSubViewsNotInList(new ArrayList<>());
			}
			
			@Override
			public void onDeleted(Map<String, Appointment> lastValue) {
			}
			
			@Override
			public void onValue(Map<String, Appointment> value) {
			}
			
			@Override
			public void onValueChanged(Map<String, Appointment> oldValue, Map<String, Appointment> value) {
			}
		});
		
		/*
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
				
				List<String> appointmentViewIds = new ArrayList<>();
				
				queueModel.getAppointmentById().forEachEntry((appointmentId, appointment) -> {
					
					int index = queueModel.appointmentIndexById(appointmentId);
					
					String appointmentViewId = appointment.subViewId();
					
					appointmentViewIds.add(appointmentViewId);
					
					displayOrUpdateAppointment(queueModel, 
							                   view, 
							                   subViewLoader, 
							                   appointmentViewId,
							                   index, 
							                   appointment);
				});

				view.deleteSubViewsNotInList(appointmentViewIds);
			}
		});
		*/
	}

	private void displayOrUpdateAppointment(QueueModel model, 
			                                V view, 
			                                ViewLoader subViewLoader, 
			                                String appointmentViewId,
			                                int index, 
			                                Appointment appointment) {


		String currentUserId = Ntro.currentUser().getId();
		boolean displayTime = model.shouldShowAppointmentTimes();
		
		AppointmentView appointmentView = (AppointmentView) view.findSubView(appointmentViewClass(), appointmentViewId);

		if(appointmentView != null) {

			appointmentView.initializeView(AquiletourMain.createNtroContext());
			appointmentView.updateAppointment(displayTime, appointment);
			
		}else {

			appointmentView = (AppointmentView) subViewLoader.createView();

			appointmentView.displayAppointement(model.getQueueId(), 
					                            currentUserId, 
					                            appointmentViewId,
					                            displayTime,
					                            appointment);

			view.insertAppointment(index, appointmentView);
		}
	}

	protected abstract Class<? extends AppointmentView> appointmentViewClass();

}
