package testCases;

import java.time.LocalDate;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.testng.internal.thread.ThreadTimeoutException;
import testBase.BaseClass;

public class BusSearchTest extends BaseClass {

    @Test
    public void TC_001_verify_route_displayed_correctly(){
        String testName = "TC_001_check_route";

        try{
            helper.searchBuses("Delhi", "Burdwan", LocalDate.now().plusDays(10));
            hp.isLogoDisplayed();

            String[] routes = sp.getRoute();
            Assert.assertEquals("Delhi", routes[0], "Your source is not correct");
            Assert.assertEquals("Burdwan", routes[1], "Your destination is not correct");
        }catch (Throwable e){

        }

    }

    @Test
    public void TC_002_bus_cards_displayed(){
        String testName = "TC_002_bus_cards_displayed";
        try{
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(10));
            hp.isLogoDisplayed();
            Assert.assertTrue(sp.waitForBusCardsToLoad(),"Cards did not load");
            Assert.assertTrue(sp.getBusCardsCount()>0,"No bus card found");
            log.info("Number of card displayed {}",sp.getBusCardsCount());
            Assert.assertTrue(sp.areBusCardsDisplayed(),"All Bus Cards not displayed");
        }catch(Throwable e){
            logTestFailure(testName,e);
        }
    }

    @Test
    public void TC_003_date_displayed_correctly(){
        String testName = "TC_003_date_displayed_correctly";
        try{

        }catch (Throwable e){
            logTestFailure(testName,e);
        }
    }
}
