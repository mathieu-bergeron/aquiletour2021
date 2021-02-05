package ca.aquiletour.core.pages.queue.values;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.models.properties.observable.list.ObservableList;
import ca.ntro.core.system.trace.T;

public class ObservableAppointmentList extends ObservableList<Appointment> {

	public ObservableAppointmentList() {
		super(new ArrayList<>());
	}

	public ObservableAppointmentList(List<Appointment> value) {
		super(value);
	}

	@Override
	protected Class<?> getValueType() {
		T.call(this);

		return List.class;
	}
	
	
	
}
