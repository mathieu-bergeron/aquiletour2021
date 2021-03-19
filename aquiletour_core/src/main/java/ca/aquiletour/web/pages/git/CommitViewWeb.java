package ca.aquiletour.web.pages.git;

import ca.aquiletour.core.pages.git.commit_list.CommitView;
import ca.aquiletour.core.pages.git.values.Commit;
import ca.aquiletour.core.pages.queue.AppointmentView;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.mvc.NtroViewWeb;

public class CommitViewWeb extends NtroViewWeb implements CommitView {

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.here();
	}


	@Override
	public void displayCommit(Commit commit) {
		T.here();
		T.call(this);

		HtmlElement commitMessage = this.getRootElement().find("#commitMessage").get(0);
		HtmlElement exercicePath = this.getRootElement().find("#exercicePath").get(0);
		HtmlElement estimatedEffort = this.getRootElement().find("#estimatedEffort").get(0);
		HtmlElement timestamp = this.getRootElement().find("#timestamp").get(0);

		MustNot.beNull(commitMessage);
		MustNot.beNull(exercicePath);
		MustNot.beNull(estimatedEffort);
		MustNot.beNull(timestamp);
		T.values(commit.getExercisePath());
		
		commitMessage.appendHtml(commit.getCommitMessage());
		exercicePath.appendHtml(commit.getExercisePath());
		estimatedEffort.appendHtml(Integer.toString(commit.getEstimatedEffort()));
		timestamp.appendHtml(commit.getTimeStamp());
	}
}
