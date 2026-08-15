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

    @FindBy(xpath = "//div[contains(@aria-label,'Departure time')]")
    WebElement sortByDepartureButton;

    @FindBy(xpath = "//div[contains(@aria-label,'Price')]")
    WebElement sortByPriceButton;

    public void clickSortByRatingButton(){
        clickElement(sortByRatingButton);
    }

    public void clickSortByDepartureButton(){
        clickElement(sortByDepartureButton);
    }

    public void clickSortByPriceButton(){
        clickElement(sortByPriceButton);
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

    public void setSortByRatingButtontoDesc(Sorting sorting) {

        Sorting ratingSort = getSortingStatus(sorting);
        while(!ratingSort.equals(Sorting.DESCENDING)) {
            clickElement(sortByRatingButton);
            ratingSort = getSortingStatus(sorting);
        }

    }

    public void setSortByRatingButtontoAsc(Sorting sorting) {

        Sorting ratingSort = getSortingStatus(sorting);
        while(!ratingSort.equals(Sorting.ASCENDING)) {
            clickElement(sortByRatingButton);
            ratingSort = getSortingStatus(sorting);
        }

    }

    public void setSortByPriceButtontoAsc(Sorting sorting) {

        Sorting ratingSort = getSortingStatus(sorting);
        while(!ratingSort.equals(Sorting.ASCENDING)) {
            clickElement(sortByPriceButton);
            ratingSort = getSortingStatus(sorting);
        }
    }

    public void setSortByPriceButtontoDesc(Sorting sorting) {

        Sorting ratingSort = getSortingStatus(sorting);
        while(!ratingSort.equals(Sorting.DESCENDING)) {
            clickElement(sortByPriceButton);
            ratingSort = getSortingStatus(sorting);
        }
    }

    public void setSortByDepartureButtontoAsc(Sorting sorting) {

        Sorting ratingSort = getSortingStatus(sorting);
        while(!ratingSort.equals(Sorting.ASCENDING)) {
            clickElement(sortByDepartureButton);
            ratingSort=getSortingStatus(sorting);
        }
    }

    public void setSortByDepartureButtontoDesc(Sorting sorting) {

        Sorting ratingSort = getSortingStatus(sorting);
        while(!ratingSort.equals(Sorting.DESCENDING)) {
            clickElement(sortByDepartureButton);
            ratingSort=getSortingStatus(sorting);
        }
    }
}