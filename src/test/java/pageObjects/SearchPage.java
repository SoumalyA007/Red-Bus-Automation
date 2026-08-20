package pageObjects;

import enums.Sorting;
import enums.TimeWindow;
import models.BusCard;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.IntStream;

public class SearchPage extends BasePage {

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    // ===========================
    // Route Locator
    // ===========================
    @FindBy(xpath = "//span[@role='text']")
    WebElement route;

    // ===========================
    // Route Methods
    // ===========================
    public String[] getRoute() {
        return route.getText().split(" ");
    }

    // ===========================
    // Bus Card Locators
    // ===========================
    @FindBy(xpath = "//div[@data-autoid='inv-wrap']")
    WebElement parentBusCard;

    @FindBy(xpath = "//li[contains(@class,'tupleWrapper')]")
    List<WebElement> busCards;

    @FindBy(xpath = "//div[contains(@class,'travelsName___')]")
    List<WebElement> busOperatorList;

    // XPath of the close/hide button on an already-expanded seat panel
    private static final By CLOSE_SEAT_PANEL_BTN = By.xpath(
            "//div[contains(@class,'topNavigationContainer__') and contains(@class,'border')]//div[contains(@class,'actionWrap___')]");

    // ===========================
    // Message Locators
    // ===========================
    @FindBy(xpath = "//div[contains(@class,'oopsInfoContainer___')]//p[contains(@class,'description___')]")
    WebElement noRouteMessage;

