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

    @FindBy(xpath = "//div[contains(@class,'container___') and @role='button']")
    private List<WebElement> filterHeaderDropdowns;

    private static final By CHECKBOX_ROW = By.xpath(".//div[@role='checkbox']");
    private static final By SEARCH_INPUT = By.xpath(".//input[contains(@class,'searchInput')]");
    private static final By VIEW_ALL_OPTIONS = By
            .xpath(".//div[contains(@class,'actionWrap___') and contains(@style,'--button')]");

    public void waitForAllFiltersDisplayed() {
        wait.until(ExpectedConditions.visibilityOfAllElements(filterHeaderDropdowns));
    }

    public void waitForFiltersToApply() {
        WebElement firstCard = driver.findElement(By.xpath("//li[contains(@class,'tupleWrapper')][1]"));
        wait.until(ExpectedConditions.stalenessOf(firstCard));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[contains(@class,'tupleWrapper')][1]")));
    }

    public WebElement getFilterHeaderDropdownElement(FilterHeaders filterHeader) {
        waitForAllFiltersDisplayed();
        return filterHeaderDropdowns.stream()
                .filter(element -> element.getText().equalsIgnoreCase(filterHeader.getHeader()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Filter header not found: " + filterHeader.getHeader()));
    }

    public void clickFilterHeaderDropdown(FilterHeaders filterHeader) {
        clickElement(getFilterHeaderDropdownElement(filterHeader));
    }

    private WebElement getFilterPanel(FilterHeaders filterHeader) {
        WebElement header = getFilterHeaderDropdownElement(filterHeader);
        String panelId = header.getAttribute("aria-controls");

        if (panelId == null || panelId.isBlank()) {
            throw new NoSuchElementException(
                    "Header '" + filterHeader.getHeader() + "' has no aria-controls attribute");
        }

        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(panelId)));
    }

    private WebElement getFilterWrapper(FilterHeaders filterHeader) {
        return getFilterPanel(filterHeader)
                .findElement(By.xpath("./ancestor::div[contains(@class,'mainListWrapper')][1]"));
    }

    private WebElement getFilterSearchBox(FilterHeaders filterHeader) {
        return getFilterWrapper(filterHeader).findElement(SEARCH_INPUT);
    }

    public void clickViewAllOptions(FilterHeaders filterHeader) {
        WebElement wrapper = getFilterWrapper(filterHeader);
        clickElement(wrapper.findElement(VIEW_ALL_OPTIONS));
    }

    private WebElement findCheckboxByText(WebElement panel, String text) {
        List<WebElement> checkboxes = panel.findElements(CHECKBOX_ROW);
        return checkboxes.stream()
                .filter(e -> e.getText().toLowerCase().contains(text.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Filter option '" + text + "' not found in panel"));
    }

    private WebElement findCheckboxByText(FilterHeaders header, String text) {
        return findCheckboxByText(getFilterPanel(header), text);
    }

    public WebElement getFilterFromDropdown(FilterHeaders filterHeader, FilterChoice filterChoice) {
        return findCheckboxByText(filterHeader, filterChoice.getChoice());
    }

    public boolean isFilterSelected(WebElement checkbox) {
        return Boolean.parseBoolean(checkbox.getAttribute("aria-checked"));
    }

    public boolean isFilterSelected(FilterHeaders header, String searchText) {
        return isFilterSelected(findCheckboxByText(header, searchText));
    }

    public boolean isFilterDisabled(WebElement checkbox) {
        return Boolean.parseBoolean(checkbox.getAttribute("aria-disabled"));
    }

    public WebElement selectFilterOption(FilterHeaders header, FilterChoice option) {
        return setFilterState(header, option, true);
    }

    public WebElement deselectFilterOption(FilterHeaders header, FilterChoice option) {
        return setFilterState(header, option, false);
    }

    private WebElement setFilterState(FilterHeaders header, FilterChoice option, boolean desiredSelected) {
        WebElement headerEl = getFilterHeaderDropdownElement(header);
        String panelId = headerEl.getAttribute("aria-controls");

        if (panelId == null || panelId.isBlank()) {
            throw new NoSuchElementException(
                    "Header '" + header.getHeader() + "' has no aria-controls attribute");
        }

        boolean panelAlreadyOpen = "true".equalsIgnoreCase(headerEl.getAttribute("aria-expanded"));
        if (!panelAlreadyOpen) {
            clickElement(headerEl);
        }

        WebElement panel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(panelId)));
        WebElement checkbox = findCheckboxByText(panel, option.getChoice());

        if (isFilterSelected(checkbox) != desiredSelected && !isFilterDisabled(checkbox)) {
            clickElement(checkbox);
        }

        return checkbox;
    }

    public WebElement searchAndSelectFilterOption(FilterHeaders filterHeader, String searchText) {
        clickFilterHeaderDropdown(filterHeader);

        WebElement searchBox = getFilterSearchBox(filterHeader);
        searchBox.clear();
        searchBox.sendKeys(searchText);

        WebElement panel = getFilterPanel(filterHeader);

        WebElement match = findCheckboxByText(panel, searchText);

        if (!isFilterSelected(match) && !isFilterDisabled(match)) {
            clickElement(match);
        }

        return match;
    }
}