package ca.aquiletour.core.models.courses.atomic_tasks;

import ca.aquiletour.core.models.courses.atomic_tasks.git_exercice.GitExerciseTask;
import ca.aquiletour.core.models.courses.atomic_tasks.git_repo.GitRepoTask;
import ca.aquiletour.core.models.courses.atomic_tasks.short_text.ShortTextTask;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.courses.base.functionnal.FindResults;
import ca.aquiletour.core.models.courses.base.functionnal.VisitDirection;
import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

import static ca.aquiletour.core.models.courses.base.functionnal.VisitDirection.*;


public class AtomicTask implements NtroModelValue {

	public static void addAtomicTasksFromDescription(Task parentTask, String description) {
		T.call(AtomicTask.class);
		
		if(description.contains("{dépôtGit}")) {
			
			parentTask.addEntryTask(new GitRepoTask(parentTask.getPath()));
		}

		if(description.contains("{remiseGit}")) {
			
			Path repoPath = new Path();
			
			FindResults findResults = parentTask.findAll(new VisitDirection[] {PREVIOUS, PARENT}, true, t -> {
				return t.hasAtomicTaskOfType(GitRepoTask.class);
			});
			
			if(findResults.size() > 0) {
				repoPath = findResults.closest().getTask().getPath();
				T.values("repoPath", repoPath);
			}

			parentTask.addExitTask(new GitExerciseTask(repoPath));
		}

		if(description.contains("{texteCourt}")) {
			
			parentTask.addExitTask(new ShortTextTask());
		}
		
		if(parentTask.getExitTasks().size() == 0) {

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

	private String id = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
