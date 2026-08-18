package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PassengerInfo extends BasePage {
    public PassengerInfo(WebDriver driver){
        super(driver);
    }


    @FindBy(xpath = "//button[normalize-space()='Fill passenger details']")
    private WebElement passengerDetailsButton;


    public boolean isFillPassengerDetailButtonPresent(){
        return isElementDisplayed(passengerDetailsButton);
    }


}
