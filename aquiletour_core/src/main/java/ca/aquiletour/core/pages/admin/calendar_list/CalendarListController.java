package ca.aquiletour.core.pages.admin.calendar_list;

import ca.aquiletour.core.models.users.Guest;
import ca.aquiletour.core.pages.admin.calendar_list.handlers.CalendarListViewModel;
import ca.aquiletour.core.pages.admin.calendar_list.models.CalendarModel;
import ca.aquiletour.core.pages.admin.calendar_list.views.CalendarListView;
import ca.aquiletour.core.pages.admin.calendar_list.views.CalendarView;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public abstract class CalendarListController extends NtroController<RootController> {
	

	@Override
	protected void onCreate(NtroContext<?> context) {
		T.call(this);

		setViewLoader(CalendarListView.class, context.lang());

		setModelLoader(CalendarModel.class, 
					   context.user().getAuthToken(),
					   context.user().getId());

		addSubViewLoader(CalendarView.class, context().lang());
		
		addModelViewSubViewHandler(CalendarView.class, new CalendarListViewModel());
		
	}
	
	@Override
	protected void onChangeContext(NtroContext<?> previousContext, NtroContext<?> context) {
		T.call(this);
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);

	}
}
