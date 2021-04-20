package ca.aquiletour.core.pages.course_list.handlers;

import ca.aquiletour.core.pages.course_list.messages.ShowCourseListMessage;
import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ParentViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowCourseListHandler extends ParentViewMessageHandler<RootView,
                                                                    CourseListView,
                                                                    ShowCourseListMessage> {
	@Override
	protected void handle(RootView parentView, CourseListView currentView, ShowCourseListMessage message) {
		T.call(this);
		
		parentView.showCourseList(currentView);
	}
}
