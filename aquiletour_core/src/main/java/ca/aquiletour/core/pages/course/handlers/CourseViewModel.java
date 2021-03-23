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
	
	@Override
	protected void handle(CourseModel model, CourseView view, ViewLoader subViewLoader, ShowTaskMessage message) {
		T.call(this);
		
		if(currentTask != null) {
			//currentTask.removeObservers();
		}
		
		System.out.println("showTask: " + message.getTaskPath().toString());

		//Task rootTask = model.getRootTask();
		
		/* We have a path
		// currentTask = model.getTaskByPath(path)
		// view.appendTask(currentTask, view)
		//
		// currentTask.clearObservers()
		// currentTask.observe()
		
		*/
		
		/*
		
		
		
		*/
		
		model.getRootTasks().observe(new ListObserver<String>() {

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
			public void onItemAdded(int index, String taskId) {

				TaskView taskView = (TaskView) subViewLoader.createView();
				
				Task task = model.getTaskById(taskId);

				if(task != null) {
					taskView.displayTask(task);
					view.insertTask(index, taskView);
				}
			}

			@Override
			public void onItemUpdated(int index, String item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemRemoved(int index, String item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onClearItems() {
				// TODO Auto-generated method stub
				
			}
		});

	}
}
