/*
 * EID's of group members
 * 
 */

public class MonitorCyclicBarrier {
	public int parties;
	public int num = 0;
	
	public MonitorCyclicBarrier(int parties) {

		this.parties= parties;
	}
	
	public synchronized int await() throws InterruptedException {
		num++;

		int index = parties - num;

		if(num < parties){
			wait();
		}
		if(num == parties){
			num = 0;
			notifyAll();
		}



		// you need to write this code
		//num--;
	    return index;
	}
}
