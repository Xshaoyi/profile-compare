package yi.shao.healthcheck;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import yi.shao.healthcheck.model.FieldObject;
import yi.shao.webdriver.uti.WaitConditionUtil;
import yi.shao.webdriver.uti.WebdriverCommonUtils;

public class FieldHealthCheckApp {
	public static Map<String,FieldObject> fieldMap = new LinkedHashMap<String, FieldObject>();
	public static String loginUrl = "https://salespro-sams--samshealth.cs17.my.salesforce.com/?un=yi.shao@samsclub.com.samshealth&pw=Pwcwelcome2";
	public static String editUrl = "https://salespro-sams--samshealth.cs17.my.salesforce.com/p/setup/layout/LayoutFieldList?type=Lead&retURL=%2Fui%2Fsetup%2FSetup%3Fsetupid%3DLead&setupid=LeadFields";
	public static String accEditUrl ="https://salespro-sams--samshealth.cs17.my.salesforce.com/p/setup/layout/LayoutFieldList?type=Account&setupid=AccountFields&retURL=%2Fui%2Fsetup%2FSetup%3Fsetupid%3DAccount";
	public static WebDriver driver ;
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "/Users/yshao009/webdriver/chromedriver");
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("user-data-dir=/Users/yshao009/Library/Application Support/Google/Chrome/Profile 2");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(loginUrl);
		WaitConditionUtil.waitUtilElemShowById(driver, "setupLink");
		driver.get(accEditUrl);
		WaitConditionUtil.waitUtilPageLoad(driver);
		FieldHealthCheckApp app = new FieldHealthCheckApp();
		List<WebElement> setUpLink = driver.findElements(By.cssSelector("#CustomFieldRelatedList_body tr.dataRow"));;
		for(WebElement we:setUpLink){
			FieldObject fo = new FieldObject();
			fo.api_name = we.findElement(By.cssSelector("td:nth-child(4)")).getText();
			fo.name = we.findElement(By.cssSelector("th:nth-child(3)")).getText();
			fieldMap.put(fo.api_name, fo);
		}
		System.out.println(fieldMap);
		app.process();
		for(String s:fieldMap.keySet()){
			System.out.printf("%40s%10s\n", s,fieldMap.get(s).isProcesed);
		}
	}
	public void process(){
		for(String s:fieldMap.keySet()){
			if(true){
				try{
					go(s);
				}catch(Exception e){
					System.out.println("error:"+s);
					e.printStackTrace();
				}
			}
		}
	}
	public void go(String targetName){
		List<WebElement> setUpLink = driver.findElements(By.cssSelector("#CustomFieldRelatedList_body tr.dataRow"));
		
		for(WebElement we:setUpLink){
			String apiName = we.findElement(By.cssSelector("td:nth-child(4)")).getText();
			System.out.println("apiName:"+apiName);
			if(!apiName.equals(targetName)){
				continue;
			}
			if(apiName.endsWith("no_use__c")){
				break;
			}
			WebElement alink = we.findElement(By.cssSelector(".actionLink"));
			alink.click();
			WaitConditionUtil.waitUtilElemShowById(driver, "setupLink");
			WebElement input = driver.findElement(By.id("DeveloperName"));
			if(!input.isEnabled()){
				driver.findElement(By.cssSelector("input[name='cancel']")).click();
				WaitConditionUtil.waitUtilElemShowById(driver, "setupLink");
				break;
			}
			System.out.println(input.getAttribute("value"));
			String inputValue = input.getAttribute("value");
			System.out.println("inputValue original:"+inputValue);
			if(inputValue.length()>32){
				inputValue = inputValue.substring(0, 32)+"_no_use";
			}else{
				inputValue = inputValue +"_no_use";
			}
			System.out.println("inputValue changed:"+inputValue);
			input.clear();
			WebdriverCommonUtils.acceptAlert(driver);
			input.sendKeys(inputValue);
			
			getByCss("input[name='save']").click();
			System.out.println("click save");
			WebdriverCommonUtils.acceptAlert(driver);
			if(checkDisplay("#errorDiv_ep")){
				driver.findElement(By.cssSelector("input[name='cancel']")).click();
				WaitConditionUtil.waitUtilElemShowById(driver, "setupLink");
			}
			WaitConditionUtil.waitUtilElemShowById(driver, "setupLink");
			
			break;
		}
	}
	
	public static boolean checkDisplay(String css){
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		WebElement ele = null;
		try{
			ele = driver.findElement(By.cssSelector(css));
		}catch(Exception e){
			System.out.println("error not find");
			return false;
		}
		
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		if(ele.isDisplayed()){
			return true;
		}else{
			return false;
		}
		
	}
	
	public static WebElement getByCss(String css){
		return driver.findElement(By.cssSelector(css));
	}
}
