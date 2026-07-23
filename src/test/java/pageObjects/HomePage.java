package pageObjects;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {

    private static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(15);
    private static final Duration ELEMENT_CLICKABLE_TIMEOUT = Duration.ofSeconds(5);
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[normalize-space()='Account']")
    public WebElement accountButton;

    @FindBy(xpath = "//button[@aria-label='Sign up']")
    public WebElement signInSignUpButton;

    @FindBy(xpath = "//button[normalize-space()='Log in']")
    public WebElement loginButton;

    @FindBy(xpath = "//img[@title='Online Bus Tickets Booking']")
    public WebElement busTicketBookingOption;

    @FindBy(xpath = "//img[@title='Online Train Tickets Booking']")
    public WebElement trainTicketBookingOption;


    @FindBy(xpath="(//div[contains(@class,'srcDestWrapper___')])[1]")
    public WebElement journeyFrom;

    @FindBy(xpath = "(//div[contains(@class,'srcDestWrapper___')])[2]")
    public WebElement journeyTo;

    @FindBy(xpath = "//div[@aria-label='Select date of journey']")
    public WebElement calendarButton;

    @FindBy(xpath = "//div[contains(@class,'dateHolder___')]")
    public WebElement datePickerPopup;

    @FindBy(xpath = "//button[normalize-space()='Search buses']")
    public WebElement searchBusesButton;

    @FindBy(xpath = "//button[normalize-space()='Book now']")
    public WebElement bookNowButton;

    @FindBy(xpath = "//img[@title='redBus']")
    public WebElement redBusLogo;

    @FindBy(xpath = "//div[@class='footer']")
    public WebElement footer;

    @FindBy(xpath = "//div[contains(@class,'suggestionsWrapper___') and @aria-label='Search suggestions']")
    public WebElement autoSuggestion;

    @FindBy(xpath = "//div[contains(@class,'swapWrap__') and @role='button']")
    public WebElement swapButton;

    @FindBy(xpath = "//input[contains(@class,'inputField') and @id='srcinput']")
    public WebElement currentSource;

    @FindBy(xpath = "//input[contains(@class,'inputField') and @id='destinput']")
    public WebElement currentDestination;

    @FindBy(xpath = "//div[contains(@class,'snackbarprimary___66b04a') and contains(@class,'warning___a03877')]")
    public WebElement emptySourcePopUpMessage;

    @FindBy(xpath = "//p[contains(@class,'monthYear___')]")
    public WebElement calenderMonthYear;

    @FindBy(xpath = "//i[contains(@class,'icon-arrow') and contains(@class,'right___')]")
    public WebElement dateProgressArrow;

    @FindBy(xpath = "//span[contains(@class,'doj___')]")
    public WebElement selectedDate;

    @FindBy(xpath = "(//div[contains(@class,'searchCategory___')])[1]")
    public WebElement suggestionCategory;
    
    @FindBy(xpath = "//h3[normalize-space()='Can I book a Government bus ticket on redBus?']")
    public WebElement faqSectionHeading;

    @FindBy(xpath = "//i[contains(@aria-label,'Previous month,\")]")
    public WebElement dateBackArrow;

    public void clickDateBackArrow() {
        dateBackArrow.click();
    }

    public boolean isDateBackArrowEnabled(){
        return dateBackArrow.isEnabled();
    }

    public void scrollToFooter() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", faqSectionHeading);
    }


    public String getCalenderMonthYear(){
        return calenderMonthYear.getText();
    }

    public String getSelectedDate(){
        return selectedDate.getText();
    }

    public void clickDateProgressArrow(){dateProgressArrow.click();}

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

    public String getCurrentSource(){
        String value = currentSource.getAttribute("value");
        return value;
    }

    public String getCurrentDestination(){
        String value = currentDestination.getAttribute("value");
        return value;
    }

    public boolean isSuggestionsVisible() {
        
        WebElement suggestion = wait.until(ExpectedConditions.visibilityOf(autoSuggestion));
        return suggestion.isDisplayed();
    }

    public void clickJourneyFrom() {
        journeyFrom.click();
    }

    public void clickJourneyTo() {
        journeyTo.click();
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

    public boolean isLogoDisplayed() {
        return redBusLogo.isDisplayed();
    }

    public boolean isJourneyFromDisplayed() {
        return journeyFrom.isDisplayed();
    }

    public boolean isJourneyToDisplayed() {
        return journeyTo.isDisplayed();
    }

    public boolean isCalendarButtonVisible() {
        return calendarButton.isDisplayed();
    }

    public void openCalendar() {
        isCalendarButtonVisible();
        clickCalendarButton();
        wait.until(ExpectedConditions.visibilityOf(datePickerPopup));
    }

    public void clickCalenderDay(int day){driver.findElement(By.xpath(
                    "//div[contains(@class,'calendarDate')]//span[text()='" + day + "']"))
            .click();
    }

    public boolean isDateEnabled(int day){
        WebElement dateElement = driver.findElement(By.xpath(
                "//div[contains(@class,'calendarDate')]//span[text()='" + day + "']"));
        
        return dateElement.isEnabled();
    }

    

    public void clickCalendarButton(){calendarButton.click();}

    public boolean isSearchButtonEnabled() {
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

    public void clickSwapButton(){
        swapButton.click();
    }

    public boolean isPageLoadedSuccessfully() {
        waitForPageToLoad();
        return isLogoDisplayed()
                && isJourneyFromDisplayed()
                && isJourneyToDisplayed()
                && isCalendarButtonVisible()
                && isSearchButtonEnabled()
                && driver.getTitle() != null
                && !driver.getTitle().isBlank();
    }

    public void waitForPageToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, PAGE_LOAD_TIMEOUT);
        wait.until(webDriver -> "complete".equals(
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState")));
        wait.until(ExpectedConditions.visibilityOf(redBusLogo));
    }

    public List<String> getNonClickableMajorElements() {
        Map<String, WebElement> majorElements = getMajorHomepageElements();
        List<String> nonClickableElements = new ArrayList<>();
        WebDriverWait wait = new WebDriverWait(driver, ELEMENT_CLICKABLE_TIMEOUT);

        for (Map.Entry<String, WebElement> entry : majorElements.entrySet()) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(entry.getValue()));
            } catch (Exception e) {
                nonClickableElements.add(entry.getKey());
            }
        }

        return nonClickableElements;
    }

    public boolean isemptySourcePopUpMessageDisplayed(){
        wait.until(ExpectedConditions.visibilityOf(emptySourcePopUpMessage));
        return emptySourcePopUpMessage.isDisplayed();
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

    @SuppressWarnings("unchecked")
    public List<String> getBrokenImageSources() throws IOException {

    scrollThroughPageToLoadLazyImages();

    List<WebElement> images = driver.findElements(By.tagName("img"));
    List<String> brokenImages = new ArrayList<>();

    for (WebElement image : images) {

        String src = image.getAttribute("src");

        if (src == null || src.isBlank()) {
            continue;
        }

        // Ignore embedded/local images
        if (!src.startsWith("http")) {
            continue;
        }

        HttpURLConnection connection =
                (HttpURLConnection) new URL(src).openConnection();

        connection.setRequestMethod("HEAD");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int responseCode = connection.getResponseCode();

        if (responseCode >= 400) {
            brokenImages.add(src + " (HTTP " + responseCode + ")");
        }

        connection.disconnect();
    }

    return brokenImages;
}


    private Map<String, WebElement> getMajorHomepageElements() {
        Map<String, WebElement> majorElements = new LinkedHashMap<>();
        majorElements.put("redBus logo", redBusLogo);
        majorElements.put("bus tickets option", busTicketBookingOption);
        majorElements.put("train tickets option", trainTicketBookingOption);
        majorElements.put("journey from field", journeyFrom);
        majorElements.put("journey to field", journeyTo);
        majorElements.put("journey date picker", calendarButton);
        majorElements.put("search buses button", searchBusesButton);
        majorElements.put("account button", accountButton);
        return majorElements;
    }

    private void scrollThroughPageToLoadLazyImages() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long pageHeight = ((Number) js.executeScript("return document.body.scrollHeight")).longValue();

        for (long position = 0; position <= pageHeight; position += 700) {
            js.executeScript("window.scrollTo(0, arguments[0]);", position);
        }

        js.executeScript("window.scrollTo(0, 0);");
    }

    public enum JourneyField { SOURCE, DESTINATION }

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



}