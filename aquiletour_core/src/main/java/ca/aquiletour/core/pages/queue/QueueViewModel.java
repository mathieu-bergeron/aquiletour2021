package ca.aquiletour.core.pages.queue;


import java.util.List;

import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.models.properties.observable.list.ListObserver;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class QueueViewModel extends ModelViewSubViewHandler<QueueModel, QueueView>  {
	
	@Override
	protected void handle(QueueModel model, QueueView view, ViewLoader subViewLoader) {
		T.call(this);
		T.here();//TODO goes here
		
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
				T.here();
				AppointmentView appointmentView = (AppointmentView) subViewLoader.createView();
				
				appointmentView.displayAppointement(item);
				
				//view.insertAppointment(index, item, appointmentView);
				view.appendAppointement(item, appointmentView);
			}

			@Override
			public void onItemUpdated(int index, Appointment item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemRemoved(int index, Appointment item) {
				// TODO Auto-generated method stub
				T.call(this);

				view.deleteAppointment(index);
			}
		});
	}
}
