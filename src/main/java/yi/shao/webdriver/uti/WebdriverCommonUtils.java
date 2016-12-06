package yi.shao.webdriver.uti;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebdriverCommonUtils {
	public static WebElement findElementByTextAndCssSelector(WebDriver driver,String cssSelector,String text) throws Exception{
		List<WebElement> elemList = driver.findElements(By.cssSelector(cssSelector));
		for(WebElement ele:elemList){
			if(ele.getText().contains(text)){
				return ele;
			}
		}
		throw new Exception("Can't find the specific element");
		
	}
	public static void acceptAlert(WebDriver driver){
		try{
			Alert alert = driver.switchTo().alert();
			System.out.println("accepted");
			alert.accept(); //for two buttons, choose the affirmative one
			// or
		}catch(Exception e){
			System.out.println("error alert");
		}
	}
}
