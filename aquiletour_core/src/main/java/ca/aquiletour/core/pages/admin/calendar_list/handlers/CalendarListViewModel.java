package ca.aquiletour.core.pages.admin.calendar_list.handlers;

import ca.aquiletour.core.pages.admin.calendar_list.models.CalendarListModel;
import ca.aquiletour.core.pages.admin.calendar_list.views.CalendarListView;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class CalendarListViewModel extends ModelViewSubViewHandler<CalendarListModel, CalendarListView> {

	@Override
	protected void handle(CalendarListModel model, CalendarListView view, ViewLoader subViewLoader) {
		T.call(this);
		
	}

}
