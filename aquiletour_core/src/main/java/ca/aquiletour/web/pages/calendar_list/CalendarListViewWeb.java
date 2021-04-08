package ca.aquiletour.web.pages.calendar_list;

import ca.aquiletour.core.pages.admin.calendar_list.views.CalendarListView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.mvc.NtroViewWeb;

public class CalendarListViewWeb extends NtroViewWeb implements CalendarListView {

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

	}
}
