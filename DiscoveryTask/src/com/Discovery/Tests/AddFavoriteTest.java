package com.Discovery.Tests;

import java.awt.AWTException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.Discovery.Pages.DiscoveryGo;
import com.Discovery.Utils.Browser;

/**
 * 
 * @author Anilesh Soni, This class contains Test Script
 */
public class AddFavoriteTest {
	WebDriver driver;
	DiscoveryGo discoveryGo;

	@BeforeTest
	public void openBrowser() {
		driver = Browser.OpenBrowser("https://go.discovery.com/");
	}

	@AfterTest
	public void closeBrowser() {
		driver.quit();
	}

	@Test
	public void addToFavorites() throws InterruptedException, AWTException {
		discoveryGo = new DiscoveryGo(driver);
		HashMap<String, String> FavVideoDetails = null;
		HashMap<String, String> addedVideoDetails = null;
		if (discoveryGo.verifyHomePage()) {
			if (discoveryGo.verifyRecommendedSpan()) {
				addedVideoDetails = discoveryGo.addVideo();
				if (discoveryGo.GotoMyVideoSection()) {
					FavVideoDetails = discoveryGo.FavoriteSection();
				} else {
					Assert.fail();
					System.out.println("My Videos Section not displayed");
				}
				System.out.println("Added: " + addedVideoDetails);
				System.out.println("Found: " + FavVideoDetails);
				Assert.assertEquals(FavVideoDetails, addedVideoDetails);
			} else {
				Assert.fail();
				System.out.println("Recommeded Span not displayed");
			}
		} else {
			Assert.fail();
			System.out.println("Discovery Home Page not landed");
		}

	}

}
