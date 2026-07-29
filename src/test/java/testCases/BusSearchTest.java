package testCases;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.testng.Assert;
import org.testng.annotations.Test;

import testBase.BaseClass;

public class BusSearchTest extends BaseClass {

    @Test
    public void TC_001_verify_search_results_displayed_correctly() {
        String testName = "TC_001_verify_search_results_displayed_correctly";
        try {
            LocalDate journeyDate = LocalDate.now().plusDays(10);
            helper.searchBuses("Kolkata", "Burdwan", journeyDate);
            hp.isLogoDisplayed();

            // Route
            String[] routes = sp.getRoute();
            Assert.assertEquals(routes[0], "Kolkata", "Your source is not correct");
            Assert.assertEquals(routes[1], "Burdwan", "Your destination is not correct");

            // Bus cards
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not load");
            Assert.assertTrue(sp.getBusCardsCount() > 0, "No bus card found");
            log.info("Number of cards displayed: {}", sp.getBusCardsCount());
            Assert.assertTrue(sp.areBusCardsDisplayed(), "All bus cards not displayed");

            // Date
            String expectedDate = journeyDate.format(DateTimeFormatter.ofPattern("dd MMM, yyyy"));
            Assert.assertEquals(searchbarcomponents.getSelectedDate(), expectedDate, "Journey date is not correct");

            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_002_verify_invalid_search_results_displayed_no_data(){
        String testName = "TC_002_verify_invalid_search_results_displayed_no_data";
        try{
            LocalDate date = LocalDate.now().plusDays(5);
            helper.searchBuses("Burdwan","Bikaner",date);
            Assert.assertTrue(sp.isNoRouteMessageDisplayed(),"No Route Message is not displayed");
            Assert.assertTrue(sp.getBusCardsCount()==0,"Multiple cards displayed in no routes");
        }catch (Throwable e){
            logTestFailure(testName,e);
        }

    }
}