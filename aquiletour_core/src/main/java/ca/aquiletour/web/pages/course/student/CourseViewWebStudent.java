package ca.aquiletour.web.pages.course.student;

import ca.aquiletour.core.pages.course.student.views.CourseViewStudent;
import ca.aquiletour.web.pages.course.CourseViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;

public class CourseViewWebStudent extends CourseViewWeb implements CourseViewStudent {

	private HtmlElement gitRepoForm;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);
		super.initializeViewWeb(context);

		gitRepoForm = this.getRootElement().find("#git-repo-form").get(0);

		MustNot.beNull(gitRepoForm);
	}

	@Override
	public void displayGitRepoForm() {
		T.call(this);
		
		gitRepoForm.show();
	}

	@Override
	public void hideGitRepoForm() {
		T.call(this);

		gitRepoForm.hide();
	}
}
