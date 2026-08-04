package testCases;

import enums.FilterChoice;
import enums.FilterHeaders;
import enums.TimeWindow;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.SearchResultsFilterPage;
import testBase.BaseClass;

import java.time.LocalDate;

public class FilterTest extends BaseClass {

    @Test
    public void TC_001_bus_type_filter_AC() {
        String testName = "TC_001_bus_type_filter_AC";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(10));
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not load");

            WebElement selected_checkbox = searchresultsfilterpage.selectFilterOption(FilterHeaders.BUS_TYPE,
                    FilterChoice.AC);
            Assert.assertTrue(searchresultsfilterpage.isFilterSelected(selected_checkbox),
                    "Intended Filter Not Selected");

            // Verify results: every card returned must be an AC bus
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not reload after AC filter");
            Assert.assertTrue(sp.allCardsMatchBusType("AC"), "Non-AC bus displayed after AC filter was applied");

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_002_departure_time_filter_morning() {
        String testName = "TC_002_departure_time_filter_morning";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(10));
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not load");

            WebElement selected_checkbox = searchresultsfilterpage.selectFilterOption(FilterHeaders.DEPARTURE_TIME,
                    FilterChoice.MORNING);
            Assert.assertTrue(searchresultsfilterpage.isFilterSelected(selected_checkbox),
                    "Intended Filter Not Selected");

            // Verify results: every card's boarding time must fall within the morning
            // window (06:00–12:00)
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not reload after Morning filter");
            Assert.assertTrue(sp.allCardsMatchBoardingWindow(TimeWindow.MORNING),
                    "Non-morning departure shown after Morning filter was applied");

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_003_dropoff_time_filter_afternoon() {
        String testName = "TC_003_dropoff_time_filter_afternoon";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not load");

            WebElement selected_checkbox = searchresultsfilterpage.selectFilterOption(FilterHeaders.ARRIVAL_TIME,
                    FilterChoice.AFTERNOON);
            Assert.assertTrue(searchresultsfilterpage.isFilterSelected(selected_checkbox),
                    "Intended Filter Not Selected");

            // Verify results: every card's drop-off (arrival) time must fall within the
            // afternoon window (12:00–17:00)
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not reload after Afternoon filter");
            Assert.assertTrue(sp.allCardsMatchDroppingWindow(TimeWindow.AFTERNOON),
                    "Non-afternoon arrival shown after Afternoon filter was applied");

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_004_single_window_seater_sleeper_filter_single_seats() {
        String testName = "TC_004_single_window_seater_sleeper_filter_single_seats";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not load");

            WebElement selected_checkbox = searchresultsfilterpage
                    .selectFilterOption(FilterHeaders.SINGLE_WINDOW_SEATER_SLEEPER, FilterChoice.SINGLE_SEATS);
            Assert.assertTrue(searchresultsfilterpage.isFilterSelected(selected_checkbox),
                    "Intended Filter Not Selected");

            // Verify results: "Single Seats" is a seat-layout filter — the card's bus type
            // text does not
            // directly expose this. We verify the filter produced a non-empty, properly
            // loaded result set.
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not reload after Single Seats filter");
            Assert.assertTrue(sp.getBusCardsCount() > 0, "No results shown after Single Seats filter was applied");

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_005_single_ac_morning_wifi_filter() {
        String testName = "TC_005_single_ac_morning_wifi_filter";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not load");

            // Apply AC filter and verify checkbox + card results
            WebElement selected_checkbox_ac = searchresultsfilterpage.selectFilterOption(FilterHeaders.BUS_TYPE,
                    FilterChoice.AC);
            Assert.assertTrue(searchresultsfilterpage.isFilterSelected(selected_checkbox_ac),
                    "Intended Filter AC Not Selected");
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not reload after AC filter");
            Assert.assertTrue(sp.allCardsMatchBusType("AC"), "Non-AC bus displayed after AC filter was applied");

            // Apply Morning departure filter and verify checkbox + card results
            WebElement selected_checkbox_morning = searchresultsfilterpage
                    .selectFilterOption(FilterHeaders.DEPARTURE_TIME, FilterChoice.MORNING);
            Assert.assertTrue(searchresultsfilterpage.isFilterSelected(selected_checkbox_morning),
                    "Intended Filter MORNING Not Selected");
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not reload after Morning filter");
            Assert.assertTrue(sp.allCardsMatchBoardingWindow(TimeWindow.MORNING),
                    "Non-morning departure shown after Morning filter was applied");

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_006_search_operator() {
        String testName = "TC_006_search_operator";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not load");

            searchresultsfilterpage.searchAndSelectFilterOption(FilterHeaders.BUS_OPERATOR, "PA");
            searchresultsfilterpage.clickViewAllOptions(FilterHeaders.BUS_OPERATOR);
            Assert.assertTrue(searchresultsfilterpage.isFilterSelected(FilterHeaders.BUS_OPERATOR, "PA"),
                    "Checkbox not selected");

            // Verify results: every card's operator name must contain the searched text
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not reload after Operator filter");
            Assert.assertTrue(sp.allCardsMatchOperator("PA"),
                    "Card with non-matching operator shown after Operator filter was applied");

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

}
