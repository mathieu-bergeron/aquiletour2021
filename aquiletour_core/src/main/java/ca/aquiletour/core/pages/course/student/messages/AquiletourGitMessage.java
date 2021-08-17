package ca.aquiletour.core.pages.course.student.messages;

import ca.aquiletour.core.messages.course.AtomicTaskMessage;
import ca.aquiletour.core.models.paths.TaskPath;
import ca.ntro.core.Path;

public class AquiletourGitMessage extends AtomicTaskMessage {
	
	private String studentId;
	private String groupId;
	private TaskPath repoPath;
	private TaskPath exercisePath;
	private String repoUrl;

	public String getRepoUrl() {
		return repoUrl;
	}

	public void setRepoUrl(String repoUrl) {
		this.repoUrl = repoUrl;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public TaskPath getRepoPath() {
		return repoPath;
	}

	public void setRepoPath(TaskPath repoPath) {
		this.repoPath = repoPath;
	}

	public Path getExercisePath() {
		return exercisePath;
	}

	public void setExercisePath(TaskPath exercisePath) {
		this.exercisePath = exercisePath;
	}
}
