package ca.aquiletour.core.messages.git;

public class RegisterExerciceMessage extends ExerciseMessage {
	
	private String exercisePath;
	private String completionKeywords;
	
	public String getExercisePath() {
		return exercisePath;
	}

	public void setExercisePath(String exercisePath) {
		this.exercisePath = exercisePath;
	}

	public String getCompletionKeywords() {
		return completionKeywords;
	}

	public void setCompletionKeywords(String completionKeywords) {
		this.completionKeywords = completionKeywords;
	}
}
