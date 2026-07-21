package testCases;

import net.bytebuddy.implementation.bytecode.Throw;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.BaseClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DatePickerTest extends BaseClass {

    @Test
    public void TC_001_select_tomorrow() {
        String testName = "TC_001_select_tomorrow";
        try {
            LocalDate tomorrow = LocalDate.now().plusDays(1);
            String expectedDate = tomorrow.format(DateTimeFormatter.ofPattern("d MMM, yyyy"));
            String actualDate = helper.selectCalendarDate(tomorrow);
            Assert.assertEquals(actualDate, expectedDate, "Selected journey date is incorrect.");
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_002_select_future_date() {
        String testName = "TC_002_select_future_date";
        try {
            LocalDate targetDate = LocalDate.of(2026, 11, 26);
            String actualDate = helper.selectCalendarDate(targetDate);
            Assert.assertEquals(actualDate, "26 Nov, 2026", "Selected journey date is incorrect.");
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }


}


