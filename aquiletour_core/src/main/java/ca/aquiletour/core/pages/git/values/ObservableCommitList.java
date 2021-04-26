package ca.aquiletour.core.pages.git.values;


import ca.ntro.core.models.StoredList;
import ca.ntro.core.system.trace.T;

public class ObservableCommitList extends StoredList<Commit> {

	public ObservableCommitList() {
		super();
		T.call(this);
	}

}
