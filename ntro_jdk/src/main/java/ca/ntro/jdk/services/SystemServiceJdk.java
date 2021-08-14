package ca.ntro.jdk.services;

import ca.ntro.core.system.trace.T;
import ca.ntro.services.SystemService;

public class SystemServiceJdk extends SystemService {

	@Override
	public String lineSeparator() {
		T.call(this);

		return System.lineSeparator();
	}

	@Override
	public boolean isJSweet() {
		T.call(this);

		return false;
	}

	@Override
	public boolean isJdk() {
		T.call(this);

		return true;
	}
}
