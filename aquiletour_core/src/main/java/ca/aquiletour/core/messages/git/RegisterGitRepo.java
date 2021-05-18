package ca.aquiletour.core.messages.git;

import ca.aquiletour.core.pages.course.student.messages.StudentRegistersRepoMessage;
import ca.ntro.core.system.trace.T;

public class RegisterGitRepo extends StudentRepoApiMessage {
	
	private String repoPath;

	public RegisterGitRepo() {
		super();
		T.call(this);
	}
	
	public RegisterGitRepo(StudentRegistersRepoMessage message) {
		super(message);
		T.call(this);

		setRepoPath(message.getRepoPath().toString());
	}
	

	public String getRepoPath() {
		return repoPath;
	}

	public void setRepoPath(String repoPath) {
		this.repoPath = repoPath;
	}
}
