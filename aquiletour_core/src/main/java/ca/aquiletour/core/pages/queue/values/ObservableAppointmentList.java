package ca.aquiletour.core.pages.queue.values;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.models.properties.NtroModelValue;
import ca.ntro.core.models.properties.observable.list.ObservableList;
import ca.ntro.core.system.trace.T;

public class ObservableAppointmentList extends ObservableList<Appointment> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9013487668542845304L;

	public ObservableAppointmentList() {
		super(new ArrayList<Appointment>());
		T.call(this);
	}

	public ObservableAppointmentList(List<Appointment> value) {
		super(value);
		T.call(this);
	}

}
