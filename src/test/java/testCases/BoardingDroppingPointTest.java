package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.BaseClass;

import java.time.LocalDate;
import java.util.HashMap;

public class BoardingDroppingPointTest extends BaseClass {

    @Test
    public void clickBoardingDroppingPointButtonTest() {
        String testName = "clickBoardingDroppingPointButtonTest";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
            // Automatically find a card with at least 2 boarding + 1 dropping point
            // The returned seat number is the one already selected by the helper
            String seatNumber = helper.clickViewSeatsForCardWithMinPoints(2, 1);

            Assert.assertTrue(busseatpage.isSeatSelected(seatNumber), "Seat " + seatNumber + " could not be selected");

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
            helper.searchBuses("Burdwan", "Durgapur (West Bengal)", LocalDate.now().plusDays(3));
            // Need at least 2 boarding + 2 dropping points so we can pick and verify
            // The returned seat number is the one already selected by the helper
            String seatNumber = helper.clickViewSeatsForCardWithMinPoints(2, 2);

            Assert.assertTrue(busseatpage.isSeatSelected(seatNumber), "Seat " + seatNumber + " could not be selected");

            boardingdroppingpoint.clickBoardingDroppingPointButton();

            boardingdroppingpoint.clickBoardingPointByIndex(1);
            Assert.assertTrue(boardingdroppingpoint.isBoardingPointCheckedByIndex(1),
                    "Boarding point at index 1 is not checked");

            boardingdroppingpoint.clickDroppingPointByIndex(0);
            boardingdroppingpoint.clickBoardingDroppingPointTab();
            Assert.assertTrue(boardingdroppingpoint.isDroppingPointCheckedByIndex(0),
                    "Dropping point at index 0 is not checked");

            // No need for Thread.sleep() - the following assertion will wait for the
            // element state using WebDriverWait
            boardingdroppingpoint.clickBoardingPointByName("Dankuni");
            Assert.assertTrue(boardingdroppingpoint.isBoardingPointCheckedByName("Dankuni"),
                    "Boarding point with name 'Dankuni' is not checked");

            boardingdroppingpoint.clickDroppingPointByName("Nawabab Hat");
            boardingdroppingpoint.clickBoardingDroppingPointTab();
            Assert.assertTrue(boardingdroppingpoint.isDroppingPointCheckedByName("Nawab Hat"),
                    "Dropping point with name 'Nawab Hat' is not checked");

            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void verify_all_boarding_dropping_times_displayed() {
        String testName = "verify_all_boarding_dropping_times_displayed";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
            // Any card with at least 1 boarding + 1 dropping point is fine for this test
            // The returned seat number is the one already selected by the helper
            String seatNumber = helper.clickViewSeatsForCardWithMinPoints(1, 1);

            Assert.assertTrue(busseatpage.isSeatSelected(seatNumber), "Seat " + seatNumber + " could not be selected");

            boardingdroppingpoint.clickBoardingDroppingPointButton();
            Assert.assertTrue(boardingdroppingpoint.areAllBoardingTimesDisplayed(),
                    "Not all boarding times are displayed");

            Assert.assertTrue(boardingdroppingpoint.areAllDroppingTimesDisplayed(),
                    "Not all dropping times are displayed");

            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void boarding_point_required_before_proceed() {
        String testName = "boarding_point_required_before_proceed";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
            String seatNumber = helper.clickViewSeatsForCardWithMinPoints(2, 2);

            Assert.assertTrue(busseatpage.isSeatSelected(seatNumber), "Seat " + seatNumber + " could not be selected");

            boardingdroppingpoint.clickBoardingDroppingPointButton();
            boardingdroppingpoint.clickDroppingPointByIndex(0);

            Assert.assertTrue(passengerinfo.isFillPassengerDetailButtonNotPresent(),
                    "Passenger detail button shall not be present before selecting any boarding point");
            passengerinfo.clickPassengerInfoButton();
            HashMap<Boolean, String> map = boardingdroppingpoint.isboardingDroppingVlidationMessageDisplayed();
            Assert.assertTrue(map.containsKey(true), "The validation message did not pop up");
            Assert.assertTrue(map.containsValue("Please select a boarding and dropping point"),
                    "The validation message does not match with the required message");

            logTestPass(testName);
        } catch (Exception e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void dropping_point_required_before_proceed() {
        String testName = "boarding_point_required_before_proceed";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
            String seatNumber = helper.clickViewSeatsForCardWithMinPoints(2, 1);

            Assert.assertTrue(busseatpage.isSeatSelected(seatNumber), "Seat " + seatNumber + " could not be selected");

            boardingdroppingpoint.clickBoardingDroppingPointButton();
            boardingdroppingpoint.clickDroppingPointByIndex(0);

            Assert.assertTrue(passengerinfo.isFillPassengerDetailButtonNotPresent(),
                    "Passenger detail button shall not be present before selecting any boarding point");
            passengerinfo.clickPassengerInfoButton();
            HashMap<Boolean, String> map = boardingdroppingpoint.isboardingDroppingVlidationMessageDisplayed();
            Assert.assertTrue(map.containsKey(true), "The valiation message did not pop up");
            Assert.assertTrue(map.containsValue("Please select a boarding and dropping point"),
                    "The validation message does not match with the required message");

        } catch (Exception e) {
            logTestFailure(testName, e);
        }
    }

}
