package ca.aquiletour.web.pages.course_list.student;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.course_list.teacher.views.CourseListViewTeacher;
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
		
		MustNot.beNull(addItemButton);
		MustNot.beNull(modalTitle);
	}

	@Override
	public void identifyCurrentSemester(String semesterId) {
		T.call(this);
		super.identifyCurrentSemester(semesterId);
		
		addSemesterIdToValue.appendToAttribute("value", semesterId);

		if(semesterId.equals(Constants.DRAFTS_SEMESTER_ID)) {
			
			String text = "Ajouter un cours aux brouillons";

			getAddItemButton().text(text);
			getModelTitle().text(text);
			
		}else {
			
			String text = "Ajouter un cours à la session " + semesterId;

			getAddItemButton().text(text);
			getModelTitle().text(text);
		}
	}
}
