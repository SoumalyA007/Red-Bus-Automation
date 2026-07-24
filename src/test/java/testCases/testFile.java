package testCases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class testFile {

    WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://www.redbus.in/search?fromCityName=Delhi&fromCityId=733&srcCountry=undefined&fromCityType=CITY&toCityName=Burdwan&toCityId=74678&destCountry=IND&toCityType=CITY&onward=24-Jul-2026&doj=24-Jul-2026&ref=home");
    }

    @Test
    public void testFile() {

        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //clicked on the source box
        WebElement sourceClick = driver.findElement(By.xpath("//div[contains(@class,'srcDestWrapper___')]"));
        sourceClick.click();

        //need to wait for the suggestion box to appear
        WebElement afterClickSuggestion = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(@class,'suggestionsWrapper___') and @aria-label='Search suggestions']")
                )
        );

        //after waiting for suggestion box to appear we need to move the focus to the current element
        WebElement currentFocusedElement = driver.switchTo().activeElement();

        //clear the element if anything present
        currentFocusedElement.sendKeys(Keys.CONTROL + "a");
        currentFocusedElement.sendKeys(Keys.DELETE);

        //need to send the source now
        currentFocusedElement.sendKeys("Mumbai");

        //need to wait for the suggestion box to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'searchCategory___')][1]")
        ));

        WebElement cityOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'searchCategory___')][1]//div[@role='button'][.//div[@role='heading' and normalize-space(text())='Mumbai']]")
        ));
        cityOption.click();

        //Destination
    }

    @Test
    public void test() {
        WebElement route = driver.findElement(
                By.xpath("//span[@role='text']")
        );

        String routeText = route.getText(); // "Delhi to Burdwan"

        System.out.println(routeText);
    }

}
