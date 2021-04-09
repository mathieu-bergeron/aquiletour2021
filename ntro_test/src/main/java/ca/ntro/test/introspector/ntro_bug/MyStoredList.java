package ca.ntro.test.introspector.ntro_bug;


import ca.ntro.core.models.StoredList;
import ca.ntro.core.system.trace.T;

public class MyStoredList extends StoredList<MyListItem> {

	public MyStoredList() {
		super();
		T.call(this);
	}

}
