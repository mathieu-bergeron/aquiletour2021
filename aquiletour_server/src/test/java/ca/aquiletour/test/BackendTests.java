package ca.aquiletour.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BackendTests {

	@Before
	public void setUp() throws IOException {
		TestUtils.installTestData();
	}

	@Test
	public void makeAppointmentTest() {
		assertTrue(true);
	}

	@Test
	public void loginTest() {
		assertTrue(true);
	}

	@After
	public void tearDown() {
		System.out.println("tearDown");
	}

}
