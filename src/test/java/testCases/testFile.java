package testCases;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testBase.BaseClass;
import utils.HelperFunctions;

public class testFile extends BaseClass {

    WebDriver driver;
    HelperFunctions helperFunctions;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://www.redbus.in/search?fromCityName=Delhi&fromCityId=733&srcCountry=undefined&fromCityType=CITY&toCityName=Burdwan&toCityId=74678&destCountry=IND&toCityType=CITY&onward=24-Jul-2026&doj=24-Jul-2026&ref=home");

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(1000));
        helperFunctions = new HelperFunctions(driver,wait);


    }

    @Test
    public void testFile() {

        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        helperFunctions.enter_source("Kolkata");
        helperFunctions.enter_destination("Burdwan");
        LocalDate targetDate = LocalDate.now().plusDays(10);

        searchbarcomponents.openCalendar();
        String targetMonth = targetDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        System.out.println("Target Month: " + targetMonth);










        searchbarcomponents.isSearchButtonClickable();
        searchbarcomponents.clickSearchBusesButton();

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
