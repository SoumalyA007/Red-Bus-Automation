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
    public void select_boarding_point_updates_selection() {
        String testName = "select_boarding_point_updates_selection";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
            sp.clickViewSeatsButtonForCard(1);

            busseatpage.selectSeat("5");
            Assert.assertTrue(busseatpage.isSeatSelected("5"), "Seat 5 could not be selected");

            boardingdroppingpoint.clickBoardingDroppingPointButton();
            boardingdroppingpoint.clickBoardingPointByIndex(1);
            Assert.assertTrue(boardingdroppingpoint.isBoardingPointCheckedByIndex(1),
                    "Boarding point at index 1 is not checked");
            boardingdroppingpoint.clickBoardingPointByName("SALAP");
            Assert.assertTrue(boardingdroppingpoint.isBoardingPointCheckedByName("SALAP"),
                    "Boarding point with name 'SALAP' is not checked");

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

    @Test
    public void boarding_point_list_matches_route(){

    }


}
