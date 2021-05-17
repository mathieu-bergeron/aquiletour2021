package ca.aquiletour.core.models.courses.base;

import ca.aquiletour.core.models.courses.atomic_tasks.GitExerciseTask;
import ca.aquiletour.core.models.courses.atomic_tasks.GitRepoTask;
import ca.aquiletour.core.models.courses.atomic_tasks.ShortTextTask;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class AtomicTask implements NtroModelValue {

	public static void addAtomicTasksFromDescription(Task parentTask, String description) {
		T.call(AtomicTask.class);
		
		if(description.contains("{dépôtGit}")) {
			
			parentTask.addEntryTask(new GitRepoTask());
		}

		if(description.contains("{remiseGit}")) {
			
			parentTask.addExitTask(new GitExerciseTask());
		}

		if(description.contains("{texteCourt}")) {
			
			parentTask.addExitTask(new ShortTextTask());
		}
	}

	public static String removeAtomicTasksFromDescription(String description) {
		T.call(AtomicTask.class);

		String result = description;
		
		result = result.replace("{dépôtGit}", "");
		result = result.replace("{remiseGit}", "");
		result = result.replace("{texteCourt}", "");
		
		return result;
	}

}
