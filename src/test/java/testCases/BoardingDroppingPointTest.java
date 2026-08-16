package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.BaseClass;

import java.time.LocalDate;

public class BoardingDroppingPointTest extends BaseClass {

    @Test
    public void clickBoardingDroppingPointButtonTest(){
        helper.searchBuses("Kolkata","Burdwan", LocalDate.now().plusDays(5));
        sp.clickViewSeatsButtonForCard(1);

        busseatpage.selectSeat("5");
        Assert.assertTrue(busseatpage.isSeatSelected("5"), "Seat 5 could not be selected");

        boardingdroppingpoint.clickBoardingDroppingPointButton();
        Assert.assertTrue(boardingdroppingpoint.isBoardingDroppingPointTabSelected(),"Boarding Dropping Tab is not selected");
    }



}
