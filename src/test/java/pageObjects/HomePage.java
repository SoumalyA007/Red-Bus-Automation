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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {

    private static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(15);
    private static final Duration ELEMENT_CLICKABLE_TIMEOUT = Duration.ofSeconds(5);

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
    public WebElement journeyDate;

    @FindBy(xpath = "//button[normalize-space()='Search buses']")
    public WebElement searchBusesButton;

    @FindBy(xpath = "//button[normalize-space()='Book now']")
    public WebElement bookNowButton;

    @FindBy(xpath = "//img[@title='redBus']")
    public WebElement redBusLogo;

    @FindBy(xpath = "//div[@class='footer']")
    public WebElement footer;

    @FindBy(xpath = "//div[contains(@class,'suggestionsWrapper___')]")
    public WebElement autoSuggestion;

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

    public String getJourneyFrom() {
        WebElement element = driver.findElement(By.xpath("//input[@id='srcinput']"));
        return element.getAttribute("value");
    }

    public String getJourneyTo() {
        WebElement element = driver.findElement(By.xpath("//input[@id='destinput']"));
        return element.getAttribute("value");
    }


    public void clickJourneyFrom() {
        journeyFrom.click();
    }

    public void clickJourneyTo() {
        journeyTo.click();
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