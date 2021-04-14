package ca.aquiletour.core.messages.course;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.users.User;
import ca.ntro.core.Path;
import ca.ntro.messages.NtroUserMessage;

public class CourseMessage extends NtroUserMessage<User> {

	private String teacherId;
	private String courseId;
	private String semesterId;
	private Path taskPath = new Path("/");
	
	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}
	
	public CoursePath coursePath() {
		return new CoursePath(teacherId, semesterId, courseId);
	}

	public Path getTaskPath() {
		return taskPath;
	}

	public void setTaskPath(Path taskPath) {
		this.taskPath = taskPath;
	}
}
