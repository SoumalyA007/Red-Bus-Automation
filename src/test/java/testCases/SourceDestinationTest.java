package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import testBase.BaseClass;

public class SourceDestinationTest extends BaseClass {

    // Enter valid source and destination cities
    @Test
    public void testSourceDestination() {
        HomePage homePage = new HomePage(driver);

        try {

            homePage.setJourneyFrom("Bangalore");
            //homePage.setJourneyTo("Chennai");

            Assert.assertEquals(homePage.getJourneyFrom(), "Bangalore", "Source city is not set correctly");
            //Assert.assertEquals(homePage.getJourneyTo(), "Chennai", "Destination city is not set correctly");
        } catch (Exception e) {
            Assert.fail("Exception occurred while entering source and destination: " + e.getMessage());
        }
    }

}
