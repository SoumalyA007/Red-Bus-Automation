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
	public static WebElement accountButton;

	@FindBy(xpath = "//button[@aria-label='Sign up']")
	public static WebElement signInSignUpButton;

	@FindBy(xpath = "//button[normalize-space()='Log in']")
	public static WebElement loginButton;

	@FindBy(xpath = "//img[@title='Online Bus Tickets Booking']")
	public static WebElement busTicketBookingOption;

	@FindBy(xpath = "//img[@title='Online Train Tickets Booking']")
	public static WebElement trainTicketBookingOption;

	@FindBy(css = "div[class='inputAndSwapWrapper___9a5930'] div[class='srcDestWrapper___e85828 ']")
	public static WebElement journeyFrom;

	@FindBy(css = "body > div:nth-child(4) > main:nth-child(4) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > search:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2)")
	public static WebElement journeyTo;

	@FindBy(xpath = "//div[@aria-label='Select date of journey']")
	public static WebElement journeyDate;

	@FindBy(xpath = "//button[normalize-space()='Search buses']")
	public static WebElement searchBusesButton;

	@FindBy(xpath = "//button[normalize-space()='Book now']")
	public static WebElement bookNowButton;

	@FindBy(xpath = "//img[@title='redBus']")
	public static WebElement redBusLogo;


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

}
