package yi.shao.webdriver.profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import yi.shao.webdriver.concurrent.MyCyclicBarrier;
import yi.shao.webdriver.model.CheckBoxRecord;

public class ProfoleCampare {
	public static final String url_dev = "https://salespro-sams--scmepdev.cs4.my.salesforce.com/?pw=Pwcwelcome2&un=yi.shao@samsclub.com.scmepdev";
	public static final String url_dev_test = "https://salespro-sams--scmepdev.cs4.my.salesforce.com";
	public static final String url_qa = "https://salespro-sams--scmepqa.lightning.force.com/?un=yi.shao@samsclub.com.scmepqa&pw=Pwcwelcome2";
	public static final String url_qa_test = "https://salespro-sams--scmepqa.lightning.force.com";
	public static final String profile_id_dev = "00e1a000000rsEx";
	public static final String profile_id_qa = "00e1a000000rsEx";
	String[] a= {"Lead","Account","Contact"};
	private List<String> profileObjNameList = new ArrayList<String>(Arrays.asList(a));
	public void execute() {
		Map<String, MyCyclicBarrier> map = new HashMap<String, MyCyclicBarrier>();
		List<CyclicBarrier>  cbList = new ArrayList<CyclicBarrier>();
		for(String objName:profileObjNameList){
			map.put(objName,new MyCyclicBarrier(2, objName));
		}
		ProfileInspectJob pcj1 = new ProfileInspectJob(url_dev, profile_id_dev, map,profileObjNameList,true);
		ProfileInspectJob pcj2 = new ProfileInspectJob(url_qa, profile_id_qa, map,profileObjNameList,false);
		pcj1.start();
		pcj2.start();

	}

	public static void main(String[] args) {
		ProfoleCampare pc = new ProfoleCampare();
		pc.execute();
	}

	

	private class ProfileInspectJob extends Thread {

		private String url;
		private String profileId;
		Map<String, MyCyclicBarrier> cbMap;
		private List<String> flsObjectNameList;
		private boolean is_main_task;
		ProfileJob profileJob ;

		public ProfileInspectJob(String urlDev, String profileIdDev, Map<String, MyCyclicBarrier> map,List<String> flsObjectNameList,boolean is_main_task) {
			super();
			this.url = urlDev;
			this.profileId = profileIdDev;
			this.cbMap = map;
			this.flsObjectNameList = flsObjectNameList;
			this.is_main_task = is_main_task;
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			profileJob = ProfileJobFactory.getProfileJob(url);
			try {
				profileJob.goToProfile("System Administrator");
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//therecordList.addAll(profileJob.handleEditFieldLevelSecurityPage("Lead"));
			
			handleFieldLevelSecurityCp();
			profileJob.quit();
		}
		
		private void handleFieldLevelSecurityCp(){
			for(String flsObjName:flsObjectNameList){
				if(is_main_task){
					cbMap.get(flsObjName).setMainLIst(profileJob.handleEditFieldLevelSecurityPage(flsObjName));
				}else{
					cbMap.get(flsObjName).setCompareList(profileJob.handleEditFieldLevelSecurityPage(flsObjName));
				}
				try {
					System.out.println("barrier waiting.."+flsObjName);
					cbMap.get(flsObjName).await();
					System.out.println("barrier done.."+flsObjName);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
