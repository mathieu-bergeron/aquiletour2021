package ca.aquiletour.core.pages.course_list.handlers;


import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.course_list.messages.SelectCourseListSubset;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.course_list.models.CourseListItems;
import ca.aquiletour.core.pages.course_list.models.CourseListModel;
import ca.aquiletour.core.pages.course_list.views.CourseListItemView;
import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.ntro.core.models.listeners.EntryAddedListener;
import ca.ntro.core.models.listeners.ItemAddedListener;
import ca.ntro.core.mvc.ModelViewSubViewMessageHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public abstract class CourseListViewModel<M extends CourseListModel, V extends CourseListView> extends ModelViewSubViewMessageHandler<M, V, SelectCourseListSubset> {
	
	private String currentCategoryId = null;

	@Override
	protected void handle(M model, V view, ViewLoader subViewLoader, SelectCourseListSubset message) {
		T.call(this);
		
		if(currentCategoryId == null) {
			appendToSemesterDropdown(Constants.CATEGORY_ID_RECYCLE_BIN,Constants.CATEGORY_TEXT_RECYCLE_BIN, view);
			appendToSemesterDropdown(Constants.CATEGORY_ID_ARCHIVE,Constants.CATEGORY_TEXT_ARCHIVE, view);
			appendToSemesterDropdown(Constants.CATEGORY_ID_DRAFTS,Constants.CATEGORY_TEXT_DRAFTS, view);
			appendToSemesterDropdown(Constants.CATEGORY_ID_CURRENT,Constants.CATEGORY_TEXT_CURRENT, view);

			/*
			 * 
			appendToSemesterDropdown(Constants.DRAFTS_SEMESTER_ID, Constants.DRAFTS_SEMESTER_TEXT, view);
			appendToSemesterDropdown(Constants.ACTIVE_SEMESTERS_ID, Constants.ACTIVE_SEMESTERS_TEXT, view);
			observeSemesterIdList(model, view);
			*/
		}
		
		String categoryId = message.getSemesterId();
		if(categoryId == null) {
			categoryId = AquiletourMain.currentCategoryId();
		}
		
		if(!categoryId.equals(currentCategoryId)) {
			currentCategoryId = categoryId;
			AquiletourMain.setCurrentCategoryId(currentCategoryId);
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
		
		/*
		model.getAllSemesters().removeObservers();
		model.getAllSemesters().onItemAdded(new ItemAddedListener<String>() {
			@Override
			public void onItemAdded(int index, String item) {
				T.call(this);
				
				appendToSemesterDropdown(item, item, view);
			}
		});
		*/
	}

	private void changeCurrentSemester(M model, 
			                           V view, 
			                           ViewLoader subViewLoader) {

		T.call(this);
		
		
		view.selectSemester(currentCategoryId);
		view.identifyCurrentSemester(currentCategoryId);
		
		observeCourses(model, view, subViewLoader);
	}

	protected void observeCourses(M model, 
			                      V view, 
			                      ViewLoader subViewLoader) {
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
