package testBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

    public static WebDriver driver;
    Logger log;
    public Properties p;

    @SuppressWarnings("null")
    @BeforeClass
    @Parameters({"OS", "browser"})
    public void setup(String OS, String browser) throws FileNotFoundException, IOException{
        FileReader file = new FileReader("C:\\Software Testing\\Selenium\\redbus_automation\\src\\test\\resources\\config.properties");
        p = new Properties();
        p.load(file);

        log = LogManager.getLogger(this.getClass());

        if(p.getProperty("executionEnv").equalsIgnoreCase("local")){
            log.info("Running on Local Environment");
            log.info("OS: " + OS);
            log.info("Browser: " + browser);
            switch (browser.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                default:
                    log.error("Invalid browser specified: " + browser);
                    throw new IllegalArgumentException("Invalid browser specified: " + browser);
            }
        }else if(p.getProperty("executionEnv").equalsIgnoreCase("remote")){
            log.info("Running on Remote Environment");
            // Implement remote WebDriver setup here
        }

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(p.getProperty("uri"));
        driver.manage().window().maximize();
    }


    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    public WebDriver getDriver() {
        return driver;
    }

    public String captureScreen(String tname) throws IOException {

		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
		File targetFile=new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
			
		return targetFilePath;

	}
    
}
