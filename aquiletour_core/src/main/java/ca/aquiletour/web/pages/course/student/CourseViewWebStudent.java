package ca.aquiletour.web.pages.course.student;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.pages.course.student.views.CourseViewStudent;
import ca.aquiletour.web.pages.course.CourseViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;

public class CourseViewWebStudent extends CourseViewWeb implements CourseViewStudent {

	private HtmlElement gitRepoForm;
	private HtmlElement gitProgressionLink;
	
	private String gitProgressionText;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);
		super.initializeViewWeb(context);

		gitRepoForm = this.getRootElement().find("#git-repo-form").get(0);
		gitProgressionLink = this.getRootElement().find("#git-progression-link").get(0);

		MustNot.beNull(gitRepoForm);
		MustNot.beNull(gitProgressionLink);
		
		gitProgressionText = gitProgressionLink.text();
	}

	@Override
	public void identifyCurrentTask(CoursePath coursePath, Task task) {
		T.call(this);
		super.identifyCurrentTask(coursePath, task);
		
		gitProgressionLink.setAttribute("href", "/" + Constants.GIT_PROGRESS_URL_SEGMENT 
				                                + coursePath.toUrlPath()
				                                + task.id()
												+ "?" + Constants.USER_URL_PARAM + "=" + Ntro.currentUser().getId()
												+ "&" + Constants.SEMESTER_URL_PARAM + "=" + coursePath.semesterId());

		gitProgressionLink.html(gitProgressionText + "&nbsp;&nbsp;" + coursePath.toUrlPath() + task.id());
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
