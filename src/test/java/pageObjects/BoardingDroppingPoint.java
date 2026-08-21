package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class BoardingDroppingPoint extends BasePage {
    public BoardingDroppingPoint(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "//button[normalize-space()='Select boarding & dropping points']")
    WebElement boardingDroppingPointButton;

    @FindBy(xpath = "//div[contains(@aria-label,'Board/Drop point')]")
    WebElement boardingDroppingPointTab;

    @FindBy(xpath = "//div[contains(@class,'snackbar___')]")
    WebElement boardingDroppingPointValidationMessageBox;

    By boardingPoint = By.xpath("//ul[@aria-label='Boarding points']//li[contains(@class,'bpdpListRow___') and @role='listitem']");

    By droppingPoint = By.xpath("//ul[@aria-label='Dropping points']//li[contains(@class,'bpdpListRow___') and @role='listitem']");

    private final By boardingDroppingPointRadio =
            By.xpath(".//div[@role='radio' and @data-id]");

    private final By boardingDroppingPointName =
            By.xpath(".//div[contains(@class,'name___')]");


    public void clickBoardingDroppingPointButton(){
        List<WebElement> existingPanel = driver.findElements(
                By.xpath("//ul[@aria-label='Boarding points']"));
        if (!existingPanel.isEmpty() && existingPanel.get(0).isDisplayed()) {
            return; // panel already open, nothing to do
        }
        clickElement(boardingDroppingPointButton);
    }

    public void clickBoardingDroppingPointTab(){
        clickElement(boardingDroppingPointTab);
    }

    public boolean isBoardingDroppingPointTabSelected(){
        return getAttributeValue(boardingDroppingPointTab,"aria-selected").equals("true");
    }

    public List<WebElement> getBoardingPoints(){
        return findElements(boardingPoint);
    }

    public List<WebElement> getDroppingPoints(){
        return findElements(droppingPoint);
    }

    public WebElement getBoardingPointByIndex(int index) {

        return wait.until(driver -> {

            List<WebElement> boardingPoints =
                    driver.findElements(boardingPoint);

            return boardingPoints.stream()
                    .map(li -> li.findElement(boardingDroppingPointRadio))
                    .filter(element ->
                            String.valueOf(index)
                                    .equals(element.getAttribute("data-id"))
                    )
                    .findFirst()
                    .orElse(null);
        });
    }


    public WebElement getDroppingPointByIndex(int index) {

        return wait.until(driver -> {

            List<WebElement> droppingPoints =
                    driver.findElements(droppingPoint);

            return droppingPoints.stream()
                    .map(li -> li.findElement(boardingDroppingPointRadio))
                    .filter(element ->
                            String.valueOf(index)
                                    .equals(element.getAttribute("data-id"))
                    )
                    .findFirst()
                    .orElse(null);
        });
    }


    public WebElement getBoardingPointByName(String name) {

        return wait.until(driver -> {

            List<WebElement> boardingPoints =
                    driver.findElements(boardingPoint);

            return boardingPoints.stream()
                    .filter(li ->
                            li.findElement(boardingDroppingPointName)
                                    .getText()
                                    .trim()
                                    .equalsIgnoreCase(name.trim())
                    )
                    .map(li -> li.findElement(boardingDroppingPointRadio))
                    .findFirst()
                    .orElse(null);
        });
    }


    public WebElement getDroppingPointByName(String name) {

        return wait.until(driver -> {

            List<WebElement> droppingPoints =
                    driver.findElements(droppingPoint);

            return droppingPoints.stream()
                    .filter(li ->
                            li.findElement(boardingDroppingPointName)
                                    .getText()
                                    .trim()
                                    .equalsIgnoreCase(name.trim())
                    )
                    .map(li -> li.findElement(boardingDroppingPointRadio))
                    .findFirst()
                    .orElse(null);
        });
    }
    public void clickBoardingPointByIndex(int index){
        WebElement boardingPointByIndex = getBoardingPointByIndex(index);
        clickElement(boardingPointByIndex);
    }

    public void clickDroppingPointByIndex(int index){
        WebElement droppingPointByIndex = getDroppingPointByIndex(index);
        clickElement(droppingPointByIndex);
    }

    public void clickBoardingPointByName(String name){
        WebElement boardingPointByname = getBoardingPointByName(name);
        clickElement(boardingPointByname);
    }

    public void clickDroppingPointByName(String name){
        WebElement droppingPointByname = getDroppingPointByName(name);
        clickElement(droppingPointByname);
    }

    public boolean isBoardingPointCheckedByIndex(int index) {

        WebElement boardingPointElement =
                getBoardingPointByIndex(index);

        return "true".equals(
                boardingPointElement.getAttribute("aria-checked")
        );
    }

    public boolean isDroppingPointCheckedByIndex(int index) {

        WebElement droppingPointElement =
                getDroppingPointByIndex(index);

        return "true".equals(
                droppingPointElement.getAttribute("aria-checked")
        );
    }

    public boolean isBoardingPointCheckedByName(String name) {
        return "true".equals(
                getBoardingPointByName(name)
                        .getAttribute("aria-checked")
        );
    }

    public boolean isDroppingPointCheckedByName(String name) {
        return "true".equals(
                getDroppingPointByName(name)
                        .getAttribute("aria-checked")
        );
    }

    public boolean areAllBoardingTimesDisplayed() {
        return getBoardingPoints().stream()
                .map(element -> element.findElement(
                        By.xpath(".//div[contains(@class,'dateTime___')]")
                ))
                .allMatch(timeElement ->
                        timeElement.getText() != null &&
                                !timeElement.getText().isBlank()
                );
    }

    public boolean areAllDroppingTimesDisplayed() {
        return getDroppingPoints().stream()
                .map(element -> element.findElement(
                        By.xpath(".//div[contains(@class,'dateTime___')]")
                ))
                .allMatch(timeElement ->
                        timeElement.getText() != null &&
                                !timeElement.getText().isBlank()
                );
    }

    public HashMap<Boolean, String> isboardingDroppingVlidationMessageDisplayed(){
        boolean status = isElementDisplayed(boardingDroppingPointValidationMessageBox);
        String message =boardingDroppingPointValidationMessageBox.getText() ;

        HashMap<Boolean, String> map = new HashMap<>();
        map.put(status,message);
        return map;
    }
}

