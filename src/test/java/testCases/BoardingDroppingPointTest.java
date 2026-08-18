package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.BaseClass;

import java.time.LocalDate;
import java.util.List;

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

    @Test
    public void select_boarding_point_updates_selection(){
        helper.searchBuses("Kolkata","Burdwan", LocalDate.now().plusDays(5));
        sp.clickViewSeatsButtonForCard(1);

        busseatpage.selectSeat("5");
        Assert.assertTrue(busseatpage.isSeatSelected("5"), "Seat 5 could not be selected");

        boardingdroppingpoint.clickBoardingDroppingPointButton();
        boardingdroppingpoint.clickBoardingPointByIndex(1);
        Assert.assertTrue(boardingdroppingpoint.isBoardingPointCheckedByIndex(1), "Boarding point at index 1 is not checked");
        boardingdroppingpoint.clickBoardingPointByName("SALAP");
        Assert.assertTrue(boardingdroppingpoint.isBoardingPointCheckedByName("SALAP"), "Boarding point with name 'SALAP' is not checked");
    }

    @Test
    public void verify_all_boarding_times_displayed(){

        helper.searchBuses("Kolkata","Burdwan", LocalDate.now().plusDays(5));
        sp.clickViewSeatsButtonForCard(1);

        busseatpage.selectSeat("5");
        Assert.assertTrue(busseatpage.isSeatSelected("5"), "Seat 5 could not be selected");

        boardingdroppingpoint.clickBoardingDroppingPointButton();
        Assert.assertTrue(boardingdroppingpoint.areAllBoardingTimesDisplayed(), "Not all boarding times are displayed");
    }




}
