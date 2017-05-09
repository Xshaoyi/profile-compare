package yi.shao.webdriver;





import yi.shao.webdriver.profile.ProfileJob;
import yi.shao.webdriver.profile.ProfileJobFactory;


public class App {
	public static final String url = "domain+password+username";
	public static final String url1 ="url";
	
	public static void main(String[] args) throws InterruptedException {
		ProfileJob profileJob = ProfileJobFactory.getProfileJob(url1);
		//profileJob.goToProfile("System Administrator");
		profileJob.setProfileId("00e1a000000rsEx");
		profileJob.handleEditFieldLevelSecurityPage("Lead");
		
	}

}
