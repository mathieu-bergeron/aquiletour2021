package ca.aquiletour.core.pages.queue;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.aquiletour.core.pages.queue.values.ObservableAppointmentMap;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.properties.observable.list.ObservableList;
import ca.ntro.core.system.trace.T;

public class QueueModel extends NtroModel {

	private ObservableAppointmentMap appointments = new ObservableAppointmentMap();
	private List<String> studentIds = new ArrayList<>();
	private int maxId;

	@Override
	public void initializeStoredValues() {
		T.call(this);
	}

	public void addAppointment(Appointment appointment) {
		T.call(this);
		
		setMaxId(getMaxId() + 1);
		String appointmenId = Integer.toString(getMaxId());
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

	public int getMaxId() {
		return maxId;
	}

	public void setMaxId(int maxId) {
		this.maxId = maxId;
	}
	
	
	public void addStudentId(String studentId) {
		T.call(this);

		studentIds.add(studentId);;
	}
	
	public void deleteStudent(String studentId) {
		T.call(this);
		
		studentIds.remove(studentId);;
	}

	public List<String> getStudentIds() {
		T.call(this);

		return studentIds;
	}

	public void setStudentIds(List<String> studentIds) {
		T.call(this);

		this.studentIds = studentIds;
	}
	
	
	
	

}
