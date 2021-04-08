package ca.aquiletour.core.pages.semester_list.handlers;

import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.core.pages.semester_list.messages.ShowSemesterListMessage;
import ca.aquiletour.core.pages.semester_list.views.SemesterListView;
import ca.ntro.core.mvc.ParentViewMessageHandler;

public class ShowSemesterListHandler extends ParentViewMessageHandler<RootView,
                                                                      SemesterListView,
                                                                      ShowSemesterListMessage> {

	@Override
	protected void handle(RootView parentView, 
			              SemesterListView currentView, 
			              ShowSemesterListMessage message) {
		
		parentView.showCalendarList(currentView);
	}

}
