package ca.aquiletour.core.models.courses.atomic_tasks.git_repo;

import ca.ntro.core.system.trace.T;

public class GitRepoCloned extends GitRepoCompletion {

	@Override
	public boolean isCompleted() {
		T.call(this);
		
		return true;
	}
}
