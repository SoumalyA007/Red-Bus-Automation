package testCases;

import java.time.LocalDate;

import org.testng.Assert;
import org.testng.annotations.Test;

import testBase.BaseClass;

public class BusSearchTest extends BaseClass {

    @Test
    public void TC_001_check_route() throws InterruptedException {

        helper.searchBuses("Delhi", "Burdwan", LocalDate.now().plusDays(3));
        //Thread.sleep(5000);
        hp.isLogoDisplayed();
        
        String[] routes = sp.getRoute();
        Assert.assertEquals("Delhi", routes[0], "Your source is not correct");
        Assert.assertEquals("Burdwan", routes[1], "Your destination is not correct");

    }

}
