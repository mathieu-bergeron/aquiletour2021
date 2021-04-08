package ca.aquiletour.core.pages.course_list.handlers;

import ca.aquiletour.core.pages.course_list.models.CourseListModel;
import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class CourseListViewModel extends ModelViewSubViewHandler<CourseListModel, CourseListView> {

	@Override
	protected void handle(CourseListModel model, CourseListView view, ViewLoader subViewLoader) {
		T.call(this);
		
	}


}
