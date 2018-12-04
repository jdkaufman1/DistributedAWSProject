
public class Tracker {

	private static int nprime = 1;
	private static int primeNum = 2;
	private static int threadsRunning;
	private static int maxThreads;
	private final int lookingFor;
	private static boolean locked = false;
	private static int numWaiting;
	private static boolean broken;
	
	public Tracker(int num, int whatPrime) {
		lookingFor = whatPrime;
		maxThreads = num;
		threadsRunning = 0;
		numWaiting = 0;
		broken = false;
	}
	
	public synchronized void await() {

		numWaiting++;
		//System.out.println(numWaiting + " waiting.");
		if(numWaiting == maxThreads) {
			broken = true;
			//System.out.println("Broken");
			numWaiting = 0;
			broken = false;
			notifyAll();
			return;
		}
		
		if(broken == false) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public synchronized boolean isDone() {
		return nprime >= lookingFor; 
	}
	
	public synchronized int getThreadsRunning() {
		if(locked) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return threadsRunning;
	}
	
	public synchronized void checkReplace(int n) {
		if(locked) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(n>primeNum) {
			primeNum= n;
		}
		//System.out.println(primeNum + " is prime and is the " + nprime + "'th prime!");
	}
	
	public synchronized void incrementNum() {
		if(locked) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		nprime++;
	}
	
	public int whatNumber() {
		return nprime;
	}
	
	public int getPrime() {
		return primeNum;
	}
	
}
