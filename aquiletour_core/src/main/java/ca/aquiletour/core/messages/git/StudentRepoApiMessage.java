package ca.aquiletour.core.messages.git;

import ca.aquiletour.core.pages.course.student.messages.StudentRepoMessage;
import ca.ntro.core.system.trace.T;

public class StudentRepoApiMessage extends StudentExerciseApiMessage {

	private String repoUrl;

	public StudentRepoApiMessage() {
		super();
		T.call(this);
	}
	
	public StudentRepoApiMessage(StudentRepoMessage message) {
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
