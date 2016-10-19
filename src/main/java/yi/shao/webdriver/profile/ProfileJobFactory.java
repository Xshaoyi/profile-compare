package yi.shao.webdriver.profile;

public class ProfileJobFactory {
	public static ProfileJob getProfileJob(String url){
		ProfileJob pjob= new ProfileJob();
		pjob.init(url);
		return pjob;
	}
}
