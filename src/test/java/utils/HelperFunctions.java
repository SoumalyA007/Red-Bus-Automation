package utils;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjects.HomePage;
import pageObjects.HomePage.JourneyField;
import pageObjects.SearchPage;

public class HelperFunctions {

    private final HomePage hp;
    private final SearchPage sp;

    public HelperFunctions(WebDriver driver, WebDriverWait wait) {
        this.hp = new HomePage(driver);
        this.sp = new SearchPage(driver);
    }

    public void enter_source(String source) {
        hp.selectJourneyCity(JourneyField.SOURCE, source);
    }

    public void enter_destination(String destination) {
        hp.selectJourneyCity(JourneyField.DESTINATION, destination);
    }

    public void selectCalendarDate(LocalDate targetDate) {
        hp.openCalendar();

        String targetMonth = targetDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        hp.navigateCalendarTo(targetMonth, targetDate.getYear());
        hp.clickCalenderDay(targetDate.getDayOfMonth());

    }

    public void searchBuses(String source,String destination,LocalDate date) throws InterruptedException{

        enter_source(source);
        enter_destination(destination);

        selectCalendarDate(date);
        hp.isSearchButtonClickable();
        hp.clickSearchBusesButton();

    }
}