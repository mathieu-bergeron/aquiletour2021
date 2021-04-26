package ca.aquiletour.core.models.courses.task_types;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class TaskType implements NtroModelValue {

	public static TaskType fromDescription(String description) {
		T.call(TaskType.class);
		
		TaskType type = null;
		
		if(description.contains("{dépôtGit}")) {
			
			type = new GitRepoTask();
			System.out.println("dépôtGit");

		}else if(description.contains("{remiseGit}")) {
			
			type = new GitExerciseTask();
			System.out.println("remiseGit");

		}else {
			
			type = new TaskType();
		}
		
		return type;
	}

}
