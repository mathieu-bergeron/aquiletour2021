package ca.aquiletour.core.pages.course_list.handlers;


import java.util.HashSet;
import java.util.Set;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.handlers.ItemListViewModel;
import ca.aquiletour.core.pages.course_list.messages.SelectCourseListSubset;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.course_list.models.CourseListItems;
import ca.aquiletour.core.pages.course_list.models.CourseListModel;
import ca.aquiletour.core.pages.course_list.views.CourseListItemView;
import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.ntro.core.models.listeners.EntryAddedListener;
import ca.ntro.core.models.listeners.ItemAddedListener;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public abstract class CourseListViewModel<M extends CourseListModel, V extends CourseListView> 
                extends ItemListViewModel<M, V, SelectCourseListSubset> {


	@Override
	protected void handle(M model, V view, ViewLoader subViewLoader, SelectCourseListSubset message) {
		T.call(this);
		
		super.handle(model, view, subViewLoader, message);
	}

	@Override
	protected void initializeCategories(M model, V view) {
		T.call(this);

		Set<String> activeSemesterIds = new HashSet<>();

		model.getActiveSemesters().removeObservers();
		model.getActiveSemesters().onItemAdded(new ItemAddedListener<String>() {
			@Override
			public void onItemAdded(int index, String activeSemesterId) {
				T.call(this);
				
				activeSemesterIds.add(activeSemesterId);

				if(activeSemesterIds.size() == 1) {
					appendToSemesterDropdown(Constants.CATEGORY_ID_CURRENT, activeSemesterId, view);
				}else {
					updateSemesterDropdown(Constants.CATEGORY_ID_CURRENT,Constants.CATEGORY_TEXT_CURRENT, view);
				}
			}
		});
	}

	@Override
	protected void onCategoryChanges(M model, 
			                         V view, 
			                         ViewLoader subViewLoader,
			                         String currentCategoryId) {
		T.call(this);
		
		String currentSemesterId = null;
		
		if(model.getActiveSemesters().size() > 0) {
			// FIXME: must display more than one active semester!
			currentSemesterId = model.getActiveSemesters().item(0);
		}
		
		if(currentCategoryId.equals(Constants.CATEGORY_ID_DRAFTS)) {
			currentSemesterId = Constants.DRAFTS_SEMESTER_ID;
		}

		view.displayCurrentSemester(currentSemesterId);
		
		observeCourses(model, view, subViewLoader, currentCategoryId);
	}

	protected void observeCourses(M model, 
			                      V view, 
			                      ViewLoader subViewLoader,
			                      String currentCategoryId) {
		T.call(this);
		
		view.clearItems();
		
		model.getCourseListItemsByCategoryId().removeObservers();
		model.getCourseListItemsByCategoryId().onEntryAdded(new EntryAddedListener<CourseListItems>() {
			@Override
			public void onEntryAdded(String categoryId, CourseListItems courseItems) {
				T.call(this);

				if(categoryId.equals(currentCategoryId)) {
					
					observeCourseItem(view, subViewLoader, courseItems);
				}
			}

		});
	}

	private void observeCourseItem(V view, ViewLoader subViewLoader, CourseListItems courseItems) {
		T.call(this);

		courseItems.removeObservers();
		courseItems.onItemAdded(new ItemAddedListener<CourseListItem>() {
			@Override
			public void onItemAdded(int index, CourseListItem courseItem) {
				T.call(this);

				CourseListItemView subView = (CourseListItemView) subViewLoader.createView();
				subView.displayCourseListItem(courseItem);

				view.appendItem(subView);

				observeCourseListItem(courseItem, subView);
			}
		});
	}

	protected abstract void observeCourseListItem(CourseListItem courseItem, CourseListItemView itemView);
}
