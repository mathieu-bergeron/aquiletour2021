package ca.ntro.jsweet.services;

import ca.ntro.services.AssertService;

public class AssertServiceJSweet extends AssertService {

	@Override
	public void fail(String message) {
		System.err.println("AssertionError, not true " + message);
	}

}
