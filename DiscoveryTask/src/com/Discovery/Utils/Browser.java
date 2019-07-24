package com.Discovery.Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Browser {

	public static WebDriver OpenBrowser(String URL) {
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\Resources\\ChromeDriver\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(URL);
		return driver;
	}

}
