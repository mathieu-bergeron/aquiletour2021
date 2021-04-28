package ca.aquiletour.core.models.courses.task_types;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class TaskType implements NtroModelValue {

	public static List<TaskType> typesFromDescription(String description) {
		T.call(TaskType.class);
		
		List<TaskType> types = new ArrayList<>();

		if(description.contains("{dépôtGit}")) {
			
			types.add(new GitRepoTask());
		}

		if(description.contains("{remiseGit}")) {
			
			types.add(new GitExerciseTask());
		}
		
		return types;
	}

	public static String removeTypesFromDescription(String description) {
		T.call(TaskType.class);

		String result = description;
		
		result = result.replace("{dépôtGit}", "");
		result = result.replace("{remiseGit}", "");
		
		return result;
	}

}
