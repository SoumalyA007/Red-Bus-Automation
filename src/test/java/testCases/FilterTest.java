package testCases;

import enums.FilterChoice;
import enums.FilterHeaders;
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
            WebElement selected_checkbox = searchresultsfilterpage.selectFilterOption(FilterHeaders.BUS_TYPE, FilterChoice.AC);
            Assert.assertTrue(searchresultsfilterpage.isFilterSelected(selected_checkbox),"Intended Filter Not Selected");

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_002_departure_time_filter_morning(){
        String testName = "TC_002_departure_time_filter_morning";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(10));
            Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not load");
            WebElement selected_checkbox = searchresultsfilterpage.selectFilterOption(FilterHeaders.DEPARTURE_TIME, FilterChoice.MORNING);
            Assert.assertTrue(searchresultsfilterpage.isFilterSelected(selected_checkbox),"Intended Filter Not Selected");

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_003_dropoff_time_filter_afternoon(){
        String testName = "TC_003_dropoff_time_filter_afternoon";
        try{
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
            Assert.assertTrue(sp.waitForBusCardsToLoad(),"Cards did not load");
            WebElement selected_checkbox = searchresultsfilterpage.selectFilterOption(FilterHeaders.ARRIVAL_TIME, FilterChoice.AFTERNOON);
            Assert.assertTrue(searchresultsfilterpage.isFilterSelected(selected_checkbox),"Intended Filter Not Selected");
        }catch(Throwable e){
            logTestFailure(testName, e);
        }
    }
    @Test
    public void TC_004_single_window_seater_sleeper_filter_single_seats(){
        String testName = "TC_004_single_window_seater_sleeper_filter_single_seats";
        try{
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
            Assert.assertTrue(sp.waitForBusCardsToLoad(),"Cards did not load");
            WebElement selected_checkbox = searchresultsfilterpage.selectFilterOption(FilterHeaders.SINGLE_WINDOW_SEATER_SLEEPER, FilterChoice.SINGLE_SEATS);
            Assert.assertTrue(searchresultsfilterpage.isFilterSelected(selected_checkbox),"Intended Filter Not Selected");
        }catch(Throwable e){
            logTestFailure(testName, e);
        }
    }
}
