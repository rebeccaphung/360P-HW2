/*
 * rp32526
 * ajp3777
 */
import java.util.concurrent.Semaphore; // for implementation using Semaphores

public class CyclicBarrier {
	public int parties;
	Semaphore inBarrier;
	Semaphore passThruBarrier;
	
	public CyclicBarrier(int parties) {
		this.parties= parties;
		inBarrier = new Semaphore(parties);
		passThruBarrier = new Semaphore(parties);
	}
	
	public int await() throws InterruptedException {
        inBarrier.acquire();

        int index = inBarrier.availablePermits();

        while(inBarrier.availablePermits() > 0){

        }

        passThruBarrier.acquire();

        if(passThruBarrier.availablePermits() == 0){
            inBarrier.release(parties);
            passThruBarrier.release(parties);
        }

        return index;
	}
}
