package pageObjects;

import enums.SortType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.NoSuchElementException;

public class SortingPage extends BasePage {

    public SortingPage(WebDriver driver) {
        super(driver);
    }

    // ----------------------------------------------------------------
    // Locators
    // ----------------------------------------------------------------

    // Sort dropdown/button selector - typical RedBus sorting container
    @FindBy(xpath = "//div[contains(@class,'sortDropdown')] | //div[@role='button' and contains(text(), 'Sort')]")
    private WebElement sortDropdown;

    // Sort option elements within the dropdown
    @FindBy(xpath = "//div[@role='option'] | //li[contains(@class,'sort')]")
    private List<WebElement> sortOptions;

    // Alternative: clickable sort buttons
    @FindBy(xpath = "//div[contains(@class,'sortOption___')] | //button[contains(@class,'sort')]")
    private List<WebElement> sortButtons;

    // ----------------------------------------------------------------
    // Waits
    // ----------------------------------------------------------------

    public void waitForSortDropdownDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(sortDropdown));
        } catch (Exception e) {
            // Sort dropdown might not be visible, continue
        }
    }

    public void waitForSortOptionsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(sortOptions));
        } catch (Exception e) {
            // Sort options might not be visible yet
        }
    }

    // ----------------------------------------------------------------
    // Sort Option Lookup / Interaction
    // ----------------------------------------------------------------

    // Get the sort dropdown element
    public WebElement getSortDropdownElement() {
        waitForSortDropdownDisplayed();
        return sortDropdown;
    }

    // Click the sort dropdown to open/close it
    public void clickSortDropdown() {
        WebElement dropdown = getSortDropdownElement();
        if (dropdown != null && dropdown.isDisplayed()) {
            dropdown.click();
            waitForSortOptionsDisplayed();
        }
    }

    // Find and click a sort option by type
    public void selectSortOption(SortType sortType) {
        clickSortDropdown();
        WebElement optionElement = findSortOptionByText(sortType.getSortOption());
        if (optionElement != null && !isOptionAlreadySelected(sortType)) {
            optionElement.click();
            // Wait for results to re-render after sorting
            waitForResultsToReload();
        }
    }

    // Find a sort option element by visible text
    private WebElement findSortOptionByText(String text) {
        // First try sortOptions
        if (!sortOptions.isEmpty()) {
            return sortOptions.stream()
                    .filter(element -> element.getText().equalsIgnoreCase(text))
                    .findFirst()
                    .orElse(null);
        }
        // Then try sortButtons
        if (!sortButtons.isEmpty()) {
            return sortButtons.stream()
                    .filter(element -> element.getText().equalsIgnoreCase(text))
                    .findFirst()
                    .orElse(null);
        }
        // Try to find via dynamic XPath if static locators don't work
        try {
            return driver.findElement(By.xpath(
                    "//div[contains(text(), '" + text + "')] | //button[contains(text(), '" + text + "')]"));
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Sort option not found: " + text);
        }
    }

    // Check if a sort option is already selected
    private boolean isOptionAlreadySelected(SortType sortType) {
        WebElement option = findSortOptionByText(sortType.getSortOption());
        if (option == null) return false;
        return option.getAttribute("aria-selected") != null &&
               option.getAttribute("aria-selected").equalsIgnoreCase("true");
    }

    // Wait for bus cards to reload after sorting is applied
    private void waitForResultsToReload() {
        try {
            WebElement firstCard = driver.findElement(By.xpath("//li[contains(@class,'tupleWrapper')][1]"));
            wait.until(ExpectedConditions.stalenessOf(firstCard));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[contains(@class,'tupleWrapper')][1]")));
        } catch (Exception e) {
            // Results might reload differently, continue
        }
    }

    // ----------------------------------------------------------------
    // Verification Methods
    // ----------------------------------------------------------------

    public boolean isSortDropdownDisplayed() {
        try {
            return sortDropdown != null && sortDropdown.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentSortSelection() {
        try {
            WebElement selectedOption = driver.findElement(
                    By.xpath("//div[@aria-selected='true'] | //button[contains(@class, 'selected')]"));
            return selectedOption.getText();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public boolean isSortOptionAvailable(SortType sortType) {
        try {
            findSortOptionByText(sortType.getSortOption());
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
