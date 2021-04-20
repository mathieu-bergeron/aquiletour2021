package ca.aquiletour.core.pages.course_list.handlers;

import java.util.List;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.course_list.messages.SelectCourseListSubset;
import ca.aquiletour.core.pages.course_list.models.CourseItem;
import ca.aquiletour.core.pages.course_list.models.CourseListModel;
import ca.aquiletour.core.pages.course_list.models.TaskDescription;
import ca.aquiletour.core.pages.course_list.views.CourseItemView;
import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.ntro.core.models.listeners.ItemAddedListener;
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
			view.insertIntoSemesterDropdown(0, Constants.DRAFTS_SEMESTER_ID);
			observeSemesterIdList(model, view);
		}
		
		if(!message.getSemesterId().equals(currentSemesterId)) {
			currentSemesterId = message.getSemesterId();

			changeCurrentSemester(model, view, subViewLoader);
		}
	}

	private void observeSemesterIdList(CourseListModel model, CourseListView view) {
		T.call(this);
		
		model.getSemesters().onItemAdded(new ItemAddedListener<String>() {
			@Override
			public void onItemAdded(int index, String item) {
				T.call(this);

				view.insertIntoSemesterDropdown(index, item);
			}
		});
	}

	private void changeCurrentSemester(CourseListModel model, 
			                           CourseListView view, 
			                           ViewLoader subViewLoader) {

		T.call(this);
		
		
		view.selectSemester(currentSemesterId);
		view.identifyCurrentSemester(currentSemesterId);
		
		observeCourses(model, view, subViewLoader);
	}

	protected void observeCourses(CourseListModel model, 
			                      CourseListView view, 
			                      ViewLoader subViewLoader) {
		T.call(this);
		
		view.clearItems();
		
		model.getCourses().removeObservers();
		model.getCourses().onItemAdded(new ItemAddedListener<CourseItem>() {
			@Override
			public void onItemAdded(int index, CourseItem description) {
				T.call(this);

				if(description.getSemesterId().equals(currentSemesterId)) {

					CourseItemView subView = (CourseItemView) subViewLoader.createView();
					subView.displayCourseDescription(description);

					view.appendItem(subView);

					observeCourseDescription(description, subView);
				}
			}
		});
	}

	protected void observeCourseDescription(CourseItem description, CourseItemView descriptionView) {
		T.call(this);
		
		description.getTasks().onItemAdded(new ItemAddedListener<TaskDescription>() {
			@Override
			public void onItemAdded(int index, TaskDescription item) {
				T.call(this);
				
				descriptionView.appendTaskDescription(item);
			}
		});
		
		description.getGroupIds().onItemAdded(new ItemAddedListener<String>() {
			@Override
			public void onItemAdded(int index, String item) {
				T.call(this);
				
				descriptionView.appendGroupId(item);
			}
		});
		
	}

}
