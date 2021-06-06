package ca.aquiletour.web.pages.course_list.student;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.course_list.teacher.views.CourseListViewTeacher;
import ca.aquiletour.core.utils.TextProcessing;
import ca.aquiletour.web.pages.course_list.CourseListViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;

public class CourseListViewWebTeacher extends CourseListViewWeb implements CourseListViewTeacher {
	
	private HtmlElement addItemButton;
	private HtmlElement modalTitle;
	private HtmlElements addSemesterIdToValue;
	
	private HtmlElement courseTitleInput;
	private HtmlElement courseIdInput;
	
	private boolean userControlsId = false;

	protected HtmlElement getAddItemButton() {
		return addItemButton;
	}
	
	protected HtmlElement getModelTitle() {
		return modalTitle;
	}

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);
		super.initializeViewWeb(context);

		addItemButton = getRootElement().find("#add-item-button").get(0);
		modalTitle = getRootElement().find("#modal-title").get(0);
		addSemesterIdToValue = this.getRootElement().find(".add-semester-id-to-value");

		courseTitleInput = this.getRootElement().find("#course-title-input").get(0);
		courseIdInput = this.getRootElement().find("#course-id-input").get(0);
		
		MustNot.beNull(addItemButton);
		MustNot.beNull(modalTitle);
		MustNot.beNull(courseTitleInput);
		
		courseTitleInput.addEventListener("keypress", e -> {
			onUserChangesTitle();
		});

		courseTitleInput.addEventListener("input", e -> {
			onUserChangesTitle();
		});

		courseTitleInput.addEventListener("paste", e -> {
			onUserChangesTitle();
		});

		courseIdInput.addEventListener("keypress", e -> {
			onUserChangesId();
		});

		courseIdInput.addEventListener("input", e -> {
			onUserChangesId();
		});

		courseIdInput.addEventListener("paste", e -> {
			onUserChangesId();
		});
	}

	private void onUserChangesId() {
		T.call(this);

		if(courseIdInput.value().length() >= 3) {

			userControlsId = true;

		}else {
			
			userControlsId = false;

		}
	}

	
	private void onUserChangesTitle() {
		T.call(this);
		
		String courseTitle = courseTitleInput.value();
		if(!userControlsId) {
			courseIdInput.value(TextProcessing.generateIdFromTitle(courseTitle));
		}
	}

	@Override
	public void displayCurrentSemester(String semesterId) {
		T.call(this);
		super.displayCurrentSemester(semesterId);
		
		addSemesterIdToValue.appendToAttribute("value", semesterId);
		
		if(semesterId == null) {

			getAddItemButton().hide();
			
		} else if(semesterId.equals(Constants.DRAFTS_SEMESTER_ID)) {
			
			String text = "Ajouter un cours aux brouillons";

			getAddItemButton().show();
			getAddItemButton().text(text);
			getModelTitle().text(text);

		}else if(semesterId.equals(Constants.ACTIVE_SEMESTERS_ID)) {
			
			getAddItemButton().hide();
			
		}else {
			
			String text = "Ajouter un cours à la session " + semesterId;

			getAddItemButton().show();
			getAddItemButton().text(text);
			getModelTitle().text(text);
		}
	}
}
