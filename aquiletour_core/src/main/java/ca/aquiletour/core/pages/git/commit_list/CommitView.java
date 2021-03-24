package ca.aquiletour.core.pages.git.commit_list;

import ca.aquiletour.core.pages.git.values.Commit;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.mvc.NtroView;

public interface CommitView extends NtroView {
	
	void displayCommit(Commit commit);

}
