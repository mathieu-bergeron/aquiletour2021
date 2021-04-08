package ca.aquiletour.web.pages.semester_list;

import ca.aquiletour.core.pages.semester_list.views.SemesterView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.mvc.NtroViewWeb;

public class SemesterViewWeb extends NtroViewWeb implements SemesterView {

	private HtmlElement addCourseButton;
	private HtmlElement closeModalButton;
	private HtmlElement addCourseTitleInput;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

		addCourseButton = getRootElement().find("#add-course-submit-button").get(0);
		closeModalButton = getRootElement().find("#close-modal-button").get(0);
		addCourseTitleInput = getRootElement().find("#add-course-title-input").get(0);

		MustNot.beNull(addCourseButton);
		MustNot.beNull(closeModalButton);
		MustNot.beNull(addCourseTitleInput);

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
}
