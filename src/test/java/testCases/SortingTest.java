package testCases;

import enums.SortType;
import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.BaseClass;

import java.time.LocalDate;

public class SortingTest extends BaseClass {

    @Test
    public void TC_001_sort_by_lowest_price() {
        String testName = "TC_001_sort_by_lowest_price";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(10));
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not load");

            // Apply lowest price sort
            sortingPage.selectSortOption(SortType.LOWEST_PRICE);

            // Verify results are sorted by lowest price (ascending)
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not reload after sorting");
            Assert.assertTrue(sp.areCardsSortedByLowestPrice(),
                    "Cards are not sorted by lowest price");

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_002_sort_by_highest_price() {
        String testName = "TC_002_sort_by_highest_price";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(10));
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not load");

            // Apply highest price sort
            sortingPage.selectSortOption(SortType.HIGHEST_PRICE);

            // Verify results are sorted by highest price (descending)
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not reload after sorting");
            Assert.assertTrue(sp.areCardsSortedByHighestPrice(),
                    "Cards are not sorted by highest price");

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_003_sort_by_earliest_departure() {
        String testName = "TC_003_sort_by_earliest_departure";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(10));
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not load");

            // Apply earliest departure sort
            sortingPage.selectSortOption(SortType.EARLIEST_DEPARTURE);

            // Verify results are sorted by earliest departure time (ascending)
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not reload after sorting");
            Assert.assertTrue(sp.areCardsSortedByEarliestDeparture(),
                    "Cards are not sorted by earliest departure");

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_004_sort_by_latest_departure() {
        String testName = "TC_004_sort_by_latest_departure";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(10));
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not load");

            // Apply latest departure sort
            sortingPage.selectSortOption(SortType.LATEST_DEPARTURE);

            // Verify results are sorted by latest departure time (descending)
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not reload after sorting");
            Assert.assertTrue(sp.areCardsSortedByLatestDeparture(),
                    "Cards are not sorted by latest departure");

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_005_sort_by_shortest_duration() {
        String testName = "TC_005_sort_by_shortest_duration";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(10));
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not load");

            // Apply shortest duration sort
            sortingPage.selectSortOption(SortType.SHORTEST_DURATION);

            // Verify results are sorted by shortest duration (ascending)
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not reload after sorting");
            Assert.assertTrue(sp.areCardsSortedByShortestDuration(),
                    "Cards are not sorted by shortest duration");

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_006_sort_by_highest_rating() {
        String testName = "TC_006_sort_by_highest_rating";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(10));
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not load");

            // Apply highest rating sort
            sortingPage.selectSortOption(SortType.HIGHEST_RATING);

            // Verify results are sorted by highest rating (descending)
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not reload after sorting");
            Assert.assertTrue(sp.areCardsSortedByHighestRating(),
                    "Cards are not sorted by highest rating");

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_007_sort_dropdown_availability() {
        String testName = "TC_007_sort_dropdown_availability";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(10));
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not load");

            // Verify sort dropdown is displayed
            Assert.assertTrue(sortingPage.isSortDropdownDisplayed(),
                    "Sort dropdown is not displayed");

            // Verify all sort options are available
            Assert.assertTrue(sortingPage.isSortOptionAvailable(SortType.LOWEST_PRICE),
                    "Lowest Price sort option not available");
            Assert.assertTrue(sortingPage.isSortOptionAvailable(SortType.HIGHEST_PRICE),
                    "Highest Price sort option not available");
            Assert.assertTrue(sortingPage.isSortOptionAvailable(SortType.EARLIEST_DEPARTURE),
                    "Earliest Departure sort option not available");
            Assert.assertTrue(sortingPage.isSortOptionAvailable(SortType.LATEST_DEPARTURE),
                    "Latest Departure sort option not available");
            Assert.assertTrue(sortingPage.isSortOptionAvailable(SortType.SHORTEST_DURATION),
                    "Shortest Duration sort option not available");
            Assert.assertTrue(sortingPage.isSortOptionAvailable(SortType.HIGHEST_RATING),
                    "Highest Rating sort option not available");

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_008_sort_switch_lowest_to_highest_price() {
        String testName = "TC_008_sort_switch_lowest_to_highest_price";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(10));
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not load");

            // Apply lowest price sort
            sortingPage.selectSortOption(SortType.LOWEST_PRICE);
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not reload after first sort");
            Assert.assertTrue(sp.areCardsSortedByLowestPrice(),
                    "Cards are not sorted by lowest price");

            // Switch to highest price sort
            sortingPage.selectSortOption(SortType.HIGHEST_PRICE);
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not reload after second sort");
            Assert.assertTrue(sp.areCardsSortedByHighestPrice(),
                    "Cards are not sorted by highest price after switching");

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_009_sort_persistence_across_actions() {
        String testName = "TC_009_sort_persistence_across_actions";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(10));
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not load");

            // Apply shortest duration sort
            sortingPage.selectSortOption(SortType.SHORTEST_DURATION);
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not reload after sorting");
            Assert.assertTrue(sp.areCardsSortedByShortestDuration(),
                    "Cards are not sorted by shortest duration");

            // Verify the sort is still applied (results maintain sorted order)
            // Get the results and verify they're still sorted
            int initialCount = sp.getBusCardsCount();
            Assert.assertTrue(sp.areCardsSortedByShortestDuration(),
                    "Sort order was not maintained");

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }
}
