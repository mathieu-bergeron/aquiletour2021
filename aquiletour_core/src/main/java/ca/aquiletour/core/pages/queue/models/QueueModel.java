package ca.aquiletour.core.pages.queue.models;


import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.common.StoredListOfIds;
import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.models.paths.TaskPath;
import ca.aquiletour.core.models.user.User;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.StoredInteger;
import ca.ntro.core.models.lambdas.Break;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.services.Ntro;

public class QueueModel implements NtroModel {

	private String queueId = "";
	private int maxId;

	private StoredTime currentTime = new StoredTime();

	private StoredInteger appointmentDurationSeconds = new StoredInteger(Constants.APPOINTMENT_DURATION_MINUTES * 60);
	private StoredTime firstAppointmentTime = new StoredTime();
	
	// XXX: sub-models should be stored by Id (will play a role in next refactoring towards more sub-controllers)
	private AppointmentById appointmentById = new AppointmentById();
	private StoredListOfIds appointmentsInOrder = new StoredListOfIds();

	private QueueSettings mainSettings = new QueueSettings();
	private SettingsByCourseKey settingsByCourseKey = new SettingsByCourseKey();

	public boolean isQueueOpen() {
		T.call(this);

		boolean isOpen = mainSettings.isQueueOpen();
		
		if(!isOpen) {
			
			isOpen = isQueueOpenForSomeCourse(isOpen);
		}
		
		return isOpen;
	}

	private boolean isQueueOpenForSomeCourse(boolean isOpen) {
		T.call(this);

		isOpen = getSettingsByCourseKey().reduceTo(Boolean.class, isOpen, (courseId, courseSettings, currentIsOpen) -> {
			if(currentIsOpen) {
				throw new Break();
			}

			return courseSettings.isQueueOpen();
		});

		return isOpen;
	}

	public void addAppointment(Appointment appointment, AppointmentAddedListener appointmentAddedListener) {
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
		
		getAppointmentById().putEntry(appointmentId, appointment);
		getAppointmentsInOrder().addItem(appointmentId);

		appointmentAddedListener.onAppointementAdded(appointment);
	}
	

	public int appointmentIndexById(String appointmentId) {
		T.call(this);
		
		if(appointmentId == null) return -1;

		return getAppointmentsInOrder().reduceTo(Integer.class, -1, (index, candidateId, accumulator) -> {
			if(accumulator > 0) {
				throw new Break();
			}
			
			if(candidateId.equals(appointmentId)) {
				accumulator = index;
			}
			
			return accumulator;
		});
	}

	public int appointmentIndexByStudentId(String studentId) {
		T.call(this);
		
		if(studentId == null) return -1;

		return getAppointmentsInOrder().reduceTo(Integer.class, -1, (index, candidateId, accumulator) -> {
			if(accumulator > 0) {
				throw new Break();
			}
			
			Appointment candidate = getAppointmentById().valueOf(candidateId);
			
			if(candidate != null 
					&& studentId.equals(candidate.getStudentId())) {
				
				accumulator = index;
			}
			
			return accumulator;
		});
	}

	public void deleteAppointment(String appointmentId) {
		T.call(this);
		
		int index = appointmentIndexById(appointmentId);
		
		if(index == 0) {
			setNowAsFirstAppointmentTime();
		}
		
		if(index != -1) {
			getAppointmentById().removeEntry(appointmentId);
			getAppointmentsInOrder().removeItemAtIndex(index);
		}

		recomputeAppointmentTimes();
	}

	public int getMaxId() {
		return maxId;
	}

	public void setMaxId(int maxId) {
		this.maxId = maxId;
	}

