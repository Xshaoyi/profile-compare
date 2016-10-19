package yi.shao.webdriver.profile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import yi.shao.webdriver.model.CheckBoxRecord;
import yi.shao.webdriver.uti.CommonUtil;
import yi.shao.webdriver.uti.WaitConditionUtil;

public class ProfileJob {
	private WebDriver driver;
	private String profileId;
	public void init(String url){
		System.setProperty("webdriver.chrome.driver", "/home/natic/webdriver/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(
				url);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void goToProfile(String profileName) throws InterruptedException{
		WebElement setUpLink = driver.findElement(By.id("setupLink"));
		setUpLink.click();
		WaitConditionUtil.waitUtilPageLoad(driver);
		WebElement userIcon = driver.findElement(By.id("Users_font"));
		userIcon.click();
		Thread.sleep(500);
		WebElement userLink = driver.findElement(By.id("EnhancedProfiles_font"));
		userLink.click();
		WaitConditionUtil.waitUtilPageLoad(driver);
		
		ignoreEleOrClick("cruc_notify");
		findProfileAndClick(profileName);
		//到此已经打开了profile的页面
		WaitConditionUtil.waitUtilPageLoad(driver);
		
		profileId  = CommonUtil.getIdInUrl(driver.getCurrentUrl());
		
	}
	public List<CheckBoxRecord> handleEditFieldLevelSecurityPage(String objName){
		if(StringUtils.isNoneEmpty(profileId)){
			driver.get(CommonUtil.fomatProfileEditUrl(profileId, objName));
		}
		WaitConditionUtil.waitUtilPageLoad(driver);
		return getProfileInfoList();
	}
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public List<CheckBoxRecord> getProfileInfoList(){
		List<CheckBoxRecord> pInfoList = new ArrayList<CheckBoxRecord>();
		List<WebElement> dataRowList = driver.findElements(By.cssSelector("#mainTable .dataRow"));
		for(WebElement ele:dataRowList){
			CheckBoxRecord t = getCheckBoxRecord(ele);
			pInfoList.add(t);
		}
		return pInfoList;
	}
	
	public void quit(){
		driver.quit();
	}
	
	private CheckBoxRecord getCheckBoxRecord(WebElement ele){
		List<WebElement> tdList = ele.findElements(By.className("dataCell"));
		CheckBoxRecord t = new CheckBoxRecord();
		Iterator<WebElement> it=tdList.iterator();
		int i = 0;
		while(it.hasNext()){
			WebElement elem = it.next();
			
			switch (i) {
			case 0:
				t.setFieldName(elem.getText());
				break;
			case 1:
				t.setFieldType(elem.getText());
				break;
			case 2:
				t.setIsReadAccess(getEditalbe(ele));
				break;
			case 3:	
				t.setIsEditAccess(getEditalbe(ele));
			default:
				break;
			}
			i++;
		}
		return t;
	}
	private boolean getEditalbe(WebElement elem){
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
		Boolean rs = false;
		try{
			elem.findElement(By.tagName("img"));
		}catch(Exception e){
			rs = elem.findElement(By.tagName("input")).isSelected();
		}
		return rs;
	}
	private void ignoreEleOrClick(String id){
		try{
			WebElement newFeatureWindow = driver.findElement(By.id(id));
			if(newFeatureWindow.isDisplayed()){
			
			}
		}catch(Exception e){
			
		}
	}
	private void findProfileAndClick(String profileName){
		//find profile start with 1st character of profile name
		List<WebElement> eleList=driver.findElements(By.cssSelector(".subNav .rolodex a"));
		WebElement clickElem = CommonUtil.findEleInListByText(eleList, profileName.substring(0, 1),null);
		clickElem.click();//这里会call ajax 导致页面局部刷新
		WaitConditionUtil.waitUtilAjaxLoad(driver);
		List<WebElement> profileEleList = driver.findElements(By.cssSelector(".x-grid3-col-ProfileName a"));
		clickElem = CommonUtil.findEleInListByText(profileEleList,profileName,"span");
		clickElem.click();
		
		WaitConditionUtil.waitUtilPageLoad(driver);
		
	}
}
