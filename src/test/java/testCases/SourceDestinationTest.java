package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import testBase.BaseClass;
import utils.DataProviders;

public class SourceDestinationTest extends BaseClass {

    @Test
    public void testSourceDestination() {
        try {
            searchbarcomponents.clickJourneyFrom();
            searchbarcomponents.isSuggestionsVisible();
            searchbarcomponents.typeCity("Mumbai");
            searchbarcomponents.selectSuggestionByText("Mumbai");
        } catch (Exception e) {
            Assert.fail("Exception occurred while entering source and destination: " + e.getMessage());
        }
    }

    @Test
    public void TC_001_enter_source_city() {
        String testName = "TC_001_enter_source_city";
        try {
            searchbarcomponents.clickJourneyFrom();
            searchbarcomponents.isSuggestionsVisible();
            searchbarcomponents.typeCity("Mumbai");
            Assert.assertEquals(searchbarcomponents.getCurrentSource(), "Mumbai", "The source is not same");
        } catch (Exception e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_002_select_city_from_suggestions() {
        String testName = "TC_002_select_city_from_suggestions";
        try {
            searchbarcomponents.clickJourneyFrom();
            searchbarcomponents.isSuggestionsVisible();
            searchbarcomponents.selectFirstSuggestion();
        } catch (Exception e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_003_enter_valid_destination() {
        String testName = "TC_003_enter_valid_destination";
        try {
            searchbarcomponents.clickJourneyFrom();
            searchbarcomponents.isSuggestionsVisible();
            searchbarcomponents.selectFirstSuggestion();

            searchbarcomponents.isSuggestionsVisible();
            searchbarcomponents.typeCity("Mumbai");
            searchbarcomponents.selectSuggestionByText("Mumbai");
        } catch (Exception e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_004_swap_source_destination() {
        String testName = "TC_004_swap_source_destination";
        try {
            searchbarcomponents.clickJourneyFrom();
            searchbarcomponents.isSuggestionsVisible();
            searchbarcomponents.selectFirstSuggestion();

            searchbarcomponents.isSuggestionsVisible();
            searchbarcomponents.typeCity("Mumbai");
            searchbarcomponents.selectSuggestionByText("Mumbai");

            searchbarcomponents.clickSwapButton();
            Assert.assertEquals(searchbarcomponents.getCurrentSource(), "Mumbai");
            Assert.assertEquals(searchbarcomponents.getCurrentDestination(), "Sindhi Camp");
            logTestPass(testName);
        } catch (Exception e) {
            logTestFailure(testName, e);
        }
    }

    @Test(dataProvider = "sourceDestinationData", dataProviderClass = DataProviders.class)
    public void TC_005_different_city_combinations(String source, String destination) {
        String testName = "TC_005_different_city_combinations";
        try {
            helper.enter_source(source);
            helper.enter_destination(destination);
            logTestPass(testName);
        } catch (Exception e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_006_non_existing_city() {
        String testName = "TC_006_non_existing_city";
        try {
            searchbarcomponents.clickJourneyFrom();
            searchbarcomponents.isSuggestionsVisible();
            searchbarcomponents.typeCity("adsdadada");
            searchbarcomponents.isSuggestionsVisible();

            String value = searchbarcomponents.getNoResultsMessage();
            Assert.assertEquals(value, "No Results Found");
            logTestPass(testName);
        } catch (Exception e) {
            logTestFailure(testName, e);
        }
    }

    // NEGATIVE

    @Test
    public void TC_007_empty_source() {
        String testName = "TC_007_empty_source";
        try {
            helper.enter_destination("Mumbai");
            searchbarcomponents.clickSearchBusesButton();
            Assert.assertTrue(hp.isemptySourcePopUpMessageDisplayed(), "Popup message did not display");
        } catch (Exception e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_008_empty_destination() {
        String testName = "TC_008_empty_destination";
        try {
            helper.enter_source("Mumbai");
            searchbarcomponents.clickSearchBusesButton();
            Assert.assertTrue(hp.isemptySourcePopUpMessageDisplayed(), "Popup message did not display");
        } catch (Exception e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_009_empty_source_destination() {
        String testName = "TC_009_empty_source_destination";
        try {
            searchbarcomponents.clickSearchBusesButton();
            Assert.assertTrue(hp.isemptySourcePopUpMessageDisplayed(), "Popup message did not display");
        } catch (Exception e) {
            logTestFailure(testName, e);
        }
    }
}