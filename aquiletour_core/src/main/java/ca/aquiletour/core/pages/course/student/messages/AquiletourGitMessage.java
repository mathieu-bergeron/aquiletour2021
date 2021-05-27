package ca.aquiletour.core.pages.course.student.messages;

import ca.aquiletour.core.messages.course.AtomicTaskMessage;
import ca.ntro.core.Path;

public class AquiletourGitMessage extends AtomicTaskMessage {
	
	private String studentId;
	private String groupId;
	private Path repoPath;
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

	public Path getRepoPath() {
		return repoPath;
	}

	public void setRepoPath(Path repoPath) {
		this.repoPath = repoPath;
	}

}