    // ===========================
    // Bus Cards Methods
    // ===========================
    public boolean waitForBusCardsToLoad() {
        try {
            wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements(parentBusCard));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public BusCard extractCardDetails(WebElement card) {
        String operator = card.findElement(By.xpath(".//div[contains(@class,'travelsName___')]")).getText().trim();
        String boardingTime = card.findElement(By.xpath(".//p[contains(@class,'boardingTime___')]")).getText().trim();
        String droppingTime = card.findElement(By.xpath(".//p[contains(@class,'droppingTime___')]")).getText().trim();
        String duration = card.findElement(By.xpath(".//p[contains(@class,'duration___')]")).getText().trim();
        String busType = card.findElement(By.xpath(".//p[contains(@class,'busType___')]")).getText().trim();

        String seatsText = card.findElement(By.xpath(".//p[contains(@class,'totalSeats___')]")).getText();
        int totalSeats = Integer.parseInt(seatsText.replaceAll("[^0-9]", ""));

        String priceText = card.findElement(By.xpath(".//p[contains(@class,'finalFare___')]")).getText();
        double price = Double.parseDouble(priceText.replaceAll("[^0-9.]", ""));

        List<WebElement> ratingEls = card.findElements(By.xpath(".//div[contains(@class,'rating___')]"));
        double rating = ratingEls.isEmpty() ? -1 : Double.parseDouble(ratingEls.get(0).getText().trim());

        return new BusCard(operator, boardingTime, droppingTime, duration, totalSeats, price, rating, busType);
    }

    public List<BusCard> getAllBusCardDetails() {
        List<BusCard> result = new ArrayList<>();
        for (WebElement card : busCards) {
            result.add(extractCardDetails(card));
        }
        return result;
    }

    public boolean allCardsMatchBoardingWindow(TimeWindow window) {
        return getAllBusCardDetails().stream()
                .allMatch(bus -> bus.isBoardingWithinWindow(window));
    }

    public boolean allCardsMatchDroppingWindow(TimeWindow window) {
        return getAllBusCardDetails().stream()
                .allMatch(bus -> bus.isDroppingWithinWindow(window));
    }

    private static String normalizeText(String s) {
        return s.toLowerCase().replaceAll("[/\\-\\s]+", "");
    }

    public boolean allCardsMatchBusType(String expectedTypeFragment) {
        String normalizedExpected = normalizeText(expectedTypeFragment);
        return getAllBusCardDetails().stream()
                .allMatch(bus -> normalizeText(bus.getBusType()).contains(normalizedExpected));
    }

    public boolean allCardsMatchOperator(String operatorFragment) {
        String normalizedExpected = normalizeText(operatorFragment);
        return getAllBusCardDetails().stream()
                .allMatch(bus -> normalizeText(bus.getOperator()).contains(normalizedExpected));
    }

    public boolean allCardsMeetMinRating(double minRating) {
        return getAllBusCardDetails().stream()
                .filter(bus -> bus.getRating() >= 0)
                .allMatch(bus -> bus.getRating() >= minRating);
    }

    public boolean allCardsWithinPriceRange(double min, double max) {
        return getAllBusCardDetails().stream()
                .allMatch(bus -> bus.getPrice() >= min && bus.getPrice() <= max);
    }

    public int getBusCardsCount() {
        return busCards.size();
    }

    public boolean areBusCardsDisplayed() {
        return !busCards.isEmpty() && busCards.stream().allMatch(WebElement::isDisplayed);
    }

    public int getBusOperatorCount() {
        return busOperatorList.size();
    }

    public <T extends Comparable<T>> boolean isSorted(
            List<BusCard> buses,
            Function<BusCard, T> extractor,
            Sorting order) {

        for (int i = 0; i < buses.size() - 1; i++) {

            T current = extractor.apply(buses.get(i));
            T next = extractor.apply(buses.get(i + 1));

            int comparison = current.compareTo(next);

            if (order == Sorting.ASCENDING && comparison > 0) {
                return false;
            }

            if (order == Sorting.DESCENDING && comparison < 0) {
                return false;
            }
        }

        return true;
    }

    public void clickViewSeatsButtonForCard(int cardIndex) {
        if (busCards.isEmpty()) {
            throw new IllegalStateException(
                    "No bus cards found on the search results page. " +
                            "The route may have no available buses, or the page did not finish loading.");
        }
        if (cardIndex >= busCards.size()) {
            throw new IndexOutOfBoundsException(
                    "Requested card index " + cardIndex + " but only " + busCards.size()
                            + " bus card(s) are available.");
        }
        WebElement card = busCards.get(cardIndex);
        WebElement viewSeatsButton = card
                .findElement(By.xpath(".//button[@type='button' and contains(@class,'viewSeatsBtn___')]"));
        clickElement(viewSeatsButton);
    }

    public void clickViewSeatsButtonByOperator(String operatorName) {

        String normalizedOperatorName = normalizeText(operatorName);
        List<BusCard> allBuses = getAllBusCardDetails();

        int matchIndex = IntStream.range(0, allBuses.size())
                .filter(i -> normalizeText(allBuses.get(i).getOperator()).contains(normalizedOperatorName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Operator not found:" + normalizedOperatorName));

        clickViewSeatsButtonForCard(matchIndex);

    }

    // ===========================
    // Panel Management
    // ===========================

    /**
     * Returns the count of boarding points visible in the currently-open seat panel.
     * Call only after clickViewSeatsButtonForCard() has been invoked.
     */
    public int getOpenPanelBoardingPointCount() {
        By boardingPoint = By.xpath(
                "//ul[@aria-label='Boarding points']//li[contains(@class,'bpdpListRow___') and @role='listitem']");
        try {
            // Use a short wait — if the boarding point list doesn't appear quickly, the panel
            // may not have a boarding/dropping section at all (single-point auto-select).
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            // First click the boarding/dropping button if it is present
            By bdpBtn = By.xpath("//button[normalize-space()='Select boarding & dropping points']");
            List<WebElement> btnList = driver.findElements(bdpBtn);
            if (!btnList.isEmpty()) {
                clickElement(btnList.get(0));
            }
            List<WebElement> points = shortWait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(boardingPoint));
            return points.size();
        } catch (TimeoutException e) {
            return 0;
        }
    }

    /**
     * Returns the count of dropping points visible in the currently-open seat panel.
     * Call only after clickViewSeatsButtonForCard() has been invoked.
     */
    public int getOpenPanelDroppingPointCount() {
        By droppingPoint = By.xpath(
                "//ul[@aria-label='Dropping points']//li[contains(@class,'bpdpListRow___') and @role='listitem']");
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            List<WebElement> points = shortWait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(droppingPoint));
            return points.size();
        } catch (TimeoutException e) {
            return 0;
        }
    }

    /**
     * Closes the currently-expanded seat panel by clicking the same "View Seats" button
     * (which acts as a toggle on RedBus). Safe to call even if no panel is open.
     */
    public void closeOpenSeatPanel() {
        List<WebElement> closeButtons = driver.findElements(CLOSE_SEAT_PANEL_BTN);
        if (!closeButtons.isEmpty()) {
            clickElement(closeButtons.get(0));
        }
    }

    // ===========================
    // Message Methods
    // ===========================
    public boolean isNoRouteMessageDisplayed() {
        return isElementDisplayed(noRouteMessage);
    }
}