package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BoardingDroppingPoint extends BasePage {
    public BoardingDroppingPoint(WebDriver driver){
        super(driver);
    }


    @FindBy(xpath = "//button[normalize-space()='Select boarding & dropping points']")
    WebElement boardingDroppingPointButton;

    @FindBy(xpath = "//div[contains(@aria-label,'Board/Drop point')]")
    WebElement boardingDroppingPointTab;



    public void clickBoardingDroppingPointButton(){
        clickElement(boardingDroppingPointButton);
    }

    public boolean isBoardingDroppingPointTabSelected(){
        return getAttributeValue(boardingDroppingPointTab,"aria-selected").equals("true");
    }



}
