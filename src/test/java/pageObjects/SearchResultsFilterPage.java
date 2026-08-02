package pageObjects;

import enums.FilterChoice;
import enums.FilterHeaders;
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
    private WebElement filterHeaderCheckbox;

    private final By filterChoiceCheckbox =
            By.xpath(".//div[contains(@class,'listItem___') and @role='checkbox']");


    //Click Filter Header to open the dropdown


    //Get the required element whose child has the required filter
    public WebElement getFilterHeaderDropdownElement(FilterHeaders  filterChoice) {
        waitForAllFiltersDisplayed();
        return filterHeaderDropdowns.stream()
                .filter(element -> element.getText().equalsIgnoreCase(filterChoice.getHeader()))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Filter not found: " + filterChoice.getHeader()));

    }

    //Click the required element whose has the required filter
    public void clickFilterHeaderDropdown(FilterHeaders filterChoice) {
        getFilterHeaderDropdownElement(filterChoice).click();
    }

    //Choose the required filter from the dropdown
    public WebElement getFilterFromDropdown(FilterHeaders filterheader,FilterChoice filterChoice) {
        WebElement filterHeaderDropdown = getFilterHeaderDropdownElement(filterheader);
        wait.until(ExpectedConditions.visibilityOfAllElements(filterHeaderCheckboxes));

        List<WebElement> checkboxes = filterHeaderDropdown.findElements(filterChoiceCheckbox);
        System.out.println("Selected Header:");
        System.out.println(filterHeaderDropdown.getAttribute("outerHTML"));

        System.out.println("Checkbox count = " + checkboxes.size());

        for (WebElement checkbox : checkboxes) {
            System.out.println("Text = [" + checkbox.getText() + "]");
        }

        return checkboxes.stream()
                .peek(e -> System.out.println("Checkbox Text: [" + e.getText() + "]"))
                .filter(e -> e.getText().equalsIgnoreCase(filterChoice.getChoice()))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Filter not found in dropdown: " + filterChoice.getChoice()));
    }

    public void selectFilterOption(FilterHeaders header, FilterChoice option) {

        clickFilterHeaderDropdown(header);

        getFilterFromDropdown(header,option).click();
    }

    public void waitForAllFiltersDisplayed() {
        wait.until(ExpectedConditions.visibilityOfAllElements(filterHeaderDropdowns));
    }


    public void waitForFiltersToApply() {
        wait.until(ExpectedConditions.stalenessOf(
                driver.findElement(By.xpath("//li[contains(@class,'tupleWrapper')][1]"))));
    }
}

