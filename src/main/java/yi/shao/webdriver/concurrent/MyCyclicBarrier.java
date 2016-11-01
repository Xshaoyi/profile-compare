package yi.shao.webdriver.concurrent;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import yi.shao.webdriver.model.CheckBoxRecord;

public class MyCyclicBarrier  {
	private List<CheckBoxRecord> mainLIst;
	
	private List<CheckBoxRecord> compareList;
	
	private String identifier;
	
	private CyclicBarrier cb;
	
	public MyCyclicBarrier(int parties,
			String identifier) {
		cb = new CyclicBarrier(parties,new CampareAction());
		this.identifier = identifier;
		// TODO Auto-generated constructor stub
	}
	public void await() throws InterruptedException, BrokenBarrierException{
		cb.await();
	}
	public List<CheckBoxRecord> getMainLIst() {
		return mainLIst;
	}
	public void setMainLIst(List<CheckBoxRecord> mainLIst) {
		this.mainLIst = mainLIst;
	}
	public List<CheckBoxRecord> getCompareList() {
		return compareList;
	}
	public void setCompareList(List<CheckBoxRecord> compareList) {
		this.compareList = compareList;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	private class CampareAction implements Runnable {
		
	
		
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("recordList1"+mainLIst);
			System.out.println("recordList2"+compareList);
			System.out.printf("%-50s%10s\n", "Field Name", "Status");
			try{
				for(CheckBoxRecord cbt : mainLIst){
					compareAndSet(cbt);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}

		private void compareAndSet(CheckBoxRecord cbr1) {
			int duplicateTimes = 0;
			for (CheckBoxRecord cbt : compareList) {

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
}
