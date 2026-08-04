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

    // Relative locator for the search input *within* a resolved filter container.
    private static final By SEARCH_INPUT = By.xpath(".//input[contains(@class,'searchInput')]");

    // Relative locator for the "View all" expand button within a filter wrapper.
    private static final By VIEW_ALL_OPTIONS = By
            .xpath(".//div[contains(@class,'actionWrap___') and contains(@style,'--button')]");

    // ----------------------------------------------------------------
    // Waits
    // ----------------------------------------------------------------

    public void waitForAllFiltersDisplayed() {
        wait.until(ExpectedConditions.visibilityOfAllElements(filterHeaderDropdowns));
    }

    public void waitForFiltersToApply() {
        WebElement firstCard = driver.findElement(By.xpath("//li[contains(@class,'tupleWrapper')][1]"));
        wait.until(ExpectedConditions.stalenessOf(firstCard));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[contains(@class,'tupleWrapper')][1]")));
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
                .orElseThrow(() -> new NoSuchElementException("Filter header not found: " + filterHeader.getHeader()));
    }

    // Click a header to expand/collapse its dropdown panel
    public void clickFilterHeaderDropdown(FilterHeaders filterHeader) {
        getFilterHeaderDropdownElement(filterHeader).click();
    }

    // ----------------------------------------------------------------
    // Panel resolution
    // ----------------------------------------------------------------

    // Resolve the checkbox panel that belongs to a given header via aria-controls
    // -> id.
    // This is what guarantees we only ever search checkboxes under the chosen
    // header,
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
    // Wrapper resolution [Point 6]
    // Both getFilterSearchBox and clickViewAllOptions used to duplicate
    // the same "./ancestor::div[contains(@class,'mainListWrapper')][1]"
    // traversal inline. This single private method owns that logic now.
    // ----------------------------------------------------------------

    private WebElement getFilterWrapper(FilterHeaders filterHeader) {
        return getFilterPanel(filterHeader)
                .findElement(By.xpath("./ancestor::div[contains(@class,'mainListWrapper')][1]"));
    }

    // Locate the search input scoped to the wrapper that contains both the panel
    // and its search box.
    private WebElement getFilterSearchBox(FilterHeaders filterHeader) {
        return getFilterWrapper(filterHeader).findElement(SEARCH_INPUT);
    }

    // Click the "View all options" expand button for a filter
    public void clickViewAllOptions(FilterHeaders filterHeader) {
        getFilterWrapper(filterHeader).findElement(VIEW_ALL_OPTIONS).click();
    }

    // ----------------------------------------------------------------
    // Checkbox lookup — single private helper [Point 2]
    // Previously the same 4-line "stream-filter by text → orElseThrow"
    // pattern was copy-pasted into getFilterFromDropdown,
    // isFilterSelected(header, searchText), and searchAndSelectFilterOption.
    // All three now delegate to this one method.
    // ----------------------------------------------------------------

    // Core lookup: finds a checkbox by visible text inside a pre-resolved panel
    // element.
    private WebElement findCheckboxByText(WebElement panel, String text) {
        List<WebElement> checkboxes = panel.findElements(CHECKBOX_ROW);
        return checkboxes.stream()
                .filter(e -> e.getText().toLowerCase().contains(text.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Filter option '" + text + "' not found in panel"));
    }

    // Convenience overload: resolves the panel from the header, then delegates
    // above.
    private WebElement findCheckboxByText(FilterHeaders header, String text) {
        return findCheckboxByText(getFilterPanel(header), text);
    }

    // ----------------------------------------------------------------
    // Checkbox lookup / interaction — public API
    // ----------------------------------------------------------------

    // Find a specific checkbox (by visible text) inside the given header's panel
    public WebElement getFilterFromDropdown(FilterHeaders filterHeader, FilterChoice filterChoice) {
        return findCheckboxByText(filterHeader, filterChoice.getChoice());
    }

    // Whether the given checkbox is currently selected, per its aria-checked
    // attribute
    public boolean isFilterSelected(WebElement checkbox) {
        return Boolean.parseBoolean(checkbox.getAttribute("aria-checked"));
    }

    // Overload: looks up the checkbox by text first, then checks its state.
    // Replaces the old inline panel-fetch + loop + stream that was a copy of
    // getFilterFromDropdown.
    public boolean isFilterSelected(FilterHeaders header, String searchText) {
        return isFilterSelected(findCheckboxByText(header, searchText));
    }

    // Whether the given checkbox is disabled
    public boolean isFilterDisabled(WebElement checkbox) {
        return Boolean.parseBoolean(checkbox.getAttribute("aria-disabled"));
    }

    // Open the header, then select the requested checkbox option within its panel.
    // No-op if it's already selected - avoids accidentally deselecting it.
    public WebElement selectFilterOption(FilterHeaders header, FilterChoice option) {
        return setFilterState(header, option, true);
    }

    // Open the header, then deselect the requested checkbox option within its
    // panel.
    // No-op if it's already deselected.
    public WebElement deselectFilterOption(FilterHeaders header, FilterChoice option) {
        return setFilterState(header, option, false);
    }

    // ----------------------------------------------------------------
    // State management [Point 7]
    // Old version: called clickFilterHeaderDropdown (→
    // getFilterHeaderDropdownElement
    // → waitForAllFilters + stream search), then getFilterFromDropdown (→
    // getFilterPanel
    // → getFilterHeaderDropdownElement → waitForAllFilters + stream search again).
    // That was two full header scans per selectFilterOption call.
    //
    // New version: fetches the header element exactly once, extracts the panelId
    // from it, checks panel visibility before clicking (fixes the accidental
    // toggle-close bug), then resolves the panel directly by id — no second scan.
    // ----------------------------------------------------------------

    private WebElement setFilterState(FilterHeaders header, FilterChoice option, boolean desiredSelected) {
        // Single header lookup — reused for both the click guard and the panel id.
        WebElement headerEl = getFilterHeaderDropdownElement(header);
        String panelId = headerEl.getAttribute("aria-controls");

        if (panelId == null || panelId.isBlank()) {
            throw new NoSuchElementException(
                    "Header '" + header.getHeader() + "' has no aria-controls attribute");
        }

        // Check the header's aria-expanded attribute — it's already on the element we hold,
        // so no DOM search is needed and the implicit wait is never triggered.
        // driver.findElements(By.id(panelId)) was used here before, but that caused a
        // 5-second implicit-wait pause every time the panel wasn't yet in the DOM.
        boolean panelAlreadyOpen = "true".equalsIgnoreCase(headerEl.getAttribute("aria-expanded"));
        if (!panelAlreadyOpen) {
            headerEl.click();
        }

        // Resolve the panel directly from the id — no second header scan needed.
        WebElement panel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(panelId)));
        WebElement checkbox = findCheckboxByText(panel, option.getChoice());

        if (isFilterSelected(checkbox) != desiredSelected && !isFilterDisabled(checkbox)) {
            checkbox.click();
        }

        return checkbox;
    }

    // ----------------------------------------------------------------
    // Search-inside-filter [Point 3]
    // Removed 'throws InterruptedException' — there is no Thread.sleep()
    // or blocking interruptible call anywhere in this method.
    // Also removed System.out.println debug lines (replaced by log.debug).
    // ----------------------------------------------------------------

    public WebElement searchAndSelectFilterOption(FilterHeaders filterHeader, String searchText) {
        clickFilterHeaderDropdown(filterHeader);

        WebElement searchBox = getFilterSearchBox(filterHeader);
        searchBox.clear();
        searchBox.sendKeys(searchText);

        // The list re-renders after typing — re-fetch the panel's checkboxes NOW,
        // not before, otherwise you'll be holding stale/unfiltered elements.
        WebElement panel = getFilterPanel(filterHeader);

        WebElement match = findCheckboxByText(panel, searchText);

        if (!isFilterSelected(match) && !isFilterDisabled(match)) {
            match.click();
        }

        return match;
    }
}