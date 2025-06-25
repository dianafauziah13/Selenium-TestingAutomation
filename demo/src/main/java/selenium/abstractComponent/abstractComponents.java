package selenium.abstractComponent;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class abstractComponents {
  WebDriver  driver;

    public abstractComponents(WebDriver driver) {
        // Constructor for AbstractComponent
        this.driver = driver;
    }

    public Boolean isElementPresent(By locator){
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void visibilityOfElementLocated(By locator) {
        // Implement visibility logic here
        // This method can be used to wait until an element is visible
        // For example, using WebDriverWait

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
