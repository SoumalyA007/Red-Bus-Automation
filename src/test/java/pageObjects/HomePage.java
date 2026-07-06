package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    
public HomePage(WebDriver driver)
    {
       super(driver);
    }

    @FindBy(xpath = "//button[normalize-space()='Account']")
    private  WebElement accountButton;

    @FindBy(xpath = "//button[@aria-label='Sign up']")
    private  WebElement signInSignUpButton;

    @FindBy(xpath = "//button[normalize-space()='Log in']")
    private  WebElement loginButton;

    @FindBy(xpath = "//img[@title='Online Bus Tickets Booking']")
    private  WebElement busTicketBookingOption;

    @FindBy(xpath = "//img[@title='Online Train Tickets Booking']")
    private  WebElement trainTicketBookingOption;

    @FindBy(css = "div[class='inputAndSwapWrapper___9a5930'] div[class='srcDestWrapper___e85828 ']")
    private  WebElement journeyFrom;

    @FindBy(css = "body > div:nth-child(4) > main:nth-child(4) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > search:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2)")
    private  WebElement journeyTo;

    @FindBy(xpath = "//div[@aria-label='Select date of journey']")
    private  WebElement journeyDate;

    @FindBy(xpath = "//button[normalize-space()='Search buses']")
    private  WebElement searchBusesButton;

    @FindBy(xpath = "//button[normalize-space()='Book now']")
    private  WebElement bookNowButton;

    @FindBy(xpath = "//img[@title='redBus']")
    private  WebElement redBusLogo;

	@FindBy(xpath = "//div[@class='footer']")
	private WebElement footer;

    public void clickAccountButton() {
       accountButton.click();
    }

    public void clickSignInSignUpButton() {
       signInSignUpButton.click();
    }

    public void clickLoginButton() {
       loginButton.click();
    }

    public void clickBusTicketBookingOption() {
       busTicketBookingOption.click();
    }

    public void clickTrainTicketBookingOption() {
       trainTicketBookingOption.click();
    }

    public void enterJourneyFrom(String from) {
       journeyFrom.sendKeys(from);
    }

    public void enterJourneyTo(String to) {
       journeyTo.sendKeys(to);
    }

    public void clickJourneyDate() {
       journeyDate.click();
    }

    public void clickSearchBusesButton() {
       searchBusesButton.click();
    }

    public void clickBookNowButton() {
       bookNowButton.click();
    }

    public void clickRedBusLogo() {
       redBusLogo.click();
    }

    public boolean isLogoDisplayed(){
       return redBusLogo.isDisplayed();
    }

    public boolean isJourneyFromDisplayed() {
       return journeyFrom.isDisplayed();
    }

    public boolean isJourneyToDisplayed() {
       return journeyTo.isDisplayed();
    }

    public boolean isCalenderVisible(){
       return busTicketBookingOption.isDisplayed();
    }

    public boolean isSearchButtonEnabled(){
       return searchBusesButton.isEnabled();
    }

	public boolean isFooterDisplayed() {
		return footer.isDisplayed();
	}

   public boolean isLoginButtonDisplayed() {
       return loginButton.isDisplayed();
   }

   public boolean isAccountButtonDisplayed() {
       return accountButton.isDisplayed();
   }

}