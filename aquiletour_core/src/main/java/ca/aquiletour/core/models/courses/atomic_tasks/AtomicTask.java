package ca.aquiletour.core.models.courses.atomic_tasks;


import ca.aquiletour.core.models.courses.atomic_tasks.default_task.DefaultAtomicTask;
import ca.aquiletour.core.models.courses.atomic_tasks.git_exercice.GitExerciseTask;
import ca.aquiletour.core.models.courses.atomic_tasks.git_repo.GitRepoTask;
import ca.aquiletour.core.models.courses.atomic_tasks.short_text.ShortTextTask;
import ca.aquiletour.core.models.courses.base.OnAtomicTaskAdded;
import ca.aquiletour.core.models.courses.base.Task;
import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;


public class AtomicTask implements NtroModelValue {

	public static void addAtomicTasksFromDescription(Task parentTask, String description, OnAtomicTaskAdded atomicTaskListener) {
		T.call(AtomicTask.class);
		
		if(description.contains("{dépôtGit}")) {
			
			parentTask.addEntryTask(new GitRepoTask(parentTask.getPath()), atomicTaskListener);
		}

		if(description.contains("{remiseGit}")) {
			
			Path repoPath = parentTask.findRepoPath();
			
			if(repoPath == null) {
				repoPath = Path.fromRawPath("/");
			}

			GitExerciseTask gitTask = new GitExerciseTask(repoPath);
			
			gitTask.setCompletionKeywords(parentTask.getTitle().getValue());

			parentTask.addExitTask(gitTask, atomicTaskListener);
		}

		if(description.contains("{texteCourt}")) {
			
			parentTask.addExitTask(new ShortTextTask(), atomicTaskListener);
		}
		
		if(parentTask.getExitTasks().size() == 0
				&& parentTask.getSubTasks().size() == 0) {

			parentTask.addExitTask(new DefaultAtomicTask(), atomicTaskListener);
		}
	}

	public static String removeAtomicTasksFromDescription(String description) {
		T.call(AtomicTask.class);

		String result = description;
		
		result = result.replace("{dépôtGit}", "");
		result = result.replace("{remiseGit}", "");
		result = result.replace("{texteCourt}", "");
		result = result.trim();
		
		return result;
	}

	private String id = idFromType(this.getClass());

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static String idFromType(Class<? extends AtomicTask> taskType) {
		return Ntro.introspector().getSimpleNameForClass(taskType);
	}
}
