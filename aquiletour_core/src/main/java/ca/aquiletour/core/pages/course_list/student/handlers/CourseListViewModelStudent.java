package ca.aquiletour.core.pages.course_list.student.handlers;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.course_list.handlers.CourseListViewModel;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.course_list.student.CourseListModelStudent;
import ca.aquiletour.core.pages.course_list.student.views.CourseListViewStudent;
import ca.aquiletour.core.pages.course_list.views.CourseListItemView;
import ca.ntro.core.system.trace.T;

public class CourseListViewModelStudent extends CourseListViewModel<CourseListModelStudent, CourseListViewStudent> {

	@Override
	protected void appendCategoriesToDropdown(CourseListViewStudent view) {
		T.call(this);

		appendToSemesterDropdown(Constants.CATEGORY_ID_ARCHIVE,Constants.CATEGORY_TEXT_ARCHIVE, view);
		appendToSemesterDropdown(Constants.CATEGORY_ID_CURRENT,Constants.CATEGORY_TEXT_CURRENT, view);
	}

	@Override
	protected void observeCourseListItem(CourseListItem courseItem, CourseListItemView itemView) {
		T.call(this);
	}

}
