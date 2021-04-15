package ca.aquiletour.core.pages.course_list.handlers;

import java.util.List;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.course_list.messages.SelectCourseListSubset;
import ca.aquiletour.core.pages.course_list.models.CourseDescription;
import ca.aquiletour.core.pages.course_list.models.CourseListModel;
import ca.aquiletour.core.pages.course_list.views.CourseDescriptionView;
import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.ntro.core.models.listeners.ListObserver;
import ca.ntro.core.mvc.ModelViewSubViewMessageHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class CourseListViewModel extends ModelViewSubViewMessageHandler<CourseListModel, CourseListView, SelectCourseListSubset> {
	
	private String currentSemesterId = null;

	@Override
	protected void handle(CourseListModel model, CourseListView view, ViewLoader subViewLoader, SelectCourseListSubset message) {
		T.call(this);
		
		if(currentSemesterId == null) {
			view.insertIntoSemesterDropdown(0, Constants.COURSE_DRAFTS);
			observeSemesterIdList(model, view);
		}
		
		if(!message.getSemesterId().equals(currentSemesterId)) {
			currentSemesterId = message.getSemesterId();

			changeCurrentSemester(currentSemesterId, model, view, subViewLoader);
		}
	}

	private void observeSemesterIdList(CourseListModel model, CourseListView view) {
		T.call(this);

		model.getSemesters().observe(new ListObserver<String>() {
			
			@Override
			public void onItemRemoved(int index, String item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemUpdated(int index, String item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemAdded(int index, String item) {
				T.call(this);

				view.insertIntoSemesterDropdown(index, item);
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
	}

	private void changeCurrentSemester(String currentSemesterId, 
			                            CourseListModel model, 
			                            CourseListView view, 
			                            ViewLoader subViewLoader) {

		T.call(this);
		
		
		view.selectSemester(currentSemesterId);
		view.identifyCurrentSemester(currentSemesterId);
		
		observeCourses(currentSemesterId, model, view, subViewLoader);
	}

	protected void observeCourses(String currentSemesterId, 
			                      CourseListModel model, 
			                      CourseListView view, 
			                      ViewLoader subViewLoader) {
		T.call(this);
		
		view.clearCourses();
		
		model.getCourses().removeObservers();
		model.getCourses().observe(new ListObserver<CourseDescription>() {

			@Override
			public void onValueChanged(List<CourseDescription> oldValue, List<CourseDescription> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValue(List<CourseDescription> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeleted(List<CourseDescription> lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemAdded(int index, CourseDescription item) {
				T.call(this);
				
				if(item.getSemesterId().equals(currentSemesterId)) {
					
					CourseDescriptionView subView = (CourseDescriptionView) subViewLoader.createView();
					subView.displayCourseDescription(item);
					
					view.appendCourseDescription(subView);
				}
			}

			@Override
			public void onItemUpdated(int index, CourseDescription item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemRemoved(int index, CourseDescription item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onClearItems() {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
}
