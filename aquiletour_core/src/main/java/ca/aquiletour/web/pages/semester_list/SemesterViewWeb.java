package ca.aquiletour.web.pages.semester_list;

import ca.aquiletour.core.pages.semester_list.models.SemesterModel;
import ca.aquiletour.core.pages.semester_list.views.SemesterView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class SemesterViewWeb extends NtroViewWeb implements SemesterView {

	private HtmlElement semesterIdAnchor;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

		semesterIdAnchor = this.getRootElement().find("#semester-id").get(0);
		
		MustNot.beNull(semesterIdAnchor);
	}

	@Override
	public void displaySemester(SemesterModel item) {
		T.call(this);
		
		semesterIdAnchor.text(item.getSemesterId());
	}
}
