package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PassengerInfo extends BasePage {
    public PassengerInfo(WebDriver driver){
        super(driver);
    }


    @FindBy(xpath = "//button[normalize-space()='Fill passenger details']")
    private WebElement passengerDetailsButton;

    @FindBy(xpath = "//div[@aria-label='Passenger Info']")
    private WebElement passengerDetailTab;


    public boolean isFillPassengerDetailButtonPresent(){
        return isElementDisplayed(passengerDetailsButton);
    }

    /**
     * Efficiently checks if the Fill Passenger Detail button is NOT present.
     * Uses a short timeout (5 seconds) instead of the default 10 seconds.
     * This prevents wasting time when checking for absence of an element.
     */
    public boolean isFillPassengerDetailButtonNotPresent(){
        return isElementNotDisplayed(passengerDetailsButton);
    }

    public void clickPassengerInfoButton(){
        clickElement(passengerDetailTab);
    }


}
