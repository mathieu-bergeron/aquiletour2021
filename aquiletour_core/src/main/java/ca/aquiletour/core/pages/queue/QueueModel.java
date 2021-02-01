package ca.aquiletour.core.pages.queue;

import ca.aquiletour.core.pages.queue.values.ObservableAppointmentList;
import ca.ntro.core.models.NtroModel;

public class QueueModel extends NtroModel {
	
	private ObservableAppointmentList appointments = new ObservableAppointmentList();

	@Override
	public void initializeStoredValues() {
		// TODO Auto-generated method stub
		
	}

	public ObservableAppointmentList getAppointments() {
		return appointments;
	}

	public void setAppointments(ObservableAppointmentList appointments) {
		this.appointments = appointments;
	}

}
