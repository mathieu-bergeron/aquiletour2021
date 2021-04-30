package ca.aquiletour.core.pages.queue.models;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.semester_list.models.CourseGroup;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.StoredInteger;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.services.Ntro;

public class QueueModel implements NtroModel {
	
	
	private ObservableTime currentTime = new ObservableTime();

	private StoredInteger appointmentDurationSeconds = new StoredInteger(Constants.APPOINTMENT_DURATION_MINUTES * 60);
	private ObservableTime firstAppointmentTime = new ObservableTime();

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
		
		if(queueEmpty()) {
			setNowAsFirstAppointmentTime();
			appointment.updateTime(firstAppointmentTime.getValue());
		}else {
			appointment.updateTime(findLatestAppointment().getTime().getValue().deltaSeconds(appointmentDurationSeconds.getValue()));
		}

		appointments.addItem(appointment);
	}
	

	public int appointmentIndexById(String appointmentId) {
		T.call(this);
		
		int index = -1;
		
		for(int i = 0; i < getAppointments().size(); i++) {
			if(getAppointments().item(i).getId().equals(appointmentId)) {
				index = i;
				break;
			}
		}
		
		return index;
	}

	public void deleteAppointment(String appointmentId) {
		T.call(this);
		
		int index = appointmentIndexById(appointmentId);
		
		if(index == 0) {
			setNowAsFirstAppointmentTime();
		}
		
		appointments.removeItemAtIndex(0);
		recomputeAppointmentTimes();
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
	
	public void moveAppointment(String toMoveId, String anchorId, String beforeOrAfter) {
		T.call(this);
		
		int toMoveIndex = appointmentIndexById(toMoveId);
		
		if(isValidAppointmentIndex(toMoveIndex)) {

			Appointment toMove =  getAppointments().item(toMoveIndex);

			getAppointments().removeItemAtIndex(toMoveIndex);

			// XXX: getting index AFTER we've removed the appointment
			int anchorIndex = appointmentIndexById(anchorId);
			
			if(isValidAppointmentIndex(anchorIndex)) {

				if(beforeOrAfter.equals("after")) {

					getAppointments().insertItem(anchorIndex+1, toMove);

				}else {

					getAppointments().insertItem(anchorIndex, toMove);
				}
			}
		}
		
		if(toMoveIndex == 0) {
			setNowAsFirstAppointmentTime();
		}

		recomputeAppointmentTimes();
	}

	private void setNowAsFirstAppointmentTime() {
		T.call(this);

		firstAppointmentTime.set(Ntro.calendar().now());
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

	public Appointment appointmentById(String appointmentId) {
		T.call(this);
		
		Appointment result = null;
		
		int index = appointmentIndexById(appointmentId);
		
		if(isValidAppointmentIndex(index)) {
			result = getAppointments().item(index);
		}
		
		return result;
	}

	private boolean isValidAppointmentIndex(int index) {
		T.call(this);

		return index >= 0 && index < getAppointments().size();
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

	public void incrementAppointmentTimesSeconds(int timeIncrementSeconds) {
		T.call(this);
		
		firstAppointmentTime.incrementBySeconds(timeIncrementSeconds);

		for(Appointment appointment : getAppointments().getValue()) {
			appointment.incrementTimeSeconds(timeIncrementSeconds);
		}
	}

	public void modifyAppointmentDurations(int durationIncrementSeconds) {
		T.call(this);

		appointmentDurationSeconds.incrementBy(durationIncrementSeconds);
		
		recomputeAppointmentTimes();
	}

	private void recomputeAppointmentTimes() {
		T.call(this);

		NtroDate currentTime = firstAppointmentTime.getValue();
		
		if(!queueEmpty()) {

			firstAppointment().updateTime(currentTime);
		}

		for(int i = 1 ; i < appointments.size(); i++) {
			currentTime = currentTime.deltaSeconds(appointmentDurationSeconds.getValue());
			appointments.item(i).updateTime(currentTime);
		}
	}

	private boolean queueEmpty() {
		T.call(this);
		
		return getAppointments().size() == 0;
	}

	private Appointment firstAppointment() {
		T.call(this);
		
		Appointment result = null;

		if(getAppointments().size() > 0) {
			result = getAppointments().item(0);
		}

		return result;
	}

	private Appointment findLatestAppointment() {
		T.call(this);

		Appointment result = null;

		if(getAppointments().size() > 0) {
			result = getAppointments().item(getAppointments().size()-1);
		}
		
		return result;
	}

	public StoredInteger getAppointmentDurationSeconds() {
		return appointmentDurationSeconds;
	}

	public void setAppointmentDurationSeconds(StoredInteger appointmentDurationSeconds) {
		this.appointmentDurationSeconds = appointmentDurationSeconds;
	}

	public ObservableTime getFirstAppointmentTime() {
		return firstAppointmentTime;
	}

	public void setFirstAppointmentTime(ObservableTime firstAppointmentTime) {
		this.firstAppointmentTime = firstAppointmentTime;
	}
	
	
}
