package testCases;

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

            searchresultsfilterpage.selectBusType(SearchResultsFilterPage.BusType.AC);
            searchresultsfilterpage.waitForFiltersToApply();

            Assert.assertTrue(sp.getBusCardsCount() > 0, "No buses matched AC filter");
            Assert.assertTrue(sp.allCardsMatchBusType("AC"), "Non-AC bus found after filtering by AC");
            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }
}
