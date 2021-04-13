package ca.aquiletour.web.pages.course_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.course_list.views.CourseDescriptionView;
import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.aquiletour.web.widgets.BootstrapDropdown;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class CourseListViewWeb extends NtroViewWeb implements CourseListView {

	private HtmlElement coursesContainer;
	private BootstrapDropdown semesterDropdown;
	private HtmlElement addCourseButton;
	private HtmlElement modalTitle;
	private HtmlElement semesterIdInput;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

		HtmlElement semesterDropdownHead = getRootElement().find("#semester-dropdown-head").get(0);
		HtmlElement semesterDropdownTail = getRootElement().find("#semester-dropdown-tail").get(0);
		addCourseButton = getRootElement().find("#add-course-button").get(0);
		modalTitle = getRootElement().find("#modal-title").get(0);
		coursesContainer = this.getRootElement().find("#courses-container").get(0);
		semesterIdInput = this.getRootElement().find("#semester-id-input").get(0);
		
		MustNot.beNull(semesterDropdownHead);
		MustNot.beNull(semesterDropdownTail);
		MustNot.beNull(addCourseButton);
		MustNot.beNull(modalTitle);
		MustNot.beNull(coursesContainer);
		MustNot.beNull(semesterIdInput);
		
		semesterDropdown = new BootstrapDropdown(semesterDropdownHead, semesterDropdownTail);
	}

	@Override
	public void insertIntoSemesterDropdown(int index, String semesterId) {
		T.call(this);
		
		semesterDropdown.insert(index, semesterId,  "?" + Constants.SEMESTER_URL_PARAM + "=" + semesterId, semesterId);
	}

	@Override
	public void selectSemester(String semesterId) {
		T.call(this);
		
		semesterDropdown.select(semesterId);
	}

	@Override
	public void identifyCurrentSemester(String semesterId) {
		T.call(this);
		
		semesterIdInput.value(semesterId);
		
		if(semesterId.equals(Constants.COURSE_DRAFTS)) {
			
			String text = Constants.CREATE_COURSE_TEXT + " aux brouillons";

			addCourseButton.text(text);
			modalTitle.text(text);
			
		}else {
			
			String text = Constants.CREATE_COURSE_TEXT + " à la session " + semesterId;

			addCourseButton.text(text);
			modalTitle.text(text);
		}
	}

	@Override
	public void appendCourseDescription(CourseDescriptionView courseDescriptionView) {
		T.call(this);
		
		CourseDescriptionViewWeb courseDescriptionViewWeb = (CourseDescriptionViewWeb) courseDescriptionView;
		
		coursesContainer.appendElement(courseDescriptionViewWeb.getRootElement());
	}

	@Override
	public void clearCourses() {
		T.call(this);
		
		coursesContainer.removeChildrenFromDocument();
		
	}
}
