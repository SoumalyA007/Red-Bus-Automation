package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import testBase.BaseClass;

public class HomePageTest extends BaseClass {

    HomePage homePage = new HomePage(driver);

    //Checking whether logo is displayed or not
    @Test
    public void TC_001_is_logo_displayed(){
        log.info("Running TC_001_is_logo_displayed ------ > ");
        try{
            Assert.assertTrue(homePage.isLogoDisplayed());
        }catch (Exception e){
            log.error("TC_001_is_logo_displayed ran into error ------ Exception: " +e.getMessage());
            e.getMessage();
        }

    }

    //Checking whether journey from and journey to is displayed or not
    @Test
    public void TC_002_is_journey_displayed(){
        log.info("Running TC_002_is_journey_displayed ------ > ");
        try{
            Assert.assertTrue(homePage.isJourneyFromDisplayed());
            Assert.assertTrue(homePage.isJourneyToDisplayed());
        }catch(Exception e){
            log.error("TC_002_is_journey_displayed ran into error ------ Exception: " +e.getMessage());
            e.getMessage();
        }
    }

    //Checking whether calendar is displayed or not
    @Test
    public void TC_003_is_calendar_displayed(){
        log.info("Running TC_003_is_calendar_displayed ------ > ");
        try{
            Assert.assertTrue(homePage.isCalenderVisible());
        } catch (Exception e) {
            log.error("TC_003_is_calendar_displayed ran into error ------ Exception: " +e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //Checking whether search button is enabled or not
    @Test
    public void TC_004_is_search_button_enabled(){

        log.info("Running TC_004_is_search_button_enabled ------ > ");
        try{
            Assert.assertTrue(homePage.isSearchButtonEnabled());
        }catch (Exception e){
            log.error("TC_004_is_search_button_enabled ran into error------ Exception: " +e.getMessage());
        }
    }

    //Checking whether title is correct or not
    @Test
    public void TC_005_verify_title(){
        log.info("Running TC_005_verify_title ------ > ");
        try{
            Assert.assertEquals(driver.getTitle(), "Bus Booking Online and Train Tickets at Lowest Price - redBus","Title did not match");
        }catch (Exception e){
            log.error("TC_005_verify_title ran into error------ Exception: " +e.getMessage());
        }
    }

    //Checking whether footer is displayed or not
    @Test
    public void TC_006_is_footer_displayed(){
        log.info("Running TC_006_is_footer_displayed ------ > ");
        try{
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement element = driver.findElement(By.xpath("//h3[normalize-space()='Can I book a Government bus ticket on redBus?']"));
            js.executeScript("arguments[0].scrollIntoView(true",element);
            Assert.assertTrue(homePage.isFooterDisplayed());
        }catch (Exception e){
            log.error("TC_006_is_footer_displayed ran into error------ Exception: " +e.getMessage());
        }
    }

    @Test
    public void TC_007_is_acoount_displayed(){
        log.info("Running TC_007_is_account_button_displayed ------ > ");
        try{
            Assert.assertTrue(homePage.isLoginButtonDisplayed(),"Account button is not displayed");
        }catch (Exception e){
            log.error("TC_007_is_account_button_displayed ran into error------ Exception: " +e.getMessage());
        }
    }

    @Test
    public void TC_008_is_login_button_displayed(){
        log.info("Running TC_008_is_login_button_displayed ------ > ");
        try{
            homePage.clickAccountButton();
            Assert.assertTrue(homePage.isLoginButtonDisplayed(),"Login button is not displayed");
        }catch (Exception e){
            log.error("TC_008_is_login_button_displayed ran into error------ Exception: " +e.getMessage());
        }
    }

}