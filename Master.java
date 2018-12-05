import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * This class takes care of executing everything - creating the appropriate threads
 * and eventually printing out what the nth prime is.  You can change what number
 * prime you're looking for and how many threads to use at the beginning
 *
 */
public class Master {

	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		//What number prime we're looking for
		int nthPrime = 100000;
		//how many threads we will have running at a time
		int numThreads = 10;
		
		//Creating a fixed pool lets us make sure we don't accidentally make too many threads
		ExecutorService es = Executors.newFixedThreadPool(numThreads);
		Tracker t = new Tracker(numThreads, nthPrime);
		Searcher temp;
		
		/*Create the appropriate amount of threads.
		 * We only need to worry about odd numbers starting at 3 - because 1 is not
		 * prime - so we have them start searching at 3, then 5, then 7, etc...
		 */
		for(int i = 0; i < numThreads; i++) {
			temp = new Searcher(3+i*2, numThreads, t);
			es.execute(temp);
		}
		
		/*Don't do anything until the threads are done searching for the specified
		prime number*/
		
		while(!t.isDone() /*|| ((ThreadPoolExecutor) es).getActiveCount() > 0*/) {
			//wait
			//System.out.println(((ThreadPoolExecutor) es).getActiveCount());
		}
		
		System.out.println("The " + nthPrime + "'th prime is " + t.getPrime());
		es.shutdown();
		System.out.println("This took " + (System.currentTimeMillis() - startTime) + " milliseconds.");
	}
	
}
