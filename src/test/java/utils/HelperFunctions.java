package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjects.HomePage;
import pageObjects.HomePage.JourneyField;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class HelperFunctions {

    private final HomePage hp;

    public HelperFunctions(WebDriver driver, WebDriverWait wait) {
        this.hp = new HomePage(driver);
    }

    public void enter_source(String source) {
        hp.selectJourneyCity(JourneyField.SOURCE, source);
    }

    public void enter_destination(String destination) {
        hp.selectJourneyCity(JourneyField.DESTINATION, destination);
    }

    public String selectCalendarDate(LocalDate targetDate) {
        hp.openCalendar();

        String targetMonth = targetDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        hp.navigateCalendarTo(targetMonth, targetDate.getYear());
        hp.clickCalenderDay(targetDate.getDayOfMonth());

        return hp.getSelectedDate();
    }
}