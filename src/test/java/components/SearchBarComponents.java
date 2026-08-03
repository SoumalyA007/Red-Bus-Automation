package components;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchBarComponents {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public SearchBarComponents(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public enum JourneyField {
        SOURCE, DESTINATION
    }

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

    @FindBy(xpath = "//i[contains(@aria-label,'Previous month')]")
    public WebElement dateBackArrow;

    // ===========================
    // Search / Book Locators
    // ===========================
    @FindBy(xpath = "//button[normalize-space()='Search buses']")
    public WebElement searchBusesButton;

    @FindBy(xpath = "//button[normalize-space()='Book now']")
    public WebElement bookNowButton;

    //div[contains(@class,'date___') and contains(@class, 'available___') and contains(@class,'calendarDate')]

    // ===========================
    // Journey Methods
    // ===========================
    public void clickJourneyFrom() {
        journeyFrom.click();
    }

    public void clickJourneyTo() {
        journeyTo.click();
    }

    public boolean isSuggestionsVisible() {
        WebElement suggestion = wait.until(ExpectedConditions.visibilityOf(autoSuggestion));
        return suggestion.isDisplayed();
    }

    public void selectJourneyCity(JourneyField field, String city) {
        if (field == JourneyField.SOURCE) {
            clickJourneyFrom();
        } else {
            clickJourneyTo();
        }

        isSuggestionsVisible();

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
        return currentSource.getAttribute("value");
    }

    public String getCurrentDestination() {
        return currentDestination.getAttribute("value");
    }

    public boolean isJourneyFromDisplayed() {
        return journeyFrom.isDisplayed();
    }

    public boolean isJourneyToDisplayed() {
        return journeyTo.isDisplayed();
    }

    // ===========================
    // Calendar Methods
    // ===========================
    public boolean isCalendarButtonVisible() {
        return calendarButton.isDisplayed();
    }

    public void openCalendar() {
        isCalendarButtonVisible();
        clickCalendarButton();
        wait.until(ExpectedConditions.visibilityOf(datePickerPopup));
    }

    public void clickCalenderDay(int day) {
        datePickerPopup.findElement(By.xpath(
                        ".//div[contains(@class,'calendarDate')]//span[text()='" + day + "']"))
                .click();
    }

    public void clickCalenderDay(LocalDate targetDate) {
        long epochMillis = targetDate.atStartOfDay(ZoneId.of("Asia/Kolkata"))
                .toInstant().toEpochMilli();

        By dayLocator = By.xpath(
                "//div[contains(@class,'calendarDate') and @data-date='" + epochMillis + "']");

        WebElement day = wait.until(ExpectedConditions.elementToBeClickable(dayLocator));
        day.click();
    }

    public void clickCalendarButton() {
        calendarButton.click();
    }

    public void clickDateProgressArrow() {
//        wait.until(ExpectedConditions.elementToBeClickable(dateProgressArrow)).click();
        dateProgressArrow.click();
    }

    public void clickDateBackArrow() {
        wait.until(ExpectedConditions.elementToBeClickable(dateBackArrow)).click();
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

            wait.until(driver ->
                    dateProgressArrow.isDisplayed()
                            && dateProgressArrow.isEnabled());

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
    // Search / Book Methods
    // ===========================
    public boolean isSearchButtonEnabled() {
        return searchBusesButton.isEnabled();
    }

    public void isSearchButtonClickable() {
        wait.until(ExpectedConditions.elementToBeClickable(searchBusesButton));
    }

    public void clickSearchBusesButton() {
        searchBusesButton.click();
    }

    public void clickBookNowButton() {
        bookNowButton.click();
    }

    public void typeCity(String city) {
        WebElement activeField = driver.switchTo().activeElement();
        activeField.sendKeys(Keys.CONTROL + "a");
        activeField.sendKeys(Keys.DELETE);
        activeField.sendKeys(city);
    }

    public List<WebElement> waitForSuggestions(int minCount) {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.xpath("//div[contains(@class,'searchCategory___')]"), minCount));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[starts-with(@id,'suggestion-')]")));
    }

    public void selectSuggestionByText(String city) {
        List<WebElement> suggestions = waitForSuggestions(2);
        for (WebElement suggestion : suggestions) {
            WebElement cityElement = suggestion.findElement(
                    By.xpath(".//div[contains(@class,'listHeader___') and @role='heading']"));
            if (cityElement.getText().trim().equalsIgnoreCase(city)) {
                suggestion.click();
                return;
            }
        }
        throw new NoSuchElementException("No suggestion found matching: " + city);
    }

    public void selectFirstSuggestion() {
        waitForSuggestions(1).get(0).click();
    }

    public String getNoResultsMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'noResultsFound___')]")));
        return driver.findElement(By.xpath("//div[contains(@class,'noResultsHeader___')]")).getText();
    }
}