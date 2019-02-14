// rp32526
// ajp3777

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class FairUnifanBathroom {
	boolean isTrashInBathroom = false;
	int bathroomCount = 0;
	int lineCount = 0;
	int lineNumInBathroom = 0;
	ThreadLocal<Integer> lineNumber = new ThreadLocal<>();

	public synchronized void enterBathroomUT() throws InterruptedException {
		lineCount++;
		lineNumber.set(lineCount);

		while(lineNumber.get() != lineNumInBathroom){
			wait();
		}

		while(isTrashInBathroom && bathroomCount > 0){
			wait();
		}
		while(bathroomCount == 5) {
			wait();
		}

		bathroomCount++;
		isTrashInBathroom = false;

	}

	public synchronized void enterBathroomOU() throws InterruptedException {
		lineCount++;
		lineNumber.set(lineCount);

		while(lineNumber.get() != lineNumInBathroom){
			wait();
		}

		while(!isTrashInBathroom && bathroomCount > 0){
			wait();
		}
		while(bathroomCount == 5) {
			wait();
		}

		bathroomCount++;
		isTrashInBathroom = true;
	}

	public synchronized void leaveBathroomUT() {
		bathroomCount--;

		if(bathroomCount == 0){
			notifyAll();
		}
	}

	public synchronized void leaveBathroomOU() {
		bathroomCount--;

		if(bathroomCount == 0){
			notifyAll();
		}
	}

	/*
	ReentrantLock bathroom = new ReentrantLock();
	Condition NoOUInBathroom = bathroom.newCondition();
	Condition NoUTInBathroom = bathroom.newCondition();
	Condition isNotFull = bathroom.newCondition();
	boolean isTrashInBathroom = false;
	int count = 0;

  public synchronized void enterBathroomUT() {
	//bathroom.lock();
	try{
		while(isTrashInBathroom){
			NoOUInBathroom.await();
		}
		while(count == 5){
			isNotFull.await();
		}
		count++;
		isTrashInBathroom = false;
	} catch (InterruptedException e) {
		e.printStackTrace();
	}// finally {
		//bathroom.unlock();
	//}
	  // Called when a UT fan wants to enter bathroom
  }
	
	public synchronized void enterBathroomOU() {
	//	bathroom.lock();
		try{
			while(!isTrashInBathroom){
				NoUTInBathroom.await();
			}
			while(count == 5){
				isNotFull.await();
			}
			count++;
			isTrashInBathroom = true;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} //finally {
	//		bathroom.unlock();
	//	}
    // Called when a OU fan wants to enter bathroom
	}
	
	public synchronized void leaveBathroomUT() {
	//	bathroom.lock();
		try{

			count--;
			if(count == 0) {
				NoUTInBathroom.signal();
			}
			isTrashInBathroom = true;
			isNotFull.signal();
		}
		catch (Exception e){
			//finally {
		}
	//		bathroom.unlock();
	//	}
    // Called when a UT fan wants to leave bathroom
	}

	public synchronized void leaveBathroomOU() {
	//	bathroom.lock();
		try {

			count--;
			if (count == 0) {
				NoOUInBathroom.signal();
			}
			isTrashInBathroom = false;
			isNotFull.signal();
		}catch (Exception e){

		}
	//	} finally {
	//		bathroom.unlock();
	//	}
    // Called when a OU fan wants to leave bathroom
	}
	*/

}
	
