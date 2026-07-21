package testCases;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import testBase.BaseClass;

public class AutoSuggestionTest extends BaseClass {

    @Test
    public void TC_001_suggestion_appears() {

        String testName = "TC_001_suggestion_appears";
        int[] previousSize = {-1}; // array to hold the previous size of suggestions
        try {
            FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofMillis(500))
                    .ignoring(StaleElementReferenceException.class);
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
            currentFocusedSourceElement.sendKeys("Mumbai");

            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(@class,'searchCategory___')][not(@role='region')]")
            ));

            List<WebElement> suggestionElements = driver.findElements(
                    By.xpath("//div[contains(@class,'searchCategory___')]"));

            Assert.assertEquals(suggestionElements.size(), 3);

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }

    }

    @Test
    public void TC_002_suggestion_disappears_after_selection() {

        String testName = "TC_002_suggestion_disappears_after_selection";
        try {

            helper.enter_source("Mumbai");
            helper.enter_destination("Pune");

            List<WebElement> suggestions = driver.findElements(
                    By.xpath("//div[contains(@class,'searchSuggestionWrapper___')]")
            );

            Assert.assertTrue(suggestions.isEmpty(), "Suggestions are still visible.");
            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }

    }

    @Test
    public void TC_OO3_Keyboard_navigation() {
        String testName = "TC_OO3_Keyboard_navigation";
        try {
            hp.clickJourneyFrom();
            //need to wait for suggestion box to appear
            hp.isSuggestionsVisible();
            //after waiting for suggestion box to appear we need to move the focus to the current element
            WebElement currentFocusedSourceElement = driver.switchTo().activeElement();
            //need to send the source now
            currentFocusedSourceElement.sendKeys("Mumbai");
            currentFocusedSourceElement.sendKeys(Keys.ARROW_DOWN);
            currentFocusedSourceElement.sendKeys(Keys.ENTER);

            hp.clickJourneyTo();
            //need to wait for suggestion box to appear
            hp.isSuggestionsVisible();
            //after waiting for suggestion box to appear we need to move the focus to the current element
            WebElement currentFocusedDestElement = driver.switchTo().activeElement();
            //send the required destination
            currentFocusedDestElement.sendKeys("Kolkata");
            currentFocusedSourceElement.sendKeys(Keys.ARROW_DOWN);
            currentFocusedSourceElement.sendKeys(Keys.ENTER);

            Assert.assertEquals(hp.getCurrentSource(), "Mumbai");
            Assert.assertEquals(hp.getCurrentDestination(), "Kolkata");

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

}
