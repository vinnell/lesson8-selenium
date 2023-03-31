package net.absoft;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.exec.CommandLine;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class FirstBrowserTest {
    private  WebDriver driver;
@BeforeSuite
public void downloadDriver(){
    WebDriverManager.chromedriver().setup();
}
    @BeforeMethod
    public  void setUp(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\olena.vinnytska\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
         driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void testSuccessfulAuthorisation() {
    driver.findElement(By.id("user-name")).sendKeys("standard_user");
    driver.findElement(By.id("password")).sendKeys("secret_sauce");
    driver.findElement(By.id("login-button")).click();

   assertFalse(driver.findElements(By.id("shopping_cart_container")).isEmpty(), "User- should be logged in");
    }

    @Test
    public void testWrongPassword() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secretsauce");
        driver.findElement(By.id("login-button")).click();
        assertFalse(driver.findElements(By.xpath("//*[@class='error-button']")).isEmpty(), "Epic sadface: Username and password do not match any user in this service");
    }

  @Test
 public void testLockedUser() {
     driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
      driver.findElement(By.id("password")).sendKeys("secret_sauce");
     driver.findElement(By.id("login-button")).click();

       assertFalse(driver.findElements(By.xpath("//*[@class='error-button']")).isEmpty(), "Epic sadface: Username and password do not match any user in this service");
   }

  @AfterMethod
 public void  tearDown(){driver.quit();}
}
