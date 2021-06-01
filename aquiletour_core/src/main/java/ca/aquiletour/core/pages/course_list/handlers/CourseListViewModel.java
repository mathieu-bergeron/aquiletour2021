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
		
		if(currentSemesterId == null) {
			appendToSemesterDropdown("_4","Corbeille", view);
			appendToSemesterDropdown("_3","Archives", view);
			appendToSemesterDropdown("_2","Brouillons", view);
			appendToSemesterDropdown("_1","En cours", view);
			currentSemesterId = "_1";
			/*
			 * 
			appendToSemesterDropdown(Constants.DRAFTS_SEMESTER_ID, Constants.DRAFTS_SEMESTER_TEXT, view);
			appendToSemesterDropdown(Constants.ACTIVE_SEMESTERS_ID, Constants.ACTIVE_SEMESTERS_TEXT, view);
			observeSemesterIdList(model, view);
			*/
		}
		
		String semesterId = message.getSemesterId();
		if(semesterId == null) {
			semesterId = Constants.ACTIVE_SEMESTERS_ID;
		}
		
		if(!semesterId.equals(currentSemesterId)) {
			currentSemesterId = semesterId;
			changeCurrentSemester(model, view, subViewLoader);
		}
	}

	private void appendToSemesterDropdown(String semesterId, String text, V view) {
		T.call(this);
		
		String href = "?" + Constants.SEMESTER_URL_PARAM + "=" + semesterId;
		
		view.appendToSemesterDropdown(semesterId, href, text);
	}

	@SuppressWarnings("unused")
	private void observeSemesterIdList(M model, V view) {
		T.call(this);
		
		model.getAllSemesters().removeObservers();
		model.getAllSemesters().onItemAdded(new ItemAddedListener<String>() {
			@Override
			public void onItemAdded(int index, String item) {
				T.call(this);
				
				appendToSemesterDropdown(item, item, view);
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
				
				if(shouldDisplayCourse(model, description)) {

					CourseListItemView subView = (CourseListItemView) subViewLoader.createView();
					subView.displayCourseListItem(description);

					view.appendItem(subView);

					observeCourseDescription(description, subView);
				}
			}

		});
	}

	private boolean shouldDisplayCourse(M model, CourseListItem description) {
		T.call(this);
		
		boolean shouldDisplay = false;
		
		if(currentSemesterId.equals(Constants.ACTIVE_SEMESTERS_ID)) {
			
			shouldDisplay = model.isActiveSemester(description.getSemesterId());
			
		}else {
			
			shouldDisplay = currentSemesterId.equals(description.getSemesterId());

		}

		return shouldDisplay;
	}

	protected abstract void observeCourseDescription(CourseListItem courseItem, CourseListItemView itemView);
}
