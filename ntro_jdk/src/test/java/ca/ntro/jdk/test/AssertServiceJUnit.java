package ca.ntro.jdk.test;

import ca.ntro.core.services.AssertService;

public class AssertServiceJUnit extends AssertService {

	@Override
	public void assertTrue(boolean value) {
		org.junit.Assert.assertTrue(value);
	}
}
