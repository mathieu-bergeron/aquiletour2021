package ca.aquiletour.core.pages.course_list.handlers;


import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.course_list.messages.SelectCourseListSubset;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.course_list.models.CourseListModel;
import ca.aquiletour.core.pages.course_list.views.CourseListItemView;
import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.ntro.core.models.listeners.ItemAddedListener;
import ca.ntro.core.mvc.ModelViewSubViewMessageHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public abstract class CourseListViewModel<M extends CourseListModel, V extends CourseListView> extends ModelViewSubViewMessageHandler<M, V, SelectCourseListSubset> {
	
	private String currentSemesterId = null;

	@Override
	protected void handle(M model, V view, ViewLoader subViewLoader, SelectCourseListSubset message) {
		T.call(this);
		
		System.out.println("CourseListViewModel");
		
		if(currentSemesterId == null) {
			appendToSemesterDropdown(Constants.DRAFTS_SEMESTER_ID, view);
			observeSemesterIdList(model, view);
		}
		
		if(!message.getSemesterId().equals(currentSemesterId)) {
			currentSemesterId = message.getSemesterId();

			changeCurrentSemester(model, view, subViewLoader);
		}
	}

	private void appendToSemesterDropdown(String semesterId, V view) {
		T.call(this);
		
		String href = "?" + Constants.SEMESTER_URL_PARAM + "=" + semesterId;
		String text = semesterId;
		
		if(semesterId.equals(Constants.DRAFTS_SEMESTER_ID)) {
			text = Constants.DRAFTS_SEMESTER_TEXT;
		}
		
		view.appendToSemesterDropdown(semesterId, href, text);
	}

	private void observeSemesterIdList(M model, V view) {
		T.call(this);
		
		model.getSemesters().removeObservers();
		model.getSemesters().onItemAdded(new ItemAddedListener<String>() {
			@Override
			public void onItemAdded(int index, String item) {
				T.call(this);
				
				appendToSemesterDropdown(item, view);
			}
		});
	}

	private void changeCurrentSemester(M model, 
			                           V view, 
			                           ViewLoader subViewLoader) {

		T.call(this);
		
		
		view.selectSemester(currentSemesterId);
		view.identifyCurrentSemester(currentSemesterId);
		
		observeCourses(model, view, subViewLoader);
	}

	protected void observeCourses(M model, 
			                      V view, 
			                      ViewLoader subViewLoader) {
		T.call(this);
		
		view.clearItems();
		
		model.getCourses().removeObservers();
		model.getCourses().onItemAdded(new ItemAddedListener<CourseListItem>() {
			@Override
			public void onItemAdded(int index, CourseListItem description) {
				T.call(this);

				if(description.getSemesterId().equals(currentSemesterId)) {

					CourseListItemView subView = (CourseListItemView) subViewLoader.createView();
					subView.displayCourseListItem(description);

					view.appendItem(subView);

					observeCourseDescription(description, subView);
				}
			}
		});
	}

	protected abstract void observeCourseDescription(CourseListItem courseItem, CourseListItemView itemView);
}
