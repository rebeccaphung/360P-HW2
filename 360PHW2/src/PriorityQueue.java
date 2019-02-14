
// EID 1
// EID 2

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PriorityQueue {

    int maxSize;
    int count = 0;
    ReentrantLock queueLock = new ReentrantLock();
    Condition isNotFull = queueLock.newCondition();
    Condition isNotEmpty = queueLock.newCondition();
    Node head = null;

	public PriorityQueue(int maxSize) {
        // Creates a Priority queue with maximum allowed size as capacity
        this.maxSize = maxSize;
	}

	public synchronized int add(String name, int priority) {
        // Adds the name with its priority to this queue.
        // Returns the current position in the list where the name was inserted;
        // otherwise, returns -1 if the name is already present in the list.
        // This method blocks when the list is full.
        int index = 0;
        Node newNode = new Node(name, priority);

        try {
            if(count == 0){
                head = newNode;
                queueLock.lock();
                isNotEmpty.signalAll();
                queueLock.unlock();
                return 0;
            }
            while (count == maxSize) {
                isNotFull.await();
            }

            Node cur = head;
            Node prev = null;

            cur.lockNode();

            while(cur != null){
                if(cur.priority > newNode.priority && prev.priority < newNode.priority){
                    newNode.next = cur;
                    prev.next = newNode;
                    return index;
                }

                if(cur.next != null) {
                    cur.next.lockNode();
                    prev.unlockNode();
                    prev = cur;
                    cur = cur.next;
                }
                else{
                    
                }


            }

            isNotEmpty.signalAll();
        }catch(InterruptedException e){}
        return index;
	}

	public int search(String name) {
        // Returns the position of the name in the list;
        // otherwise, returns -1 if the name is not found.
        int index = 0;
        Node cur = head;


        while (cur != null && !cur.name.equals(name)){
            cur = cur.next;
            index++;
        }

        if(cur == null){
            index = -1;
        }

        return index;
	}

	public String getFirst() {
        // Retrieves and removes the name with the highest priority in the list,
        // or blocks the thread if the list is empty.
        /*
        while(count == 0){
            isNotEmpty.await();
        }

        isNotFull.signalAll();*/
        return "we suck";
	}

	public class Node{
        ReentrantLock nodeLock = new ReentrantLock();
	    public String name;
	    public int priority;
	    public Node next;


	    public Node(String name, int priority){
	        this.name = name;
	        this.priority = priority;
	        next = null;
        }

        public void lockNode() {
            nodeLock.lock();
        }

        public void unlockNode() {
            nodeLock.unlock();
        }
    }
}

