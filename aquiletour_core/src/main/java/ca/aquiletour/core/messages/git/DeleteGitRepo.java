package ca.aquiletour.core.messages.git;

import ca.aquiletour.core.messages.git.base.GitApiRepoUrlMessage;
import ca.aquiletour.core.pages.course.student.messages.AquiletourGitMessage;
import ca.ntro.core.system.trace.T;

public class DeleteGitRepo extends GitApiRepoUrlMessage {
	
	public DeleteGitRepo() {
		super();
		T.call(this);
	}

	public DeleteGitRepo(AquiletourGitMessage message) {
		super(message);
		T.call(this);
	}
}
