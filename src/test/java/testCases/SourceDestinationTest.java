package testCases;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import testBase.BaseClass;
import utils.DataProviders;

public class SourceDestinationTest extends BaseClass {


    @Test
    public void testSourceDestination() {
        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement journeyFromElement = wait.until(ExpectedConditions.visibilityOf(hp.journeyFrom));
            journeyFromElement.click();
            wait.until(ExpectedConditions.visibilityOf(hp.autoSuggestion));
            WebElement focusedElement = driver.switchTo().activeElement();
            focusedElement.sendKeys("Mumbai");

            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[contains(@class ,'searchCategory___')]"), 2));
            List<WebElement> suggestions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[starts-with(@id,'suggestion-')]")));

            log.info("Number of suggestions found: " + suggestions);
            for (WebElement suggestion : suggestions) {

                WebElement cityElement = suggestion.findElement(By.xpath("//div[contains(@class,'listHeader___') and @role='heading']"));
                String suggestionText = cityElement.getText().trim();
                log.info("Suggestion Text: " + suggestionText);
                if (suggestionText.equalsIgnoreCase("Mumbai")) {
                    suggestion.click();
                    break;
                }
            }

        } catch (Exception e) {
            Assert.fail("Exception occurred while entering source and destination: " + e.getMessage());
        }
    }

    @Test
    public void TC_001_enter_source_city() {
        String testName = "TC_001_enter_source_city";
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement journeyFromElement = wait.until(ExpectedConditions.visibilityOf(hp.journeyFrom));
            journeyFromElement.click();
            wait.until(ExpectedConditions.visibilityOf(hp.autoSuggestion));
            WebElement focusedElement = driver.switchTo().activeElement();
            focusedElement.sendKeys("Mumbai");

        } catch (Exception e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_002_select_city_from_suggestions() {
        String testName = "TC_002_select_city_from_suggestions";
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement journeyFromElement = wait.until(ExpectedConditions.visibilityOf(hp.journeyFrom));
            journeyFromElement.click();
            wait.until(ExpectedConditions.visibilityOf(hp.autoSuggestion));
            WebElement focusedElement = driver.switchTo().activeElement();
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[contains(@class ,'searchCategory___')]"), 1));
            WebElement suggestion = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[starts-with(@id,'suggestion-') and @aria-describedby=\":r0:\"][1]"))));
            suggestion.click();
        } catch (Exception e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_003_enter_valid_destination() {
        String testName = "TC_003_enter_valid_destination";
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement journeyFromElement = wait.until(ExpectedConditions.visibilityOf(hp.journeyFrom));
            journeyFromElement.click();
            wait.until(ExpectedConditions.visibilityOf(hp.autoSuggestion));
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[contains(@class ,'searchCategory___')]"), 1));
            WebElement suggestion = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[starts-with(@id,'suggestion-') and @aria-describedby=\":r0:\"][1]"))));
            suggestion.click();
            wait.until(ExpectedConditions.visibilityOf(hp.autoSuggestion));
            WebElement focusedElement = driver.switchTo().activeElement();
            focusedElement.sendKeys("Mumbai");
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[contains(@class ,'searchCategory___')]"), 2));
            List<WebElement> destSuggestions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[starts-with(@id,'suggestion-')]")));

            log.info("Number of suggestions found: " + destSuggestions);
            for (WebElement destsuggestion : destSuggestions) {

                WebElement cityElement = destsuggestion.findElement(By.xpath("//div[contains(@class,'listHeader___') and @role='heading']"));
                String suggestionText = cityElement.getText().trim();
                log.info("Suggestion Text: " + suggestionText);
                if (suggestionText.equalsIgnoreCase("Mumbai")) {
                    destsuggestion.click();
                    break;
                }
            }
        } catch (Exception e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_004_swap_source_destination() {
        String testName = "TC_004_swap_source_destination";
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement journeyFromElement = wait.until(ExpectedConditions.visibilityOf(hp.journeyFrom));
            journeyFromElement.click();
            wait.until(ExpectedConditions.visibilityOf(hp.autoSuggestion));
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[contains(@class ,'searchCategory___')]"), 1));
            WebElement suggestion = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[starts-with(@id,'suggestion-') and @aria-describedby=\":r0:\"][1]"))));
            suggestion.click();
            wait.until(ExpectedConditions.visibilityOf(hp.autoSuggestion));
            WebElement focusedElement = driver.switchTo().activeElement();
            focusedElement.sendKeys("Mumbai");
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[contains(@class ,'searchCategory___')]"), 2));
            List<WebElement> destSuggestions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[starts-with(@id,'suggestion-')]")));

            log.info("Number of suggestions found: " + destSuggestions);
            for (WebElement destsuggestion : destSuggestions) {

                WebElement cityElement = destsuggestion.findElement(By.xpath("//div[contains(@class,'listHeader___') and @role='heading']"));
                String suggestionText = cityElement.getText().trim();
                log.info("Suggestion Text: " + suggestionText);
                if (suggestionText.equalsIgnoreCase("Mumbai")) {
                    destsuggestion.click();
                    break;
                }
            }

            hp.clickSwapButton();
            String currentSource = hp.getCurrentSource();
            Assert.assertEquals(currentSource, "Mumbai");
            String currentDestination = hp.getCurrentDestination();
            Assert.assertEquals(currentDestination, "Sindhi Camp");
            logTestPass(testName);

        } catch (Exception e) {
            logTestFailure(testName, e);
        }
    }

    @Test(dataProvider = "sourceDestinationData", dataProviderClass = DataProviders.class)
    public void TC_005_different_city_combinations(String source, String destination){
        String testName =  "TC_005_different_city_combinations";
        try{

            //SOURCE
            helper.enter_source(source);

            //DESTINATION

            helper.enter_destination(destination);
            logTestPass(testName);

        } catch (Exception e) {
            logTestFailure(testName,e);
        }
    }

    @Test
    public void TC_006_non_existing_city(){
        String testName = "TC_006_non_existing_city";
        try{

            //clicked on the source box
            hp.clickJourneyFrom();
            //need to wait for suggestion box to appear
            hp.isSuggestionsVisible();
            //after waiting for suggestion box to appear we need to move the focus to the current element
            WebElement currentFocusedSourceElement = driver.switchTo().activeElement();
            //clear the element if anything present
            currentFocusedSourceElement.sendKeys(Keys.CONTROL + "a");
            currentFocusedSourceElement.sendKeys(Keys.DELETE);
            //need to send the source now
            currentFocusedSourceElement.sendKeys("adsdadada");

            hp.isSuggestionsVisible();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'noResultsFound___')]")));
            String value = driver.findElement(By.xpath("//div[contains(@class,'noResultsHeader___')]")).getText();
            Assert.assertEquals(value, "No Results Found");
            logTestPass(testName);
        }catch (Exception e){
            logTestFailure(testName,e);
        }
    }


    //NEGATIVE

    @Test
    public void TC_007_empty_source(){
        String testName = "TC_006_empty_source";
        try{
            helper.enter_destination("Mumbai");
            hp.clickSearchBusesButton();
            boolean isDisplayed = hp.isemptySourcePopUpMessageDisplayed();
            Assert.assertTrue(isDisplayed,"Popup message did not display");

        } catch (Exception e) {
            logTestFailure(testName,e);
        }

    }

    @Test
    public void TC_008_empty_destination(){
        String testName = "TC_006_empty_destination";
        try{
            helper.enter_destination("Mumbai");
            hp.clickSearchBusesButton();
            boolean isDisplayed = hp.isemptySourcePopUpMessageDisplayed();
            Assert.assertTrue(isDisplayed,"Popup message did not display");

        } catch (Exception e) {
            logTestFailure(testName,e);
        }

    }

    @Test
    public void TC_009_empty_source_destination(){
        String testName = "TC_006_empty_source_destination";
        try{
            hp.clickSearchBusesButton();
            boolean isDisplayed = hp.isemptySourcePopUpMessageDisplayed();
            Assert.assertTrue(isDisplayed,"Popup message did not display");

        } catch (Exception e) {
            logTestFailure(testName,e);
        }

    }




}

