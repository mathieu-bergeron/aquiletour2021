package ca.aquiletour.core.pages.semester_list.teacher.handlers;

import ca.aquiletour.core.pages.semester_list.handlers.ShowSemesterListHandler;
import ca.aquiletour.core.pages.semester_list.teacher.views.SemesterListViewTeacher;
import ca.aquiletour.core.pages.semester_list.views.SemesterListView;
import ca.ntro.core.system.trace.T;

public class ShowSemesterListHandlerTeacher extends ShowSemesterListHandler {

	@Override
	protected Class<? extends SemesterListView> semesterListViewClass() {
		T.call(this);

		return SemesterListViewTeacher.class;
	}

}
