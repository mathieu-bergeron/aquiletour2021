package ca.aquiletour.web.pages.semester_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.semester_list.views.SemesterListView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class SemesterListViewWeb extends NtroViewWeb implements SemesterListView {
	
	private HtmlElement semesterCodeInput;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

		semesterCodeInput = this.getRootElement().find("#semester-code-input").get(0);

		MustNot.beNull(semesterCodeInput);
		
		initializeInputs();
	}

	private void initializeInputs() {
		T.call(this);
		
		semesterCodeInput.setAttribute("name", Constants.ADD_SEMESTER_URL_PARAM);
	}
}
