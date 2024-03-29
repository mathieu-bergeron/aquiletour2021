package ca.aquiletour.web.pages.git.commit_list;

import ca.aquiletour.core.pages.git.commit_list.models.CommitListModel;
import ca.aquiletour.core.pages.git.commit_list.views.CommitListView;
import ca.aquiletour.core.pages.git.commit_list.views.CommitView;
import ca.aquiletour.core.pages.git.values.Commit;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class CommitListViewWeb extends NtroViewWeb implements CommitListView {
	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {

	}

	@Override
	public void appendCommit(Commit commit, CommitView commitView) {
		HtmlElement container = this.getRootElement().find("#commit-list").get(0);

		MustNot.beNull(container);

		CommitViewWeb commitViewWeb = (CommitViewWeb) commitView;

		container.appendElement(commitViewWeb.getRootElement());
		
	}

	@Override
	public void displayCommitList(CommitListModel commitListModel, long deadline) {
		if(commitListModel.getCommits().size() <= CommitViewWeb.commitId ) {
			CommitViewWeb.commitId = 1;
		}
		HtmlElement studentId = this.getRootElement().find("#studentId").get(0);
		HtmlElement semesterId = this.getRootElement().find("#semesterId").get(0);
		HtmlElement exercisePath = this.getRootElement().find("#exercisePath").get(0);
		HtmlElement deadlineHtml = this.getRootElement().find("#deadline").get(0);
		HtmlElement fromDate = this.getRootElement().find("#fromDate").get(0);
		HtmlElement toDate = this.getRootElement().find("#toDate").get(0);
		

		MustNot.beNull(studentId);
		MustNot.beNull(semesterId);
		MustNot.beNull(exercisePath);
		MustNot.beNull(fromDate);
		MustNot.beNull(toDate);
		MustNot.beNull(deadlineHtml);
		
		studentId.appendHtml(commitListModel.getStudentId());
		semesterId.appendHtml(commitListModel.getSemesterId());
		exercisePath.appendHtml(commitListModel.getExercisePath());
		fromDate.appendHtml(commitListModel.getFromDate());
		toDate.appendHtml(commitListModel.getToDate());
		deadlineHtml.appendHtml(Long.toString(deadline));
	}
}
