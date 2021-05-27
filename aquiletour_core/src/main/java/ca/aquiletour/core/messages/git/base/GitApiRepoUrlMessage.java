package ca.aquiletour.core.messages.git.base;

import ca.aquiletour.core.pages.course.student.messages.AquiletourGitMessage;
import ca.ntro.core.system.trace.T;

public class GitApiRepoUrlMessage extends GitApiRepoMessage {

	private String repoUrl;
	
	public GitApiRepoUrlMessage() {
		super();
		T.call(this);
	}

	public GitApiRepoUrlMessage(AquiletourGitMessage message) {
		super(message);
		T.call(this);

		setRepoUrl(message.getRepoUrl());
	}

	public String getRepoUrl() {
		return repoUrl;
	}

	public void setRepoUrl(String repoUrl) {
		this.repoUrl = repoUrl;
	}

}
