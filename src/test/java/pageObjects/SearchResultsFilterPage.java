package pageObjects;

import enums.FilterChoice;
import enums.FilterHeaders;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.NoSuchElementException;

public class SearchResultsFilterPage extends BasePage {

    public SearchResultsFilterPage(WebDriver driver) {
        super(driver);
    }

    // ----------------------------------------------------------------
    // Locators
    // ----------------------------------------------------------------

    // Clickable filter headers (e.g. "Bus type", "Departure time")
    @FindBy(xpath = "//div[contains(@class,'container___') and @role='button']")
    private List<WebElement> filterHeaderDropdowns;

    // Relative locator for checkbox rows *within* a resolved panel.

    private static final By CHECKBOX_ROW = By.xpath(".//div[@role='checkbox']");

    // ----------------------------------------------------------------
    // Waits
    // ----------------------------------------------------------------

    public void waitForAllFiltersDisplayed() {
        wait.until(ExpectedConditions.visibilityOfAllElements(filterHeaderDropdowns));
    }

    public void waitForFiltersToApply() {
        wait.until(ExpectedConditions.stalenessOf(
                driver.findElement(By.xpath("//li[contains(@class,'tupleWrapper')][1]"))));
    }

    // ----------------------------------------------------------------
    // Header lookup / interaction
    // ----------------------------------------------------------------

    // Get the header element for a given filter group (e.g. "Bus type")
    public WebElement getFilterHeaderDropdownElement(FilterHeaders filterHeader) {
        waitForAllFiltersDisplayed();
        return filterHeaderDropdowns.stream()
                .filter(element -> element.getText().equalsIgnoreCase(filterHeader.getHeader()))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Filter header not found: " + filterHeader.getHeader()));
    }

    // Click a header to expand/collapse its dropdown panel
    public void clickFilterHeaderDropdown(FilterHeaders filterHeader) {
        getFilterHeaderDropdownElement(filterHeader).click();
    }

    // ----------------------------------------------------------------
    // Panel resolution
    // ----------------------------------------------------------------

    // Resolve the checkbox panel that belongs to a given header via aria-controls -> id.
    // This is what guarantees we only ever search checkboxes under the chosen header,
    // even if another filter group's panel is also expanded elsewhere on the page.
    private WebElement getFilterPanel(FilterHeaders filterHeader) {
        WebElement header = getFilterHeaderDropdownElement(filterHeader);
        String panelId = header.getAttribute("aria-controls");

        if (panelId == null || panelId.isBlank()) {
            throw new NoSuchElementException(
                    "Header '" + filterHeader.getHeader() + "' has no aria-controls attribute");
        }

        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(panelId)));
    }

    // ----------------------------------------------------------------
    // Checkbox lookup / interaction
    // ----------------------------------------------------------------

    // Find a specific checkbox (by visible text) inside the given header's panel
    public WebElement getFilterFromDropdown(FilterHeaders filterHeader, FilterChoice filterChoice) {
        WebElement panel = getFilterPanel(filterHeader);
        List<WebElement> checkboxes = panel.findElements(CHECKBOX_ROW);

        return checkboxes.stream()
                .filter(e -> e.getText().equalsIgnoreCase(filterChoice.getChoice()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Filter option '" + filterChoice.getChoice()
                                + "' not found under header '" + filterHeader.getHeader() + "'"));
    }

    // Whether the given checkbox is currently selected, per its aria-checked attribute
    public boolean isFilterSelected(WebElement checkbox) {
        return Boolean.parseBoolean(checkbox.getAttribute("aria-checked"));
    }

    // Open the header, then select the requested checkbox option within its panel.
    // No-op if it's already selected - avoids accidentally deselecting it.
    public WebElement selectFilterOption(FilterHeaders header, FilterChoice option) {
        return setFilterState(header, option, true);
    }

    // Open the header, then deselect the requested checkbox option within its panel.
    // No-op if it's already deselected.
    public WebElement deselectFilterOption(FilterHeaders header, FilterChoice option) {
        return setFilterState(header, option, false);
    }

    // Only clicks the checkbox if its current aria-checked state differs from desiredSelected
    private WebElement setFilterState(FilterHeaders header, FilterChoice option, boolean desiredSelected) {
        clickFilterHeaderDropdown(header);
        WebElement checkbox = getFilterFromDropdown(header, option);

        if (isFilterSelected(checkbox) != desiredSelected) {
            checkbox.click();
        }
        return checkbox;
    }
}