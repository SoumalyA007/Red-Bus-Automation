package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjects.HomePage;

public class HelperFunctions {

    private final HomePage hp;
    private final WebDriverWait wait;
    private final WebDriver driver;
     public HelperFunctions(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.hp = new HomePage(driver);
    }

    public void enter_source(String source) {
        
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
        currentFocusedSourceElement.sendKeys(source);
        //need to wait for the suggestion box to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'searchCategory___')][1]")
        ));
        //wait for the source option and click it
        WebElement cityOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(
                        "//div[contains(@class,'searchCategory___')]//div[contains(@class,'suggestion-item')]"
                        + "[.//div[@role='heading' and "
                        + "translate(normalize-space(.),"
                        + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                        + "'abcdefghijklmnopqrstuvwxyz')='" + source.trim().toLowerCase() + "']]"
                )
        ));

        cityOption.click();
    }

    public void enter_destination(String destination) {
        //clicked on the source box
        hp.clickJourneyTo();
        //need to wait for suggestion box to appear
        hp.isSuggestionsVisible();
        //after waiting for suggestion box to appear we need to move the focus to the current element
        WebElement currentFocusedDestElement = driver.switchTo().activeElement();
        //clear the element if anything present
        currentFocusedDestElement.sendKeys(Keys.CONTROL + "a");
        currentFocusedDestElement.sendKeys(Keys.DELETE);
        //send the required destination
        currentFocusedDestElement.sendKeys(destination);
        //need to wait for the suggestion box to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'searchCategory___')][1]")
        ));

        WebElement destOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(
                        "//div[contains(@class,'searchCategory___')]//div[contains(@class,'suggestion-item')]"
                        + "[.//div[@role='heading' and "
                        + "translate(normalize-space(.),"
                        + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                        + "'abcdefghijklmnopqrstuvwxyz')='" + destination.trim().toLowerCase() + "']]"
                )
        ));

        destOption.click();
    }

}
