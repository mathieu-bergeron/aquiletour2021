package ca.aquiletour.web.pages.course.teacher;

import ca.aquiletour.core.pages.course.teacher.views.CourseViewTeacher;
import ca.aquiletour.web.pages.course.CourseViewWeb;
import ca.aquiletour.web.widgets.BootstrapDropdown;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;

public class CourseViewWebTeacher extends CourseViewWeb implements CourseViewTeacher {

	private BootstrapDropdown semesterDropdown;
	private BootstrapDropdown groupDropdown;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);
		super.initializeViewWeb(context);

		HtmlElement semesterDropdownHead = getRootElement().find("#semester-dropdown-head-courseview").get(0);
		HtmlElement semesterDropdownTail = getRootElement().find("#semester-dropdown-tail-courseview").get(0);
		HtmlElement groupDropdownHead = getRootElement().find("#group-dropdown-head-courseview").get(0);
		HtmlElement groupDropdownTail = getRootElement().find("#group-dropdown-tail-courseview").get(0);
		
		MustNot.beNull(semesterDropdownHead);
		MustNot.beNull(semesterDropdownTail);
		MustNot.beNull(groupDropdownHead);
		MustNot.beNull(groupDropdownTail);

		semesterDropdown = new BootstrapDropdown(semesterDropdownHead, semesterDropdownTail);
		groupDropdown = new BootstrapDropdown(groupDropdownHead, groupDropdownTail);
	}

	@Override
	public void insertIntoSemesterDropdown(int index, String semesterId, String href, String text) {
		T.call(this);
		
		semesterDropdown.insert(index, semesterId, href, text);
	}

	@Override
	public void appendToSemesterDropdown(String semesterId, String href, String text) {
		T.call(this);

		semesterDropdown.append(semesterId, href, text);
	}

	@Override
	public void selectSemester(String semesterId) {
		T.call(this);
		
		semesterDropdown.select(semesterId);
	}



	@Override
	public void insertIntoGroupDropdown(int index, String groupId, String href, String text) {
		T.call(this);
		
		groupDropdown.insert(index, groupId, href, text);
	}

	@Override
	public void appendToGroupDropdown(String groupId, String href, String text) {
		T.call(this);

		groupDropdown.append(groupId, href, text);
	}


	@Override
	public void selectGroup(String groupId) {
		T.call(this);
		
		groupDropdown.select(groupId);
	}
}
