package ca.aquiletour.core.pages.queue.values;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.models.properties.NtroModelValue;
import ca.ntro.core.models.properties.observable.map.ObservableMap;
import ca.ntro.core.system.trace.T;

public class ObservableAppointmentMap extends ObservableMap<Appointment> {

	public ObservableAppointmentMap() {
		super(new HashMap<>());
	}

	public ObservableAppointmentMap(Map<String, Appointment> map) {
		super(map);
	}

	@Override
	protected Class<?> valueType() {
		T.call(this);

		return NtroModelValue.class;
	}

}
