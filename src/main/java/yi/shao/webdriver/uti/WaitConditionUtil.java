package yi.shao.webdriver.uti;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitConditionUtil {
	/**
	 * wait until page load
	 * @return
	 */
	public static ExpectedCondition<Boolean> pageLoadComplete() {
		return new ExpectedCondition<Boolean>() {
			private String state = "";

			public Boolean apply(WebDriver driver) {
				state = ((JavascriptExecutor) driver).executeScript("return document.readyState").toString();
				System.out.println(state);
				return state.equals("interactive")||state.equals("complete");
			}

			@Override
			public String toString() {
				return String.format("Current state: \"%s\"", state);
			}
		};
	}
	public static ExpectedCondition<Boolean> ajaxLoadComplete(){
		return new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver driver) {
				// TODO Auto-generated method stub
				Boolean isLoadingIconDisplayed = driver.findElement(By.className("waitingSearchDiv")).isDisplayed();
				System.out.println("waiting... ajax loading,status"+isLoadingIconDisplayed);
				return !driver.findElement(By.className("waitingSearchDiv")).isDisplayed();
			}
		};
	}
	
	public static ExpectedCondition<Boolean> elemShowById(final String id){
		return new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver driver) {
				// TODO Auto-generated method stub
				Boolean isLoadingIconDisplayed = false;
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				
				try{
					isLoadingIconDisplayed = driver.findElement(By.id(id)).isDisplayed();
				}catch(Exception e){
					
				}
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				System.out.printf("Waiting... Element:%s,status:%b\n",id,isLoadingIconDisplayed);
				return isLoadingIconDisplayed;
			}
		};
	}
	public static ExpectedCondition<Boolean> elemShowByCssSelector(final String cssSelector){
		return new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver driver) {
				// TODO Auto-generated method stub
				Boolean isLoadingIconDisplayed = false;
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				
				try{
					isLoadingIconDisplayed = driver.findElement(By.cssSelector(cssSelector)).isDisplayed();
				}catch(Exception e){
					
				}
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				System.out.printf("Waiting... Element:%s,status:%b\n",cssSelector,isLoadingIconDisplayed);
				return isLoadingIconDisplayed;
			}
		};
	}
	public static void waitUtilPageLoad(WebDriver driver){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(pageLoadComplete());
	}
	public static void waitUtilAjaxLoad(WebDriver driver){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ajaxLoadComplete());
	}
	public static void waitUtilElemShowById(WebDriver driver,String id){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(elemShowById(id));
	}
	public static void waitUtilElemShowByCssSelector(WebDriver driver,String css){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(elemShowByCssSelector(css));
	}
}
