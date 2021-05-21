package ca.aquiletour.core.models.courses.atomic_tasks.git_exercice;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.ntro.core.Path;
import ca.ntro.core.system.trace.T;

public class GitExerciseTask extends AtomicTask {
	
	private Path repoPath = new Path();

	public GitExerciseTask() {
		super();
		T.call(this);
	}
	
	public GitExerciseTask(Path repoPath) {
		super();
		T.call(this);
		
		this.repoPath = repoPath;
	}

	@Override
	public String toString() {
		return "Remise Git";
	}

	public Path getRepoPath() {
		return repoPath;
	}

	public void setRepoPath(Path repoPath) {
		this.repoPath = repoPath;
	}

	@Override
	public boolean equals(Object other) {
		T.call(this);
		
		if(other == null) return false;
		if(this == other) return true;
		if(other instanceof GitExerciseTask) {
			return equalsOther((GitExerciseTask) other);
		}
		return false;
	}
	
	private boolean equalsOther(GitExerciseTask otherTask) {
		T.call(this);
		
		boolean isEqual = true;
		
		if(repoPath == null && otherTask.repoPath != null) {
			isEqual = false;

		}else if(!repoPath.equals(otherTask.repoPath)) {
			isEqual = false;
		}
		
		return isEqual;
	}


}
