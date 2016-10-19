package yi.shao.webdriver.profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import yi.shao.webdriver.model.CheckBoxRecord;

public class ProfoleCampare {
	public static final String url_dev = "https://salespro-sams--scmepdev.cs4.my.salesforce.com/?pw=Pwcwelcome2&un=yi.shao@samsclub.com.scmepdev";
	public static final String url_dev_test = "https://salespro-sams--scmepdev.cs4.my.salesforce.com";
	public static final String url_qa = "https://salespro-sams--scmepqa.lightning.force.com/?un=yi.shao@samsclub.com.scmepqa&pw=Pwcwelcome2";
	public static final String url_qa_test = "https://salespro-sams--scmepqa.lightning.force.com";
	public static final String profile_id_dev = "00e1a000000rsEx";
	public static final String profile_id_qa = "00e1a000000rsEx";
	private List<CheckBoxRecord> recordList1 = new LinkedList<CheckBoxRecord>();
	private List<CheckBoxRecord> recordList2 = new LinkedList<CheckBoxRecord>();
	String[] a= {"Lead","Account","Contact"};
	private List<String> profileObjNameList = new ArrayList<String>(Arrays.asList(a));
	public void execute() {
		List<CyclicBarrier>  cbList = new ArrayList<CyclicBarrier>();
		for(String objName:profileObjNameList){
			cbList.add(new CyclicBarrier(2, new CampareAction()));
		}
		ProfileInspectJob pcj1 = new ProfileInspectJob(url_dev, profile_id_dev, cb, recordList1);
		ProfileInspectJob pcj2 = new ProfileInspectJob(url_qa, profile_id_qa, cb, recordList2);
		pcj1.start();
		pcj2.start();

	}

	public static void main(String[] args) {
		ProfoleCampare pc = new ProfoleCampare();
		pc.execute();
	}

	private class CampareAction implements Runnable {

		public void run() {
			// TODO Auto-generated method stub
			System.out.println("recordList1"+recordList1);
			System.out.println("recordList2"+recordList2);
			System.out.printf("%-50s%10s\n", "Field Name", "Status");
			try{
				for(CheckBoxRecord cbt : recordList1){
					compareAndSet(cbt);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}

		private void compareAndSet(CheckBoxRecord cbr1) {
			int duplicateTimes = 0;
			for (CheckBoxRecord cbt : recordList2) {

				if (cbr1.getFieldName().equals(cbt.getFieldName())  && cbr1.getFieldType().equals(cbt.getFieldType())) {
					duplicateTimes++;
					if (duplicateTimes > 1) {
						cbt.setIsUpdate(false);
						cbr1.setIsUpdate(false);
						break;
					}
					if (cbr1.getIsEditAccess() != cbt.getIsEditAccess()
							|| cbr1.getIsReadAccess() != cbt.getIsReadAccess()) {
						cbt.setIsEditAccess(cbr1.getIsEditAccess());
						cbt.setIsReadAccess(cbr1.getIsReadAccess());
						cbt.setIsUpdate(true);
						cbr1.setIsUpdate(true);
					}
				}
			}
			if(duplicateTimes == 0){
				System.out.printf("%-50s%10s\n", cbr1.getFieldName(), "miss");
			}else if(duplicateTimes == 1){
				System.out.printf("%-50s%10s\n", cbr1.getFieldName(), cbr1.getIsUpdate()?"update":"stable");
			}else if(duplicateTimes >1){
				System.out.printf("%-50s%10s\n", cbr1.getFieldName(), "duplicate");
			}

		}

	}

	private class ProfileInspectJob extends Thread {

		private String url;
		private String profileId;
		CyclicBarrier cb;

		private List<CheckBoxRecord> therecordList;

		public ProfileInspectJob(String url, String profileId, CyclicBarrier cb, List<CheckBoxRecord> therecordList ) {
			super();
			this.url = url;
			this.profileId = profileId;
			this.cb = cb;
			this.therecordList = therecordList;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			ProfileJob profileJob = ProfileJobFactory.getProfileJob(url);
			try {
				profileJob.goToProfile("System Administrator");
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			therecordList.addAll(profileJob.handleEditFieldLevelSecurityPage("Lead"));
			
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
