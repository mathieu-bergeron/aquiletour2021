package ca.ntro.jsweet.services;

import ca.ntro.core.system.trace.T;
import ca.ntro.services.SystemService;

public class SystemServiceJSweet extends SystemService {

	@Override
	public String lineSeparator() {
		T.call(this);

		return "\n";
	}
}
