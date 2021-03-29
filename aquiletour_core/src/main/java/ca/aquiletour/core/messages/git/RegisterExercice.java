package ca.aquiletour.core.messages.git;

public class RegisterExercice extends ExerciseMessage {
	
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
