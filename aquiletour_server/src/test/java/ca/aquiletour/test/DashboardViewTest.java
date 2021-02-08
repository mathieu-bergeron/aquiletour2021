package ca.aquiletour.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class DashboardViewTest {
	private WebDriver driver;
	private String aquiletourSite = "http://localhost:8080";
	
	@Before
	public void setUp() throws IOException {

		TestUtils.installTestData();

		//driver = new FirefoxDriver();
		driver = new ChromeDriver();
	}

	@Test
	public void gitHubFetchTasksTest() {
		addClass(driver, "testSeleniumTitle2", "testSeleniumSummary2", "testSeleniumDate2");
			
	}

	@After
	public void tearDown() {
		driver.close();
	}
	
	private void addClass(WebDriver driver, String titleInput, String summaryInput, String dateInput ){
		
		Boolean created = false;
		

		driver.get(aquiletourSite);

		driver.manage().addCookie(new Cookie("userId", "bob"));
		driver.manage().addCookie(new Cookie("authToken", "bobToken"));
		
		waitForPageToLoad(500);

        //search for tasks
        driver.findElement(By.xpath("//a[@href='/dashboard']")).click();
        waitForPageToLoad(500);
        
        //add class
        WebElement title = driver.findElement(By.name("title"));
        WebElement summary = driver.findElement(By.name("summary"));
        WebElement date = driver.findElement(By.name("date"));
        title.sendKeys(titleInput);
        summary.sendKeys(summaryInput);
        date.sendKeys(dateInput);
        
        driver.findElement(By.xpath("//button[text()='Ajouter']")).click();
        ArrayList<WebElement> titles = (ArrayList<WebElement>) driver.findElements(By.id("course-title"));
        for (WebElement webElement : titles) {
        	if (webElement.getAttribute("innerHTML").equals(titleInput)) {
				created = true; 
			}
		}
        assertTrue(created); //true if title given as parameter exists as a course title
        
	}
	
	private void waitForPageToLoad(int time) {
		try {
			Thread.sleep(time); // wait 5 seconds for page to load
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
