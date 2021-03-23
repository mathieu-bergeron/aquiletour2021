package ca.aquiletour.core.pages.course.handlers;


import java.util.List;

import ca.aquiletour.core.pages.course.models.CourseModel;
import ca.aquiletour.core.pages.course.models.Task;
import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.course.views.TaskView;
import ca.ntro.core.models.listeners.ListObserver;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class CourseViewModel extends ModelViewSubViewHandler<CourseModel, CourseView>  {
	
	@Override
	protected void handle(CourseModel model, CourseView view, ViewLoader subViewLoader) {
		T.call(this);
		
		model.getTasks().observe(new ListObserver<Task>() {
			
			@Override
			public void onItemRemoved(int index, Task item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemUpdated(int index, Task item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemAdded(int index, Task item) {
				
				TaskView taskView = (TaskView) subViewLoader.createView();
				
				taskView.displayTask(item);
				
				view.insertTask(index, taskView);
			}
			
			@Override
			public void onDeleted(List<Task> lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(List<Task> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValueChanged(List<Task> oldValue, List<Task> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClearItems() {
				// TODO Auto-generated method stub
				
			}
		});

	}
}
