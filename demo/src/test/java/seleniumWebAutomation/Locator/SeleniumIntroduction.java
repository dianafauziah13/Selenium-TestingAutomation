package seleniumWebAutomation.Locator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SeleniumIntroduction {
    private WebDriver driver;
    
    @BeforeTest
    public void setup() throws InterruptedException{
         // Setup WebDriver
        System.setProperty("webdriver.chrome.drive", "D:\\Garuda\\Sertifikasi-Selenium\\Selenium-TestingAutomation\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/v1/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
        Thread.sleep(5000);
    }

     @Test
    public void testloginScenarioTest() throws InterruptedException{

        /*
         * Steps:
         * 1. User opens the browser and navigates to the login page
         * 2. User enters the username and password
         * 3. User clicks the login button
         * 4. User is redirected to the home page
         */

         WebElement userName = driver.findElement(By.id("user-name"));
         userName.sendKeys("standard_user");


         driver.findElement(By.name("password")).sendKeys("secret_sauce");
        //  driver.findElement(By.xpath("//input[@type='password']")).sendKeys("rahulshettyacademy");

         WebElement loginButton = driver.findElement(By.className("btn_action"));
         loginButton.click();

         Thread.sleep(5000);

         // redirect to home page
         String name = driver.findElement(By.xpath("//div[@class = 'product_label']")).getText();

         System.out.println("Ini menu utama : " + name);
    }

     @Test
    public void incorrectPasswordTest() throws InterruptedException{
        /*
         * Steps:
         * 1. User opens the browser and navigates to the login page
         * 2. User enters the username and invalid password
         * 3. User clicks the login button
         * 4. User will get error message
         */

        WebElement userName = driver.findElement(By.id("user-name"));
         userName.sendKeys("standard_user_Failed");


         driver.findElement(By.name("password")).sendKeys("secret_sauce");
        //  driver.findElement(By.xpath("//input[@type='password']")).sendKeys("rahulshettyacademy");

         WebElement loginButton = driver.findElement(By.className("btn_action"));
         loginButton.click();

         Thread.sleep(5000);
        

         // verify error message
         WebElement errorElement = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/h3"));
         String errorMessage = errorElement.getText();

         System.out.println("Ini adalah error message: " + errorMessage);

       
        
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
