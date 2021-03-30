package ca.aquiletour.core.pages.queue;


import java.util.List;

import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.models.listeners.ListObserver;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class QueueViewModel extends ModelViewSubViewHandler<QueueModel, QueueView>  {
	
	@Override
	protected void handle(QueueModel model, QueueView view, ViewLoader subViewLoader) {
		T.call(this);
		
		view.clearQueue();
		
		view.initializeCloseQueueButton(model.getCourseId());

		model.getAppointments().observe(new ListObserver<Appointment>() {

			@Override
			public void onValueChanged(List<Appointment> oldValue, List<Appointment> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValue(List<Appointment> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeleted(List<Appointment> lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemAdded(int index, Appointment item) {
				// TODO Auto-generated method stub
				T.call(this);
				
				String currentUserId = Ntro.userService().user().getId();
				
				AppointmentView appointmentView = (AppointmentView) subViewLoader.createView();
				
				appointmentView.displayAppointement(model.getCourseId(), currentUserId, item);
				
				view.insertAppointment(index, appointmentView);
			}

			@Override
			public void onItemUpdated(int index, Appointment item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemRemoved(int index, Appointment item) {
				// TODO Auto-generated method stub
				T.call(this);
				
				view.deleteAppointment(item.getId());
			}

			@Override
			public void onClearItems() {
				view.clearQueue();
			}
		});
	}
}
