package testCases;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import testBase.BaseClass;

public class SourceDestinationTest extends BaseClass {

    @Test
    public void testSourceDestination(){
        HomePage homePage = new HomePage(driver);
        try{

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement journeyFromElement = wait.until(ExpectedConditions.visibilityOf(homePage.journeyFrom));
            journeyFromElement.click();
            wait.until(ExpectedConditions.visibilityOf(homePage.autoSuggestion));
            WebElement focusedElement = driver.switchTo().activeElement();
            focusedElement.sendKeys("Mumbai");

            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[contains(@class ,'searchCategory___')]"), 2));
            List<WebElement> suggestions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[starts-with(@id,'suggestion-')]")));
            
            log.info("Number of suggestions found: " + suggestions);
            for(WebElement suggestion : suggestions){

                WebElement cityElement = suggestion.findElement(By.xpath(".//*[@role='heading']"));
                String suggestionText = cityElement.getText().trim();
                log.info("Suggestion Text: " + suggestionText);
                if(suggestionText.equalsIgnoreCase("Mumbai")){
                    suggestion.click();
                    break;
                }
            }


        }catch(Exception e){
            Assert.fail("Exception occurred while entering source and destination: " + e.getMessage());
        }
    }

}