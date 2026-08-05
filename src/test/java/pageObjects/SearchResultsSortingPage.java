package pageObjects;

import enums.Sorting;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchResultsSortingPage extends BasePage {

    public SearchResultsSortingPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[contains(@aria-label,'Ratings')]")
    WebElement sortByRatingButton;

    @FindBy(xpath = "//div[contains(@aria-label,'Departure Time')]")
    WebElement sortByDepartureButton;

    @FindBy(xpath = "//div[contains(@aria-label,'Price')]")
    WebElement sortByPriceButton;


    public void clickSortByRatingButton(){
        wait.until(ExpectedConditions.elementToBeClickable(sortByRatingButton));
        sortByRatingButton.click();
    }

    public void clickSortByDepartureButton(){
        wait.until(ExpectedConditions.elementToBeClickable(sortByDepartureButton));
        sortByDepartureButton.click();
    }

    public void clickSortByPriceButton(){
        wait.until(ExpectedConditions.elementToBeClickable(sortByPriceButton));
        sortByPriceButton.click();
    }


    public Sorting getSortingStatus(Sorting sorting) {

        WebElement element;

        switch (sorting) {
            case RATING:
                element = sortByRatingButton;
                break;

            case DEPARTURE_TIME:
                element = sortByDepartureButton;
                break;

            case PRICE:
                element = sortByPriceButton;
                break;

            default:
                throw new IllegalArgumentException("Invalid sorting option");
        }

        String ariaLabel = element.getAttribute("aria-label").toLowerCase();

        if (ariaLabel.contains("ascending")) {
            return Sorting.ASCENDING;
        }

        if (ariaLabel.contains("descending")) {
            return Sorting.DESCENDING;
        }

        return Sorting.NOT_SELECTED;
    }




}
