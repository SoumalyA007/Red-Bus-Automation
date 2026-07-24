package pageObjects;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchPage extends BasePage {

    public SearchPage(WebDriver driver){
        super(driver);
    }


    // ===========================
    // Route Locator
    // ===========================
    @FindBy(xpath="//span[@role='text']")
    WebElement route;

    // ===========================
    // Route Methods
    // ===========================
    public String[] getRoute(){
        return route.getText().split(" ");
    }

    // ===========================
    // Bus Card Locators
    // ===========================

    @FindBy(xpath = "//li[contains(@class,'tupleWrapper')]")
    List<WebElement> busCards;

    // ===========================
    // Bus Cards Methods
    // ===========================
    public boolean waitForBusCardsToLoad() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(busCards));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public int getBusCardsCount() {
        return busCards.size();
    }

    public boolean areBusCardsDisplayed() {
        return !busCards.isEmpty() && busCards.stream().allMatch(WebElement::isDisplayed);
    }
    

}
