package com.Discovery.Pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 
 * @author Anilesh Soni, Date: 25-07-2019
 *
 */
public class DiscoveryGo {
	WebDriver driver;
	Actions actions;

	/*
	 * This is constructor of DiscoveryGo Page to use webDriver object
	 */
	public DiscoveryGo(WebDriver driver) {
		this.driver = driver;
	}

	/*
	 * Below are all Web Locators of Discovery Home Page
	 */
	By DiscoveryGOHome = By.xpath("//*[@id=\"react-root\"]/div/div[1]/div[2]/header/div[2]/div/a/picture/img");
	By recommendedSpan = By.xpath("//*[@id=\"react-root\"]/div/div[1]/section[8]/h2");
	By AddNotif = By.className("notification__main");
	By MyVideoHeading = By.tagName("h1");
	By FavoriteSection = By.xpath("//*[@id=\"react-root\"]/div/div[1]/div[3]/main/div/div[1]/section[4]/div/h2");
	By FavoriteVideos = By
			.xpath("/html/body/div[1]/div[1]/div[1]/div[3]/main/div/div[1]/section[4]/div/div/div/div[1]/div[1]");

	/*
	 * This method will verify the Discovery Home Page
	 */
	public boolean verifyHomePage() {
		if (driver.findElement(DiscoveryGOHome).isDisplayed()) {
			System.out.println("Discovery Go Home Page opened successfully");
			return true;
		}
		return false;
	}

	/**
	 * This method will verify Recommended Span
	 * 
	 * @return Boolean
	 */
	public boolean verifyRecommendedSpan() {
		if (driver.findElement(recommendedSpan).isDisplayed()) {
			System.out.println("Recommended Span displayed successfully");
			return true;
		}
		return false;
	}

	/**
	 * This method will Add Video to Favorites
	 * 
	 * @return HashMap with all titles and respective description of added videos
	 * @throws InterruptedException
	 */
	public HashMap<String, String> addVideo() throws InterruptedException {
		HashMap<String, String> video_Title_Desc = new HashMap<String, String>();
		for (int j = 1; j <= 2; j++) {
			actions = new Actions(driver);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			String HREF = driver
					.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/section[8]/div[1]/div[1]/div[1]/div[1]/div["
							+ j + "]/section[1]/div[3]/a[1]"))
					.getAttribute("href");
			actions.moveToElement(driver
					.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/section[8]/div[1]/div[1]/div[1]/div[1]/div["
							+ j + "]/section[1]/div[3]/a[1]/div[1]/div[2]/div[1]/span[1]/i[1]")))
					.build().perform();
			driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/section[8]/div[1]/div[1]/div[1]/div[1]/div["
					+ j + "]/section[1]/div[3]/a[1]/div[1]/div[2]/div[1]/span[1]/i[1]")).click();
			String successMsg = driver.findElement(AddNotif).getText();
			String title = driver
					.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/section[8]/div[1]/div[1]/div[1]/div[1]/div["
							+ j + "]/section[1]/div[3]/a[1]/div[1]/h3[1]"))
					.getText();
			String desc = driver
					.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/section[8]/div[1]/div[1]/div[1]/div[1]/div["
							+ j + "]/section[1]/div[3]/a[1]/div[1]/div[1]/div[1]"))
					.getText();
			video_Title_Desc.put(title, desc);
			if (successMsg.equals("Show added to My Favorite Shows on the My Videos page.")) {
				System.out.println("Video " + title + " added to My Favorites");
			}
		}
		return video_Title_Desc;
	}

	/**
	 * This method will go to MyVideos Page
	 * 
	 * @return Boolean
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public boolean GotoMyVideoSection() throws AWTException, InterruptedException {
		Robot robot = new Robot();
		actions = new Actions(driver);
		WebElement element = driver.findElement(By.xpath("//*[@alt=\"Discovery.com\"]"));
		actions.moveToElement(element).build().perform();
		for (int i = 1; i < 8; i++) {
			robot.keyPress(KeyEvent.VK_PAGE_UP);
			robot.keyRelease(KeyEvent.VK_PAGE_UP);
		}
		driver.findElement(By.xpath("//*[@alt='Discovery.com']")).click();
		driver.findElement(By.xpath("/html/body/section/div/header/section[2]/div/div[8]/div/span")).click();
		driver.findElement(By.xpath("//*[@href=\"https://www.discovery.com/my-videos\"]")).click();
		ArrayList<String> Tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(Tab.get(1));
		if (driver.findElement(MyVideoHeading).getText().equals("My Videos")) {
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			return true;
		}
		return false;
	}

	/**
	 * This method will goto Favorite section
	 * 
	 * @return HashMap with all the video title with description which are added in
	 *         favorites
	 * @throws InterruptedException
	 */
	public HashMap<String, String> FavoriteSection() throws InterruptedException {
		actions = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, 50);
		HashMap<String, String> fav_Video_Title_Desc = new HashMap<String, String>();
		if (driver.findElement(FavoriteSection).getText().equalsIgnoreCase("Favorite Shows")) {
			for (int k = 1; k <= 2; k++) {
				String href = driver.findElement(By.xpath(
						"/html/body/div[1]/div[1]/div[1]/div[3]/main[1]/div[1]/div[1]/section[4]/div[1]/div[1]/div[1]/div[1]/div[1]/div["
								+ k + "]/section[1]/div[3]/a[1]"))
						.getAttribute("href");
				String url = href.substring(href.indexOf("com") + 3, href.length());
				String xpth = "//*[@href=\"" + url + "\"]/div[1]";
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpth)));
				actions.moveToElement(driver.findElement(By.xpath(xpth + "/h3[1]/div[1]"))).build().perform();
				String fav_title = driver.findElement(By.xpath(xpth + "/h3[1]/div[1]")).getText();
				System.out.println("Fav Title:" + fav_title);
				String fav_desc = driver.findElement(By.xpath(xpth + "/div[1]/div[1]")).getText();
				System.out.println("Fav Desc:" + fav_desc);
				fav_Video_Title_Desc.put(fav_title, fav_desc);
			}
		}
		return fav_Video_Title_Desc;

	}
}
