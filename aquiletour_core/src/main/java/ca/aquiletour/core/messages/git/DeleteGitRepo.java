package ca.aquiletour.core.messages.git;

import ca.aquiletour.core.pages.course.student.messages.StudentDeletesRepoMessage;
import ca.ntro.core.system.trace.T;

public class DeleteGitRepo extends StudentRepoApiMessage {
	
	public DeleteGitRepo() {
		super();
		T.call(this);
	}

	public DeleteGitRepo(StudentDeletesRepoMessage message) {
		super(message);
		T.call(this);
	}
}
