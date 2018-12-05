
/**
 * A class that serves as a sort of monitor and barrier while keeping track of 
 * what number prime we're on.
 *
 */
public class Tracker {

	//These variables keep track of what prime we have and what number prime it is
	private static int nprime = 1;
	private static int primeNum = 2;
	
	//This keeps track of what our maximum # of threads is.
	private static int maxThreads;
	
	//Keeps track of what number prime we're looking for so we know when we're done
	private final int lookingFor;

	//Used in the barrier
	private static int numWaiting;
	private static boolean broken;
	
	//Constructor for our Tracker - initializes it based on what number we're looking for
	public Tracker(int num, int whatPrime) {
		lookingFor = whatPrime;
		maxThreads = num;
		//threadsRunning = 0;
		numWaiting = 0;
		broken = false;
	}
	
	/**
	 * Standard barrier await method
	 */
	public synchronized void await() {

		numWaiting++;
		if(numWaiting == maxThreads) {
			broken = true;
			numWaiting = 0;
			//Not sure why but this makes it work but not having that there kills it
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
	
	/**
	 * Used to force the tracker to replace primeNum even if the number is smaller
	 * @param n
	 */
	public synchronized void forceReplace(int n) {
		primeNum = n;
	}
	
	/**
	 * 
	 * @return whether the tracker is done or not
	 */
	public synchronized boolean isDone() {
		return nprime >= lookingFor; 
	}
	
	/**
	 * See if we should replace the current prime that we are holding.  This is
	 * necessary to check because we don't know the order that the threads are going
	 * to return their primes in - its possible that when starting out, 3 could be the
	 * fifth prime to be reported back even though its the second prime.  By always
	 * incrementing what number prime we're on but potentially not replacing the 
	 * prime, we make sure that if things are out of order they eventually get fixed.
	 * @param n
	 */
	public synchronized void checkReplace(int n) {
		
		if(n>primeNum) {
			primeNum= n;
		}
	}
	
	/**
	 * Increment which number prime we're on
	 */
	public synchronized void incrementNum() {
		nprime++;
	}
	
	/**
	 * Return which number prime we're on
	 * @return nprime
	 */
	public int whatNumber() {
		return nprime;
	}
	
	/**
	 * Return what the actual prime number is
	 * @return the actual prime.
	 */
	public int getPrime() {
		return primeNum;
	}
	
}
