package utils;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import components.SearchBarComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjects.HomePage;
import components.SearchBarComponents.JourneyField;
import pageObjects.SearchPage;

public class HelperFunctions {

    private final HomePage hp;
    private final SearchPage sp;
    private final SearchBarComponents searchbarcomponents;

    public HelperFunctions(WebDriver driver, WebDriverWait wait) {
        this.hp = new HomePage(driver);
        this.sp = new SearchPage(driver);
        this.searchbarcomponents = new SearchBarComponents(driver);

    }

    public void enter_source(String source) {
        searchbarcomponents.selectJourneyCity(JourneyField.SOURCE, source);
    }

    public void enter_destination(String destination) {
        searchbarcomponents.selectJourneyCity(JourneyField.DESTINATION, destination);
    }

    public void selectCalendarDate(LocalDate targetDate) {
        searchbarcomponents.openCalendar();

        String targetMonth = targetDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        searchbarcomponents.navigateCalendarTo(targetMonth, targetDate.getYear());
        searchbarcomponents.clickCalenderDay(targetDate.getDayOfMonth());

    }

    public void searchBuses(String source,String destination,LocalDate date) throws InterruptedException{

        enter_source(source);
        enter_destination(destination);

        selectCalendarDate(date);
        searchbarcomponents.isSearchButtonClickable();
        searchbarcomponents.clickSearchBusesButton();

    }
}