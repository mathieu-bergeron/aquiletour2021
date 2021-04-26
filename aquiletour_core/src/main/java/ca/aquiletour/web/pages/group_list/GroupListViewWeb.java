package ca.aquiletour.web.pages.group_list;

import ca.aquiletour.core.pages.group_list.views.GroupView;
import ca.aquiletour.core.pages.group_list.views.GroupListView;
import ca.aquiletour.web.pages.bases.ListViewWebTeacher;
import ca.aquiletour.web.widgets.BootstrapDropdown;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;

public class GroupListViewWeb extends ListViewWebTeacher<GroupView> implements GroupListView {

	private BootstrapDropdown courseDropdown;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		super.initializeViewWeb(context);

		HtmlElement courseDropdownHead = getRootElement().find("#course-dropdown-head").get(0);
		HtmlElement courseDropdownTail = getRootElement().find("#course-dropdown-tail").get(0);

		MustNot.beNull(courseDropdownHead);
		MustNot.beNull(courseDropdownTail);
		
		courseDropdown = new BootstrapDropdown(courseDropdownHead, courseDropdownTail);
	}

	@Override
	public void selectCourse(String courseId) {
		T.call(this);
		
		courseDropdown.select(courseId);
		
	}

	@Override
	public void identifyCurrentCourse(String courseId) {
		T.call(this);

		String text = "Ajouter un groupe au cours " + courseId;

		getAddItemButton().text(text);
		getModelTitle().text(text);
		
	}

	@Override
	public void insertIntoCourseDropdown(int index, String courseId, String href) {
		T.call(this);
		
		courseDropdown.insert(index, courseId, href, courseId);
	}

	@Override
	public void clearCourseDropdown() {
		T.call(this);
		
		courseDropdown.clear();
	}

	@Override
	public void appendToCourseDropdown(String courseId, String href) {
		T.call(this);
		
		courseDropdown.append(courseId, href, courseId);
	}
}
