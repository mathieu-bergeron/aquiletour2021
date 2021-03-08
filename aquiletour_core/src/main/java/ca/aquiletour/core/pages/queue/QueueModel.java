package ca.aquiletour.core.pages.queue;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.aquiletour.core.pages.queue.values.ObservableAppointmentList;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.properties.observable.list.ObservableList;
import ca.ntro.core.system.trace.T;

public class QueueModel extends NtroModel {

	private static final long serialVersionUID = 2545995167728311236L;
	private ObservableAppointmentList appointments = new ObservableAppointmentList(new ArrayList<>());
	private List<String> studentIds = new ArrayList<>();
	private int maxId;

	@Override
	public void initializeStoredValues() {
		T.call(this);
	}
	
	public void removeStudentFromClass(String studentId) {
		T.call(this);
		for (int i = 0; i < studentIds.size(); i++) {
			if(studentIds.get(i).equals(studentId)) {
				studentIds.remove(i);
			}
		}
	}
	
	public void addStudentToClass(String studentId) {
		T.call(this);
		boolean alreadyExists = false;
		for (int i = 0; i < studentIds.size() && !alreadyExists; i++) {
			if(studentIds.get(i).equals(studentId)) {
				alreadyExists = true;
			}
		}
		if (!alreadyExists) {
			studentIds.add(studentId);
		}
	}
	public void addAppointment(Appointment appointment) {
		T.call(this);
		T.here();
		
		setMaxId(getMaxId() + 1);
		T.values(getMaxId());
		String appointmentId = Integer.toString(getMaxId());
		appointment.setAppointmentId(appointmentId);
		appointments.addItem(appointment);;
	}
	
	public void deleteAppointment(String appointmentId) {
		T.call(this);
		if(getMaxId()>0)setMaxId(getMaxId() - 1);
		for (int i = 0; i < appointments.size(); i++) {
			if(appointments.getItem(i).getAppointmentId().equals(appointmentId)) {
				appointments.removeItem(appointments.getItem(i));
			}
		};
	}
	
	public ObservableAppointmentList getAppointments() {
		T.call(this);
		
		return appointments;
	}
	
	public void setAppointments(ObservableAppointmentList appointments) {
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

	public void updateOrder(String appointmentDestinationId, String appointmentDepartureId) {
		Appointment appointmentDestination = null;
		Appointment appointmentDeparture = null;
		for (int i = 0; i < appointments.size(); i++) {
			Appointment currentAppointment = appointments.getItem(i);
			if(currentAppointment.getAppointmentId().equals(appointmentDestinationId)) {
				appointmentDestination = currentAppointment;
			} else if (currentAppointment.getAppointmentId().equals(appointmentDepartureId)) {
				appointmentDeparture = currentAppointment;
			}
		}
		if(appointmentDestination != null && appointmentDeparture != null) {
			int destinationIndex = appointments.indexOf(appointmentDestination);
			int departureIndex = appointments.indexOf(appointmentDeparture);
			
			appointments.insertItem(destinationIndex + 1, appointmentDeparture );
			appointments.removeItem(appointmentDestination);
			appointments.insertItem(departureIndex + 1, appointmentDestination );
			appointments.removeItem(appointmentDeparture);
		}
	}
	
	
	
	

}
