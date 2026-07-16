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

import pageObjects.HomePage;
import testBase.BaseClass;
import utils.ExcelDataProvider;

public class SourceDestinationTest extends BaseClass {

    @Test
    public void testSourceDestination() {
        HomePage homePage = new HomePage(driver);
        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement journeyFromElement = wait.until(ExpectedConditions.visibilityOf(homePage.journeyFrom));
            journeyFromElement.click();
            wait.until(ExpectedConditions.visibilityOf(homePage.autoSuggestion));
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
        HomePage homePage = new HomePage(driver);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement journeyFromElement = wait.until(ExpectedConditions.visibilityOf(homePage.journeyFrom));
            journeyFromElement.click();
            wait.until(ExpectedConditions.visibilityOf(homePage.autoSuggestion));
            WebElement focusedElement = driver.switchTo().activeElement();
            focusedElement.sendKeys("Mumbai");

        } catch (Exception e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_002_select_city_from_suggestions() {
        String testName = "TC_002_select_city_from_suggestions";
        HomePage homePage = new HomePage(driver);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement journeyFromElement = wait.until(ExpectedConditions.visibilityOf(homePage.journeyFrom));
            journeyFromElement.click();
            wait.until(ExpectedConditions.visibilityOf(homePage.autoSuggestion));
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
        HomePage homePage = new HomePage(driver);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement journeyFromElement = wait.until(ExpectedConditions.visibilityOf(homePage.journeyFrom));
            journeyFromElement.click();
            wait.until(ExpectedConditions.visibilityOf(homePage.autoSuggestion));
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[contains(@class ,'searchCategory___')]"), 1));
            WebElement suggestion = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[starts-with(@id,'suggestion-') and @aria-describedby=\":r0:\"][1]"))));
            suggestion.click();
            wait.until(ExpectedConditions.visibilityOf(homePage.autoSuggestion));
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
        HomePage homePage = new HomePage(driver);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement journeyFromElement = wait.until(ExpectedConditions.visibilityOf(homePage.journeyFrom));
            journeyFromElement.click();
            wait.until(ExpectedConditions.visibilityOf(homePage.autoSuggestion));
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[contains(@class ,'searchCategory___')]"), 1));
            WebElement suggestion = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[starts-with(@id,'suggestion-') and @aria-describedby=\":r0:\"][1]"))));
            suggestion.click();
            wait.until(ExpectedConditions.visibilityOf(homePage.autoSuggestion));
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

            homePage.clickSwapButton();
            String currentSource = homePage.getCurrentSource();
            Assert.assertEquals(currentSource, "Mumbai");
            String currentDestination = homePage.getCurrentDestination();
            Assert.assertEquals(currentDestination, "Sindhi Camp");
            logTestPass(testName);

        } catch (Exception e) {
            logTestFailure(testName, e);
        }
    }

//@Test(dataProvider = "sourceDestinationData", dataProviderClass = ExcelDataProvider.class)
    @Test(dataProvider = "sourceDestinationData", dataProviderClass = ExcelDataProvider.class)
    public void TC_005_different_city_combinations(String source, String destination) {

        String testName = "TC_005_different_city_combinations";

        HomePage homePage = new HomePage(driver);
        driver.navigate().refresh();

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement journeyFromElement = wait.until(ExpectedConditions.visibilityOf(homePage.journeyFrom));
            journeyFromElement.click();
            wait.until(ExpectedConditions.visibilityOf(homePage.autoSuggestion));
            WebElement focusedElement = driver.switchTo().activeElement();

            focusedElement.sendKeys(Keys.CONTROL + "a");
            focusedElement.sendKeys(Keys.DELETE);
            focusedElement.sendKeys(source);

            //wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[contains(@class ,'searchCategory___')]"), 2));
            wait.until(driver -> {
                List<WebElement> suggestions = driver.findElements(
                        By.xpath("//div[contains(@class,'commonSuggestionWrapper___')]"));
                return suggestions.stream().anyMatch(s -> {
                    try {
                        return s.findElement(By.xpath(".//div[contains(@class,'listHeader___') and @role='heading']"))
                                .getText().toLowerCase().contains(source.toLowerCase().substring(0, 3));
                    } catch (Exception e) {
                        return false;
                    }
                });
            });

            List<WebElement> sourceSuggestions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'searchCategory___')][1]")));
            for (WebElement suggestion : sourceSuggestions) {
                wait.until(ExpectedConditions.visibilityOf(suggestion));
                Thread.sleep(1000);
                WebElement cityElement = suggestion.findElement(By.xpath(".//div[contains(@class,'listHeader___') and @role='heading']"));
                String suggestionText = cityElement.getText().trim();
                log.info("Suggestion found : " + suggestionText);

                log.error(testName, "Source Text: " + source);
                if (suggestionText.equalsIgnoreCase(source)) {
                    log.info("CLICKING : " + suggestionText);
                    suggestion.click();
                    break;
                }
            }

            //DESTINATION
            WebElement journeyToElement = wait.until(ExpectedConditions.visibilityOf(homePage.journeyTo));
            journeyToElement.click();
            wait.until(ExpectedConditions.visibilityOf(homePage.autoSuggestion));
            focusedElement = driver.switchTo().activeElement();
            focusedElement.sendKeys(Keys.CONTROL + "a");
            focusedElement.sendKeys(Keys.DELETE);
            focusedElement.sendKeys(destination);
            //wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[contains(@class ,'searchCategory___')]"), 2));

            wait.until(driver -> {
                List<WebElement> suggestions = driver.findElements(
                        By.xpath("//div[contains(@class,'commonSuggestionWrapper___')]"));
                return suggestions.stream().anyMatch(s -> {
                    try {
                        return s.findElement(By.xpath(".//div[contains(@class,'listHeader___') and @role='heading']"))
                                .getText().toLowerCase().contains(destination.toLowerCase().substring(0, 3)); // ✅ fixed
                    } catch (Exception e) {
                        return false;
                    }
                });
            });

            List<WebElement> destSuggestions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'searchCategory___')][1]")));
            for (WebElement destsuggestion : destSuggestions) {

                wait.until(ExpectedConditions.visibilityOf(destsuggestion));
                Thread.sleep(1000);
                WebElement cityElement = destsuggestion.findElement(By.xpath(".//div[contains(@class,'listHeader___') and @role='heading']"));
                String suggestionText = cityElement.getText().trim();
                log.error(testName, "Destination Text: " + destination);
                log.info("Suggestion found : " + suggestionText);

                if (suggestionText.equalsIgnoreCase(destination)) {
                    log.info("CLICKING : " + suggestionText);

                    destsuggestion.click();
                    break;
                }
            }

            String currentSource = homePage.getCurrentSource();
            log.info("Expected source: " + source + ", Current Source: " + currentSource);
            Assert.assertTrue(
                    currentSource.toLowerCase().contains(source.toLowerCase()),
                    "Source mismatch"
            );
            String currentDestination = homePage.getCurrentDestination();
            log.info("Expected destination: " + destination + ", Current Destination: " + currentDestination);
            Assert.assertTrue(
                    currentDestination.toLowerCase().contains(destination.toLowerCase()),
                    "Destination mismatch"
            );
            logTestPass(testName);

        } catch (Exception e) {
            logTestFailure(testName, e);
        }
    }

}
