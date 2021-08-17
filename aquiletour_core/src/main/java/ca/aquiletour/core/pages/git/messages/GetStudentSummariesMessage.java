package ca.aquiletour.core.pages.git.messages;

import ca.aquiletour.core.models.user.User;
import ca.ntro.messages.NtroUserMessage;

public class GetStudentSummariesMessage extends NtroUserMessage<User> {

	private String semesterId, groupId, exercisePath, completionKeywords, deadline;

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}

	public String getExercisePath() {
		return exercisePath;
	}

	public void setExercisePath(String exercisePath) {
		this.exercisePath = exercisePath;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getCompletionKeywords() {
		return completionKeywords;
	}

	public void setCompletionKeywords(String completionKeywords) {
		this.completionKeywords = completionKeywords;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	
	

}
