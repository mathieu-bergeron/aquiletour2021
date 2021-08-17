package ca.aquiletour.core.messages.git;

import ca.aquiletour.core.messages.git.base.GitApiRepoUrlMessage;
import ca.aquiletour.core.pages.course.student.messages.StudentRegistersRepoMessage;
import ca.ntro.core.system.trace.T;

public class RegisterGitRepoForTeacher extends GitApiRepoUrlMessage {
	
	public RegisterGitRepoForTeacher() {
		super();
		T.call(this);
	}
	
	public RegisterGitRepoForTeacher(StudentRegistersRepoMessage message) {
		super(message);
		T.call(this);
	}
}
