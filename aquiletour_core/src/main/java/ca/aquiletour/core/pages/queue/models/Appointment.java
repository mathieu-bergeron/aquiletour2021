package ca.aquiletour.core.pages.queue.models;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;

public class Appointment implements NtroModelValue {
	
	private String id = "";
	private String studentId = "";
	private String studentName = "";
	private String studentSurname = "";
	
	private ObservableTime time = new ObservableTime();
	private StoredString courseTitle = new StoredString();
	private StoredString taskTitle = new StoredString();
	private ObservableTags tags = new ObservableTags();
	private StoredString comment = new StoredString();

	//private User user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentSurname() {
		return studentSurname;
	}
	public void setStudentSurname(String studentSurname) {
		this.studentSurname = studentSurname;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other == this) return true;
		if(other instanceof Appointment) {
			Appointment otherAppointment = (Appointment) other;
			
			return (id != null && id.equals(otherAppointment.id))
					|| id == otherAppointment.id;
		}
		
		return false;
	}

	public ObservableTime getTime() {
		return time;
	}

	public void setTime(ObservableTime time) {
		this.time = time;
	}

	public StoredString getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(StoredString courseTitle) {
		this.courseTitle = courseTitle;
	}

	public StoredString getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(StoredString taskTitle) {
		this.taskTitle = taskTitle;
	}

	public ObservableTags getTags() {
		return tags;
	}

	public void setTags(ObservableTags tags) {
		this.tags = tags;
	}


	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	public void updateTime(NtroDate time) {
		T.call(this);
		
		getTime().set(time);
	}

	public void incrementTimeSeconds(long timeIncrementSeconds) {
		T.call(this);
		
		NtroDate time = getTime().getValue();
		
		time = time.deltaSeconds(timeIncrementSeconds);
		
		updateTime(time);
	}

	public StoredString getComment() {
		return comment;
	}

	public void setComment(StoredString comment) {
		this.comment = comment;
	}

	public void updateComment(String comment) {
		T.call(this);
		
		getComment().set(comment);
	}
}

