package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class BusSeatPage extends BasePage {

    public BusSeatPage(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath="//div[@aria-label='Tabs']")
    WebElement seatTab;

    @FindBy(xpath="//div[contains(@class,'seat__') and @role='button']")
    List<WebElement> allSeats;

    @FindBy(xpath = "//div[contains(@class,'seat___') and contains(@aria-label,'availability sold. Seat is not available for selection.')]")
    List<WebElement> soldSeats;


    public void isSeatTabVisible(){
        wait.until(ExpectedConditions.visibilityOf(seatTab));
    }

    public WebElement seatStatus(int seatNumber){
        isSeatTabVisible();
        WebElement selectedSeat = allSeats.stream()
                .filter(seat ->
                        String.valueOf(seatNumber)
                                .equals(seat.getAttribute("id")))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Seat " + seatNumber + " was not found."
                        ));
        return selectedSeat;



    }

    public boolean isSeatAvailable(int seatNumber){
        WebElement seatStatus = seatStatus(seatNumber);
        return "false".equals(seatStatus.getAttribute("aria-disabled"));
    }


    public void selectSeat(int seatNumber){
        isSeatTabVisible();
        WebElement selectedSeat = seatStatus(seatNumber);
        if(selectedSeat.getAttribute("aria-selected").equals("true")){
            selectedSeat.click();
        }else{
            Assert.fail("Seat was not available for number " + seatNumber);
        }
    }

    public boolean isSeatSelected(int seatNumber){
        isSeatTabVisible();
        WebElement selectedSeat = seatStatus(seatNumber);

        return "true".equalsIgnoreCase(selectedSeat.getAttribute("aria-selected"));
    }

    public boolean areSoldSeatDisabled(){
        List<WebElement> soldBusSeats = soldSeats.stream()
                .filter(soldSeat -> "true".equals(soldSeat.getAttribute("aria-disabled")))
                .collect(Collectors.toList());

        return soldBusSeats.size() == soldSeats.size();
    }










}
