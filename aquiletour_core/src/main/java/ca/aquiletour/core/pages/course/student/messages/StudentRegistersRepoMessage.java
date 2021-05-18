package ca.aquiletour.core.pages.course.student.messages;

public class StudentRegistersRepoMessage extends StudentRepoMessage {
	
	private String repoUrl;

	public String getRepoUrl() {
		return repoUrl;
	}

	public void setRepoUrl(String repoUrl) {
		this.repoUrl = repoUrl;
	}
}
