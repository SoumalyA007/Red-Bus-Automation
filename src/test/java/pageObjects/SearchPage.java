package pageObjects;

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
            wait.until(ExpectedConditions.visibilityOfAllElements(parentBusCard));
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

    // Normalizes a bus-type or operator string for loose matching:
    // strips /, -, and whitespace then lowercases, so "A/C" and "AC" both
    // become "ac" and a simple .contains() comparison works correctly.
    private static String normalizeText(String s) {
        return s.toLowerCase().replaceAll("[/\\-\\s]+", "");
    }

    // Verifies every visible card's bus type contains the expected fragment
    // after normalization (e.g. "AC" matches cards showing "A/C Seater (2+2)").
    public boolean allCardsMatchBusType(String expectedTypeFragment) {
        String normalizedExpected = normalizeText(expectedTypeFragment);
        return getAllBusCardDetails().stream()
                .allMatch(bus -> normalizeText(bus.getBusType()).contains(normalizedExpected));
    }

    // Verifies every visible card's operator name contains the given fragment.
    // Used after applying a Bus Operator filter to confirm the results changed.
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

    // ===========================
    // Sorting Verification Methods
    // ===========================

    public boolean areCardsSortedByLowestPrice() {
        List<BusCard> cards = getAllBusCardDetails();
        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).getPrice() > cards.get(i + 1).getPrice()) {
                return false;
            }
        }
        return true;
    }

    public boolean areCardsSortedByHighestPrice() {
        List<BusCard> cards = getAllBusCardDetails();
        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).getPrice() < cards.get(i + 1).getPrice()) {
                return false;
            }
        }
        return true;
    }

    public boolean areCardsSortedByEarliestDeparture() {
        List<BusCard> cards = getAllBusCardDetails();
        for (int i = 0; i < cards.size() - 1; i++) {
            if (compareTime(cards.get(i).getDepartureTime(), cards.get(i + 1).getDepartureTime()) > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean areCardsSortedByLatestDeparture() {
        List<BusCard> cards = getAllBusCardDetails();
        for (int i = 0; i < cards.size() - 1; i++) {
            if (compareTime(cards.get(i).getDepartureTime(), cards.get(i + 1).getDepartureTime()) < 0) {
                return false;
            }
        }
        return true;
    }

    public boolean areCardsSortedByShortestDuration() {
        List<BusCard> cards = getAllBusCardDetails();
        for (int i = 0; i < cards.size() - 1; i++) {
            if (compareDuration(cards.get(i).getDuration(), cards.get(i + 1).getDuration()) > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean areCardsSortedByHighestRating() {
        List<BusCard> cards = getAllBusCardDetails();
        for (int i = 0; i < cards.size() - 1; i++) {
            double currentRating = cards.get(i).getRating();
            double nextRating = cards.get(i + 1).getRating();
            // Handle cases where rating might be -1 (not available)
            if (currentRating >= 0 && nextRating >= 0) {
                if (currentRating < nextRating) {
                    return false;
                }
            }
        }
        return true;
    }

    // Helper method to compare time strings (HH:MM format)
    private int compareTime(String time1, String time2) {
        try {
            String[] parts1 = time1.trim().split(":");
            String[] parts2 = time2.trim().split(":");

            int hour1 = Integer.parseInt(parts1[0]);
            int minute1 = parts1.length > 1 ? Integer.parseInt(parts1[1]) : 0;

            int hour2 = Integer.parseInt(parts2[0]);
            int minute2 = parts2.length > 1 ? Integer.parseInt(parts2[1]) : 0;

            if (hour1 != hour2) {
                return Integer.compare(hour1, hour2);
            }
            return Integer.compare(minute1, minute2);
        } catch (Exception e) {
            return 0;
        }
    }

    // Helper method to compare duration strings (H:MM format or HHh MMm format)
    private int compareDuration(String duration1, String duration2) {
        try {
            int minutes1 = parseDurationToMinutes(duration1);
            int minutes2 = parseDurationToMinutes(duration2);
            return Integer.compare(minutes1, minutes2);
        } catch (Exception e) {
            return 0;
        }
    }

    // Convert duration string to total minutes
    private int parseDurationToMinutes(String duration) {
        try {
            // Handle formats like "12h 30m", "2h 15m", "45m", "1:30"
            String cleaned = duration.toLowerCase().trim();

            if (cleaned.contains("h")) {
                String[] parts = cleaned.split("h");
                int hours = Integer.parseInt(parts[0].trim());
                int minutes = 0;

                if (parts.length > 1 && parts[1].contains("m")) {
                    minutes = Integer.parseInt(parts[1].replaceAll("[^0-9]", "").trim());
                }

                return hours * 60 + minutes;
            } else if (cleaned.contains("m")) {
                return Integer.parseInt(cleaned.replaceAll("[^0-9]", ""));
            } else if (cleaned.contains(":")) {
                String[] parts = cleaned.split(":");
                int hours = Integer.parseInt(parts[0]);
                int minutes = Integer.parseInt(parts[1]);
                return hours * 60 + minutes;
            }

            return Integer.parseInt(cleaned.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return Integer.MAX_VALUE;
        }
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




    // ===========================
    // Message Methods
    // ===========================
    public boolean isNoRouteMessageDisplayed(){
        return noRouteMessage.isDisplayed();
    }

    

}
