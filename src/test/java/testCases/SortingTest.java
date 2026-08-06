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

        Assert.assertTrue(sp.isSorted(sp.getAllBusCardDetails(), BusCard::getDepartureTime, Sorting.DESCENDING),"The departure time is nto sorted");

    }

}
