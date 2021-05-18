package ca.aquiletour.core.pages.course.student.handlers;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;
import ca.aquiletour.core.models.courses.student.CompletionByAtomicTaskId;
import ca.aquiletour.core.models.courses.student.CourseModelStudent;
import ca.aquiletour.core.pages.course.handlers.CourseViewModel;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.student.views.CourseViewStudent;
import ca.ntro.core.models.listeners.EntryAddedListener;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class CourseViewModelStudent extends CourseViewModel<CourseModelStudent, CourseViewStudent> {

	@Override
	protected void handle(CourseModelStudent model, CourseViewStudent view, ViewLoader subViewLoader, ShowTaskMessage message) {
		T.call(this);
		super.handle(model, view, subViewLoader, message);
		
	}

	@Override
	protected void displayStudentCompletion(String studentId, CourseViewStudent view) {
		T.call(this);

		view.checkCompletion(true);
	}

	protected void observeCompletions(CourseModelStudent model, CourseViewStudent view) {
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
	protected void displayEntryTask(CourseModelStudent model, CourseViewStudent view, AtomicTask atomicTask) {
		T.call(this);
		
		AtomicTaskCompletion completion = getCompletion(model, atomicTask);
		String groupId = model.getGroupId().getValue();
		view.appendEntryTask(groupId, atomicTask, completion);
	}

	private AtomicTaskCompletion getCompletion(CourseModelStudent model, AtomicTask atomicTask) {
		T.call(this);
		
		CompletionByAtomicTaskId completions =  model.getCompletions().valueOf(currentTask().id());

		AtomicTaskCompletion completion = null;
		if(completions != null) {
			completion = completions.valueOf(atomicTask.getId());
		}

		return completion;
	}

	@Override
	protected void displayExitTask(CourseModelStudent model, CourseViewStudent view, AtomicTask task) {
		T.call(this);

		AtomicTaskCompletion completion = getCompletion(model, task);
		String groupId = model.getGroupId().getValue();
		view.appendExitTask(groupId, task, completion);
	}
}
