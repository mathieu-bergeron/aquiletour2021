package ca.ntro.jdk.services;

import ca.ntro.core.system.trace.T;
import ca.ntro.services.SystemService;

public class SystemServiceJdk extends SystemService {

	@Override
	public String lineSeparator() {
		T.call(this);

		return System.lineSeparator();
	}
}
