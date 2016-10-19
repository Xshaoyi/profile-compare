package yi.shao.webdriver.uti;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CommonUtil {
	private static final String PROFILE_EDIT_URL="https://salespro-sams--scmepdev.cs4.my.salesforce.com/setup/layout/flsedit.jsp?id={id}&type={objname}";
	public static WebElement findEleInListByText(List<WebElement> eleList, String text, String cssSelector) {
		WebElement eleRs = null;
		for (WebElement ele : eleList) {
			if (StringUtils.isEmpty(cssSelector)) {
				if (text.equalsIgnoreCase(ele.getText())) {
					eleRs = ele;
					break;
				}
			}else{
				if (text.equalsIgnoreCase(ele.findElement(By.cssSelector(cssSelector)).getText())) {
					eleRs = ele;
					break;
				}
			}

		}
		return eleRs;

	}
	public static String findMatchInString(String str,String param){
		String patterns = ".*"+param+"=(\\w+)&.*";
		Pattern p = Pattern.compile(patterns);
		Matcher m = p.matcher("asdaid=12a&asa");
		boolean a = m.matches();
		if(a){
			return m.group(1);
		}else{
			return null;
		}
	}
	public static String getIdInUrl(String str){
		String patterns = ".*//.*/(\\w+)$";
		Pattern p = Pattern.compile(patterns);
		Matcher m = p.matcher(str);
		boolean a = m.matches();
		if(a){
			return m.group(1);
		}else{
			return null;
		}
	}
	public static String fomatProfileEditUrl(String profileId,String objectName){
		return PROFILE_EDIT_URL.replace("{id}", profileId).replace("{objname}", objectName);
		
	}
}
