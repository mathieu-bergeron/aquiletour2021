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
	public static int commitId = 1;
	
	@Override
	public void initializeViewWeb(NtroContext<?> context) {
	}


	@Override
	public void displayCommit(Commit commit) {
		T.call(this);

		HtmlElement commitMessage = this.getRootElement().find("#commitMessage").get(0);
		HtmlElement exercicePath = this.getRootElement().find("#exercicePath").get(0);
		HtmlElement estimatedEffort = this.getRootElement().find("#estimatedEffort").get(0);
		HtmlElement modifiedFiles = this.getRootElement().find("#modifiedFiles").get(0);
		HtmlElement timestamp = this.getRootElement().find("#timestamp").get(0);
		HtmlElement commitId = this.getRootElement().find("#commitId").get(0);

		MustNot.beNull(commitMessage);
		MustNot.beNull(exercicePath);
		MustNot.beNull(estimatedEffort);
		MustNot.beNull(timestamp);
		MustNot.beNull(modifiedFiles);
		MustNot.beNull(commitId);
		
		commitMessage.appendHtml(commit.getCommitMessage());
		exercicePath.appendHtml(commit.getExercisePath());
		estimatedEffort.appendHtml(Integer.toString(commit.getEstimatedEffort()));
		timestamp.appendHtml(commit.getTimeStamp());
		
		//commitId.appendHtml(Integer.toString(CommitViewWeb.commitId));
		commitId.setAttribute("id", "commit-" + CommitViewWeb.commitId);
		CommitViewWeb.commitId ++;
		
		for (int i = 0; i < commit.getModifiedFiles().size(); i++) {
			if(i == commit.getModifiedFiles().size() - 1) {
				modifiedFiles.appendHtml(commit.getModifiedFiles().get(i));
			}else {
				modifiedFiles.appendHtml(commit.getModifiedFiles().get(i) + ", ");
			}
		}
	}
}
