package ca.aquiletour.core.pages.queue.models;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.semester_list.models.CourseGroup;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;

public class QueueModel implements NtroModel {
	
	private long appointmentDurationSeconds = Constants.APPOINTMENT_DURATION_MINUTES * 60;
	
	private ObservableTime currentTime = new ObservableTime();

	private ObservableAppointmentList appointments = new ObservableAppointmentList();
	private List<String> studentIds = new ArrayList<>();
	private int maxId;
	private String teacherId = "";
	private String courseId = "";
	
	private StoredString currentCourseId = new StoredString();
	private StoredString currentGroupId = new StoredString();
	
	// TODO: the queue is open for some courseGroups
	private List<CourseGroup> openForCourseGroups = new ArrayList<>();
	private List<CourseGroup> selectedCourseGroupes = new ArrayList<>();

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
		
		Appointment latestAppointment = findLatestAppointment();
		if(latestAppointment != null) {
			appointment.updateTime(latestAppointment.getTime().getValue().deltaMinutes(Constants.APPOINTMENT_DURATION_MINUTES));
		}
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
	
	public void moveAppointment(String appointmentId, String destinationId, String beforeOrAftetr) {
		T.call(this);
		
		Appointment appointmentToMove = findAppointmentById(appointmentId);
		Appointment anchorAppointment = findAppointmentById(destinationId);
		
		if(appointmentToMove != null && anchorAppointment != null) {

			getAppointments().removeItem(appointmentToMove);

			int anchorIndex = getAppointments().indexOf(anchorAppointment);
			
			if(beforeOrAftetr.equals("after")) {

				getAppointments().insertItem(anchorIndex+1, appointmentToMove);

			}else {

				getAppointments().insertItem(anchorIndex, appointmentToMove);
			}
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
		setMaxId(0);
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public Appointment findAppointmentById(String appointmentId) {
		Appointment result = null;
		for(Appointment candidate : appointments.getValue()) {
			if(candidate.getId().equals(appointmentId)) {
				result = candidate;
				break;
			}
		}
		
		return result;
	}

	public StoredString getCurrentCourseId() {
		return currentCourseId;
	}

	public void setCurrentCourseId(StoredString currentCourseId) {
		this.currentCourseId = currentCourseId;
	}

	public StoredString getCurrentGroupId() {
		return currentGroupId;
	}

	public void setCurrentGroupId(StoredString currentGroupId) {
		this.currentGroupId = currentGroupId;
	}

	public ObservableTime getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(ObservableTime currentTime) {
		this.currentTime = currentTime;
	}

	public void modifyAppointmentTimes(long timeIncrementSeconds) {
		T.call(this);

		for(Appointment appointment : getAppointments().getValue()) {
			appointment.modifyAppointmentTime(timeIncrementSeconds);
		}
	}

	public void modifyAppointmentDurations(long durationIncrementSeconds) {
		T.call(this);
		
		Appointment first = findEarliestAppointment();
		
		if(first != null) {
			appointmentDurationSeconds += durationIncrementSeconds;
			
			// FIXME: not that simple!
			forEachAppointmentInOrder(a -> {
				a.updateTime(a.getTime().getValue().deltaSeconds(durationIncrementSeconds));
			});
		}
	}
	
	private void forEachAppointmentInOrder(AppointmentLambda lambda) {
		T.call(this);

		// TODO: sort appointment in time order
		for(Appointment appointment : getAppointments().getValue()) {
			lambda.execute(appointment);
		}
	}

	private Appointment findEarliestAppointment() {
		T.call(this);
		
		Appointment current = null;

		for(Appointment candidate : getAppointments().getValue()) {
			if(current == null) {
				current = candidate;
			}else {
				NtroDate candidateTime = candidate.getTime().getValue();
				NtroDate currentTime = current.getTime().getValue();
				
				if(candidateTime.smallerThan(currentTime)) {
					current = candidate;
				}
			}
		}
		
		return current;
	}

	private Appointment findLatestAppointment() {
		T.call(this);

		Appointment current = null;

		for(Appointment candidate : getAppointments().getValue()) {
			if(current == null) {
				current = candidate;
			}else {
				NtroDate candidateTime = candidate.getTime().getValue();
				NtroDate currentTime = current.getTime().getValue();
				
				if(candidateTime.biggerThan(currentTime)) {
					current = candidate;
				}
			}
		}
		
		return current;
	}
	
	
	
}
