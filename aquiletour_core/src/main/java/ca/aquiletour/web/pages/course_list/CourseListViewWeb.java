package ca.aquiletour.web.pages.course_list;

import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class CourseListViewWeb extends NtroViewWeb implements CourseListView {

	private HtmlElement semesterDropdownHead;
	private HtmlElement semesterDropdownTail;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

		semesterDropdownHead = getRootElement().find("#semester-dropdown-head").get(0);
		semesterDropdownTail = getRootElement().find("#semester-dropdown-tail").get(0);
		
		MustNot.beNull(semesterDropdownHead);
		MustNot.beNull(semesterDropdownTail);
	}

	@Override
	public void insertIntoSemesterDropdown(int index, String semesterId) {
		T.call(this);
		
		String dropdownTailItem = "<a class=\"dropdown-item\" href=\"#\"></a";

		HtmlElement semesterIdOption = getRootElement().createElement("<option></option>");
		
		/*
		if(index >= 0 && index < appointmentList.children("*").size()) {

			HtmlElement anchorElement = appointmentList.children("*").get(index);
			appointmentElement.insertBefore(anchorElement);

		}else {

			appointmentList.appendElement(appointmentElement);
		}
		*/
	}

}
