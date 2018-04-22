package org.practise.sprint5_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import Flash.FlashObjectWebDriver;

public class MavenTest {

	WebDriver driver;
	FlashObjectWebDriver flash;

	public MavenTest() {
		System.setProperty("webdriver.chrome.driver", "F:\\My_Workspace\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();

	}

	@Test
	public void testOne() throws IOException {

		File file = new File("F:\\cookies.data");
		file.delete();

		file.createNewFile();

		FileWriter fWriter = new FileWriter(file);
		BufferedWriter bWriter = new BufferedWriter(fWriter);

		driver.get("http://demo.guru99.com/test/cookie/selenium_aut.php");
		driver.findElement(By.name("username")).sendKeys("testUser123");
		driver.findElement(By.name("password")).sendKeys("testPassword123");
		driver.findElement(By.name("submit")).click();
		;

		for (Cookie c : driver.manage().getCookies()) {
			bWriter.write(c.getName() + ";" + c.getValue() + ";" + c.getDomain() + ";" + c.getPath() + ";"
					+ c.getExpiry() + ";" + c.isSecure());
			bWriter.newLine();
		}
		bWriter.close();
		driver.close();

	}

	@Test
	public void testTwo() throws FileNotFoundException, IOException, ParseException {
		driver = new ChromeDriver();

		File file = new File("F:\\cookies.data");

		FileReader fReader = new FileReader(file);
		BufferedReader bReader = new BufferedReader(fReader);

		String str = null;
		while ((str = bReader.readLine()) != null) {
			String[] temp = str.split(";");
			String cName = temp[0];
			String cValue = temp[1];
			String cDomain = temp[2];
			String cPath = temp[3];
			Date cExpiry = null;
			if (!(temp[4]).equals("null"))
				cExpiry = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy").parse(temp[4]);
			else
				cExpiry = new Date();

			Boolean cIsSecure = new Boolean(temp[5]).booleanValue();

			Cookie newCookie = new Cookie(cName, cValue, cDomain, cPath, cExpiry, cIsSecure);
			driver.get("http://demo.guru99.com/");
			driver.manage().addCookie(newCookie);

		}

		driver.get("http://demo.guru99.com/test/cookie/selenium_cookie.php");
		driver.close();
	}

}
