package ca.aquiletour.core.pages.course.handlers;

import java.util.List;
import ca.aquiletour.core.models.courses.base.CourseModel;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.course.views.TaskView;
import ca.ntro.core.models.listeners.ListObserver;
import ca.ntro.core.mvc.ModelViewSubViewMessageHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class CourseViewModel<M extends CourseModel, V extends CourseView> extends ModelViewSubViewMessageHandler<M, V, ShowTaskMessage>  {
	
	private Task currentTask;
	
	protected Task getCurrentTask() {
		return currentTask;
	}

	@Override
	protected void handle(M model, V view, ViewLoader subViewLoader, ShowTaskMessage message) {
		T.call(this);

		if(currentTask != null) {
			currentTask.removeObservers();
		}
		
		currentTask = model.findTaskByPath(message.getTaskPath());
		
		if(currentTask != null) {
			
			view.identifyCurrentTask(model.getCoursePath(), currentTask);
			view.displayBreadcrumbs(model.getCoursePath(), currentTask.breadcrumbs());

			observeCurrentTask(model, view, subViewLoader);
		}
	}

	private void observeCurrentTask(CourseModel model, CourseView view, ViewLoader subViewLoader) {
		T.call(this);

		if(currentTask.isRootTask()) {

			view.hidePreviousTasks();

		}else {

			view.showPreviousTasks();
			observePreviousTasks(model, view, subViewLoader);
		}


		observeSubTasks(model, view, subViewLoader);

		
		if(currentTask.isRootTask()) {
			
			view.hideNextTasks();

		}else {

			view.showNextTasks();
			observeNextTasks(model, view, subViewLoader);
		}
	}


	private void observePreviousTasks(CourseModel model, CourseView view, ViewLoader subViewLoader) {
		T.call(this);

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

	private void observeSubTasks(CourseModel model, CourseView view, ViewLoader subViewLoader) {
		T.call(this);

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

	private void observeNextTasks(CourseModel model, CourseView view, ViewLoader subViewLoader) {
		T.call(this);

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

	private void displayPreviousTasksInOrder(CourseModel model, CourseView view, ViewLoader subViewLoader) {
		T.call(this);

		view.clearPreviousTasks();
		
		currentTask.forEachPreviousTaskInOrder(pt -> {
			view.appendPreviousTask(model.getCoursePath(), pt);
		});
	}

	private void displaySubtasksInOrder(CourseModel model, CourseView view, ViewLoader subViewLoader) {
		T.call(this);

		view.clearSubtasks();
		
		currentTask.forEachSubTaskInOrder(st -> {

			TaskView taskView = (TaskView) subViewLoader.createView();
			view.appendSubtask(taskView);

			taskView.displayTask(model.getCoursePath(), st);
		});
	}

	private void displayNextTasksInOrder(CourseModel model, CourseView view, ViewLoader subViewLoader) {
		T.call(this);

		view.clearNextTasks();

		currentTask.forEachNextTaskInOrder(st -> {
			view.appendNextTask(model.getCoursePath(), st);
		});
	}
}
