package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.BaseClass;

import java.util.List;

public class AutoSuggestionTest extends BaseClass {

    @Test
    public void TC_001_suggestion_appears(){

        String testName = "TC_001_suggestion_appears";
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
            currentFocusedSourceElement.sendKeys("Mumbai");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'searchCategory___')]")));
            List<WebElement> suggestionElements = driver.findElements(By.xpath("//div[contains(@class,'searchCategory___')]"));
            Assert.assertEquals(suggestionElements.size(), 3);

        } catch (Exception e) {
            logTestFailure(testName, e);
        }



    }





}

