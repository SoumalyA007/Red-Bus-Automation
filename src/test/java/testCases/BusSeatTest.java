package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.BaseClass;

import java.time.LocalDate;

public class BusSeatTest extends BaseClass {


    @Test
    public void clickViewSeatsButtonForCard(){
        helper.searchBuses("Kolkata","Burdwan", LocalDate.now().plusDays(5));
        sp.clickViewSeatsButtonByOperator("Royal Cruiser");
        Assert.assertFalse(busseatpage.isSeatAvailable(5),"Seat 5 is not available");
        busseatpage.selectSeat(5);
        Assert.assertTrue(busseatpage.isSeatSelected(5),"Seat 5 is not available");

    }

    @Test
    public void soldSeatsDisabled(){
        helper.searchBuses("Kolkata","Burdwan", LocalDate.now().plusDays(5));
        sp.clickViewSeatsButtonByOperator("Royal Cruiser");
        Assert.assertTrue(busseatpage.areSoldSeatDisabled(),"Sold seats are not disabled");
    }



}