	public void moveAppointment(String toMoveId, String anchorId, String beforeOrAfter) {
		T.call(this);
		
		int toMoveIndex = appointmentIndexById(toMoveId);
		if(isValidAppointmentIndex(toMoveIndex)) {

			getAppointmentsInOrder().removeItemAtIndex(toMoveIndex);

			// XXX: getting index AFTER we've removed the appointment
			int anchorIndex = appointmentIndexById(anchorId);
			
			if(isValidAppointmentIndex(anchorIndex)) {

				if(beforeOrAfter.equals("after")) {

					getAppointmentsInOrder().insertItem(anchorIndex+1, toMoveId);

				}else {

					getAppointmentsInOrder().insertItem(anchorIndex, toMoveId);
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

	public void clearQueue() {
		getAppointmentById().clear();
		getAppointmentsInOrder().clearItems();
		setMaxId(0);
	}

	public Appointment appointmentById(String appointmentId) {
		T.call(this);
		
		return getAppointmentById().valueOf(appointmentId);
	}

	public Appointment appointmentByStudentId(String studentId) {
		T.call(this);
		
		if(studentId == null) return null;
		
		return getAppointmentById().reduceTo(Appointment.class, null, (appointmentId, appointement, accumulator) -> {
			if(accumulator != null) {
				throw new Break();
			}
			
			if(studentId.equals(appointement.getStudentId())) {
				
				accumulator = appointement;
			}
			
			return accumulator;
		});
	}

	private boolean isValidAppointmentIndex(int index) {
		T.call(this);

		return index >= 0 && index < getAppointmentsInOrder().size();
	}

	public StoredTime getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(StoredTime currentTime) {
		this.currentTime = currentTime;
	}

	public void incrementAppointmentTimesSeconds(int timeIncrementSeconds) {
		T.call(this);
		
		firstAppointmentTime.incrementBySeconds(timeIncrementSeconds);
		
		getAppointmentById().forEachEntry((appointmentId, appointment) -> {
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
		
		getAppointmentsInOrder().reduceTo(NtroDate.class, firstTime, (index, appointmentId, currentTime) -> {
			if(index != 0) {
				currentTime = currentTime.deltaSeconds(appointmentDurationSeconds.getValue());
			}
			
			Appointment appointment = getAppointmentById().valueOf(appointmentId);
			if(appointment != null) {
				appointment.updateTime(currentTime);
			}

			return currentTime;
		});
	}

	private boolean queueEmpty() {
		T.call(this);
		
		return getAppointmentsInOrder().size() == 0;
	}

	private Appointment findLatestAppointment() {
		T.call(this);

		Appointment result = null;

		if(getAppointmentsInOrder().size() > 0) {
			String appointmentId = getAppointmentsInOrder().item(getAppointmentsInOrder().size()-1);
			result = getAppointmentById().valueOf(appointmentId);
		}
		
		return result;
	}

	public StoredInteger getAppointmentDurationSeconds() {
		return appointmentDurationSeconds;
	}

	public void setAppointmentDurationSeconds(StoredInteger appointmentDurationSeconds) {
		this.appointmentDurationSeconds = appointmentDurationSeconds;
	}

	public StoredTime getFirstAppointmentTime() {
		return firstAppointmentTime;
	}

	public void setFirstAppointmentTime(StoredTime firstAppointmentTime) {
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
		
		if(courseId.equals(Constants.ALL_COURSES_ID)) {

			mainSettings.updateIsQueueOpen(isQueueOpen);

		}else {
			
			QueueSettingsCourse courseSettings = settingsByCourseKey.valueOf(courseId);
			if(courseSettings == null) {
				courseSettings = new QueueSettingsCourse();
				settingsByCourseKey.putEntry(courseId, courseSettings);
			}
			
			courseSettings.updateIsQueueOpen(isQueueOpen);
		}
	}

	public QueueSettings getMainSettings() {
		return mainSettings;
	}

	public void setMainSettings(QueueSettings mainSettings) {
		this.mainSettings = mainSettings;
	}

	public SettingsByCourseKey getSettingsByCourseKey() {
		return settingsByCourseKey;
	}

	public void addCourseSettings(CoursePath coursePath) {
		T.call(this);
		
		if(!getSettingsByCourseKey().containsKey(coursePath.toString())) {
			getSettingsByCourseKey().putEntry(coursePath.toString(), new QueueSettingsCourse());
		}
	}

	public void updateCourseTitle(CoursePath coursePath, String courseTitle) {
		T.call(this);
		
		QueueSettingsCourse courseSettings = getSettingsByCourseKey().valueOf(coursePath.toKey());
		if(courseSettings != null) {
			
			courseSettings.updateTitle(courseTitle);
			
		}else {
			Log.warning("Cannot find coursePath " + coursePath);
		}
	}

	public void addGroupSettings(String courseId, String groupId) {
		T.call(this);

		QueueSettingsCourse courseSettings = getSettingsByCourseKey().valueOf(courseId);
		if(courseSettings != null) {
			
			courseSettings.addGroupSettings(groupId);
			
		}else {
			Log.warning("[addGroupSettings] Cannot find courseId " + courseId);
		}
		
	}

	public String getQueueId() {
		return queueId;
	}

	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}

	public void setSettingsByCourseKey(SettingsByCourseKey settingsByCourseKey) {
		this.settingsByCourseKey = settingsByCourseKey;
	}

	public void addAppointment(User user, CoursePath coursePath, TaskPath taskPath, String taskTitle) {
		
	}

	public void updateQueueMessage(String semesterId, String courseId, String groupId, String queueMessage) {
		T.call(this);
		
		if(semesterId != null
				&& courseId != null
				&& semesterId.equals(Constants.ACTIVE_SEMESTERS_ID)
				&& courseId.equals(Constants.ALL_COURSES_ID)) {

			mainSettings.updateQueueMessage(queueMessage);
		}
	}

	public AppointmentById getAppointmentById() {
		return appointmentById;
	}

	public void setAppointmentById(AppointmentById appointmentById) {
		this.appointmentById = appointmentById;
	}

	public StoredListOfIds getAppointmentsInOrder() {
		return appointmentsInOrder;
	}

	public void setAppointmentsInOrder(StoredListOfIds appointmentsInOrder) {
		this.appointmentsInOrder = appointmentsInOrder;
	}

	public boolean shouldShowAppointmentTimes() {
		T.call(this);
		
		return mainSettings.getShowAppointmentTimes().getValue();
	}
}
