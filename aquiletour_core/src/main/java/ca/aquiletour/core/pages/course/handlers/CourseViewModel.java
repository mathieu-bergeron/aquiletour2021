package ca.aquiletour.core.pages.course.handlers;

import java.util.List;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.aquiletour.core.models.courses.base.CourseModel;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.dates.AquiletourDate;
import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.course.views.TaskView;
import ca.ntro.core.models.listeners.ClearItemsListener;
import ca.ntro.core.models.listeners.ItemAddedListener;
import ca.ntro.core.models.listeners.ListObserver;
import ca.ntro.core.models.listeners.ValueObserver;
import ca.ntro.core.mvc.ModelViewSubViewMessageHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public abstract class CourseViewModel<M extends CourseModel<?>, V extends CourseView> extends ModelViewSubViewMessageHandler<M, V, ShowTaskMessage>  {
	
	private CoursePath currentCoursePath;
	private Task currentTask;
	private String currentGroupId;
	
	protected Task currentTask() {
		return currentTask;
	}
	
	protected String currentGroupId() {
		return currentGroupId;
	}
	
	protected CoursePath currentCoursePath() {
		return currentCoursePath;
	}

	protected boolean isEditable() {
		return false;
	}

	@Override
	protected void handle(M model, V view, ViewLoader subViewLoader, ShowTaskMessage message) {
		T.call(this);
		
		if(message.getGroupId() != null
				&& !message.getGroupId().equals(currentGroupId)) {
			currentGroupId = message.getGroupId();

		}else if(currentGroupId == null) {
			
			currentGroupId = Constants.COURSE_STRUCTURE_ID;

		}

		if(!model.getCoursePath().equals(currentCoursePath)) {
			currentCoursePath = model.getCoursePath();
		}

		currentTask = model.findTaskByPath(message.getTaskPath());
		
		if(currentTask != null) {
			
			view.displayTaskEndTime(false);
			
			view.identifyCurrentTask(currentCoursePath(), currentTask);
			view.displayBreadcrumbs(currentCoursePath(), currentTask.breadcrumbs());

			observeCurrentTask(model, currentGroupId(), view, subViewLoader);
			observeTaskCompletions(model, view);
		}
	}

	protected abstract void observeTaskCompletions(M model, V view);

	protected abstract void displayStudentCompletion(String studentId, V view);

	protected void observeCurrentTask(M model, String groupId, V view, ViewLoader subViewLoader) {
		T.call(this);
		
		observeCurrentTaskTitle(view);
		observeCurrentTaskDescription(view);
		observeCurrentTaskEndTime(model, view);

		observePreviousTasks(model, view);
		observeEntryTasks(model, view);
		observeSubTasks(model, view, subViewLoader);
		observeExitTasks(model, view);
		observeNextTasks(model, view);
	}

	private void observeCurrentTaskDescription(V view) {
		T.call(this);
		
		view.displayTaskDescription(false);

		currentTask.getDescription().removeObservers();
		currentTask.getDescription().observe(new ValueObserver<String>() {

			@Override
			public void onValue(String value) {
				T.call(this);
				
				displayTaskDescription(view, value);
			}


			@Override
			public void onDeleted(String lastValue) {
			}

			@Override
			public void onValueChanged(String oldValue, String value) {
				T.call(this);
				
				displayTaskDescription(view, value);
			}
		});
	}

	private void displayTaskDescription(V view, String rawDescription) {
		T.call(this);


		String description = rawDescription;
		
		if(!isEditable()) {
			
			description = AtomicTask.removeAtomicTasksFromDescription(rawDescription);

		}

		view.displayTaskDescription(!description.isEmpty());
		view.updateTaskDescription(description, isEditable());

	}

	private void observeEntryTasks(M model, V view) {
		T.call(this);

		view.clearEntryTasks();

		currentTask.getEntryTasks().removeObservers();
		currentTask.getEntryTasks().onItemAdded(new ItemAddedListener<AtomicTask>() {
			@Override
			public void onItemAdded(int index, AtomicTask item) {
				T.call(this);
				
				displayEntryTask(model, view, item);
			}
		});
		
		currentTask.getEntryTasks().onClearItems(new ClearItemsListener() {
			@Override
			public void onClearItems() {
				view.clearEntryTasks();
			}
		});
	}
	
	protected abstract void displayEntryTask(M model, V view, AtomicTask task);
	protected abstract void displayExitTask(M model, V view, AtomicTask task);

	private void observeExitTasks(M model, V view) {
		T.call(this);

		view.clearExitTasks();
		currentTask.getExitTasks().removeObservers();
		currentTask.getExitTasks().onItemAdded(new ItemAddedListener<AtomicTask>() {
			@Override
			public void onItemAdded(int index, AtomicTask item) {
				T.call(this);
				
				displayExitTask(model, view, item);
			}
		});
		
		currentTask.getExitTasks().onClearItems(new ClearItemsListener() {
			@Override
			public void onClearItems() {
				view.clearExitTasks();
			}
		});
	}
		

	private void observeCurrentTaskEndTime(M model, V view) {
		T.call(this);
		
		currentTask.getEndTime().removeObservers();
		currentTask.getEndTime().observe(new ValueObserver<AquiletourDate>() {

			@Override
			public void onValue(AquiletourDate value) {
				T.call(this);
				
				displayCurrentTaskEndTime(model, view);
			}

			@Override
			public void onDeleted(AquiletourDate lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValueChanged(AquiletourDate oldValue, AquiletourDate value) {
				T.call(this);
				
				displayCurrentTaskEndTime(model, view);
			}
		});

	}

	private void displayCurrentTaskEndTime(M model, V view) {
		T.call(this);
		
		
		AquiletourDate endTime = model.taskEndTimeForGroup(currentGroupId(), currentTask.id());
		
		if(endTime != null && endTime.isDefined()) {

			view.displayTaskEndTime(true);
			view.updateTaskEndTime(endTime, isEditable());
		}
	}


	private void observeCurrentTaskTitle(V view) {
		T.call(this);

		currentTask.getTitle().removeObservers();
		currentTask.getTitle().observe(new ValueObserver<String>() {
			
			@Override
			public void onDeleted(String lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(String value) {
				T.call(this);
				
				view.displayTaskTitle(value, isEditable());
			}
			
			@Override
			public void onValueChanged(String oldValue, String value) {
				T.call(this);

				view.displayTaskTitle(value, isEditable());
			}
		});
	}


	private void observePreviousTasks(M model, V view) {
		T.call(this);

		view.displayPreviousTasks(isEditable());

		currentTask.getPreviousTasks().removeObservers();
		currentTask.getPreviousTasks().observe(new ListObserver<String>() {
			
			@Override
			public void onItemRemoved(int index, String item) {
				T.call(this);

				displayPreviousTasksInOrder(model, view);
			}
			
			@Override
			public void onItemUpdated(int index, String item) {
				T.call(this);

				displayPreviousTasksInOrder(model, view);
			}
			
			@Override
			public void onItemAdded(int index, String taskId) {
				T.call(this);

				displayPreviousTasksInOrder(model, view);
			}
			
			@Override
			public void onDeleted(List<String> lastValue) {
			}
			
			@Override
			public void onValue(List<String> value) {
			}
			
			@Override
			public void onValueChanged(List<String> oldValue, List<String> value) {
			}
			
			@Override
			public void onClearItems() {
				T.call(this);

				displayPreviousTasksInOrder(model, view);
			}
		});
	}

	private void observeSubTasks(M model, V view, ViewLoader subViewLoader) {
		T.call(this);
		
		view.displaySubTasks(isEditable());

		view.clearSubtasks();
		currentTask.getSubTasks().removeObservers();
		currentTask.getSubTasks().observe(new ListObserver<String>() {
			
			@Override
			public void onItemRemoved(int index, String item) {
				T.call(this);
				
				if(currentTask.getSubTasks().size() == 0) {

					view.displaySubTasks(isEditable());

				}else {
					
					view.displaySubTasks(true);
					displaySubtasksInOrder(model, view, subViewLoader);
				}
			}
			
			@Override
			public void onItemUpdated(int index, String item) {
				T.call(this);
				
				view.displaySubTasks(true);
				displaySubtasksInOrder(model, view, subViewLoader);
			}
			
			@Override
			public void onItemAdded(int index, String taskId) {
				T.call(this);

				view.displaySubTasks(true);
				displaySubtasksInOrder(model, view, subViewLoader);
			}
			
			@Override
			public void onDeleted(List<String> lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(List<String> value) {
			}
			
			@Override
			public void onValueChanged(List<String> oldValue, List<String> value) {
			}

			
			@Override
			public void onClearItems() {
				T.call(this);
				view.displaySubTasks(isEditable());
			}
		});
	}

	private void observeNextTasks(M model, V view) {
		T.call(this);
		
		view.displayNextTasks(isEditable());

		currentTask.getNextTasks().removeObservers();
		currentTask.getNextTasks().observe(new ListObserver<String>() {
			
			@Override
			public void onItemRemoved(int index, String item) {
				T.call(this);
				
				if(currentTask.getNextTasks().size() == 0) {

					view.displayNextTasks(isEditable());
					
				}else {

					view.displayNextTasks(true);
					displayNextTasksInOrder(model, view);
				}
			}
			
			@Override
			public void onItemUpdated(int index, String item) {
				T.call(this);

				view.displayNextTasks(true);
				displayNextTasksInOrder(model, view);
			}
			
			@Override
			public void onItemAdded(int index, String taskId) {
				T.call(this);

				view.displayNextTasks(true);
				displayNextTasksInOrder(model, view);
			}
			
			@Override
			public void onDeleted(List<String> lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(List<String> value) {
			}
			
			@Override
			public void onValueChanged(List<String> oldValue, List<String> value) {
			}

			
			@Override
			public void onClearItems() {
				T.call(this);

				view.displayNextTasks(isEditable());
			}
		});
	}

	private void displayPreviousTasksInOrder(M model, CourseView view) {
		T.call(this);
		
		view.displayPreviousTasks(true);
		
		currentTask.forEachPreviousTaskInOrder(pt -> {
			view.appendPreviousTask(model.getCoursePath(), pt);
		});
	}

	private void displaySubtasksInOrder(M model, CourseView view, ViewLoader subViewLoader) {
		T.call(this);

		view.clearSubtasks();
		
		currentTask.forEachSubTaskInOrder(st -> {
			
			TaskView taskView = (TaskView) subViewLoader.createView();
			view.appendSubtask(taskView);

			taskView.displayTask(model.getCoursePath(), st);
		});
	}

	private void displayNextTasksInOrder(M model, CourseView view) {
		T.call(this);

		view.clearNextTasks();

		currentTask.forEachNextTaskInOrder(nt -> {
			view.appendNextTask(model.getCoursePath(), nt);
		});
	}
}
