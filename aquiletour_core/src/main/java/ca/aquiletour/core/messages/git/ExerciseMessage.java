package ca.aquiletour.core.messages.git;

import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;

public class ExerciseMessage extends NtroMessage {

	private String courseId;
	private String groupId;
	private String exercisePath;

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
