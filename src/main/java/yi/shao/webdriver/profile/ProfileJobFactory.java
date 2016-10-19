package yi.shao.webdriver.profile;

public class ProfileJobFactory {
	public static ProfileJob getProfileJob(String url){
		ProfileJob pjob= new ProfileJob();
		pjob.init("https://salespro-sams--scmepdev.cs4.my.salesforce.com/?pw=Pwcwelcome2&un=yi.shao@samsclub.com.scmepdev");
		return pjob;
	}
}
