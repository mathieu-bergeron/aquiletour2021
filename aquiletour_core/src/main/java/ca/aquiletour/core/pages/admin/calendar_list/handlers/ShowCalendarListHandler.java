package ca.aquiletour.core.pages.admin.calendar_list.handlers;

import ca.aquiletour.core.pages.admin.calendar_list.messages.ShowCalendarListMessage;
import ca.aquiletour.core.pages.admin.calendar_list.views.CalendarListView;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ParentViewMessageHandler;

public class ShowCalendarListHandler extends ParentViewMessageHandler<RootView,
                                                                      CalendarListView,
                                                                      ShowCalendarListMessage> {

	@Override
	protected void handle(RootView parentView, 
			              CalendarListView currentView, 
			              ShowCalendarListMessage message) {
		
		parentView.showCalendarList(currentView);
	}

}
