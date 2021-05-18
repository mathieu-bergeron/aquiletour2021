package ca.aquiletour.core.messages.git;

import ca.aquiletour.core.pages.course.student.messages.StudentRepoMessage;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;

public class ExerciseMessage extends NtroMessage {

	private String courseId;
	private String groupId;
	private String exercisePath;
	
	public ExerciseMessage() {
		super();
		T.call(this);
	}

	public ExerciseMessage(StudentRepoMessage message) {
		super();
		T.call(this);

		setGroupId(message.getGroupId());

		setCourseId(message.getTeacherId() + "/" + message.getCourseId());
		setExercisePath(message.getTaskPath().toString());
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getExercisePath() {
		return exercisePath;
	}

	public void setExercisePath(String exercisePath) {
		this.exercisePath = exercisePath;
	}
	
	public void loadExerciseInfo(ExerciseMessage message) {
		T.call(this);
		
		setCourseId(message.getCourseId());
		setExercisePath(message.getExercisePath());
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
