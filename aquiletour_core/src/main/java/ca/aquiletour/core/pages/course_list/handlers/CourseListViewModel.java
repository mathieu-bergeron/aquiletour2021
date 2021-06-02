package ca.aquiletour.core.pages.course_list.handlers;


import ca.aquiletour.core.AquiletourMain;
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
	protected void onCategoryChanges(M model, 
			                         V view, 
			                         ViewLoader subViewLoader,
			                         String currentCategoryId) {
		T.call(this);

		view.displayActiveSemesters(currentCategoryId);
		
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
			}
		});
	}

	protected abstract void observeCourseListItem(CourseListItem courseItem, CourseListItemView itemView);
}
