package testBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import components.SearchBarComponents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import pageObjects.*;
import utils.HelperFunctions;

public class BaseClass {

    public static WebDriver driver;
    public Logger log;
    public Properties p;
    public WebDriverWait wait;
    public HomePage hp;
    public HelperFunctions helper;
    public SearchPage sp;
    public SearchBarComponents searchbarcomponents;
    public SearchResultsFilterPage searchresultsfilterpage;
    public SearchResultsSortingPage searchresultssortingpage;
    public BusSeatPage busseatpage;

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    @SuppressWarnings("null")
    @BeforeClass
    @Parameters({"OS", "browser"})
    public void setup(@Optional("windows") String OS, @Optional("chrome") String browser) throws FileNotFoundException, IOException{
        FileReader file = new FileReader(System.getProperty("user.dir") + "/src/test/resources/config.properties");
        p = new Properties();
        p.load(file);

        log = LogManager.getLogger(this.getClass());

        // FIXED: assign to class field, not a local variable
        driver = createDriver(OS, browser);  // creates a fresh driver for this thread
        driverThreadLocal.set(driver);

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(p.getProperty("uri"));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        hp = new HomePage(driver);
        helper = new HelperFunctions(driver, wait);
        sp = new SearchPage(driver);
        searchbarcomponents = new SearchBarComponents(driver);
        searchresultsfilterpage = new SearchResultsFilterPage(driver);
        searchresultssortingpage = new SearchResultsSortingPage(driver);
        busseatpage =  new BusSeatPage(driver);

    }

    private WebDriver createDriver(String OS, String browser) throws IOException {
        if (p.getProperty("executionEnv").equalsIgnoreCase("local")) {
            switch (browser.toLowerCase()) {
                case "chrome":  return new ChromeDriver();
                case "firefox": return new FirefoxDriver();
                case "edge":    return new EdgeDriver();
                default: throw new IllegalArgumentException("Invalid browser: " + browser);
            }
        } else {
            URL gridURL = new URL("http://localhost:4444/wd/hub");
            MutableCapabilities options;
            switch (browser.toLowerCase()) {
                case "chrome":  options = new ChromeOptions(); break;
                case "firefox": options = new FirefoxOptions(); break;
                case "edge":    options = new EdgeOptions(); break;
                default: throw new IllegalArgumentException("Invalid browser: " + browser);
            }
            switch (OS.toLowerCase()) {
                case "linux":   options.setCapability("platformName", "linux"); break;
                case "windows": options.setCapability("platformName", "windows"); break;
                case "mac":     options.setCapability("platformName", "mac"); break;
            }
            return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
        }
    }

    //    @AfterClass
    //    public void tearDown() {
    //        driverThreadLocal.get().quit();
    //        driverThreadLocal.remove();   // prevents memory leaks
    //    }

    public WebDriver getDriver() {
        return driver;
    }

    public long getPageLoadTimeInMillis(String url) {
        long startTime = System.nanoTime();
        driver.get(url);
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(driver ->
                        ((JavascriptExecutor) driver)
                                .executeScript("return document.readyState")
                                .equals("complete"));
        long endTime = System.nanoTime();

        return Duration.ofNanos(endTime - startTime).toMillis();
    }

    public String captureScreen(String tname) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        java.io.File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
        java.io.File targetFile=new java.io.File(targetFilePath);

        sourceFile.renameTo(targetFile);

        return targetFilePath;

    }

    public void logTestStart(String testName) {
        log.info("Running " + testName + " ------ > ");
    }

    public void logTestPass(String testName) {
        log.info(testName + " ------ > PASSED");
    }

    public void logTestFailure(String testName, Throwable e) {
        log.error(testName + " ------ > FAILED. Exception: " + e.getMessage(), e);
        Assert.fail(testName + " failed: " + e.getMessage(), e);
    }

}