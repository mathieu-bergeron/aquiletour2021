package ca.aquiletour.core.pages.queue.models;

import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.models.paths.TaskPath;
import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;

public class Appointment implements NtroModelValue {
	
	private String id = "";
	private String studentId = "";
	private String studentName = "";
	private String studentSurname = "";
	
	private StoredTime time = new StoredTime();

	private StoredCoursePath coursePath = new StoredCoursePath();
	private StoredString courseTitle = new StoredString();
	
	private StoredTaskPath taskPath = new StoredTaskPath();
	private StoredString taskTitle = new StoredString();

	private StoredTags tags = new StoredTags();
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
	
	public StoredCoursePath getCoursePath() {
		return coursePath;
	}

	public void setCoursePath(StoredCoursePath coursePath) {
		this.coursePath = coursePath;
	}

	public StoredTaskPath getTaskPath() {
		return taskPath;
	}

	public void setTaskPath(StoredTaskPath taskPath) {
		this.taskPath = taskPath;
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

	public StoredTime getTime() {
		return time;
	}

	public void setTime(StoredTime time) {
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

	public StoredTags getTags() {
		return tags;
	}

	public void setTags(StoredTags tags) {
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
		
		tags.clearItems();
		tags.addTagsFromComment(comment);
		
		getComment().set(comment);
	}

	public void updateCourseTitle(String courseTitle) {
		T.call(this);
		
		getCourseTitle().set(courseTitle);
	}

	public void updateTaskTitle(String taskTitle) {
		T.call(this);
		
		getTaskTitle().set(taskTitle);
	}

	public void updateCoursePath(CoursePath coursePath) {
		T.call(this);

		getCoursePath().set(coursePath);
	}

	public void updateTaskPath(TaskPath taskPath) {
		T.call(this);

		getTaskPath().set(taskPath);
	}

	public static String htmlId(Appointment appointment) {
		T.call(Appointment.class);

		Path path = new Path();
		path.addName("appointment");
		path.addName(appointment.getId());

		return path.toHtmlId();
	}
}

