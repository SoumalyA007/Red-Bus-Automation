package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    @FindBy(xpath="//span[contains(@id,'LD') and @role='button']")
    List<WebElement> allSleepers;

    @FindBy(xpath = "//div[contains(@class,'seat___') and contains(@aria-label,'availability sold. Seat is not available for selection.')]")
    List<WebElement> soldSeats;

    @FindBy(xpath = "//div[@role = 'button' and contains(@class,'priceWrap__')]")
    WebElement totalFair;

    @FindBy(xpath = "//table[contains(@class,'legendTable__')]//tbody//tr[contains(@class,'legendItem__')]")
    List<WebElement> legendItems;

    By elementFair = By.xpath(".//span[contains(@class,'seatPrice__')]");

    public boolean isSeatLayoutVisible(){
        return isElementDisplayed(seatTab);
    }

    // ---------- shared/generic helpers (id = "id" attribute for seats, could differ for sleepers) ----------

    private WebElement findByNumber(List<WebElement> elements, String number, String type){
        isSeatLayoutVisible();
        return elements.stream()
                .filter(el -> String.valueOf(number).equals(el.getAttribute("id")))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(type + " " + number + " was not found."));
    }

    private boolean isAvailable(List<WebElement> elements, String number, String type){
        WebElement el = findByNumber(elements, number, type);
        return !"true".equals(el.getAttribute("aria-disabled"));
    }

    private boolean select(List<WebElement> elements, String number, String type){
        WebElement el = findByNumber(elements, number, type);
        if(isAvailable(elements, number, type)){
            clickElement(el);
            return true;
        }
        return false;
    }

    private boolean isSelected(List<WebElement> elements, String number, String type){
        WebElement el = findByNumber(elements, number, type);
        return "true".equalsIgnoreCase(el.getAttribute("aria-pressed"));
    }

    private boolean deselect(List<WebElement> elements, String number, String type){
        if(isSelected(elements, number, type)){
            clickElement(findByNumber(elements, number, type));
            return true;
        }
        return false;
    }

    private int selectedCount(List<WebElement> elements){
        isSeatLayoutVisible();
        return (int) elements.stream()
                .filter(el -> "true".equals(el.getAttribute("aria-pressed")))
                .count();
    }

    private double fareOf(List<WebElement> elements, String number, String type){
        WebElement el = findByNumber(elements, number, type);
        String fareText = getText(findElement(el, elementFair));
        return Double.parseDouble(fareText.replaceAll("[^0-9.]", ""));
    }

    // ---------- seat-facing API (unchanged behavior, now delegating) ----------

    public WebElement seatStatus(String seatNumber){ return findByNumber(allSeats, seatNumber, "Seat"); }
    public boolean isSeatAvailable(String seatNumber){ return isAvailable(allSeats, seatNumber, "Seat"); }
    public boolean selectSeat(String seatNumber){ return select(allSeats, seatNumber, "Seat"); }
    public boolean isSeatSelected(String seatNumber){ return isSelected(allSeats, seatNumber, "Seat"); }
    public boolean deselectSeat(String seatNumber){ return deselect(allSeats, seatNumber, "Seat"); }
    public void selectSeats(List<String> seatNumbers){ seatNumbers.forEach(this::selectSeat); }
    public boolean deselectSeats(List<String> seatNumbers){ return seatNumbers.stream().allMatch(this::deselectSeat); }
    public int getSelectedSeatsCount(){ return selectedCount(allSeats); }
    public double getSelectedSeatFairAmount(String seatNumber){ return fareOf(allSeats, seatNumber, "Seat"); }

    // ---------- sleeper-facing API (new, mirrors seat API exactly) ----------

    public WebElement sleeperStatus(String sleeperNumber){ return findByNumber(allSleepers, sleeperNumber, "Sleeper"); }
    public boolean isSleeperAvailable(String sleeperNumber){ return isAvailable(allSleepers, sleeperNumber, "Sleeper"); }
    public boolean selectSleeper(String sleeperNumber){ return select(allSleepers, sleeperNumber, "Sleeper"); }
    public boolean isSleeperSelected(String sleeperNumber){ return isSelected(allSleepers, sleeperNumber, "Sleeper"); }
    public boolean deselectSleeper(String sleeperNumber){ return deselect(allSleepers, sleeperNumber, "Sleeper"); }
    public void selectSleepers(List<String> sleeperNumbers){ sleeperNumbers.forEach(this::selectSleeper); }
    public boolean deselectSleepers(List<String> sleeperNumbers){ return sleeperNumbers.stream().allMatch(this::deselectSleeper); }
    public int getSelectedSleepersCount(){ return selectedCount(allSleepers); }
    public double getSelectedSleeperFairAmount(String sleeperNumber){ return fareOf(allSleepers, sleeperNumber, "Sleeper"); }

    // ---------- other existing methods (getFareAmount, legend, sold-seat checks) unchanged ----------
}