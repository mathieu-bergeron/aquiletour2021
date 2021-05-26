package ca.aquiletour.core.pages.queue.models;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.Constants;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.StoredInteger;
import ca.ntro.core.models.lambdas.Break;
import ca.ntro.core.system.log.Log;
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

	private IsOpenById isOpenByCourseId = new IsOpenById();
	private IsOpenById isOpenByGroupId = new IsOpenById();
	private MessageById messageByCourseId = new MessageById();
	private MessageById messageByGroupId = new MessageById();

	public boolean isQueueOpen() {
		T.call(this);

		boolean isOpen = false;
		
		isOpen = isQueueOpenForSomeId(isOpen, isOpenByCourseId);
		isOpen = isQueueOpenForSomeId(isOpen, isOpenByGroupId);

		return isOpen;
	}

	private boolean isQueueOpenForSomeId(boolean isQueueOpen, IsOpenById isOpenById) {
		T.call(this);

		return isOpenById.reduceTo(Boolean.class, isQueueOpen, (key, isOpenForId, isOpen) -> {
			if(isOpen) {
				throw new Break();
			}

			if(isOpenForId) {
				isOpen = true;
			}

			return isOpen;
		});
	}
	
	public IsOpenById getIsOpenByCourseId() {
		return isOpenByCourseId;
	}

	public void setIsOpenByCourseId(IsOpenById isOpenByCourseId) {
		this.isOpenByCourseId = isOpenByCourseId;
	}

	public IsOpenById getIsOpenByGroupId() {
		return isOpenByGroupId;
	}

	public void setIsOpenByGroupId(IsOpenById isOpenByGroupId) {
		this.isOpenByGroupId = isOpenByGroupId;
	}


	public MessageById getMessageByCourseId() {
		return messageByCourseId;
	}


	public void setMessageByCourseId(MessageById messageByCourseId) {
		this.messageByCourseId = messageByCourseId;
	}


	public MessageById getMessageByGroupId() {
		return messageByGroupId;
	}


	public void setMessageByGroupId(MessageById messageByGroupId) {
		this.messageByGroupId = messageByGroupId;
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

	public int appointmentIndexByStudentId(String studentId) {
		T.call(this);
		
		int index = -1;
		
		for(int i = 0; i < getAppointments().size(); i++) {
			if(getAppointments().item(i).getStudentId().equals(studentId)) {
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

	public Appointment appointmentByStudentId(String studentId) {
		T.call(this);
		
		Appointment result = null;
		
		int index = appointmentIndexByStudentId(studentId);
		
		if(isValidAppointmentIndex(index)) {
			result = getAppointments().item(index);
		}
		
		return result;
	}

	private boolean isValidAppointmentIndex(int index) {
		T.call(this);

		return index >= 0 && index < getAppointments().size();
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

		getAppointments().forEachItem((index, appointment) -> {
			appointment.incrementTimeSeconds(timeIncrementSeconds);
		});
	}

	public void modifyAppointmentDurations(int durationIncrementSeconds) {
		T.call(this);

		appointmentDurationSeconds.incrementBy(durationIncrementSeconds);
		
		recomputeAppointmentTimes();
	}

	private void recomputeAppointmentTimes() {
		T.call(this);

		NtroDate firstTime = firstAppointmentTime.getValue();
		
		getAppointments().reduceTo(NtroDate.class, firstTime, (index, appointment, currentTime) -> {
			if(index != 0) {
				currentTime = currentTime.deltaSeconds(appointmentDurationSeconds.getValue());
			}

			appointment.updateTime(currentTime);

			return currentTime;
		});
	}

	private boolean queueEmpty() {
		T.call(this);
		
		return getAppointments().size() == 0;
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

	public void modifyAppointmentComment(String studentId, String comment) {
		T.call(this);
		
		Appointment appointment = appointmentByStudentId(studentId);
		
		if(appointment != null) {

			appointment.updateComment(comment);

		}else {
			Log.warning("Appointment not found for student: " + studentId);
		}
	}

	public void updateIsQueueOpenForCourseId(String courseId, boolean isQueueOpen) {
		T.call(this);
		
		isOpenByCourseId.putEntry(courseId, isQueueOpen);
	}
}
