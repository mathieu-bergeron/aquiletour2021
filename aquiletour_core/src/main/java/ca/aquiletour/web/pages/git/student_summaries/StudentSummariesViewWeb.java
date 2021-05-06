package ca.aquiletour.web.pages.git.student_summaries;

import ca.aquiletour.core.pages.git.student_summaries.StudentSummariesModel;
import ca.aquiletour.core.pages.git.student_summaries.StudentSummariesView;
import ca.aquiletour.core.pages.git.student_summaries.StudentSummaryView;
import ca.aquiletour.core.pages.git.values.StudentSummary;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class StudentSummariesViewWeb extends NtroViewWeb implements StudentSummariesView {
	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {

	}

	@Override
	public void appendStudentSummary(StudentSummary studentSummary, StudentSummaryView studentSummaryView) {
		// TODO Auto-generated method stub
		HtmlElement container = this.getRootElement().find("#studentSummary-list").get(0);

		MustNot.beNull(container);

		StudentSummaryViewWeb studentSummaryViewWeb = (StudentSummaryViewWeb) studentSummaryView;

		container.appendElement(studentSummaryViewWeb.getRootElement());
	}

	@Override
	public void displayStudentSummaries(StudentSummariesModel studentSummariesModel) {
		T.call(this);
		HtmlElement groupId = this.getRootElement().find("#groupId").get(0);
		HtmlElement semesterId = this.getRootElement().find("#semesterId").get(0);
		HtmlElement exercisePath = this.getRootElement().find("#exercisePath").get(0);
		

		MustNot.beNull(semesterId);
		MustNot.beNull(exercisePath);
		MustNot.beNull(groupId);
		
		groupId.appendHtml(studentSummariesModel.getGroupId());
		semesterId.appendHtml(studentSummariesModel.getSemesterId());
		exercisePath.appendHtml(studentSummariesModel.getExercisePath());
	}
}
