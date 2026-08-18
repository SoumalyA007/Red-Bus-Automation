package testCases;

import enums.Sorting;
import models.BusCard;
import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.BaseClass;

import java.time.LocalDate;

public class SortingTest extends BaseClass {

    @Test
    public void sortByDepartureButtontoDesc() {
        String testName = "sortByDepartureButtontoDesc";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
            searchresultssortingpage.setSortByDepartureButtontoDesc(Sorting.DEPARTURE_TIME);

            Assert.assertTrue(
                    sp.isSorted(sp.getAllBusCardDetails(), BusCard::getDepartureTime, Sorting.DESCENDING),
                    "The departure time is not sorted in descending order");

            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void sortByDepartureButtontoAsc() {
        String testName = "sortByDepartureButtontoAsc";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
            searchresultssortingpage.setSortByDepartureButtontoAsc(Sorting.DEPARTURE_TIME);

            Assert.assertTrue(
                    sp.isSorted(sp.getAllBusCardDetails(), BusCard::getDepartureTime, Sorting.ASCENDING),
                    "The departure time is not sorted in ascending order");

            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void sortByPriceButtontoAsc() {
        String testName = "sortByPriceButtontoAsc";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
            searchresultssortingpage.setSortByPriceButtontoAsc(Sorting.PRICE);

            Assert.assertTrue(
                    sp.isSorted(sp.getAllBusCardDetails(), BusCard::getPrice, Sorting.ASCENDING),
                    "The Price is not sorted in ascending order");

            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void sortByPriceButtontoDesc() {
        String testName = "sortByPriceButtontoDesc";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
            searchresultssortingpage.setSortByPriceButtontoDesc(Sorting.PRICE);

            Assert.assertTrue(
                    sp.isSorted(sp.getAllBusCardDetails(), BusCard::getPrice, Sorting.DESCENDING),
                    "The Price is not sorted in descending order");

            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void sortByRatingButtontoAsc() {
        String testName = "sortByRatingButtontoAsc";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
            searchresultssortingpage.setSortByDepartureButtontoAsc(Sorting.RATING);

            Assert.assertTrue(
                    sp.isSorted(sp.getAllBusCardDetails(), BusCard::getRating, Sorting.ASCENDING),
                    "The Rating is not sorted in ascending order");

            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void sortByRatingButtontoDesc() {
        String testName = "sortByRatingButtontoDesc";
        try {
            helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
            searchresultssortingpage.setSortByDepartureButtontoDesc(Sorting.RATING);

            Assert.assertTrue(
                    sp.isSorted(sp.getAllBusCardDetails(), BusCard::getRating, Sorting.DESCENDING),
                    "The Rating is not sorted in descending order");

            logTestPass(testName);
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

}
