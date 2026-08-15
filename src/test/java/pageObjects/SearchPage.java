package pageObjects;

import enums.Sorting;
import enums.TimeWindow;
import models.BusCard;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.IntStream;

public class SearchPage extends BasePage {

    public SearchPage(WebDriver driver){
        super(driver);
    }

    // ===========================
    // Route Locator
    // ===========================
    @FindBy(xpath="//span[@role='text']")
    WebElement route;

    // ===========================
    // Route Methods
    // ===========================
    public String[] getRoute(){
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

    public int getBusOperatorCount(){
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
        WebElement card = busCards.get(cardIndex);
        WebElement viewSeatsButton = card.findElement(By.xpath(".//button[@type='button' and contains(@class,'viewSeatsBtn___')]"));
        clickElement(viewSeatsButton);
    }

    public void clickViewSeatsButtonByOperator(String operatorName) {

        String normalizedOperatorName  = normalizeText(operatorName);
        List<BusCard> allBuses = getAllBusCardDetails();

        int matchIndex = IntStream.range(0, allBuses.size())
                .filter(i -> normalizeText(allBuses.get(i).getOperator()).contains(normalizedOperatorName))
                .findFirst()
                .orElseThrow(()->new NoSuchElementException("Operator not found:" + normalizedOperatorName));

        clickViewSeatsButtonForCard(matchIndex);

    }

    // ===========================
    // Message Methods
    // ===========================
    public boolean isNoRouteMessageDisplayed(){
        return isElementDisplayed(noRouteMessage);
    }
}