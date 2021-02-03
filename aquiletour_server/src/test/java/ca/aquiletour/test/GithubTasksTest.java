package ca.aquiletour.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Test;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;

public class GithubTasksTest {
	
	private WebDriver driver;
	private String githubRepoProjectMarwaneUrl = "https://github.com/mathieu-bergeron/aquiletour2021/projects/7";
	
	@Before
	public void setUp() {
		//driver = new FirefoxDriver();
		driver = new ChromeDriver();
		//driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS); // set how long it should wait for each element it searches 
	}

	@Test
	public void gitHubFetchTasksTest() {
		ArrayList<String> tasksTextString = getTask(driver);
		boolean taskExists = false;
		
		//verify if task TEST exists
		for (String string : tasksTextString) {
			if(string.equals("TEST")) {
				taskExists = true;
			}
		}
		assertTrue(taskExists);
	}

	@After
	public void tearDown() {
		driver.close();
	}
	
	private ArrayList<String> getTask(WebDriver driver){
		
		WebElement div, p;
		
		driver.get(githubRepoProjectMarwaneUrl);
        waitForPageToLoad(500);

        ArrayList<String> tasksTextString = new ArrayList<String>();
        
        //search for tasks
        ArrayList<WebElement> elements = (ArrayList<WebElement>) driver.findElements(By.tagName("task-lists"));
        
        for (int i = 0; i < elements.size(); i++) {
			div = elements.get(i).findElement(By.className("js-comment-body"));
			p = div.findElement(By.tagName("p"));
			tasksTextString.add(p.getAttribute("innerHTML"));
			
		}
		return tasksTextString;
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
