package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
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

    @FindBy(xpath = "//div[@role = 'button' and contains(@class,'priceWrap__')]")
    WebElement totalFair;

    @FindBy(xpath="//div[contains(@class,'seatMain__')]//div[@class=' ']//div[contains(@class,'container__') ]")
    WebElement seatLegend;

    @FindBy(xpath = "//table[contains(@class,'legendTable__')]//tbody//tr[contains(@class,'legendItem__')]")
    List<WebElement> legendItems;

    By selectedSeatFair = By.xpath(".//span[contains(@class,'seatPrice__')]");

    public boolean isSeatLayoutVisible(){
        return isElementDisplayed(seatTab);
    }

    public WebElement seatStatus(String seatNumber){
        isSeatLayoutVisible();
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

    public boolean isSeatAvailable(String seatNumber){
        WebElement seatStatus = seatStatus(seatNumber);
        return !"true".equals(seatStatus.getAttribute("aria-disabled"));
    }

    public boolean selectSeat(String seatNumber){
        isSeatLayoutVisible();
        WebElement selectedSeat = seatStatus(seatNumber);
        if(isSeatAvailable(seatNumber)){
            clickElement(selectedSeat);
            return true;
        }
        return false;
    }

    public boolean isSeatSelected(String seatNumber){
        isSeatLayoutVisible();
        WebElement selectedSeat = seatStatus(seatNumber);
        return "true".equalsIgnoreCase(selectedSeat.getAttribute("aria-pressed"));
    }

    public boolean areSoldSeatDisabled(){
        List<WebElement> soldBusSeats = soldSeats.stream()
                .filter(soldSeat -> "true".equals(soldSeat.getAttribute("aria-pressed")))
                .collect(Collectors.toList());

        return soldBusSeats.size() == soldSeats.size();
    }

    public boolean deselectSeat(String seatNumber){
        isSeatLayoutVisible();
        if(isSeatSelected(seatNumber)){
            clickElement(seatStatus(seatNumber));
            return true;
        }
        return false;
    }

    public void selectSeats(List<String> seatNumbers){
        seatNumbers.stream()
                .forEach(this::selectSeat);
    }

    public boolean deselectSeats(List<String> seatNumbers){
        return seatNumbers.stream()
                .allMatch(this::deselectSeat);
    }

    public boolean areSeatsSelected(List<String> seatNumbers){

        return false;
    }

    public int getSelectedSeatsCount(){
        isSeatLayoutVisible();
        return (int) allSeats.stream()
                .filter(seat -> "true".equals(seat.getAttribute("aria-pressed")))
                .count();

    }

    public String getFirstSoldSeatNumber(){
        return soldSeats.stream()
                .filter(seat -> "true".equals(seat.getAttribute("aria-disabled")))
                .findFirst()
                .map(seat -> seat.getAttribute("id"))
                .orElseThrow(() -> new NoSuchElementException("No sold seats available"));
    }

    public String getFirstAvailableSeatNumber(){

        return allSeats.stream()
                .peek(seat -> System.out.println("Available seat found: " + seat.getAttribute("id")))
                .filter(seat -> !"true".equals(seat.getAttribute("aria-disabled")))
                .findFirst()
                .map(seat -> seat.getAttribute("id"))
                .orElseThrow(() -> new NoSuchElementException("No available seats"));

    }

    public double getFareAmount(){
        isSeatLayoutVisible();
        String fareText = getText(totalFair);
        return Double.parseDouble(fareText.replaceAll("[^0-9.]", ""));
    }

    public double getSelectedSeatFairAmount(String seatNumber){
        WebElement selectedSeat = seatStatus(seatNumber);
        String fairText = getText(findElement(selectedSeat,selectedSeatFair));

        return Double.parseDouble(fairText.replaceAll("[^0-9.]", ""));
    }

    public List<String> getSeatLegendLabels(){
        return legendItems.stream()
                .map(item -> getText(item).trim())
                .map(String :: trim)
                .filter(text -> !text.isEmpty())
                .collect(Collectors.toList());
    }

    public boolean isSeatLegendDisplayed(){
        return isElementDisplayed(seatLegend);
    }



}