package ca.aquiletour.core.pages.course.handlers;


import java.util.List;

import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.models.CourseModel;
import ca.aquiletour.core.pages.course.models.Task;
import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.course.views.TaskView;
import ca.ntro.core.models.listeners.ListObserver;
import ca.ntro.core.mvc.ModelViewSubViewMessageHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class CourseViewModel extends ModelViewSubViewMessageHandler<CourseModel, CourseView, ShowTaskMessage>  {
	
	private Task currentTask;
	
	protected Task getCurrentTask() {
		return currentTask;
	}

	@Override
	protected void handle(CourseModel model, CourseView view, ViewLoader subViewLoader, ShowTaskMessage message) {
		T.call(this);

		if(currentTask != null) {
			currentTask.removeObservers();
		}
		
		currentTask = model.findTaskByPath(message.getTaskPath());
		
		if(currentTask != null) {
			
			view.identifyCurrentTask(currentTask.id());
			view.displayBreadcrumbs(model.getCourseId(), currentTask.breadcrumbs());

			observeCurrentTask(model, view, subViewLoader);
		}
	}

	private void observeCurrentTask(CourseModel model, CourseView view, ViewLoader subViewLoader) {
		T.call(this);
		
		currentTask.getSubTasks().observe(new ListObserver<String>() {
			
			@Override
			public void onItemRemoved(int index, String item) {
				T.call(this);

				displaySubtasksInOrder(model, view, subViewLoader);
			}
			
			@Override
			public void onItemUpdated(int index, String item) {
				// TODO Auto-generated method stub
				
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
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void displaySubtasksInOrder(CourseModel model, CourseView view, ViewLoader subViewLoader) {
		T.call(this);

		view.clearTasks();
		
		currentTask.forEachSubTaskInOrder(st -> {

			TaskView taskView = (TaskView) subViewLoader.createView();
			view.appendTask(taskView);

			displayTask(model.getCourseId(), st, taskView);

			observeSubTask(st, model.getCourseId(), taskView);
		});
	}

	private void displayTask(String courseId, Task st, TaskView taskView) {
		T.call(this);

		taskView.clear();
		taskView.displayTask(courseId, st);
	}

	private void observeSubTask(Task st, String courseId, TaskView taskView) {
		T.call(this);
		
		st.getPreviousTasks().removeObservers();
		st.getSubTasks().removeObservers();
		st.getNextTasks().removeObservers();
		
		st.getPreviousTasks().observe(new ListObserver<String>() {
			
			@Override
			public void onItemRemoved(int index, String item) {
				T.call(this);

				displayTask(courseId, st, taskView);
			}
			
			@Override
			public void onItemUpdated(int index, String item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemAdded(int index, String item) {
				T.call(this);

				displayTask(courseId, st, taskView);
			}
			
			@Override
			public void onDeleted(List<String> lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(List<String> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValueChanged(List<String> oldValue, List<String> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClearItems() {
				// TODO Auto-generated method stub
				
			}
		});
		
		st.getSubTasks().observe(new ListObserver<String>() {

			@Override
			public void onValueChanged(List<String> oldValue, List<String> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValue(List<String> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeleted(List<String> lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemAdded(int index, String item) {
				T.call(this);

				displayTask(courseId, st, taskView);
			}

			@Override
			public void onItemUpdated(int index, String item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemRemoved(int index, String item) {
				T.call(this);

				displayTask(courseId, st, taskView);
			}

			@Override
			public void onClearItems() {
				// TODO Auto-generated method stub
				
			}
		});
		
		st.getNextTasks().observe(new ListObserver<String>() {

			@Override
			public void onValueChanged(List<String> oldValue, List<String> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValue(List<String> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeleted(List<String> lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemAdded(int index, String item) {
				T.call(this);

				displayTask(courseId, st, taskView);
			}

			@Override
			public void onItemUpdated(int index, String item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemRemoved(int index, String item) {
				T.call(this);

				displayTask(courseId, st, taskView);
			}

			@Override
			public void onClearItems() {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
