package ca.aquiletour.web.pages.group_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.course_list.views.CourseDescriptionView;
import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.aquiletour.core.pages.group_list.views.GroupListView;
import ca.aquiletour.web.pages.course_list.CourseDescriptionViewWeb;
import ca.aquiletour.web.widgets.BootstrapDropdown;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class GroupListViewWeb extends NtroViewWeb implements GroupListView {

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);
	}
}
