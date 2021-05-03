package ca.aquiletour.web.pages.git.student_summaries;

import ca.aquiletour.core.pages.git.commit_list.CommitView;
import ca.aquiletour.core.pages.git.student_summaries.StudentSummaryView;
import ca.aquiletour.core.pages.git.values.Commit;
import ca.aquiletour.core.pages.git.values.StudentSummary;
import ca.aquiletour.core.pages.queue.AppointmentView;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.mvc.NtroViewWeb;

public class StudentSummaryViewWeb extends NtroViewWeb implements StudentSummaryView {
	
	@Override
	public void initializeViewWeb(NtroContext<?> context) {
	}


	@Override
	public void displayStudentSummary(StudentSummary studentSummary) {
		T.call(this);
		
		HtmlElement studentId = this.getRootElement().find("#studentId").get(0);
		HtmlElement lastCommitBeforeDealine = this.getRootElement().find("#lastCommitBeforeDeadline").get(0);
		HtmlElement lastCommitAfterDealine = this.getRootElement().find("#lastCommitAfterDeadline").get(0);
		HtmlElement exerciseCompleted = this.getRootElement().find("#exerciseCompleted").get(0);
		HtmlElement exerciseCompletedBeforeDeadline = this.getRootElement().find("#exerciseCompletedBeforeDeadline").get(0);

		MustNot.beNull(studentId);
		MustNot.beNull(lastCommitBeforeDealine);
		MustNot.beNull(lastCommitAfterDealine);
		MustNot.beNull(exerciseCompleted);
		MustNot.beNull(exerciseCompletedBeforeDeadline);
		
		studentId.appendHtml(studentSummary.getStudentId());
		lastCommitBeforeDealine.appendHtml(Long.toString(studentSummary.getLastCommitBeforeDeadline()));
		lastCommitAfterDealine.appendHtml(Long.toString(studentSummary.getLastCommitAfterDeadline()));
		if(studentSummary.isExerciseCompleted()) {
			exerciseCompleted.appendHtml("true");
		} else {
			exerciseCompleted.appendHtml("false");
		}
		if(studentSummary.isExerciseCompletedBeforeDeadline()) {
			exerciseCompletedBeforeDeadline.appendHtml("true");
		} else {
			exerciseCompletedBeforeDeadline.appendHtml("false");
		}
	}
}
