package yi.shao.webdriver.uti;

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
				System.out.println("isLoadingIconDisplayed"+isLoadingIconDisplayed);
				return !driver.findElement(By.className("waitingSearchDiv")).isDisplayed();
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
}
