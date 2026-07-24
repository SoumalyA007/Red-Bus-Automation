package components;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.HomePage;

import java.time.Duration;


public class SearchBarComponents {
    private  WebDriver driver;
    private  WebDriverWait wait;
    public SearchBarComponents(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    HomePage hp = new HomePage(driver);

    public enum JourneyField { SOURCE, DESTINATION }
    // ===========================
    // Journey Locators
    // ===========================
    @FindBy(xpath = "(//div[contains(@class,'srcDestWrapper___')])[1]")
    public WebElement journeyFrom;

    @FindBy(xpath = "(//div[contains(@class,'srcDestWrapper___')])[2]")
    public WebElement journeyTo;

    @FindBy(xpath = "//input[contains(@class,'inputField') and @id='srcinput']")
    public WebElement currentSource;

    @FindBy(xpath = "//input[contains(@class,'inputField') and @id='destinput']")
    public WebElement currentDestination;

    @FindBy(xpath = "//div[contains(@class,'suggestionsWrapper___') and @aria-label='Search suggestions']")
    public WebElement autoSuggestion;

    @FindBy(xpath = "//div[contains(@class,'swapWrap__') and @role='button']")
    public WebElement swapButton;

    @FindBy(xpath = "(//div[contains(@class,'searchCategory___')])[1]")
    public WebElement suggestionCategory;

    // ===========================
    // Calendar Locators
    // ===========================
    @FindBy(xpath = "//div[@aria-label='Select date of journey']")
    public WebElement calendarButton;

    @FindBy(xpath = "//div[contains(@class,'dateHolder___')]")
    public WebElement datePickerPopup;

    @FindBy(xpath = "//p[contains(@class,'monthYear___')]")
    public WebElement calenderMonthYear;

    @FindBy(xpath = "//i[contains(@class,'icon-arrow') and contains(@class,'right___')]")
    public WebElement dateProgressArrow;

    @FindBy(xpath = "//span[contains(@class,'doj___')]")
    public WebElement selectedDate;

    @FindBy(xpath = "//i[contains(@aria-label,'Previous month,\")]")
    public WebElement dateBackArrow;

    // ===========================
    // Search Locators
    // ===========================
    @FindBy(xpath = "//button[normalize-space()='Search buses']")
    public WebElement searchBusesButton;

    @FindBy(xpath = "//button[normalize-space()='Book now']")
    public WebElement bookNowButton;




    // ===========================
    // Journey Methods
    // ===========================
    public void clickJourneyFrom() {
        journeyFrom.click();
    }

    public void clickJourneyTo() {
        journeyTo.click();
    }

    public void selectJourneyCity(SearchBarComponents.JourneyField field, String city) {
        if (field == SearchBarComponents.JourneyField.SOURCE) {
            clickJourneyFrom();
        } else {
            clickJourneyTo();
        }

        hp.isSuggestionsVisible();

        WebElement activeField = driver.switchTo().activeElement();
        activeField.sendKeys(Keys.CONTROL + "a");
        activeField.sendKeys(Keys.DELETE);
        activeField.sendKeys(city);

        wait.until(ExpectedConditions.visibilityOf(suggestionCategory));

        WebElement cityOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(
                        "//div[contains(@class,'searchCategory___')]//div[contains(@class,'suggestion-item')]"
                                + "[.//div[@role='heading' and "
                                + "translate(normalize-space(.),"
                                + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                                + "'abcdefghijklmnopqrstuvwxyz')='" + city.trim().toLowerCase() + "']]"
                )));

        cityOption.click();
    }

    public void clickSwapButton() {
        swapButton.click();
    }

    public String getCurrentSource() {
        String value = currentSource.getAttribute("value");
        return value;
    }

    public String getCurrentDestination() {
        String value = currentDestination.getAttribute("value");
        return value;
    }

    // ===========================
    // Calendar Methods
    // ===========================
    public void openCalendar() {
        hp.isCalendarButtonVisible();
        clickCalendarButton();
        wait.until(ExpectedConditions.visibilityOf(datePickerPopup));
    }

    public void clickCalenderDay(int day) {
        driver.findElement(By.xpath(
                        "//div[contains(@class,'calendarDate')]//span[text()='" + day + "']"))
                .click();
    }

    public void clickCalendarButton() {
        calendarButton.click();
    }

    public void clickDateProgressArrow() {
        dateProgressArrow.click();
    }

    public void clickDateBackArrow() {
        dateBackArrow.click();
    }

    public boolean isDateBackArrowEnabled() {
        return dateBackArrow.isEnabled();
    }

    public String getCalenderMonthYear() {
        return calenderMonthYear.getText();
    }

    public void navigateCalendarTo(String targetMonth, int targetYear) {
        while (true) {
            String calendarMonthYear = getCalenderMonthYear();
            String[] parts = calendarMonthYear.split(" ");
            String currentMonth = parts[0];
            int currentYear = Integer.parseInt(parts[1]);

            if (currentMonth.equalsIgnoreCase(targetMonth) && currentYear == targetYear) {
                break;
            }

            clickDateProgressArrow();
            wait.until(ExpectedConditions.not(
                    ExpectedConditions.textToBePresentInElement(calenderMonthYear, calendarMonthYear)));
        }
    }

    public boolean isDateEnabled(int day) {
        WebElement dateElement = driver.findElement(By.xpath(
                "//div[contains(@class,'calendarDate')]//span[text()='" + day + "']"));

        return dateElement.isEnabled();
    }

    public String getSelectedDate() {
        return selectedDate.getText();
    }

    // ===========================
    // Search Methods
    // ===========================
    public void isSearchButtonClickable(){
        wait.until(ExpectedConditions.elementToBeClickable(searchBusesButton));
    }
    public void clickSearchBusesButton() {
        searchBusesButton.click();
    }




}
