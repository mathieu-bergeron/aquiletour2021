package ca.aquiletour.core.pages.group_list.handlers;

import ca.aquiletour.core.pages.course_list.messages.ShowCourseListMessage;
import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.aquiletour.core.pages.group_list.messages.ShowGroupListMessage;
import ca.aquiletour.core.pages.group_list.views.GroupListView;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ParentViewMessageHandler;
import ca.ntro.core.system.trace.T;

public class ShowGroupListHandler extends ParentViewMessageHandler<RootView,
                                                                      GroupListView,
                                                                      ShowGroupListMessage> {

	@Override
	protected void handle(RootView parentView, GroupListView currentView, ShowGroupListMessage message) {
		T.call(this);
		
		parentView.showGroupList(currentView);
	}


}
