package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.BaseClass;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class BusSeatTest extends BaseClass {

    @Test
    public void openSeatLayoutTest(){
        helper.searchBuses("Kolkata","Burdwan", LocalDate.now().plusDays(5));
        sp.clickViewSeatsButtonForCard(1);
        Assert.assertTrue(busseatpage.isSeatLayoutVisible(),"Seat Layout not visible");
    }

    @Test
    public void availableSeatsSelectableTest(){
        helper.searchBuses("Kolkata","Burdwan", LocalDate.now().plusDays(5));
        sp.clickViewSeatsButtonByOperator("Royal Cruiser");

        String availableSeat = busseatpage.getFirstAvailableSeatNumber();
        Assert.assertTrue(busseatpage.isSeatAvailable(availableSeat), "Seat " + availableSeat + " should be available");

        Assert.assertTrue(busseatpage.selectSeat(availableSeat), "Could not select available seat " + availableSeat);
        Assert.assertTrue(busseatpage.isSeatSelected(availableSeat), "Available seat " + availableSeat + " did not get selected");
    }

    @Test
    public void bookedSeatsDisabledTest(){
        helper.searchBuses("Kolkata","Burdwan", LocalDate.now().plusDays(5));
        sp.clickViewSeatsButtonByOperator("Royal Cruiser");

        String soldSeat = busseatpage.getFirstSoldSeatNumber();
        Assert.assertFalse(busseatpage.isSeatAvailable(soldSeat), "Sold seat " + soldSeat + " should not be available for selection");
        Assert.assertTrue(busseatpage.areSoldSeatDisabled(), "Sold seats are not disabled");
    }

    @Test
    public void clickViewSeatsButtonForCard(){
        helper.searchBuses("Kolkata","Burdwan", LocalDate.now().plusDays(5));
        sp.clickViewSeatsButtonByOperator("Royal Cruiser");
        Assert.assertTrue(busseatpage.isSeatAvailable("5"),"Seat 5 is not available");
        Assert.assertTrue(busseatpage.selectSeat("5"), "Seat 5 could not be selected");
        Assert.assertTrue(busseatpage.isSeatSelected("5"),"Seat 5 is not selected");
    }

    @Test
    public void selectMultipleSeatsTest(){
        helper.searchBuses("Kolkata","Burdwan", LocalDate.now().plusDays(5));
        sp.clickViewSeatsButtonByOperator("Royal Cruiser");

        List<String> seatsToSelect = Arrays.asList("5", "6", "7");
        busseatpage.selectSeats(seatsToSelect);

        Assert.assertTrue(busseatpage.areSeatsSelected(seatsToSelect), "Not all requested seats were selected");
        Assert.assertEquals(busseatpage.getSelectedSeatsCount(), seatsToSelect.size(),
                "Selected seat count does not match number of seats chosen");
    }

    @Test
    public void deselectSeatTest(){
        helper.searchBuses("Kolkata","Burdwan", LocalDate.now().plusDays(5));
        sp.clickViewSeatsButtonByOperator("Royal Cruiser");

        Assert.assertTrue(busseatpage.selectSeat("5"), "Seat 5 could not be selected");
        Assert.assertTrue(busseatpage.isSeatSelected("5"), "Seat 5 was not selected");

        Assert.assertTrue(busseatpage.deselectSeat("5"), "Seat 5 could not be deselected");
        Assert.assertFalse(busseatpage.isSeatSelected("5"), "Seat 5 is still showing as selected after deselect");
    }

    @Test
    public void fareUpdatesOnSeatSelectionTest(){
        helper.searchBuses("Kolkata","Burdwan", LocalDate.now().plusDays(5));
        sp.clickViewSeatsButtonByOperator("Royal Cruiser");

        Assert.assertTrue(busseatpage.selectSeat(5), "Seat 5 could not be selected");
        double fareAfterOneSeat = busseatpage.getFareAmount();

        Assert.assertTrue(busseatpage.selectSeat(6), "Seat 6 could not be selected");
        double fareAfterTwoSeats = busseatpage.getFareAmount();

        Assert.assertTrue(fareAfterTwoSeats > fareAfterOneSeat,
                "Fare did not increase after selecting an additional seat");

        Assert.assertTrue(busseatpage.deselectSeat(6), "Seat 6 could not be deselected");
        double fareAfterDeselect = busseatpage.getFareAmount();

        Assert.assertEquals(fareAfterDeselect, fareAfterOneSeat,
                "Fare did not revert correctly after deselecting a seat");
    }

    @Test
    public void seatLegendValidationTest(){
        helper.searchBuses("Kolkata","Burdwan", LocalDate.now().plusDays(5));
        sp.clickViewSeatsButtonByOperator("Royal Cruiser");

        Assert.assertTrue(busseatpage.isSeatLegendDisplayed(), "Seat legend is not displayed");

        List<String> legendLabels = busseatpage.getSeatLegendLabels();
        Assert.assertFalse(legendLabels.isEmpty(), "Seat legend has no labels");
        Assert.assertTrue(legendLabels.stream().anyMatch(l -> l.toLowerCase().contains("available")),
                "Legend missing 'Available' entry");
        Assert.assertTrue(legendLabels.stream().anyMatch(l -> l.toLowerCase().contains("sold") || l.toLowerCase().contains("booked")),
                "Legend missing 'Sold/Booked' entry");
    }

    @Test
    public void soldSeatsDisabled(){
        helper.searchBuses("Kolkata","Burdwan", LocalDate.now().plusDays(5));
        sp.clickViewSeatsButtonByOperator("Royal Cruiser");
        Assert.assertTrue(busseatpage.areSoldSeatDisabled(),"Sold seats are not disabled");
    }
}