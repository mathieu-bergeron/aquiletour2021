package ca.aquiletour.core.messages.git;

import ca.aquiletour.core.messages.git.base.GitApiExerciseMessage;

public class RegisterExercise extends GitApiExerciseMessage {
	
	private String sourceFolderPath;
	private String completionKeywords;
	
	public String getSourceFolderPath() {
		return sourceFolderPath;
	}

	public void setSourceFolderPath(String sourceFolderPath) {
		this.sourceFolderPath = sourceFolderPath;
	}

	public String getCompletionKeywords() {
		return completionKeywords;
	}

	public void setCompletionKeywords(String completionKeywords) {
		this.completionKeywords = completionKeywords;
	}
}
