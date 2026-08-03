package testCases;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import components.SearchBarComponents;
import org.testng.Assert;
import org.testng.annotations.Test;

import testBase.BaseClass;

public class DatePickerTest extends BaseClass {

    @Test
    public void TC_001_select_tomorrow() {
        String testName = "TC_001_select_tomorrow";
        try {
            LocalDate tomorrow = LocalDate.now().plusDays(1);
            String expectedDate = tomorrow.format(DateTimeFormatter.ofPattern("d MMM, yyyy"));
            helper.selectCalendarDate(tomorrow);
            String actualDate = searchbarcomponents.getSelectedDate();
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
            helper.selectCalendarDate(targetDate);
            String actualDate = searchbarcomponents.getSelectedDate();
            Assert.assertEquals(actualDate, "26 Nov, 2026", "Selected journey date is incorrect.");
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_003_select_next_month() {
        String testName = "TC_003_select_next_month";

        try {

            LocalDate targetDate = LocalDate.now()
                    .plusMonths(1)
                    .withDayOfMonth(15);

            String expectedMonth = targetDate.format(DateTimeFormatter.ofPattern("MMMM"));

            searchbarcomponents.openCalendar();

            // Navigate to next month
            searchbarcomponents.clickDateProgressArrow();

            // Verify month has changed
            String calendarMonthYear = searchbarcomponents.getCalenderMonthYear();
            String actualMonth = calendarMonthYear.split(" ")[0];

            Assert.assertEquals(actualMonth,
                    expectedMonth,
                    "Next month was not displayed.");

            // Select a date from next month
            //searchbarcomponents.clickCalenderDay(targetDate.getDayOfMonth());
            searchbarcomponents.clickCalenderDay(targetDate);

            // Verify selected journey date
            String expectedDate
                    = targetDate.format(DateTimeFormatter.ofPattern("dd MMMM, yyyy"));

            Assert.assertEquals(
                    searchbarcomponents.getSelectedDate(),
                    expectedDate,
                    "Incorrect date selected."
            );

            logTestPass(testName);

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_004_select_next_year() {
        String testName = "TC_004_select_next_year";

        try {

            // Example: Select 15th of the current month in the next year
            LocalDate targetDate = LocalDate.now()
                    .plusYears(1)
                    .withMonth(1)
                    .withDayOfMonth(1);

            String expectedYear = String.valueOf(targetDate.getYear());

            searchbarcomponents.openCalendar();

            // Navigate until the required year is reached
            while (true) {

                String[] parts = searchbarcomponents.getCalenderMonthYear().split(" ");

                if (parts[1].equals(expectedYear)) {
                    break;
                }

                searchbarcomponents.clickDateProgressArrow();
            }

            // Verify the year displayed
            String displayedYear = searchbarcomponents.getCalenderMonthYear().split(" ")[1];

            Assert.assertEquals(
                    displayedYear,
                    expectedYear,
                    "Expected next year was not displayed."
            );

            // Select a date in that year
            //searchbarcomponents.clickCalenderDay(targetDate.getDayOfMonth());
            searchbarcomponents.clickCalenderDay(targetDate);

            // Verify the selected journey date
            String expectedDate = targetDate.format(
                    DateTimeFormatter.ofPattern("dd MMM, yyyy")
            );

            Assert.assertEquals(
                    searchbarcomponents.getSelectedDate(),
                    expectedDate,
                    "Incorrect journey date selected."
            );

            logTestPass(testName);

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_005_past_date_disabled() {
        String testName = "TC_005_past_date_disabled";
        try {
            LocalDate currentDateMonthYear = LocalDate.now();
            String currentDate = currentDateMonthYear.format(DateTimeFormatter.ofPattern("dd"));
            searchbarcomponents.clickCalendarButton();
            if (currentDate.equals("1")) {
                boolean isEnabled = searchbarcomponents.isDateBackArrowEnabled();
                Assert.assertFalse(isEnabled, "Back arrow should be disabled for the previous month.");
            } else {
                String toBeSelectedDate = currentDateMonthYear.minusDays(1).format(DateTimeFormatter.ofPattern("dd"));
                int day = Integer.parseInt(toBeSelectedDate);
                boolean isEnabled = searchbarcomponents.isDateEnabled(day);
                Assert.assertTrue(isEnabled, "Back arrow should be enabled for the previous month.");
            }
        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }

    @Test
    public void TC_006_select_today() {
        String testName = "TC_006_select_today";
        try {
            searchbarcomponents.openCalendar();
            //int todaysDate = Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("dd")));
            LocalDate currentDate = LocalDate.now();
            searchbarcomponents.clickCalenderDay(currentDate);
            String selectedDate = searchbarcomponents.getSelectedDate();
            String toBeSelectedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM, YYYY"));
            Assert.assertEquals(selectedDate, toBeSelectedDate, "today's date not selected");

        } catch (Throwable e) {
            logTestFailure(testName, e);
        }
    }
}
