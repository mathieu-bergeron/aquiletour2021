package ca.aquiletour.web.pages.semester_list;

import ca.aquiletour.core.pages.semester_list.views.SemesterListView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.mvc.NtroViewWeb;

public class SemesterListViewWeb extends NtroViewWeb implements SemesterListView {

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

	}
}
