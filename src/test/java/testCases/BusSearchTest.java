package testCases;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import models.BusCard;
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

    @Test
    public void TC_002_bus_cards_displayed() {
        String testName = "TC_002_bus_cards_displayed";
        try {
            helper.searchBuses("Kolkata", "Burdwan",LocalDate.now().plusDays(10));

            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not load");
            Assert.assertTrue(sp.getBusCardsCount() > 0, "No bus cards found");

            List<BusCard> cards = sp.getAllBusCardDetails();
            log.info("Cards found: " + cards);   // this is where toString() from before earns its keep

            for (BusCard bus : cards) {
                Assert.assertFalse(bus.getOperator().isEmpty(), "Operator missing for: " + bus);
                Assert.assertFalse(bus.getDepartureTime().isEmpty(), "Departure time missing for: " + bus);
                Assert.assertFalse(bus.getArrivalTime().isEmpty(), "Arrival time missing for: " + bus);
                Assert.assertFalse(bus.getDuration().isEmpty(), "Duration missing for: " + bus);
                Assert.assertTrue(bus.getSeatsLeft() >= 0, "Seats left invalid for: " + bus);
                Assert.assertTrue(bus.getPrice() > 0, "Price invalid for: " + bus);
                Assert.assertFalse(bus.getBusType().isEmpty(), "Bus type missing for: " + bus);
                // rating can legitimately be absent (-1) for a new operator — assert separately if you want it mandatory
            }

            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }
}