package yi.shao.webdriver.concurrent;

import java.util.concurrent.CyclicBarrier;

public class MyCyclicBarrier extends CyclicBarrier {

	public MyCyclicBarrier(int parties, Runnable barrierAction) {
		super(parties, barrierAction);
		// TODO Auto-generated constructor stub
	}

}
