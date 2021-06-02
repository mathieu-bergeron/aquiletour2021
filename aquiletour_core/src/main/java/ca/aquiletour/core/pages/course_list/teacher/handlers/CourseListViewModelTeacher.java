package ca.aquiletour.core.pages.course_list.teacher.handlers;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.course_list.handlers.CourseListViewModel;
import ca.aquiletour.core.pages.course_list.messages.SelectCourseListSubset;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.course_list.models.TaskDescription;
import ca.aquiletour.core.pages.course_list.teacher.CourseListModelTeacher;
import ca.aquiletour.core.pages.course_list.teacher.views.CourseListItemViewTeacher;
import ca.aquiletour.core.pages.course_list.teacher.views.CourseListViewTeacher;
import ca.aquiletour.core.pages.course_list.views.CourseListItemView;
import ca.ntro.core.models.listeners.ItemAddedListener;
import ca.ntro.core.models.listeners.ValueObserver;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class CourseListViewModelTeacher extends CourseListViewModel<CourseListModelTeacher, CourseListViewTeacher> {

	@Override
	protected void handle(CourseListModelTeacher model, CourseListViewTeacher view, ViewLoader subViewLoader, SelectCourseListSubset message) {
		T.call(this);
		super.handle(model, view, subViewLoader, message);
	}

	@Override
	protected void appendCategoriesToDropdown(CourseListViewTeacher view) {
		T.call(this);

		appendToSemesterDropdown(Constants.CATEGORY_ID_RECYCLE_BIN,Constants.CATEGORY_TEXT_RECYCLE_BIN, view);
		appendToSemesterDropdown(Constants.CATEGORY_ID_ARCHIVE,Constants.CATEGORY_TEXT_ARCHIVE, view);
		appendToSemesterDropdown(Constants.CATEGORY_ID_DRAFTS,Constants.CATEGORY_TEXT_DRAFTS, view);
		appendToSemesterDropdown(Constants.CATEGORY_ID_CURRENT,Constants.CATEGORY_TEXT_CURRENT, view);
	}
	
	@Override
	protected void observeCourseListItem(CourseListItem courseItem, CourseListItemView itemView) {
		T.call(this);
		
		CourseListItemViewTeacher itemViewTeacher = (CourseListItemViewTeacher) itemView;
		
		itemViewTeacher.displayTasksSummary(courseItem.getTasksSummary());
		itemViewTeacher.displayGroupsSummary(courseItem.getGroupsSummary());
		
		courseItem.getTasks().removeObservers();
		courseItem.getTasks().onItemAdded(new ItemAddedListener<TaskDescription>() {
			@Override
			public void onItemAdded(int index, TaskDescription item) {
				T.call(this);
				
				itemViewTeacher.appendTaskDescription(item);
			}
		});
		
		courseItem.getGroupIds().onItemAdded(new ItemAddedListener<String>() {
			@Override
			public void onItemAdded(int index, String item) {
				T.call(this);
				
				itemViewTeacher.appendGroupId(item);
			}
		});
		
		courseItem.getQueueOpen().removeObservers();
		courseItem.getQueueOpen().observe(new ValueObserver<Boolean>() {
			@Override
			public void onDeleted(Boolean lastValue) {
			}
			
			@Override
			public void onValue(Boolean value) {
				T.call(this);

				displayOpenQueueMessage(value, courseItem.getTeacherId(), courseItem.getCourseId(),  itemViewTeacher);
			}
			
			@Override
			public void onValueChanged(Boolean oldValue, Boolean value) {
				T.call(this);

				displayOpenQueueMessage(value, courseItem.getTeacherId(), courseItem.getCourseId(),  itemViewTeacher);
			}
		});
		
	}
	
	private void displayOpenQueueMessage(boolean queueOpen, String teacherId, String courseId, CourseListItemViewTeacher itemView) {
		T.call(this);
		
		String queueHref = "/" + Constants.QUEUE_URL_SEGMENT + "/" + teacherId + "/" + courseId;
		
		if(queueOpen) {
			
			itemView.displayQueueLink(queueOpen, "ouverte", queueHref);
			
		}else {

			itemView.displayQueueLink(queueOpen, "fermée", queueHref);
		}
	}

}
