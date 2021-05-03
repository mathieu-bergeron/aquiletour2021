package ca.aquiletour.core.pages.course.handlers;

import java.util.List;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.courses.model.CompletionByStudentId;
import ca.aquiletour.core.models.courses.model.CourseModel;
import ca.aquiletour.core.models.courses.student.TaskCompletion;
import ca.aquiletour.core.models.courses.task_types.TaskType;
import ca.aquiletour.core.models.dates.AquiletourDate;
import ca.aquiletour.core.models.dates.CourseDate;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.course.views.TaskView;
import ca.ntro.core.models.listeners.ClearItemsListener;
import ca.ntro.core.models.listeners.EntryAddedListener;
import ca.ntro.core.models.listeners.ItemAddedListener;
import ca.ntro.core.models.listeners.ListObserver;
import ca.ntro.core.models.listeners.ValueObserver;
import ca.ntro.core.mvc.ModelViewSubViewMessageHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public abstract class CourseViewModel<M extends CourseModel, V extends CourseView> extends ModelViewSubViewMessageHandler<M, V, ShowTaskMessage>  {
	
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
		
		if(!message.getGroupId().equals(currentGroupId)) {
			currentGroupId = message.getGroupId();
		}

		if(!model.getCoursePath().equals(currentCoursePath)) {
			currentCoursePath = model.getCoursePath();
		}

		if(currentTask != null) {
			removeAllObservers();
		}
		
		currentTask = model.findTaskByPath(message.getTaskPath());
		
		if(currentTask != null) {
			
			view.identifyCurrentTask(currentCoursePath(), currentTask);
			view.displayBreadcrumbs(currentCoursePath(), currentTask.breadcrumbs());

			observeCurrentTask(model, currentGroupId(), view, subViewLoader);
			observeCompletionByTaskId(model, view);
		}
	}

	protected void observeCompletionByTaskId(M model, V view) {
		T.call(this);

		model.getCompletions().removeObservers();
		model.getCompletions().onEntryAdded(new EntryAddedListener<CompletionByStudentId>() {
			@Override
			public void onEntryAdded(String taskId, CompletionByStudentId completionByStudentId) {
				T.call(this);
				
				observeCompletionByStudentId(view, taskId, completionByStudentId);
			}
		});
	}

	protected void observeCompletionByStudentId(V view, 
											  String taskId, 
											  CompletionByStudentId completionByStudentId) {
		T.call(this);

		completionByStudentId.removeObservers();
		if(currentTask() != null && currentTask().id().equals(taskId)) {
			completionByStudentId.onEntryAdded(new EntryAddedListener<TaskCompletion>() {
				@Override
				public void onEntryAdded(String studentId, TaskCompletion value) {
					T.call(this);
					
					displayStudentCompletion(studentId, value, view);
				}
			});
		}
	}
	
	protected abstract void displayStudentCompletion(String studentId, TaskCompletion completion, V view);

	private void removeAllObservers() {
		T.call(this);

		currentTask.getPreviousTasks().removeObservers();
		currentTask.getSubTasks().removeObservers();
		currentTask.getNextTasks().removeObservers();
		currentTask.getDescription().removeObservers();
		currentTask.getEndTime().removeObservers();
		currentTask.getTaskTypes().removeObservers();
		currentTask.getTitle().removeObservers();
	}

	private void observeCurrentTask(M model, String groupId, V view, ViewLoader subViewLoader) {
		T.call(this);

		if(currentTask.isRootTask()) {

			view.hidePreviousTasks();

		}else {

			view.showPreviousTasks();
			observePreviousTasks(model, view, subViewLoader);
		}
		
		observeCurrentTaskTitle(view);
		observeCurrentTaskDescription(view);
		observeCurrentTaskEndTime(model, view);
		observeCurrentTaskTypes(view);

		observeSubTasks(model, view, subViewLoader);
		
		if(currentTask.isRootTask()) {
			
			view.hideNextTasks();

		}else {

			view.showNextTasks();
			observeNextTasks(model, view, subViewLoader);
		}
	}

	private void observeCurrentTaskDescription(V view) {
		T.call(this);
		
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

	private void displayTaskDescription(V view, String value) {
		T.call(this);

		String description = value;
		
		if(!isEditable()) {
			
			description = TaskType.removeTypesFromDescription(value);

		}

		view.displayTaskDescription(description, isEditable());
	}

	private void observeCurrentTaskTypes(V view) {
		T.call(this);

		view.clearTaskTypes();
		currentTask.getTaskTypes().removeObservers();
		currentTask.getTaskTypes().onItemAdded(new ItemAddedListener<TaskType>() {
			@Override
			public void onItemAdded(int index, TaskType item) {
				T.call(this);
				
				view.appendTaskType(item);
			}
		});
		
		currentTask.getTaskTypes().onClearItems(new ClearItemsListener() {
			@Override
			public void onClearItems() {
				view.clearTaskTypes();
			}
		});
	}
		

	private void observeCurrentTaskEndTime(M model, V view) {
		T.call(this);
		
		currentTask.getEndTime().observe(new ValueObserver<CourseDate>() {

			@Override
			public void onValue(CourseDate value) {
				T.call(this);
				
				displayCurrentTaskEndTime(model, view);
			}

			@Override
			public void onDeleted(CourseDate lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValueChanged(CourseDate oldValue, CourseDate value) {
				T.call(this);
				
				displayCurrentTaskEndTime(model, view);
			}
		});

	}

	private void displayCurrentTaskEndTime(M model, V view) {
		T.call(this);
		
		AquiletourDate endTime = model.taskEndTimeForGroup(currentGroupId(), currentTask.id());

		view.displayTaskEndTime(endTime, isEditable());
	}


	private void observeCurrentTaskTitle(V view) {
		T.call(this);

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


	private void observePreviousTasks(M model, V view, ViewLoader subViewLoader) {
		T.call(this);
		
		view.clearPreviousTasks();
		currentTask.getPreviousTasks().removeObservers();
		currentTask.getPreviousTasks().observe(new ListObserver<String>() {
			
			@Override
			public void onItemRemoved(int index, String item) {
				T.call(this);

				displayPreviousTasksInOrder(model, view, subViewLoader);
			}
			
			@Override
			public void onItemUpdated(int index, String item) {
				T.call(this);

				displayPreviousTasksInOrder(model, view, subViewLoader);
			}
			
			@Override
			public void onItemAdded(int index, String taskId) {
				T.call(this);

				displayPreviousTasksInOrder(model, view, subViewLoader);
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

				displayPreviousTasksInOrder(model, view, subViewLoader);
			}
		});
	}

	private void observeSubTasks(M model, V view, ViewLoader subViewLoader) {
		T.call(this);
		
		view.clearSubtasks();
		currentTask.getSubTasks().removeObservers();
		currentTask.getSubTasks().observe(new ListObserver<String>() {
			
			@Override
			public void onItemRemoved(int index, String item) {
				T.call(this);

				displaySubtasksInOrder(model, view, subViewLoader);
			}
			
			@Override
			public void onItemUpdated(int index, String item) {
				T.call(this);

				displaySubtasksInOrder(model, view, subViewLoader);
			}
			
			@Override
			public void onItemAdded(int index, String taskId) {
				T.call(this);

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

				displaySubtasksInOrder(model, view, subViewLoader);
			}
		});
	}

	private void observeNextTasks(M model, V view, ViewLoader subViewLoader) {
		T.call(this);
		
		view.clearNextTasks();
		currentTask.getNextTasks().removeObservers();
		currentTask.getNextTasks().observe(new ListObserver<String>() {
			
			@Override
			public void onItemRemoved(int index, String item) {
				T.call(this);

				displayNextTasksInOrder(model, view, subViewLoader);
			}
			
			@Override
			public void onItemUpdated(int index, String item) {
				T.call(this);

				displayNextTasksInOrder(model, view, subViewLoader);
			}
			
			@Override
			public void onItemAdded(int index, String taskId) {
				T.call(this);

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

				displayNextTasksInOrder(model, view, subViewLoader);
			}
		});
	}

	private void displayPreviousTasksInOrder(M model, CourseView view, ViewLoader subViewLoader) {
		T.call(this);

		view.clearPreviousTasks();
		
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

	private void displayNextTasksInOrder(M model, CourseView view, ViewLoader subViewLoader) {
		T.call(this);

		view.clearNextTasks();

		currentTask.forEachNextTaskInOrder(st -> {
			view.appendNextTask(model.getCoursePath(), st);
		});
	}
}
