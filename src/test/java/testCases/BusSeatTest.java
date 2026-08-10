package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.BaseClass;

import java.time.LocalDate;

public class BusSeatTest extends BaseClass {


    @Test
    public void openSeatLayoutTest(){
        helper.searchBuses("Kolkata","Burdwan", LocalDate.now().plusDays(5));
        sp.clickViewSeatsButtonForCard(1);
        Assert.assertTrue(busseatpage.isSeatLayoutVisible(),"Seat Layout not visible");
    }

    @Test
    public void clickViewSeatsButtonForCard(){
        helper.searchBuses("Kolkata","Burdwan", LocalDate.now().plusDays(5));
        sp.clickViewSeatsButtonByOperator("Royal Cruiser");
        Assert.assertTrue(busseatpage.isSeatAvailable(5),"Seat 5 is not available");
        busseatpage.selectSeat(5);
        Assert.assertTrue(busseatpage.isSeatSelected(5),"Seat 5 is not selected");

    }

    @Test
    public void soldSeatsDisabled(){
        helper.searchBuses("Kolkata","Burdwan", LocalDate.now().plusDays(5));
        sp.clickViewSeatsButtonByOperator("Royal Cruiser");
        Assert.assertTrue(busseatpage.areSoldSeatDisabled(),"Sold seats are not disabled");
    }





}
