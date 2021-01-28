package ca.aquiletour.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.Test;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;

public class GithubTasksTest {
	
	private WebDriver driver;
	
	@Before
	public void setUp() {
		//driver = new FirefoxDriver();
		driver = new ChromeDriver();
	}

	@Test
	public void gitHubFetchTasksTest() {
		assertNotEquals(null, driver);
	}

	@After
	public void tearDown() {
		driver.close();
	}

}
