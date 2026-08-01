package pageObjects;

import enums.FilterChoice;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class SearchResultsFilterPage extends BasePage {

    public SearchResultsFilterPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[contains(@class,'container___') and @role='button']")
    private List<WebElement> filterHeaderDropdowns;

    @FindBy(xpath = "//div[contains(@class,'listItem___') and @role='checkbox']")
    private List<WebElement> filterHeaderCheckboxes;

    @FindBy(xpath = "//div[contains(@class,'listItem___') and @role='checkbox']")
    private WebElement filterHeaderCheckboxe;



    //Click Filter Header to open the dropdown

    //Click the required element whose has the required filter
    public void clickFilterHeaderDropdown(FilterChoice filterChoice) {
        getFilterHeaderDropdownElement(filterChoice).click();
    }

    //Get the required element whose child has the required filter
    public WebElement getFilterHeaderDropdownElement(FilterChoice  filterChoice) {
        waitForAllFiltersDisplayed();
        return filterHeaderDropdowns.stream()
                .filter(element -> element.getText().equalsIgnoreCase(filterChoice.getChoice()))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Filter not found: " + filterChoice.getChoice()));

    }

    //Choose the required filter from the dropdown
    public WebElement getFilterFromDropdown(FilterChoice filterChoice) {
        WebElement filterHeaderDropdown = getFilterHeaderDropdownElement(filterChoice);
        wait.until(ExpectedConditions.visibilityOfAllElements(filterHeaderCheckboxes));
        return filterHeaderCheckboxes.stream()
                .filter(element -> element.getText().equalsIgnoreCase(filterChoice.getChoice()))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Filter not found in dropdown: " + filterChoice.getChoice()));
    }

    public void selectFilterOption(FilterChoice header, FilterChoice option) {

        clickFilterHeaderDropdown(header);

        getFilterFromDropdown(option).click();
    }

    public void waitForAllFiltersDisplayed() {
        wait.until(ExpectedConditions.visibilityOfAllElements(filterHeaderDropdowns));
    }


    public void waitForFiltersToApply() {
        wait.until(ExpectedConditions.stalenessOf(
                driver.findElement(By.xpath("//li[contains(@class,'tupleWrapper')][1]"))));
    }
}

