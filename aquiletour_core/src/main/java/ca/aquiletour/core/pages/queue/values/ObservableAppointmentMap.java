package ca.aquiletour.core.pages.queue.values;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.ObservableMap;
import ca.ntro.core.system.trace.T;

public class ObservableAppointmentMap extends ObservableMap<Appointment> {

	public ObservableAppointmentMap() {
		super(new HashMap<>());
	}

	public ObservableAppointmentMap(Map<String, Appointment> map) {
		super(map);
	}
}
