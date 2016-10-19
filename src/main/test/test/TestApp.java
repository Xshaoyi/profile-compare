package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import yi.shao.webdriver.uti.CommonUtil;

public class TestApp {
	public static void main(String[] args) {
		System.out.println(getDomainName("aa//dsfsdf.sadfas/1"));
		
		
	}
	private static String getDomainName(String url){
		String pattern = ".*//(.+)/.*";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(url);
		if(m.matches()){
			return m.group(1);
		}else{
			return null;
		}
	}
}
