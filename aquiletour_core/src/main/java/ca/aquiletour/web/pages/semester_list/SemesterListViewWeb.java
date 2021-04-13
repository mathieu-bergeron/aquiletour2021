package ca.aquiletour.web.pages.semester_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.semester_list.views.SemesterListView;
import ca.aquiletour.core.pages.semester_list.views.SemesterView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.mvc.NtroViewWeb;

public class SemesterListViewWeb extends NtroViewWeb implements SemesterListView {
	
	private HtmlElement semesterCodeInput;
	private HtmlElement semesterContainer;

	private HtmlElement addCourseButton;
	private HtmlElement closeModalButton;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		semesterCodeInput = this.getRootElement().find("#semester-code-input").get(0);
		semesterContainer = this.getRootElement().find("#semester-container").get(0);

		addCourseButton = getRootElement().find("#add-semester-submit-button").get(0);
		closeModalButton = getRootElement().find("#close-modal-button").get(0);

		MustNot.beNull(addCourseButton);
		MustNot.beNull(closeModalButton);

		MustNot.beNull(semesterCodeInput);
		MustNot.beNull(semesterContainer);
		
		initializeInputs();
		addListeners();
	}

	private void addListeners() {
		T.call(this);

		addCourseButton.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);
				
				closeModalButton.trigger("click");
			}
		});
	}

	private void initializeInputs() {
		T.call(this);
		
		semesterCodeInput.setAttribute("name", Constants.ADD_SEMESTER_URL_PARAM);
	}

	@Override
	public void appendSemester(SemesterView semesterView) {
		T.call(this);
		
		SemesterViewWeb semesterViewWeb = (SemesterViewWeb) semesterView;
		
		semesterContainer.appendElement(semesterViewWeb.getRootElement());
	}
}
