package ca.ntro.jsweet.services;

import ca.ntro.core.services.AssertService;

public class AssertServiceJSweet extends AssertService {

	@Override
	public void fail(String message) {
		System.err.println("AssertionError: " + message);
	}

}
