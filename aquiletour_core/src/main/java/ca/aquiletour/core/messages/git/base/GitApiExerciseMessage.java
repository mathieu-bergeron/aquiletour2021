package ca.aquiletour.core.messages.git.base;

import ca.aquiletour.core.models.paths.TaskPath;
import ca.aquiletour.core.pages.course.student.messages.AquiletourGitMessage;
import ca.ntro.core.system.trace.T;

public class GitApiExerciseMessage extends GitApiRepoMessage {

	private String exercisePath;
	
	public GitApiExerciseMessage() {
		super();
		T.call(this);
	}

	public GitApiExerciseMessage(AquiletourGitMessage message) {
		super(message);
		T.call(this);

		setExercisePath(message.getTaskPath().toString());
	}


	public String getExercisePath() {
		return exercisePath;
	}

	public void setExercisePath(String exercisePath) {
		this.exercisePath = exercisePath;
	}
	
	public void loadExerciseInfo(GitApiExerciseMessage message) {
		T.call(this);
		
		setCourseId(message.getCourseId());
		setExercisePath(message.getExercisePath());
	}

	public TaskPath taskPath() {
		T.call(this);

		TaskPath taskPath = TaskPath.fromRawPath(getExercisePath());
		
		return taskPath;
	}

	
}
