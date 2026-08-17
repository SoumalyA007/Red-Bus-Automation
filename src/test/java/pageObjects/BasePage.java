package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Centralized page base that provides robust element actions for all pages.
 * Pages should continue to extend this class and call super(driver).
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // sensible defaults - make configurable later via config.properties if desired
    protected static final int DEFAULT_TIMEOUT_SECONDS = 10;
    protected static final int SHORT_TIMEOUT_SECONDS = 5;
    protected static final int STALE_RETRY_COUNT = 3;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS));
        PageFactory.initElements(driver, this);
    }

    // -------------------------
    // Basic find helpers
    // -------------------------
    public WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    public WebElement findElement(By parentLocator,  By locator) {
        WebElement parentElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(parentLocator)
        );

        return parentElement.findElement(locator);
    }

    public WebElement findElement(WebElement parentElement, By locator) {
        return parentElement.findElement(locator);
    }

    public List<WebElement> findElements(By locator) {
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(locator)
        );
    }

    // -------------------------
    // Wait wrappers
    // -------------------------
    public void waitForElementVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public boolean isElementDisplayed(WebElement element) {
        try {
            waitForElementVisibility(element);
            return element.isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementClickable(WebElement element) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(SHORT_TIMEOUT_SECONDS));
            shortWait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    // -------------------------
    // Scrolling / JS helpers
    // -------------------------
    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // -------------------------
    // Action helpers
    // -------------------------
    public void clickElement(WebElement element) {
        clickElement(element, DEFAULT_TIMEOUT_SECONDS);
    }

    public void clickElement(WebElement element, int timeoutSeconds) {
        if (element == null) {
            throw new IllegalArgumentException("Element to click must not be null");
        }

        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        try {
            customWait.until(ExpectedConditions.elementToBeClickable(element));
            attemptClickWithRetries(element);
        } catch (TimeoutException te) {
            // fallback: try JS click as a last resort
            try {
                jsClick(element);
            } catch (Exception e) {
                throw new RuntimeException("Element not clickable within " + timeoutSeconds + "s and JS click failed", e);
            }
        }
    }

    /**
     * Attempts to click an element with automatic retry on StaleElementReferenceException.
     * Uses WebDriverWait-based polling instead of Thread.sleep() to avoid blocking.
     *
     * @param element the WebElement to click
     */
    private void attemptClickWithRetries(WebElement element) {
        StaleElementReferenceException lastException = null;

        for (int attempt = 0; attempt <= STALE_RETRY_COUNT; attempt++) {
            try {
                element.click();
                return;  // success
            } catch (StaleElementReferenceException sere) {
                lastException = sere;
                if (attempt < STALE_RETRY_COUNT) {
                    // Wait briefly for DOM to stabilize using WebDriverWait instead of sleep
                    try {
                        new WebDriverWait(driver, Duration.ofMillis(500))
                                .until(driver -> {
                                    // Condition will timeout if element stays stale
                                    try {
                                        element.isDisplayed();
                                        return true;
                                    } catch (StaleElementReferenceException e) {
                                        return false;
                                    }
                                });
                    } catch (TimeoutException ignored) {
                        // Element is still stale, will retry in next iteration
                    }
                }
            } catch (WebDriverException e) {
                // fallback to JS click for intercepted or other click errors
                try {
                    jsClick(element);
                    return;
                } catch (Exception jsException) {
                    throw new RuntimeException("Normal click failed and JS click also failed: " + e.getMessage(), jsException);
                }
            }
        }

        // All retries exhausted
        if (lastException != null) {
            throw new RuntimeException("Element remained stale after " + STALE_RETRY_COUNT + " retries", lastException);
        }
    }

    public void clearAndSendKeys(WebElement element, String text) {
        try {
            waitForElementVisibility(element);
            element.clear();
            element.sendKeys(text);
        } catch (TimeoutException te) {
            throw new RuntimeException("Element not visible to type text", te);
        }
    }

    public void sendKeys(WebElement element, String text) {
        try {
            waitForElementVisibility(element);
            element.sendKeys(text);
        } catch (TimeoutException te) {
            throw new RuntimeException("Element not visible to send keys", te);
        }
    }

    public String getText(WebElement element) {
        try {
            waitForElementVisibility(element);
            return element.getText();
        } catch (TimeoutException te) {
            throw new RuntimeException("Element not visible to get text", te);
        }
    }

    public String getAttributeValue(WebElement element, String attributeName) {
        try {
            waitForElementVisibility(element);
            return element.getAttribute(attributeName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get attribute '" + attributeName + "' from element", e);
        }
    }

    // Convenience: click by locator
    public void click(By locator) {
        WebElement el = findElement(locator);
        clickElement(el);
    }

    // Convenience: send keys by locator
    public void sendKeys(By locator, String text) {
        WebElement el = findElement(locator);
        sendKeys(el, text);
    }
}