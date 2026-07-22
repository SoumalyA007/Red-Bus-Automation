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

    @Test
    public void TC_003_select_next_month(){
        String testName = "TC_003_select_next_month";
        try{
            LocalDate nextMonth = LocalDate.now().plusMonths(1);
            String nextMonthValue = nextMonth.format(DateTimeFormatter.ofPattern("MMMM"));
            hp.openCalendar();
            hp.clickDateProgressArrow();
            String calenderMonthYear=hp.getCalenderMonthYear();
            String[] parts = calenderMonthYear.split(" ");
            Assert.assertEquals(nextMonthValue,parts[0],"Expected next month value did not match");
        } catch (Throwable e) {
            logTestFailure(testName,e);
        }
    }

    @Test
    public void TC_004_select_next_year(){
        String testName = "TC_004_select_next_year";
        try{
            LocalDate nextYear = LocalDate.now().plusYears(1);
            String nextYearValue = nextYear.format(DateTimeFormatter.ofPattern("yyyy"));
            hp.openCalendar();
            while(true){
                hp.clickDateProgressArrow();
                String calenderMonthYear=hp.getCalenderMonthYear();
                String[] parts = calenderMonthYear.split(" ");
                String currentYear = parts[1];
                if(currentYear.equalsIgnoreCase(nextYearValue)){
                    break;
                }
            }
            String[] parts = hp.getCalenderMonthYear().split(" ");
            String currentSelectedYear = parts[1];
            Assert.assertEquals(nextYearValue,currentSelectedYear,"Expected next year value did not match");
        } catch (Throwable e) {
            logTestFailure(testName,e);
        }
    }

    @Test
    public void TC_005_past_date_disabled(){
        String testName = "TC_005_past_date_disabled";
        try{
            LocalDate currentDateMonthYear = LocalDate.now();
            String currentDate = currentDateMonthYear.format(DateTimeFormatter.ofPattern("dd"));

            if(currentDate.equals(1)){

            }



        } catch (Exception e) {
            logTestFailure(testName,e);
        }
    }


}


