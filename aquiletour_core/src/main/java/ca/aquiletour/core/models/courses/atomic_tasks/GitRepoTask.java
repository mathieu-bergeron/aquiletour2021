package ca.aquiletour.core.models.courses.atomic_tasks;

import ca.ntro.core.Path;
import ca.ntro.core.system.trace.T;

public class GitRepoTask extends AtomicTask {
	
	private Path repoPath = new Path();
	
	public GitRepoTask() {
		super();
		T.call(this);
	}

	public GitRepoTask(Path repoPath) {
		super();
		T.call(this);
		
		this.repoPath = repoPath;
	}

	@Override
	public String toString() {
		return "Dépôt Git";
	}

	public Path getRepoPath() {
		return repoPath;
	}

	public void setRepoPath(Path repoPath) {
		this.repoPath = repoPath;
	}

	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other == this) return true;
		if(other instanceof GitRepoTask) {
			return equals((GitRepoTask) other);
		}

		return false;
	}
	
	private boolean equals(GitRepoTask otherTask) {
		T.call(this);
		
		boolean isEqual = true;
		
		if(repoPath == null 
				&& otherTask.repoPath != null) {

			isEqual = false;

		}else if(repoPath != null 
				&& !repoPath.equals(otherTask.repoPath)) {

			isEqual = false;
		}
		
		return isEqual;
	}
	
}
