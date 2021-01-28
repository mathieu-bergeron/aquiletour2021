package ca.aquiletour.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Test;
import static org.junit.Assert.assertNotEquals;

public class GithubTasksTest {

	@Test
	public void gitHubFetchTasksTest() {
		
		WebDriver driver = new ChromeDriver();

		assertNotEquals(null, driver);
	}

}
