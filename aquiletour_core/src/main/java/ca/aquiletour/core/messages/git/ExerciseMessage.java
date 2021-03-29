package ca.aquiletour.core.messages.git;

import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;

public class ExerciseMessage extends NtroMessage {

	private String courseId;
	private String exerciseId;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(String exerciseId) {
		this.exerciseId = exerciseId;
	}
	
	public void loadExerciseInfo(ExerciseMessage message) {
		T.call(this);
		
		setCourseId(message.getCourseId());
		setExerciseId(message.getExerciseId());
	}

}
