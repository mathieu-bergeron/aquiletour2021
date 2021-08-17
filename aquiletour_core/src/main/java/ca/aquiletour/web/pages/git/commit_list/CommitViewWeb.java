package ca.aquiletour.web.pages.git.commit_list;

import ca.aquiletour.core.pages.git.commit_list.views.CommitView;
import ca.aquiletour.core.pages.git.values.Commit;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class CommitViewWeb extends NtroViewWeb implements CommitView {
	public static int commitId = 1;
	
	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
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
		exercicePath.appendHtml(commit.getExercisePathIfCompleted());
		estimatedEffort.appendHtml(Integer.toString(commit.getEstimatedEffort()));
		timestamp.appendHtml(commit.getTimeStamp());
		
		commitId.setAttribute("id", "commit-" + CommitViewWeb.commitId);
		CommitViewWeb.commitId ++;
		
		for (int i = 0; i < commit.getModifiedFiles().size(); i++) {
			if(i == commit.getModifiedFiles().size() - 1) {
				modifiedFiles.appendHtml("Chemin : " + commit.getModifiedFiles().get(i).getPath() + "<br>"
						+ "Effort estim&#233 : " + commit.getModifiedFiles().get(i).getEstimatedEffort() + "<br>"
						+ "Chemin de l'exercise : " + commit.getModifiedFiles().get(i).getExercisePath() + "<br>"
						+ "Message : " + commit.getModifiedFiles().get(i).getMessage() + "<br>"
						);
			}else {
				modifiedFiles.appendHtml("Chemin : " + commit.getModifiedFiles().get(i).getPath() + "<br>"
						+ "Effort estim&#233 : " + commit.getModifiedFiles().get(i).getEstimatedEffort() + "<br>"
						+ "Chemin de l'exercise : " + commit.getModifiedFiles().get(i).getExercisePath() + "<br>"
						+ "Message : " + commit.getModifiedFiles().get(i).getMessage() + "<br>"
						+ "--------- <br>"
						);
			}
		}
	}
}
