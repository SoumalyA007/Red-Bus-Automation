package testCases;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import testBase.BaseClass;

public class HomePageTest extends BaseClass {

    private static final long MAX_ACCEPTABLE_PAGE_LOAD_TIME_MS = 15000;

    private HomePage homePage;

    @BeforeClass
    public void initializeHomePage() {
        homePage = new HomePage(driver);
    }

    // Functional Test: Verify page loads successfully
    @Test(priority = 1)
    public void TC_001_verify_page_loads_successfully() {
        String testName = "TC_001_verify_page_loads_successfully";
        logTestStart(testName);

        try {
            Assert.assertTrue(homePage.isPageLoadedSuccessfully(), "Home page did not load successfully");
            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    // Functional Test: Verify page loads under acceptable time
    @Test(priority = 2)
    public void TC_002_verify_page_loads_under_acceptable_time() {
        String testName = "TC_002_verify_page_loads_under_acceptable_time";
        logTestStart(testName);

        try {
            long pageLoadTime = getPageLoadTimeInMillis(p.getProperty("uri"));
            homePage.waitForPageToLoad();
            log.info(testName + " ------ > Page load time: " + pageLoadTime + " ms");

            Assert.assertTrue(pageLoadTime > 0, "Page load time was not captured correctly");
            Assert.assertTrue(
                    pageLoadTime <= MAX_ACCEPTABLE_PAGE_LOAD_TIME_MS,
                    "Page load time exceeded " + MAX_ACCEPTABLE_PAGE_LOAD_TIME_MS + " ms. Actual: " + pageLoadTime + " ms");
            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    // Functional Test: Verify no broken images
    @Test(priority = 3)
    public void TC_003_verify_no_broken_images() {
        String testName = "TC_003_verify_no_broken_images";
        logTestStart(testName);

        try {
            List<String> brokenImages = homePage.getBrokenImageSources();
            log.info(testName + " ------ > Broken image count: " + brokenImages.size());

            Assert.assertTrue(brokenImages.isEmpty(), "Broken images found on home page: " + brokenImages);
            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    // Functional Test: Verify all major links are clickable
    @Test(priority = 4)
    public void TC_004_verify_all_major_links_are_clickable() {
        String testName = "TC_004_verify_all_major_links_are_clickable";
        logTestStart(testName);

        try {
            List<String> nonClickableElements = homePage.getNonClickableMajorElements();
            log.info(testName + " ------ > Non-clickable major elements: " + nonClickableElements);

            Assert.assertTrue(nonClickableElements.isEmpty(), "Major home page elements are not clickable: " + nonClickableElements);
            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    // Functional Test: Verify page refresh retains functionality
    @Test(priority = 5)
    public void TC_005_verify_page_refresh_retains_functionality() {
        String testName = "TC_005_verify_page_refresh_retains_functionality";
        logTestStart(testName);

        try {
            driver.navigate().refresh();
            homePage.waitForPageToLoad();

            Assert.assertTrue(homePage.isPageLoadedSuccessfully(), "Home page functionality was not retained after refresh");
            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    // Checking whether logo is displayed or not
    @Test(priority = 6)
    public void TC_006_is_logo_displayed() {
        String testName = "TC_006_is_logo_displayed";
        logTestStart(testName);

        try {
            Assert.assertTrue(homePage.isLogoDisplayed(), "redBus logo is not displayed");
            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    // Checking whether journey from and journey to is displayed or not
    @Test(priority = 7)
    public void TC_007_is_journey_displayed() {
        String testName = "TC_007_is_journey_displayed";
        logTestStart(testName);

        try {
            Assert.assertTrue(homePage.isJourneyFromDisplayed(), "Journey from field is not displayed");
            Assert.assertTrue(homePage.isJourneyToDisplayed(), "Journey to field is not displayed");
            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    // Checking whether calendar is displayed or not
    @Test(priority = 8)
    public void TC_008_is_calendar_displayed() {
        String testName = "TC_008_is_calendar_displayed";
        logTestStart(testName);

        try {
            Assert.assertTrue(homePage.isCalendarVisible(), "Journey date calendar is not displayed");
            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    // Checking whether search button is enabled or not
    @Test(priority = 9)
    public void TC_009_is_search_button_enabled() {
        String testName = "TC_009_is_search_button_enabled";
        logTestStart(testName);

        try {
            Assert.assertTrue(homePage.isSearchButtonEnabled(), "Search buses button is not enabled");
            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    // Checking whether title is correct or not
    @Test(priority = 10)
    public void TC_010_verify_title() {
        String testName = "TC_010_verify_title";
        logTestStart(testName);

        try {
            Assert.assertEquals(
                    driver.getTitle(),
                    "Bus Booking Online and Train Tickets at Lowest Price - redBus",
                    "Title did not match");
            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    // Checking whether footer is displayed or not
    @Test(priority = 11)
    public void TC_011_is_footer_displayed() {
        String testName = "TC_011_is_footer_displayed";
        logTestStart(testName);

        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement element = driver.findElement(By.xpath("//h3[normalize-space()='Can I book a Government bus ticket on redBus?']"));
            js.executeScript("arguments[0].scrollIntoView(true);", element);

            Assert.assertTrue(homePage.isFooterDisplayed(), "Footer is not displayed");
            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test(priority = 12)
    public void TC_012_is_account_button_displayed() {
        String testName = "TC_012_is_account_button_displayed";
        logTestStart(testName);

        try {
            Assert.assertTrue(homePage.isAccountButtonDisplayed(), "Account button is not displayed");
            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test(priority = 13)
    public void TC_013_is_login_button_displayed() {
        String testName = "TC_013_is_login_button_displayed";
        logTestStart(testName);

        try {
            homePage.clickAccountButton();

            Assert.assertTrue(homePage.isLoginButtonDisplayed(), "Login button is not displayed");
            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }



}
