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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import components.SearchBarComponents;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {

    private static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(15);
    private static final Duration ELEMENT_CLICKABLE_TIMEOUT = Duration.ofSeconds(5);

    public final SearchBarComponents searchBar;

    public HomePage(WebDriver driver) {
        super(driver);
        this.searchBar = new SearchBarComponents(driver);
    }

    // ===========================
    // Header Locators
    // ===========================
    @FindBy(xpath = "//button[normalize-space()='Account']")
    public WebElement accountButton;

    @FindBy(xpath = "//button[@aria-label='Sign up']")
    public WebElement signInSignUpButton;

    @FindBy(xpath = "//button[normalize-space()='Log in']")
    public WebElement loginButton;

    @FindBy(xpath = "//img[@title='redBus']")
    public WebElement redBusLogo;

    // ===========================
    // Booking Locators
    // ===========================
    @FindBy(xpath = "//img[@title='Online Bus Tickets Booking']")
    public WebElement busTicketBookingOption;

    @FindBy(xpath = "//img[@title='Online Train Tickets Booking']")
    public WebElement trainTicketBookingOption;

    // ===========================
    // Message Locators
    // ===========================
    @FindBy(xpath = "//div[contains(@class,'snackbarprimary___66b04a') and contains(@class,'warning___a03877')]")
    public WebElement emptySourcePopUpMessage;

    // ===========================
    // Footer Locators
    // ===========================
    @FindBy(xpath = "//div[@class='footer']")
    public WebElement footer;

    @FindBy(xpath = "//h3[normalize-space()='Can I book a Government bus ticket on redBus?']")
    public WebElement faqSectionHeading;

    // ===========================
    // Page Load Methods
    // ===========================
    public void waitForPageToLoad() {
        WebDriverWait localWait = new WebDriverWait(driver, PAGE_LOAD_TIMEOUT);
        localWait.until(webDriver -> "complete".equals(
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState")));
        wait.until(ExpectedConditions.visibilityOf(redBusLogo));
    }

    public boolean isPageLoadedSuccessfully() {
        waitForPageToLoad();
        return isLogoDisplayed()
                && searchBar.isJourneyFromDisplayed()
                && searchBar.isJourneyToDisplayed()
                && searchBar.isCalendarButtonVisible()
                && searchBar.isSearchButtonEnabled()
                && driver.getTitle() != null
                && !driver.getTitle().isBlank();
    }

    public void scrollToFooter() {
        scrollToElement(faqSectionHeading);
    }

    // ===========================
    // Header Methods
    // ===========================
    public void clickAccountButton() {
        clickElement(accountButton);
    }

    public void clickSignInSignUpButton() {
        clickElement(signInSignUpButton);
    }

    public void clickLoginButton() {
        clickElement(loginButton);
    }

    public void clickRedBusLogo() {
        clickElement(redBusLogo);
    }

    // ===========================
    // Booking Methods
    // ===========================
    public void clickBusTicketBookingOption() {
        clickElement(busTicketBookingOption);
    }

    public void clickTrainTicketBookingOption() {
        clickElement(trainTicketBookingOption);
    }

    public void clickBookNowButton() {
        searchBar.clickBookNowButton();
    }

    // ===========================
    // Validation Methods
    // ===========================
    public boolean isSuggestionsVisible() {
        return searchBar.isSuggestionsVisible();
    }

    public boolean isLogoDisplayed() {
        return isElementDisplayed(redBusLogo);
    }

    public boolean isJourneyFromDisplayed() {
        return searchBar.isJourneyFromDisplayed();
    }

    public boolean isJourneyToDisplayed() {
        return searchBar.isJourneyToDisplayed();
    }

    public boolean isCalendarButtonVisible() {
        return searchBar.isCalendarButtonVisible();
    }

    public boolean isSearchButtonEnabled() {
        return searchBar.isSearchButtonEnabled();
    }

    public boolean isFooterDisplayed() {
        return isElementDisplayed(footer);
    }

    public boolean isLoginButtonDisplayed() {
        return isElementDisplayed(loginButton);
    }

    public boolean isAccountButtonDisplayed() {
        return isElementDisplayed(accountButton);
    }

    public boolean isemptySourcePopUpMessageDisplayed() {
        return isElementDisplayed(emptySourcePopUpMessage);
    }

    // ===========================
    // Utility Methods
    // ===========================
    public List<String> getNonClickableMajorElements() {
        Map<String, WebElement> majorElements = getMajorHomepageElements();
        List<String> nonClickableElements = new ArrayList<>();
        WebDriverWait waitLocal = new WebDriverWait(driver, ELEMENT_CLICKABLE_TIMEOUT);

        for (Map.Entry<String, WebElement> entry : majorElements.entrySet()) {
            try {
                waitLocal.until(ExpectedConditions.elementToBeClickable(entry.getValue()));
            } catch (Exception e) {
                nonClickableElements.add(entry.getKey());
            }
        }

        return nonClickableElements;
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

            HttpURLConnection connection
                    = (HttpURLConnection) new URL(src).openConnection();

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

    // ===========================
    // Private Helpers
    // ===========================
    private Map<String, WebElement> getMajorHomepageElements() {
        Map<String, WebElement> majorElements = new LinkedHashMap<>();
        majorElements.put("redBus logo", redBusLogo);
        majorElements.put("bus tickets option", busTicketBookingOption);
        majorElements.put("train tickets option", trainTicketBookingOption);
        majorElements.put("journey from field", searchBar.journeyFrom);
        majorElements.put("journey to field", searchBar.journeyTo);
        majorElements.put("journey date picker", searchBar.calendarButton);
        majorElements.put("search buses button", searchBar.searchBusesButton);
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

    // ===========================
    // Enum
    // ===========================
    public enum JourneyField {
        SOURCE, DESTINATION
    }

}