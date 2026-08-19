package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.BaseClass;

import java.time.LocalDate;

public class BoardingDroppingPointTest extends BaseClass {

    @Test
    public void clickBoardingDroppingPointButtonTest() {
        String testName = "clickBoardingDroppingPointButtonTest";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
            sp.clickViewSeatsButtonForCard(1);

            busseatpage.selectSeat("5");
            Assert.assertTrue(busseatpage.isSeatSelected("5"), "Seat 5 could not be selected");

            boardingdroppingpoint.clickBoardingDroppingPointButton();
            Assert.assertTrue(boardingdroppingpoint.isBoardingDroppingPointTabSelected(),
                    "Boarding Dropping Tab is not selected");

            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void select_boarding_dropping_point_updates_selection() {
        String testName = "select_boarding_dropping_point_updates_selection";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(1));
            sp.clickViewSeatsButtonForCard(3);

            busseatpage.selectSeat("5");
            Assert.assertTrue(busseatpage.isSeatSelected("5"), "Seat 5 could not be selected");

            boardingdroppingpoint.clickBoardingDroppingPointButton();

            boardingdroppingpoint.clickBoardingPointByIndex(1);
            Assert.assertTrue(boardingdroppingpoint.isBoardingPointCheckedByIndex(1),
                    "Boarding point at index 1 is not checked");

            boardingdroppingpoint.clickDroppingPointByIndex(0);
            boardingdroppingpoint.clickBoardingDroppingPointTab();
            Assert.assertTrue(boardingdroppingpoint.isDroppingPointCheckedByIndex(0),
                    "Dropping point at index 0 is not checked");

            Thread.sleep(5000);


            boardingdroppingpoint.clickBoardingPointByName("Dankuni");
            Assert.assertTrue(boardingdroppingpoint.isBoardingPointCheckedByName("Dankuni"),
                    "Boarding point with name 'Dankuni' is not checked");

            Thread.sleep(5000);

            boardingdroppingpoint.clickDroppingPointByName("Nawabab Hat");
            boardingdroppingpoint.clickBoardingDroppingPointTab();
            Assert.assertTrue(boardingdroppingpoint.isDroppingPointCheckedByName("Nawabab Hat"),
                    "Dropping point with name 'Nawab Hat' is not checked");
            Thread.sleep(5000);

            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void verify_all_boarding_times_displayed() {
        String testName = "verify_all_boarding_times_displayed";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
            sp.clickViewSeatsButtonForCard(1);

            busseatpage.selectSeat("5");
            Assert.assertTrue(busseatpage.isSeatSelected("5"), "Seat 5 could not be selected");

            boardingdroppingpoint.clickBoardingDroppingPointButton();
            Assert.assertTrue(boardingdroppingpoint.areAllBoardingTimesDisplayed(),
                    "Not all boarding times are displayed");

            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void boarding_point_required_before_proceed(){
        String testName ="boarding_point_required_before_proceed";
        try{
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
            sp.clickViewSeatsButtonForCard(1);

            busseatpage.selectSeat("5");
            Assert.assertTrue(busseatpage.isSeatSelected("5"), "Seat 5 could not be selected");

            Assert.assertTrue(!passengerinfo.isFillPassengerDetailButtonPresent(),"Passenger detail button shall not be present before selecting any boarding point");


        } catch (Exception e) {
            logTestFailure(testName,e);
        }
    }


}
