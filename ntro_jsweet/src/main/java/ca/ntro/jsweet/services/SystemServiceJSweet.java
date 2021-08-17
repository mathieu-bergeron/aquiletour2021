package ca.ntro.jsweet.services;

import ca.ntro.core.system.trace.T;
import ca.ntro.services.SystemService;

public class SystemServiceJSweet extends SystemService {

	@Override
	public String lineSeparator() {
		T.call(this);

		return "\n";
	}

	@Override
	public boolean isJSweet() {
		T.call(this);

		return true;
	}

	@Override
	public boolean isJdk() {
		T.call(this);

		return false;
	}
}
