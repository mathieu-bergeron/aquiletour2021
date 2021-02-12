package ca.aquiletour.core.pages.queue.handlers;


import java.util.Map;

import ca.aquiletour.core.pages.queue.AppointmentView;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.models.properties.observable.map.MapObserver;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class QueueViewModel 
       extends ModelViewSubViewHandler<QueueModel, QueueView>  {

	@Override
	protected void handle(QueueModel model, QueueView view, ViewLoader subViewLoader) {
		T.call(this);
		
		model.getAppointments().observe(new MapObserver<Appointment>() {

			@Override
			public void onValueChanged(Map<String, Appointment> oldValue, Map<String, Appointment> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValue(Map<String, Appointment> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeleted(Map<String, Appointment> lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onEntryAdded(String key, Appointment value) {
				T.call(this);

				AppointmentView appointmentView = (AppointmentView) subViewLoader.createView();
				
				appointmentView.displayAppointement(value);
				
				view.appendAppointement(value, appointmentView);
			}

			@Override
			public void onEntryRemoved(String key, Appointment value) {
				T.call(this);

				view.deleteAppointment(key);
			}
		});
	}
}
