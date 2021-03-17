package ca.aquiletour.core.pages.git;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredBoolean;
import ca.ntro.core.system.trace.T;

public class LateStudents implements NtroModel {
	
	String semesterId, deadline, groupId, exercisePath;
	String[] studentIds;


	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getExercisePath() {
		return exercisePath;
	}

	public void setExercisePath(String exercisePath) {
		this.exercisePath = exercisePath;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String[] getStudentIds() {
		return studentIds;
	}

	public void setStudentIds(String[] studentIds) {
		this.studentIds = studentIds;
	}
	
	

}
