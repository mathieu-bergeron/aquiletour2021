package ca.aquiletour.core.pages.course.student.handlers;

import java.util.Map;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;
import ca.aquiletour.core.models.courses.status.BlockedWaitingForParent;
import ca.aquiletour.core.models.courses.status.StatusBlocked;
import ca.aquiletour.core.models.courses.status.TaskStatus;
import ca.aquiletour.core.models.courses.student.CompletionByAtomicTaskId;
import ca.aquiletour.core.models.courses.student.CourseModelStudent;
import ca.aquiletour.core.pages.course.handlers.CourseViewModel;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.student.views.CourseViewStudent;
import ca.ntro.core.models.listeners.EntryAddedListener;
import ca.ntro.core.models.listeners.MapObserver;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class CourseViewModelStudent extends CourseViewModel<CourseModelStudent, CourseViewStudent> {

	@Override
	protected void handle(CourseModelStudent model, CourseViewStudent view, ViewLoader subViewLoader, ShowTaskMessage message) {
		T.call(this);
		super.handle(model, view, subViewLoader, message);
		
	}

	@Override
	protected void observeCurrentTask(CourseModelStudent model, String groupId, CourseViewStudent view, ViewLoader subViewLoader) {
		T.call(this);

		super.observeCurrentTask(model, groupId, view, subViewLoader);
		
		displayTaskStatus(currentTask().status(model.getCompletions()), view);
		
		view.enableSubTasks(currentTask().areEntryTasksDone(model.getCompletions()));
	}

	private void displayTaskStatus(TaskStatus status, CourseViewStudent view) {
		T.call(this);
		
		if(status.isBlocked() 
				&& currentTask().hasExitTasks()) {

			view.displayToCompleteFirst(true);
			view.updateToCompleteFirst((StatusBlocked) status);
			
		}else {
			
			view.displayToCompleteFirst(false);
		}
		
		
	}

	@Override
	protected void displayStudentCompletion(String studentId, CourseViewStudent view) {
		T.call(this);
	}

	protected void observeTaskCompletions(CourseModelStudent model, CourseViewStudent view) {
		T.call(this);
		
		view.displayDoneTasks(false);
		
		model.getCompletions().removeObservers();
		model.getCompletions().onEntryAdded(new EntryAddedListener<CompletionByAtomicTaskId>() {
			@Override
			public void onEntryAdded(String taskId, CompletionByAtomicTaskId completions) {
				T.call(this);
				
				if(taskId.equals(currentTask().id())) {
					observeAtomicTaskCompletions(model, completions, view, model.getGroupId().getValue());
					displayStudentCompletion(null, view);
				}
			}
		});
	}

	@Override
	protected void displayEntryTask(CourseModelStudent model, CourseViewStudent view, AtomicTask atomicTask) {
		T.call(this);

		view.appendEntryTask(model.getGroupId().getValue(), atomicTask);
	}

	private void observeAtomicTaskCompletions(CourseModelStudent model, CompletionByAtomicTaskId completions, CourseViewStudent view, String groupId) {
		T.call(this);

		completions.removeObservers();
		completions.observe(new MapObserver<AtomicTaskCompletion>() {
			
			@Override
			public void onEntryAdded(String atomicTaskId, AtomicTaskCompletion completion) {
				T.call(this);

				AtomicTask atomicTask = model.atomicTask(currentTask().getPath(), atomicTaskId);
				if(atomicTask != null) {
					view.addCompletionToEntryTask(groupId, atomicTask, completion);
				}
			}

			@Override
			public void onEntryRemoved(String atomicTaskId, AtomicTaskCompletion completion) {
				T.call(this);
				
				AtomicTask atomicTask = model.atomicTask(currentTask().getPath(), atomicTaskId);
				if(atomicTask != null) {
					view.removeEntryTask(atomicTask);
					view.appendEntryTask(groupId, atomicTask);
				}
			}
			
			@Override
			public void onClearEntries() {
			}
			
			@Override
			public void onDeleted(Map<String, AtomicTaskCompletion> lastValue) {
			}
			
			@Override
			public void onValue(Map<String, AtomicTaskCompletion> value) {
			}
			
			@Override
			public void onValueChanged(Map<String, AtomicTaskCompletion> oldValue, Map<String, AtomicTaskCompletion> value) {
			}
			
		});

		completions.onEntryAdded(new EntryAddedListener<AtomicTaskCompletion>() {
			@Override
			public void onEntryAdded(String atomicTaskId, AtomicTaskCompletion completion) {
				T.call(this);
				
			}

		});
		
	}

	@SuppressWarnings("unused")
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

		String groupId = model.getGroupId().getValue();
		view.appendExitTask(groupId, task);
	}
}
