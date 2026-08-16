package testCases;

import enums.Sorting;
import models.BusCard;
import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.BaseClass;

import java.time.LocalDate;

public class SortingTest extends BaseClass {


    @Test
    public void sortByDepartureButtontoDesc(){
        helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
        searchresultssortingpage.setSortByDepartureButtontoDesc(Sorting.DEPARTURE_TIME);

        Assert.assertTrue(sp.isSorted(sp.getAllBusCardDetails(), BusCard::getDepartureTime, Sorting.DESCENDING),"The departure time is nto sorted in descending order");

    }

    @Test
    public void sortByDepartureButtontoAsc(){
        helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
        searchresultssortingpage.setSortByDepartureButtontoAsc(Sorting.DEPARTURE_TIME);

        Assert.assertTrue(sp.isSorted(sp.getAllBusCardDetails(), BusCard::getDepartureTime, Sorting.ASCENDING),"The departure time is not sorted in ascending order");

    }


    @Test
    public void sortByPriceButtontoAsc(){
        helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
        searchresultssortingpage.setSortByPriceButtontoAsc(Sorting.PRICE);

        Assert.assertTrue(sp.isSorted(sp.getAllBusCardDetails(), BusCard::getPrice, Sorting.ASCENDING),"The Price is not sorted in ascending order");

    }

    @Test
    public void sortByPriceButtontoDesc(){
        helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
        searchresultssortingpage.setSortByPriceButtontoDesc(Sorting.PRICE);

        Assert.assertTrue(sp.isSorted(sp.getAllBusCardDetails(), BusCard::getPrice, Sorting.DESCENDING),"The Price is not sorted in descending order");

    }

    @Test
    public void sortByRatingButtontoAsc(){
        helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
        searchresultssortingpage.setSortByDepartureButtontoAsc(Sorting.RATING);

        Assert.assertTrue(sp.isSorted(sp.getAllBusCardDetails(), BusCard::getRating, Sorting.ASCENDING),"The Rating is not sorted in ascending order");

    }

    @Test
    public void sortByRatingButtontoDesc(){
        helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(5));
        searchresultssortingpage.setSortByDepartureButtontoDesc(Sorting.RATING);

        Assert.assertTrue(sp.isSorted(sp.getAllBusCardDetails(), BusCard::getRating, Sorting.DESCENDING),"The departure time is not sorted in ascending order");

    }

}
