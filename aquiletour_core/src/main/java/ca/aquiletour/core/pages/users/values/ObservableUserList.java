package ca.aquiletour.core.pages.users.values;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.models.properties.observable.list.ObservableList;
import ca.ntro.core.system.trace.T;

public class ObservableUserList extends ObservableList<User> {

	public ObservableUserList() {
		super(new ArrayList<>());
	}

	public ObservableUserList(List<User> value) {
		super(value);
	}

	@Override
	protected Class<?> getValueType() {
		T.call(this);

		return List.class;
	}
	
	
	
}
