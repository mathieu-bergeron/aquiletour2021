package ca.ntro.jdk.test;

import ca.ntro.services.AssertService;

public class AssertServiceJUnit extends AssertService {

	@Override
	public void fail(String message) {
		org.junit.Assert.fail(message);
	}
}
