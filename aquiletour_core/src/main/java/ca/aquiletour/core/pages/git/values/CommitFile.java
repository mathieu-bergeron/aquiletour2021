package ca.aquiletour.core.pages.git.values;

import ca.ntro.core.models.NtroModelValue;

public class CommitFile implements NtroModelValue {
	
	String path= "";	
	int estimatedEffort;	
	String exercisePath= "";
	String message= "";


	public int getEstimatedEffort() {
		return estimatedEffort;
	}

	public void setEstimatedEffort(int estimatedEffort) {
		this.estimatedEffort = estimatedEffort;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getExercisePath() {
		return exercisePath;
	}

	public void setExercisePath(String exercisePath) {
		this.exercisePath = exercisePath;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
