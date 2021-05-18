package ca.aquiletour.core.models.courses.atomic_tasks.git_repo;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;

public class GitRepoCompletion extends AtomicTaskCompletion {
	
	private String repoUrl = "";

	public String getRepoUrl() {
		return repoUrl;
	}

	public void setRepoUrl(String repoUrl) {
		this.repoUrl = repoUrl;
	}
}
