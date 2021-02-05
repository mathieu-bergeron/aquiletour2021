package ca.aquiletour.core.pages.queue;

import java.util.List;

import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.NtroViewModel;
import ca.ntro.core.models.properties.observable.list.ListObserver;
import ca.ntro.core.mvc.view.NtroView;
import ca.ntro.core.system.trace.T;

public class QueueViewModel implements NtroViewModel {

	@Override
	public void observeAndDisplay(NtroModel model, NtroView view) {
		T.call(this);
		
		QueueModel queueModel = (QueueModel) model;
		QueueView queueView = (QueueView) view;
		
		queueModel.getAppointments().observe(new ListObserver<Appointment>() {
			
			@Override
			public void onItemRemoved(int index, Appointment item) {
				// TODO Auto-generated method stub
				
				queueView.deleteAppointment(item);
			}
			
			@Override
			public void onItemUpdated(int index, Appointment item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemAdded(int index, Appointment item) {
				// TODO Auto-generated method stub
				
				queueView.appendAppointement(item);
			}
			
			@Override
			public void onDeleted(List<Appointment> lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(List<Appointment> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValueChanged(List<Appointment> oldValue, List<Appointment> value) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

}
