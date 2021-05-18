package ca.aquiletour.core.pages.course.student.handlers;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.aquiletour.core.models.courses.student.CompletionByAtomicTaskId;
import ca.aquiletour.core.models.courses.student.CourseStudent;
import ca.aquiletour.core.models.courses.task_completions.AtomicTaskCompletion;
import ca.aquiletour.core.pages.course.handlers.CourseViewModel;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.student.views.CourseViewStudent;
import ca.ntro.core.models.listeners.EntryAddedListener;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class CourseViewModelStudent extends CourseViewModel<CourseStudent, CourseViewStudent> {

	@Override
	protected void handle(CourseStudent model, CourseViewStudent view, ViewLoader subViewLoader, ShowTaskMessage message) {
		T.call(this);
		super.handle(model, view, subViewLoader, message);
		
	}

	@Override
	protected void displayStudentCompletion(String studentId, CourseViewStudent view) {
		T.call(this);

		view.checkCompletion(true);
	}

	protected void observeCompletions(CourseStudent model, CourseViewStudent view) {
		T.call(this);
		
		model.getCompletions().onEntryAdded(new EntryAddedListener<CompletionByAtomicTaskId>() {
			@Override
			public void onEntryAdded(String atomicTaskId, CompletionByAtomicTaskId value) {
				T.call(this);
				
				displayStudentCompletion(null, view);
			}
		});
	}

	@Override
	protected void displayEntryTask(CourseStudent model, CourseViewStudent view, AtomicTask task) {
		T.call(this);
		
		AtomicTaskCompletion completion = getCompletion(model, task);
		String groupId = model.getGroupId().getValue();
		view.appendEntryTask(groupId, task, completion);
	}

	private AtomicTaskCompletion getCompletion(CourseStudent model, AtomicTask task) {
		T.call(this);

		CompletionByAtomicTaskId completions =  model.getCompletions().valueOf(currentTask().id());

		AtomicTaskCompletion completion = null;
		if(completions != null) {
			completion = completions.valueOf(task.getId());
		}

		return completion;
	}

	@Override
	protected void displayExitTask(CourseStudent model, CourseViewStudent view, AtomicTask task) {
		T.call(this);

		AtomicTaskCompletion completion = getCompletion(model, task);
		String groupId = model.getGroupId().getValue();
		view.appendExitTask(groupId, task, completion);
	}
}
