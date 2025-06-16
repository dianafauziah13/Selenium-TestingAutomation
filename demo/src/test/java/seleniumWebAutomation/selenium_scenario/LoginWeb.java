package seleniumWebAutomation.selenium_scenario;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginWeb {
 private WebDriver driver;
    
    @BeforeMethod
    public void setup() throws InterruptedException{
         // Setup WebDriver
        System.setProperty("webdriver.chrome.drive", "D:\\Garuda\\Sertifikasi-Selenium\\Selenium-TestingAutomation\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/v1/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
    }
    @Test (priority = 1)
    public void validCredentials() throws InterruptedException {
        // This is a placeholder for the actual test implementation
        // You can add your test logic here
        System.out.println("Valid credentials test is running.");

        //Insert credential
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.name("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Thread.sleep(5000);

         // redirect to home page
         String tittle = driver.findElement(By.xpath("//div[@class = 'product_label']")).getText();

         Assert.assertEquals(tittle, "Products", "Tittle Home text does not match!");
    }

    @Test(priority = 1, dataProvider = "invalidCredentialsData")
    public void invalidCredentials(String username, String password, String usernameError, String passwordError) throws InterruptedException {
        // This is a placeholder for the actual test implementation
        // You can add your test logic here
        System.out.println("Invalid credentials test is running.");

        // kombinasi username password
        /*
         * 1. Valid Username , Invalid Password
         * 2. Invalid Username , Valid Password
         * 3. Invalid Username, Invalid Password
         * 4. Empty Username, Invalid Password
         */

        //Insert credential
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();


        // Validate error messages
        // Validate email error
        if(isElementPresent(By.xpath("//*[@id=\"login_button_container\"]/div/form/h3"))){
            String usernameErrorMessage = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/h3")).getText();
            Assert.assertEquals(usernameErrorMessage, usernameError, "Username error message does not match!");
        }

        // Validate password error
        if (isElementPresent(By.xpath("//*[@id=\"login_button_container\"]/div/form/h3"))) {
            String passwordErrorMessage = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/h3")).getText();           
            Assert.assertEquals(passwordErrorMessage, passwordError, "Password error message does not match!");
        }
        
    }


    @DataProvider(name = "invalidCredentialsData")
    public Object[][] invalidCredentialsData() {
        return new Object[][] {
            {"standard_user","testt","Epic sadface: Username and password do not match any user in this service","Epic sadface: Username and password do not match any user in this service"},
            {"standard_user_Failed","secret_sauce","Epic sadface: Username and password do not match any user in this service","Epic sadface: Username and password do not match any user in this service"},
            {"standard_user","","Epic sadface: Username and password do not match any user in this service","Epic sadface: Username and password do not match any user in this service"},
            {"","secret_sauce","Epic sadface: Username and password do not match any user in this service","Epic sadface: Username and password do not match any user in this service"},
            {"","", "Epic sadface: Username and password do not match any user in this service", "Epic sadface: Username and password do not match any user in this service"}
        };
    }

    public Boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @AfterMethod
    public void tearDown() {
        // Close the browser after the test
        if (driver != null) {
            driver.quit();
        }
    }

}
