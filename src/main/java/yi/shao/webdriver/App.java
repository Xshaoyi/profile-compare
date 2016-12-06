package yi.shao.webdriver;





import yi.shao.webdriver.profile.ProfileJob;
import yi.shao.webdriver.profile.ProfileJobFactory;


public class App {
	public static final String url = "https://salespro-sams--scmepdev.cs4.my.salesforce.com/?pw=Pwcwelcome2&un=yi.shao@samsclub.com.scmepdev";
	public static final String url1 ="https://salespro-sams--scmepdev.cs4.my.salesforce.com/00e1a000000rsEx";
	
	public static void main(String[] args) throws InterruptedException {
		ProfileJob profileJob = ProfileJobFactory.getProfileJob(url1);
		//profileJob.goToProfile("System Administrator");
		profileJob.setProfileId("00e1a000000rsEx");
		profileJob.handleEditFieldLevelSecurityPage("Lead");
		
	}

}
