package ca.aquiletour.core.pages.semester_list.admin.handlers;

import ca.aquiletour.core.pages.semester_list.admin.views.SemesterListViewAdmin;
import ca.aquiletour.core.pages.semester_list.handlers.ShowSemesterListHandler;
import ca.aquiletour.core.pages.semester_list.views.SemesterListView;
import ca.ntro.core.system.trace.T;

public class ShowSemesterListHandlerAdmin extends ShowSemesterListHandler {

	@Override
	protected Class<? extends SemesterListView> semesterListViewClass() {
		T.call(this);
		
		return SemesterListViewAdmin.class;
	}

}
