import java.math.BigInteger;

/**
 * This class creates a thread which starts at a certain number and checks
 * numbers it is assigned to to see if they are prime.  The thread never stops
 * running, and instead waits for every other thread to finish so that 
 * an entire batch of numbers is finished.  This is neccessary to prevent one
 * thread from getting way ahead and getting an inaccurate result for the
 * nth prime.
 * 
 * Numbers are assigned based on how many threads are being created:  for example,
 * if only two threads are being used, one thread will be assigned 3,7,11,15,etc...
 * whereas the other thread is assigned 5,9,13,17,...
 * 
 * Alternatively, if five threads are used, Thread 1 will get 3,13,23,...
 * Thread 2 will get 5,15,25,...
 * Thread 3 will get 7,17,27,...
 * And so forth.
 * 
 *
 */
public class Searcher implements Runnable{

	private BigInteger num;
	private Tracker track;
	private int startNum;
	private int howManyThreads;
	
	/**
	 * Constructor method that creates a searcher
	 * @param n the number to start searching at
	 * @param c how many threads are being created
	 * @param t the tracker which the threads use to communicate with
	 */
	public Searcher(int n, int c, Tracker t) {
		startNum = n;
		howManyThreads = c;
		track = t;
	}
	
	/**
	 * This method takes an integer and turns it into a BigInteger, which is
	 * a class that is used to store numbers greater than the integer limit of 
	 * java
	 * @param n the number
	 * @return the number as a big integer
	 */
	public BigInteger toBig(int n) {
		//System.out.println("Turning " + n + " to big");
		return BigInteger.valueOf(n);
	}
	
	/**
	 * Checks if a given number is composite or not - only useful in the context of
	 * Fermat's test
	 * @param n the number being checked for compositeness
	 * @param a a number coprime to it
	 * @return the results of the test.
	 */
	public boolean compCheck(BigInteger n, int a) {
		BigInteger result = toBig(a).modPow(n.subtract(toBig(1)), n);
		if(!result.equals(toBig(1))) {
			return true;
		}
		return false;
	}
	
	/**
	 * A fast, probabalistic test for whether a number is prime.  If this test
	 * is false, then the number is definitely composite.  If it is true, then
	 * it may or may not be prime - further testing is required.
	 * @param n the number being checked
	 * @return the results of the test
	 */
	public boolean fermat_test(BigInteger n) {
		//if the number is equal to 2, its prime.
		if(n.intValue() == 2) {
			return true;
		}
		if(n.intValue() % 2 == 0) {
			return false;
		}
		/*Fermat's test works using numbers coprime to the number you are checking.
		 *However, some of these numbers are known as "liars" - they falsely report
		 *that the number is prime.  The more different coprimes you use, the better
		 *chance you have of getting a correct result, so we use four.
		 * 
		 */
		int[] coprimes = {0,0,0,0};
		int counter = 0;
		//Find numbers coprime to our number and add them to our array.
		for(int i = 2; coprimes[coprimes.length - 1] == 0; i++) {
			if(n.gcd(toBig(i)).equals(toBig(1))) {
				coprimes[counter] = i;
				//System.out.println(i + " is coprime.");
				counter++;
			}
		}
		
		//Run the fermat test on each one of our coprimes.  Only return true
		//if it passes the test for each number.
		for(int i = 0; i < coprimes.length; i++) {
			int a = coprimes[i];
			if(compCheck(n,a)) 
				return false;
			
		}
		
		return true;
	}
	
	/**
	 * A simple, decently efficient deterministic test for a number being prime:
	 * Checking if it is even and then checking each odd number less than the
	 * square root of it to see if they divide it.  If they don't, we know this
	 * number is prime with 100% certainty.  We don't do this from the start
	 * since it can be very slow and we want to save time.
	 * @param n The number to check
	 * @return whether it is prime.
	 */
	public boolean deterministic_test(BigInteger n) {
		int num = Integer.parseInt(n.toString());
		int sqrt = (int)Math.sqrt(num)+1;
		if(num == 2) {
			return true;
		}
		else if(num%2 == 0) {
			return false;
		}
		else {
			for(int i = 3; i < sqrt; i+=2){
				if(num%i == 0)
					return false;
			}
		}
		return true;
	}
	
	public void run() {
		/*
		 * Loop until it is reported that we have found our prime number.
		 * In the loop, we check the fermat test and then, if necessary, the
		 * deterministic test.  If we find a prime number, we attempt to replace
		 * our current "nth prime" with it, and always increment the number of primes
		 * that we have found.  Then the number that we are checking is incremented
		 * by the appropriate value to stay within the numbers that this thread
		 * was "assigned".  Finally, we wait at a barrier until each thread has
		 * finished checking its number.
		 */
		while(!track.isDone()) {
			num = toBig(startNum);
			if(fermat_test(num)) {
				if(deterministic_test(num)) {
					track.incrementNum();
					track.checkReplace(num.intValue());
				}
			}
			startNum += howManyThreads*2;
			track.await();
		}
		
		//used to make sure that each thread still evaluates their number
		//in case a bigger prime returned back first for being the "nth prime"
		System.out.println(startNum);
		if(startNum < track.getPrime()) {
			System.out.println("Checking if " + startNum + " is smaller and should replace the nth prime.");
			if(fermat_test(toBig(startNum))) {
				if(deterministic_test(toBig(startNum))) {
					System.out.println("Replacing with " + startNum);
					track.forceReplace(startNum);
				}
			}
		}
	}

}
