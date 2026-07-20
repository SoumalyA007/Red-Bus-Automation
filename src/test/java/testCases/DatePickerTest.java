package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import testBase.BaseClass;

import java.time.LocalDate;

public class DatePickerTest extends BaseClass {

//    @Test
//    public void TC_001_select_tomorrow(){
//        hp.isCalendarButtonVisible();
//        hp.clickCalendarButton();
//        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'dateHolder___')]")));
//        LocalDate tomorrow = LocalDate.now().plusDays(1);
//
//        int day = tomorrow.getDayOfMonth();
//        String month = tomorrow.getMonth().name(); // JULY
//        int year = tomorrow.getYear();
//        while(true){
//            String currentMonthYear = driver.findElement(By.xpath("//p[contains(@class,'monthYear___')]")).getText();
//            System.out.println("currentMonth -> "+currentMonthYear);
//            String[] parts = currentMonthYear.split(" ");
//            System.out.println(parts[0]); // July
//            System.out.println(parts[1]); // 2026
//            String  currentMonth = parts[0];
//            String currentYear =parts[1];
//
//            if (currentMonth.equals("July") && currentYear.equals("2026")) {
//                break;
//            }
//
//            driver.findElement(By.xpath("//i[contains(@class,'icon') and contains(@class,'icon-arrow') and contains(@class,'arrow___') and contains(@class,'right___')]")).click();
//
//        }
//
//        driver.findElement(By.xpath("//span[text()='25']")).click();
//
//        }

//        String currentMonthYear = driver.findElement(By.xpath("//p[contains(@class,'monthYear___')]")).getText();
//        System.out.println("currentMonth -> "+currentMonthYear);
//        String[] parts = currentMonthYear.split(" ");
//        System.out.println(parts[0]); // July
//        System.out.println(parts[1]); // 2026
//        String  month = parts[0];
//        String year =parts[1];

    @Test
    public void TC_001_select_tomorrow(){
        hp.isCalendarButtonVisible();
        hp.clickCalendarButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'dateHolder___')]")));

        LocalDate tomorrow = LocalDate.now().plusDays(1);

        int day = tomorrow.getDayOfMonth();
        String month = tomorrow.getMonth().name().toLowerCase();
        int year = tomorrow.getYear();

        while (true) {

            String currentMonthYear = driver.findElement(
                            By.xpath("//p[contains(@class,'monthYear___')]"))
                    .getText();

            String[] parts = currentMonthYear.split(" ");

            String currentMonth = parts[0];
            int currentYear = Integer.parseInt(parts[1]);

            if (currentMonth.equals(month) && currentYear == year) {
                break;
            }

            driver.findElement(By.xpath(
                            "//i[contains(@class,'icon-arrow') and contains(@class,'right___')]"))
                    .click();

            // Wait until month changes
            wait.until(ExpectedConditions.not(
                    ExpectedConditions.textToBe(
                            By.xpath("//p[contains(@class,'monthYear___')]"),
                            currentMonthYear)));
        }

        driver.findElement(By.xpath(
                        "//div[contains(@class,'calendarDate')]//span[text()='" + day + "']"))
                .click();
    }




}


