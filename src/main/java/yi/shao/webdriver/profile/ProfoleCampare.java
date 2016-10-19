package yi.shao.webdriver.profile;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import yi.shao.webdriver.model.CheckBoxRecord;

public class ProfoleCampare {
	public static final String url_dev = "https://salespro-sams--scmepdev.cs4.my.salesforce.com/?pw=Pwcwelcome2&un=yi.shao@samsclub.com.scmepdev";
	public static final String url_dev_test = "https://salespro-sams--scmepdev.cs4.my.salesforce.com";
	public static final String url_qa = "https://salespro-sams--scmepqa.lightning.force.com/?un=yi.shao@samsclub.com.scmepqa&pw=Pwcwelcome2";
	public static final String url_qa_test = "https://salespro-sams--scmepqa.lightning.force.com";
	private List<CheckBoxRecord> recordList1;
	private List<CheckBoxRecord> recordList2;
	public void execute(){
		CyclicBarrier cb = new CyclicBarrier(2, new CampareAction());
		
	}
	private class CampareAction implements Runnable{

		public void run() {
			// TODO Auto-generated method stub
			System.out.println("compare go!");
		}
		
	}
	private class ProfileCampareJob extends Thread{
		
		private String url ;
		private String profileId;
		CyclicBarrier cb;
		public ProfileCampareJob(String url, String profileId,CyclicBarrier cb) {
			super();
			this.url = url;
			this.profileId = profileId;
			this.cb = cb;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			ProfileJob profileJob = ProfileJobFactory.getProfileJob(url);
			profileJob.setProfileId(profileId);
			profileJob.handleEditFieldLevelSecurityPage("Lead");
			try {
				cb.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			profileJob.quit();
		}
		
	}
}
