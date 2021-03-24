package ca.aquiletour.core.pages.git.values;

import java.lang.reflect.Array;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredBoolean;
import ca.ntro.core.system.trace.T;

public class Commit implements NtroModelValue {
	
	String commitMessage= "";
	String timeStamp= "";
	String exercisePath= "";
	String id= "";
	int estimatedEffort;
	//String[] modifiedFiles = new String[0];

	public String getCommitMessage() {
		return commitMessage;
	}

	public void setCommitMessage(String commitMessage) {
		this.commitMessage = commitMessage;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getEstimatedEffort() {
		return estimatedEffort;
	}

	public void setEstimatedEffort(int estimatedEffort) {
		this.estimatedEffort = estimatedEffort;
	}

	public String getExercisePath() {
		return exercisePath;
	}

	public void setExercisePath(String exercisePath) {
		this.exercisePath = exercisePath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	public String[] getModifiedFiles() {
//		return modifiedFiles;
//	}
//
//	public void setModifiedFiles(String[] modifiedFiles) {
//		this.modifiedFiles = modifiedFiles;
//	}
//	
	

	
}
