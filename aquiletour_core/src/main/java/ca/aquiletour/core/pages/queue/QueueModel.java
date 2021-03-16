package ca.aquiletour.core.pages.queue;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.aquiletour.core.pages.queue.values.ObservableAppointmentList;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class QueueModel implements NtroModel {

	private ObservableAppointmentList appointments = new ObservableAppointmentList();
	private List<String> studentIds = new ArrayList<>();
	private int maxId;
	private String teacherId = "";
	private String courseId = "";

	public void removeStudentFromClass(String studentId) {
		T.call(this);
		for (int i = 0; i < studentIds.size(); i++) {
			if (studentIds.get(i).equals(studentId)) {
				studentIds.remove(i);
			}
		}
	}

	public void addStudentToClass(String studentId) {
		T.call(this);
		boolean alreadyExists = false;
		for (int i = 0; i < studentIds.size() && !alreadyExists; i++) {
			if (studentIds.get(i).equals(studentId)) {
				alreadyExists = true;
			}
		}
		if (!alreadyExists) {
			studentIds.add(studentId);
		}
	}

	public void addAppointment(Appointment appointment) {
		T.call(this);

		setMaxId(getMaxId() + 1);
		String appointmentId = Integer.toString(getMaxId());
		appointment.setId(appointmentId);
		appointments.addItem(appointment);
		;
	}

	public void deleteAppointment(String appointmentId) {
		T.call(this);
		for (int i = 0; i < appointments.size(); i++) {
			if (appointments.item(i).getId().equals(appointmentId)) {
				appointments.removeItem(appointments.item(i));
			}
		}
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

		studentIds.add(studentId);
		;
	}

	public void deleteStudent(String studentId) {
		T.call(this);

		studentIds.remove(studentId);
		;
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
			Appointment currentAppointment = appointments.item(i);
			if (currentAppointment.getId().equals(appointmentDestinationId)) {
				appointmentDestination = currentAppointment;
			} else if (currentAppointment.getId().equals(appointmentDepartureId)) {
				appointmentDeparture = currentAppointment;
			}
		}
		if (appointmentDestination != null && appointmentDeparture != null) {
			int destinationIndex = appointments.indexOf(appointmentDestination);
			int departureIndex = appointments.indexOf(appointmentDeparture);

			appointments.insertItem(destinationIndex + 1, appointmentDeparture);
			appointments.removeItem(appointmentDestination);
			appointments.insertItem(departureIndex + 1, appointmentDestination);
			appointments.removeItem(appointmentDeparture);
		}
	}

	public void removeAllAppointmentsOfStudent(String studentId) {
		ObservableAppointmentList copy = this.appointments;
		for (int i = 0; i < copy.size(); i++) {
			if (copy.item(i).getStudentId().equals(studentId)) {// if appointment is owned by student
				appointments.removeItem(copy.item(i));
				setMaxId(getMaxId() - 1);
				;
			}
		}
	}

	public String receiveStudentIdOfAppointment(String appointmentId) {
		String studentId = null;
		for (int i = 0; i < appointments.size(); i++) {
			Appointment appointment = appointments.item(i);
			if (appointment.getId().equals(appointmentId)) {
				studentId = appointment.getStudentId();
			}
		}
		return studentId;

	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public void clearQueue() {
		appointments.clearItems();
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
}
