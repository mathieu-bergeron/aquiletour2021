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
//	private String connectTeacher = "localhost:8080?userId=alice&authToken=aliceToken";
	//private String loadJS = "localhost:8080?nojs";
	
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
		

//		driver.get(loadJS);
//		waitForPageToLoad(100);
		
		driver.get(aquiletourSite);
		waitForPageToLoad(500);
		driver.manage().addCookie(new Cookie("userId", "alice"));
		driver.manage().addCookie(new Cookie("authToken", "aliceToken"));

        //search for tasks
		driver.findElement(By.xpath("//a[@href='/mescours']")).click();
        waitForPageToLoad(500);
        
        //add class
        driver.findElement(By.xpath("//button[@data-target='#modalDashboard']")).click();
        WebElement title = driver.findElement(By.name("title"));
        WebElement summary = driver.findElement(By.name("summary"));
        WebElement date = driver.findElement(By.name("date"));
        title.sendKeys(titleInput);
        summary.sendKeys(summaryInput);
        date.sendKeys(dateInput);
        
        driver.findElement(By.xpath("//button[@id='add-course-submit-button']")).click();
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
