package utils;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import components.SearchBarComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjects.HomePage;
import components.SearchBarComponents.JourneyField;
import pageObjects.SearchPage;

public class HelperFunctions {

    private final HomePage hp;
    private final SearchPage sp;
    private final SearchBarComponents searchbarcomponents;

    public HelperFunctions(WebDriver driver, WebDriverWait wait) {
        this.hp = new HomePage(driver);
        this.sp = new SearchPage(driver);
        this.searchbarcomponents = new SearchBarComponents(driver);

    }

    public void enter_source(String source) {
        searchbarcomponents.selectJourneyCity(JourneyField.SOURCE, source);
    }

    public void enter_destination(String destination) {
        searchbarcomponents.selectJourneyCity(JourneyField.DESTINATION, destination);
    }

    public void selectCalendarDate(LocalDate targetDate) {
        searchbarcomponents.openCalendar();

        String targetMonth = targetDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        searchbarcomponents.navigateCalendarTo(targetMonth, targetDate.getYear());
        //searchbarcomponents.clickCalenderDay(targetDate.getDayOfMonth());
        searchbarcomponents.clickCalenderDay(targetDate);

    }

    public void searchBuses(String source,String destination,LocalDate date){

        enter_source(source);
        enter_destination(destination);

        selectCalendarDate(date);
        searchbarcomponents.isSearchButtonClickable();
        searchbarcomponents.clickSearchBusesButton();
        sp.waitForBusCardsToLoad();

    }

    /**
     * Iterates through all loaded bus cards and clicks "View Seats" on the first card
     * that has at least {@code minBoarding} boarding points AND at least {@code minDropping}
     * dropping points.
     *
     * <p>This eliminates the need to manually check card indices. RedBus auto-selects points
     * when only one option exists, so tests that rely on choosing a boarding/dropping point
     * must use a card that truly has multiple options. This method finds that card for you.</p>
     *
     * @param minBoarding minimum number of boarding points required (use 2 to ensure user choice)
     * @param minDropping minimum number of dropping points required (use 2 to ensure user choice)
     * @throws java.util.NoSuchElementException if no card on the current results page satisfies
     *                                          the criteria
     */
    public void clickViewSeatsForCardWithMinPoints(int minBoarding, int minDropping) {
        int total = sp.getBusCardsCount();
        if (total == 0) {
            throw new java.util.NoSuchElementException(
                    "No bus cards found on the search results page. " +
                    "The route may have no available buses, or the page did not finish loading.");
        }

        for (int i = 0; i < total; i++) {
            // Open this card's seat panel
            sp.clickViewSeatsButtonForCard(i);

            // Peek at how many boarding/dropping points it has
            int boardingCount = sp.getOpenPanelBoardingPointCount();
            int droppingCount = sp.getOpenPanelDroppingPointCount();

            if (boardingCount >= minBoarding && droppingCount >= minDropping) {
                // This card qualifies — leave the panel open for the test to use
                return;
            }

            // Not enough points — close the panel and try the next card
            sp.closeOpenSeatPanel();
        }

        throw new java.util.NoSuchElementException(
                "No bus card found with at least " + minBoarding + " boarding point(s) and " +
                minDropping + " dropping point(s) out of " + total + " card(s) on the page.");
    }

    /**
     * Convenience overload: finds a card with at least {@code minBoarding} boarding points
     * and at least 1 dropping point.
     */
    public void clickViewSeatsForCardWithMinBoardingPoints(int minBoarding) {
        clickViewSeatsForCardWithMinPoints(minBoarding, 1);
    }
}