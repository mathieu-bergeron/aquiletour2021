package ca.aquiletour.core.pages.queue;

import java.awt.List;

import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.aquiletour.core.pages.queue.values.ObservableAppointmentMap;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class QueueModel extends NtroModel {

	private ObservableAppointmentMap appointments = new ObservableAppointmentMap();
	private List studentIds = new List();

	@Override
	public void initializeStoredValues() {
		T.call(this);
	}

	public void addAppointment(Appointment appointment) {
		T.call(this);

		String appointmenId = Integer.toString(appointments.size());
		appointment.setAppointmentId(appointmenId);
		appointments.addEntry(appointmenId, appointment);
	}
	
	public void deleteAppointment(String appointmentId) {
		T.call(this);
		
		appointments.removeEntry(appointmentId);
	}

	public ObservableAppointmentMap getAppointments() {
		T.call(this);

		return appointments;
	}

	public void setAppointments(ObservableAppointmentMap appointments) {
		T.call(this);

		this.appointments = appointments;
	}
	
	

}
