package ca.aquiletour.core.messages.git;

public class RegisterRepo extends StudentExerciseMessage {
	
	private String repoUrl;


	public String getRepoUrl() {
		return repoUrl;
	}

	public void setRepoUrl(String repoUrl) {
		this.repoUrl = repoUrl;
	}
}
