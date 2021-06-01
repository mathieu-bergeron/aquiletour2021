package ca.aquiletour.core.pages.git.values;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.models.NtroModelValue;

public class Commit implements NtroModelValue {
	
	String commitMessage= "";	
	String commitId= "";	
	String commitMessageFirstLine= "";
	String timeStamp= "";
	String exercisePathIfCompleted= "";
	String id= "";
	int estimatedEffort;
	private List<CommitFile> modifiedFiles = new ArrayList<>();

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

	public String getExercisePathIfCompleted() {
		return exercisePathIfCompleted;
	}

	public void setExercisePathIfCompleted(String exercisePath) {
		this.exercisePathIfCompleted = exercisePath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCommitId() {
		return commitId;
	}

	public void setCommitId(String commitId) {
		this.commitId = commitId;
	}

	public String getCommitMessageFirstLine() {
		return commitMessageFirstLine;
	}

	public void setCommitMessageFirstLine(String commitMessageFirstLine) {
		this.commitMessageFirstLine = commitMessageFirstLine;
	}

	public List<CommitFile> getModifiedFiles() {
		return modifiedFiles;
	}

	public void setModifiedFiles(List<CommitFile> modifiedFiles) {
		this.modifiedFiles = modifiedFiles;
	}
	

	
}
