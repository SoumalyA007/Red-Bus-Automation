package pageObjects;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {

    private static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(15);
    private static final Duration ELEMENT_CLICKABLE_TIMEOUT = Duration.ofSeconds(5);

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[normalize-space()='Account']")
    private WebElement accountButton;

    @FindBy(xpath = "//button[@aria-label='Sign up']")
    private WebElement signInSignUpButton;

    @FindBy(xpath = "//button[normalize-space()='Log in']")
    private WebElement loginButton;

    @FindBy(xpath = "//img[@title='Online Bus Tickets Booking']")
    private WebElement busTicketBookingOption;

    @FindBy(xpath = "//img[@title='Online Train Tickets Booking']")
    private WebElement trainTicketBookingOption;

    @FindBy(css = "div[class='inputAndSwapWrapper___9a5930'] div[class='srcDestWrapper___e85828 ']")
    private WebElement journeyFrom;

    @FindBy(css = "body > div:nth-child(4) > main:nth-child(4) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > search:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2)")
    private WebElement journeyTo;

    @FindBy(xpath = "//div[@aria-label='Select date of journey']")
    private WebElement journeyDate;

    @FindBy(xpath = "//button[normalize-space()='Search buses']")
    private WebElement searchBusesButton;

    @FindBy(xpath = "//button[normalize-space()='Book now']")
    private WebElement bookNowButton;

    @FindBy(xpath = "//img[@title='redBus']")
    private WebElement redBusLogo;

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

    public void setJourneyFrom(String from) {
        journeyFrom.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement sourceInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@id='srcinput']")));
        sourceInput.clear();
        sourceInput.sendKeys(from);
        // Click the first autocomplete suggestion so the city gets committed to the div
        WebElement firstSuggestion = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".srcDestWrapper___e85828.focused___4f7642")));
        firstSuggestion.click();
    }

    public void setJourneyTo(String to) {
        journeyTo.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement destinationInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@id='destinput']")));
        destinationInput.clear();
        destinationInput.sendKeys(to);
        // Click the first autocomplete suggestion so the city gets committed to the div
        WebElement firstSuggestion = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".srcDestWrapper___e85828.focused___4f7642")));
        firstSuggestion.click();
    }

    public String getJourneyFrom() {
        return journeyFrom.getAttribute("value");
    }

    public String getJourneyTo() {
        return journeyTo.getAttribute("value");
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

    public boolean isLogoDisplayed() {
        return redBusLogo.isDisplayed();
    }

    public boolean isJourneyFromDisplayed() {
        return journeyFrom.isDisplayed();
    }

    public boolean isJourneyToDisplayed() {
        return journeyTo.isDisplayed();
    }

    public boolean isCalendarVisible() {
        return journeyDate.isDisplayed();
    }

    public boolean isCalenderVisible() {
        return isCalendarVisible();
    }

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

    public boolean isPageLoadedSuccessfully() {
        waitForPageToLoad();
        return isLogoDisplayed()
                && isJourneyFromDisplayed()
                && isJourneyToDisplayed()
                && isCalendarVisible()
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

    @SuppressWarnings("unchecked")
    public List<String> getBrokenImageSources() {
        scrollThroughPageToLoadLazyImages();

        return (List<String>) ((JavascriptExecutor) driver).executeScript(
                "return Array.from(document.images)"
                        + ".filter(img => img.src && (!img.complete || img.naturalWidth === 0 || img.naturalHeight === 0))"
                        + ".map(img => img.currentSrc || img.src);");
    }

    private Map<String, WebElement> getMajorHomepageElements() {
        Map<String, WebElement> majorElements = new LinkedHashMap<>();
        majorElements.put("redBus logo", redBusLogo);
        majorElements.put("bus tickets option", busTicketBookingOption);
        majorElements.put("train tickets option", trainTicketBookingOption);
        majorElements.put("journey from field", journeyFrom);
        majorElements.put("journey to field", journeyTo);
        majorElements.put("journey date picker", journeyDate);
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

}
