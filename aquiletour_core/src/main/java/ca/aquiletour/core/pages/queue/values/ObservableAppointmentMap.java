package ca.aquiletour.core.pages.queue.values;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.models.StoredMap;
import ca.ntro.core.system.trace.T;

public class ObservableAppointmentMap extends StoredMap<Appointment> {

	public ObservableAppointmentMap() {
		super(new HashMap<>());
	}

	public ObservableAppointmentMap(Map<String, Appointment> map) {
		super(map);
	}
}
