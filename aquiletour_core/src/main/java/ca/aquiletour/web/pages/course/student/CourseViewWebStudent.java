package ca.aquiletour.web.pages.course.student;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.dates.AquiletourDate;
import ca.aquiletour.core.pages.course.student.views.CourseViewStudent;
import ca.aquiletour.web.pages.course.CourseViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import static ca.ntro.assertions.Factory.that;

public class CourseViewWebStudent extends CourseViewWeb implements CourseViewStudent {

	private HtmlElement gitRepoContainer;
	private HtmlElement gitProgressionLink;
	private HtmlElement taskCompletedContainer;
	private HtmlElement taskCompletedCheckbox;

	private HtmlElements addTaskIdToForm;
	private HtmlElements addTaskIdToValue;
	private HtmlElements addTaskIdToId;

	private String gitProgressionText;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);
		super.initializeViewWeb(context);

		gitRepoContainer = this.getRootElement().find("#git-repo-container").get(0);
		gitProgressionLink = this.getRootElement().find("#git-progression-link").get(0);
		taskCompletedContainer = this.getRootElement().find("#task-completed-container").get(0);
		taskCompletedCheckbox = this.getRootElement().find("#task-completed-checkbox").get(0);

		addTaskIdToForm = this.getRootElement().find(".add-task-id-to-form");
		addTaskIdToValue = this.getRootElement().find(".add-task-id-to-value");
		addTaskIdToId = this.getRootElement().find(".add-task-id-to-id");

		MustNot.beNull(gitRepoContainer);
		MustNot.beNull(gitProgressionLink);
		MustNot.beNull(taskCompletedContainer);
		MustNot.beNull(taskCompletedCheckbox);

		Ntro.verify(that(addTaskIdToForm.size() > 0).isTrue());
		Ntro.verify(that(addTaskIdToValue.size() > 0).isTrue());
		Ntro.verify(that(addTaskIdToId.size() > 0).isTrue());
		
		gitProgressionText = gitProgressionLink.text();
	}

	@Override
	public void identifyCurrentTask(CoursePath coursePath, Task task) {
		T.call(this);

		addTaskIdToForm.appendToAttribute("form", task.getPath().toFileName());
		addTaskIdToValue.appendToAttribute("value", task.getPath().toFileName());
		addTaskIdToId.appendToAttribute("id", task.getPath().toFileName());
		
		gitProgressionLink.setAttribute("href", "/" + Constants.GIT_COMMIT_LIST_URL_SEGMENT 
				                                + coursePath.toUrlPath()
				                                + task.id()
												+ "?" + Constants.USER_URL_PARAM + "=" + Ntro.currentUser().getId()
												+ "&" + Constants.SEMESTER_URL_PARAM + "=" + coursePath.semesterId());

		gitProgressionLink.html(gitProgressionText + "&nbsp;&nbsp;" + coursePath.toUrlPath() + task.id());
	}

	@Override
	public void displayGitRepoForm(boolean show) {
		T.call(this);
		
		if(show) {
			
			gitRepoContainer.show();

		}else {

			gitRepoContainer.hide();
		}
	}

	@Override
	public void displayTaskEndTime(AquiletourDate endTime, boolean editable) {
		T.call(this);
		
	}

	@Override
	public void displayCompletionCheckbox(boolean show) {
		T.call(this);
		
		if(show) {

			taskCompletedContainer.show();

		}else {

			taskCompletedContainer.hide();
		}
	}

	@Override
	public void checkCompletion(boolean check) {
		T.call(this);
		
		if(check) {
			
			taskCompletedCheckbox.setAttribute("checked", "true");
			
		}else {

			taskCompletedCheckbox.removeAttribute("checked");
		}
	}

	@Override
	public void enableCompletionCheckbox(boolean enable) {
		T.call(this);

		if(enable) {
			
			taskCompletedCheckbox.removeAttribute("checked");
			
		}else {

			taskCompletedCheckbox.setAttribute("disabled", "true");

		}
		
	}



}
