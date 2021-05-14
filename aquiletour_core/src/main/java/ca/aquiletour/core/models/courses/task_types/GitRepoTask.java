package ca.aquiletour.core.models.courses.task_types;

import ca.ntro.core.Path;

public class GitRepoTask extends TaskType {
	
	private Path repoPath = new Path();
	private Path exercicePath = new Path();

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

	public Path getExercicePath() {
		return exercicePath;
	}

	public void setExercicePath(Path exercicePath) {
		this.exercicePath = exercicePath;
	}
}
