package ca.aquiletour.core.pages.queue.models;

import ca.aquiletour.core.models.paths.CoursePath;
import ca.ntro.core.models.StoredProperty;
import ca.ntro.core.system.trace.T;

public class StoredCoursePath extends StoredProperty<CoursePath> {
	
	public StoredCoursePath() {
		super(new CoursePath());
		T.call(this);
	}

}
