package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchPage extends BasePage {

    public SearchPage(WebDriver driver){
        super(driver);
    }



    @FindBy(xpath="//span[@role='text']")
    WebElement route;

    public String[] getRoute(){
        return route.getText().split(" ");
    }
    

}
