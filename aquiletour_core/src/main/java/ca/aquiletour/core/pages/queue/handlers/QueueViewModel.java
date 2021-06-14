package ca.aquiletour.core.pages.queue.handlers;


import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.aquiletour.core.pages.queue.models.QueueModel;
import ca.aquiletour.core.pages.queue.views.AppointmentView;
import ca.aquiletour.core.pages.queue.views.QueueView;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelObserver;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;

public abstract class QueueViewModel<V extends QueueView> extends ModelViewSubViewHandler<QueueModel, V>  {

	@Override
	protected void handle(QueueModel model, V view, ViewLoader subViewLoader) {
		T.call(this);
		
		ModelStoreSync modelStore = new ModelStoreSync(Ntro.modelStore());
		
		modelStore.removeObservers(model);
		modelStore.observeModel(model, new ModelObserver() {
			@Override
			public void onModelUpdate(NtroModel updatedModel) {
				T.call(this);
				
				QueueModel queueModel = (QueueModel) updatedModel;
				
				List<String> currentAppointmentIds = new ArrayList<>();
				
				queueModel.getAppointments().forEachItem((index, appointment) -> {
					
					currentAppointmentIds.add(Appointment.htmlId(appointment));
					
					displayOrUpdateAppointment(queueModel, view, subViewLoader, index, appointment);
				});
				
				view.deleteAppointmentsNotInList(currentAppointmentIds);
			}
		});
	}

	private void displayOrUpdateAppointment(QueueModel model, 
			                                V view, 
			                                ViewLoader subViewLoader, 
			                                int index, 
			                                Appointment appointment) {

		String currentUserId = Ntro.currentUser().getId();
		
		AppointmentView appointmentView = (AppointmentView) view.findSubView(appointmentViewClass(), Appointment.htmlId(appointment));
		
		if(appointmentView != null) {

			appointmentView.initializeView(AquiletourMain.createNtroContext());
			appointmentView.updateAppointment(appointment);
			
		}else {

			appointmentView = (AppointmentView) subViewLoader.createView();
			appointmentView.displayAppointement(model.getQueueId(), currentUserId, model.getMainSettings().getShowAppointmentTimes().getValue(), appointment);

			view.insertAppointment(index, appointmentView);
		}
	}

	protected abstract Class<? extends AppointmentView> appointmentViewClass();

}
