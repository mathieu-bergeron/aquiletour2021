package ca.aquiletour.core.pages.queue;

import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.aquiletour.core.pages.queue.values.ObservableAppointmentList;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class QueueModel extends NtroModel {
	
	private ObservableAppointmentList appointments = new ObservableAppointmentList();

	@Override
	public void initializeStoredValues() {
		// TODO Auto-generated method stub
		
	}
	
	public void addAppointment(Appointment appointment) {
		T.call(this);
		int id = appointments.size();
		appointment.setAppointmentId(Integer.toString(id));
		appointments.addItem(appointment);
	}
	
	public void deleteAppointment(int appointmentId) {
		T.call(this);
		
		Appointment appointment = appointments.getItem(appointmentId);

		appointments.removeItem(appointment);;
	}

	public ObservableAppointmentList getAppointments() {
		return appointments;
	}

	public void setAppointments(ObservableAppointmentList appointments) {
		this.appointments = appointments;
	}

}
