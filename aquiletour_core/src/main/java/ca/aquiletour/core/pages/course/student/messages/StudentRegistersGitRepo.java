package ca.aquiletour.core.pages.course.student.messages;

import ca.aquiletour.core.models.user.User;
import ca.ntro.messages.NtroUserMessage;

public class StudentRegistersGitRepo extends NtroUserMessage<User> {
	
	private String gitRepoUrl;

	public String getGitRepoUrl() {
		return gitRepoUrl;
	}

	public void setGitRepoUrl(String gitRepoUrl) {
		this.gitRepoUrl = gitRepoUrl;
	}
}
