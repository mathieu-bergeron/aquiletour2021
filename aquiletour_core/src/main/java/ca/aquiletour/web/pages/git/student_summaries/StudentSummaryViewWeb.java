package ca.aquiletour.web.pages.git.student_summaries;

import ca.aquiletour.core.pages.git.student_summaries.StudentSummaryView;
import ca.aquiletour.core.pages.git.values.StudentSummary;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class StudentSummaryViewWeb extends NtroViewWeb implements StudentSummaryView {

	private HtmlElement studentId;
	private HtmlElement lastCommitBeforeDealine;
	private HtmlElement lastCommitAfterDealine;
	private HtmlElement exerciseCompleted;
	private HtmlElement exerciseCompletedBeforeDeadline;

	@Override
	public void initializeViewWeb(NtroContext<?, ?> context) {
		T.call(this);

		studentId = this.getRootElement().find("#studentId").get(0);
		lastCommitBeforeDealine = this.getRootElement().find("#lastCommitBeforeDeadline").get(0);
		lastCommitAfterDealine = this.getRootElement().find("#lastCommitAfterDeadline").get(0);
		exerciseCompleted = this.getRootElement().find("#exerciseCompleted").get(0);
		exerciseCompletedBeforeDeadline = this.getRootElement().find("#exerciseCompletedBeforeDeadline").get(0);

		MustNot.beNull(studentId);
		MustNot.beNull(lastCommitBeforeDealine);
		MustNot.beNull(lastCommitAfterDealine);
		MustNot.beNull(exerciseCompleted);
		MustNot.beNull(exerciseCompletedBeforeDeadline);
	}

	@Override
	public void displayStudentSummary(StudentSummary studentSummary) {
		T.call(this);
		
		studentId.appendHtml(studentSummary.getStudentId());
		
		NtroDate dateBefore = new NtroDate(studentSummary.getLastCommitBeforeDeadline());
		NtroDate dateAfter = new NtroDate(studentSummary.getLastCommitAfterDeadline());

		lastCommitBeforeDealine.appendHtml(dateBefore.toString());
		lastCommitAfterDealine.appendHtml(dateAfter.toString());
		if(studentSummary.isExerciseCompleted()) {
			exerciseCompleted.appendHtml("Vrai");
		} else {
			exerciseCompleted.appendHtml("Faux");
		}
		if(studentSummary.isExerciseCompletedBeforeDeadline()) {
			exerciseCompletedBeforeDeadline.appendHtml("Vrai");
		} else {
			exerciseCompletedBeforeDeadline.appendHtml("Faux");
		}
	}
}
