package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BoardingDroppingPoint extends BasePage {
    public BoardingDroppingPoint(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "//button[normalize-space()='Select boarding & dropping points']")
    WebElement boardingDroppingPointButton;

    @FindBy(xpath = "//div[contains(@aria-label,'Board/Drop point')]")
    WebElement boardingDroppingPointTab;

    By boardingPoint = By.xpath("//ul[@aria-label='Boarding points']//li[contains(@class,'bpdpListRow___') and @role='listitem']");

    By droppingPoint = By.xpath("//ul[@aria-label='Dropping points']//li[contains(@class,'bpdpListRow___') and @role='listitem']");

    private final By boardingPointRadio =
            By.xpath(".//div[@role='radio' and @data-id]");

    private final By boardingPointName =
            By.xpath(".//div[contains(@class,'name___')]");



    public void clickBoardingDroppingPointButton(){
        clickElement(boardingDroppingPointButton);
    }

    public boolean isBoardingDroppingPointTabSelected(){
        return getAttributeValue(boardingDroppingPointTab,"aria-selected").equals("true");
    }

    public List<WebElement> getBoardingPoints(){
        return findElements(boardingPoint);
    }

    public WebElement getBoardingPointByIndex(int index){
        List<WebElement> boardingPoints = findElements(boardingPoint);
        return wait.until(driver ->
                boardingPoints.stream()
                        .map(li -> li.findElement(
                                By.xpath(".//div[@data-id]")
                        ))
                        .filter(element ->
                                String.valueOf(index)
                                        .equals(element.getAttribute("data-id"))
                        )
                        .findFirst()
                        .orElse(null)
        );
    }

    public WebElement getBoardingPointByName(String name) {

        return wait.until(driver -> {

            List<WebElement> boardingPoints =
                    driver.findElements(boardingPoint);

            return boardingPoints.stream()
                    .filter(li ->
                            li.findElement(boardingPointName)
                                    .getText()
                                    .toLowerCase()
                                    .contains(name.toLowerCase())
                    )
                    .map(li -> li.findElement(boardingPointRadio))
                    .findFirst()
                    .orElse(null);
        });
    }

    public void clickBoardingPointByIndex(int index){
        WebElement boardingPointByIndex = getBoardingPointByIndex(index);
        clickElement(boardingPointByIndex);
    }

    public void clickBoardingPointByName(String name){
        WebElement boardingPointByname = getBoardingPointByName(name);
        clickElement(boardingPointByname);
    }

    public boolean isBoardingPointCheckedByIndex(int index) {

        WebElement boardingPointElement =
                getBoardingPointByIndex(index);

        return "true".equals(
                boardingPointElement.getAttribute("aria-checked")
        );
    }

    public boolean isBoardingPointCheckedByName(String name) {
        return "true".equals(
                getBoardingPointByName(name)
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
}

