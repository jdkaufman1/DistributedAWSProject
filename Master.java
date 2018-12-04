import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Master {

	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		//What number prime we're looking for
		int nthPrime = 1000000;
		int numThreads = 1;
		ExecutorService es = Executors.newFixedThreadPool(numThreads);
		Tracker t = new Tracker(numThreads, nthPrime);
		//Searcher twoSearch = new Searcher(2,t);
		//es.execute(twoSearch);
		
		Searcher temp;
		for(int i = 0; i < numThreads; i++) {
			//System.out.println("Spawned " + (i+1) + " threads");
			temp = new Searcher(3+i*2, numThreads, t);
			es.execute(temp);
		}
		
		while(!t.isDone()) {
			//wait
		}
		/*
		while(nthPrime > t.whatNumber()) {
			if(((ThreadPoolExecutor) es).getActiveCount() < t.getMaxThreads()) {
				temp = new Searcher(numToCheck, t);
				es.execute(temp);
				System.out.println(((ThreadPoolExecutor) es).getActiveCount() + " threads running.");
				numToCheck += 2;
			}
		}*/
		
		System.out.println("The " + nthPrime + "'th prime is " + t.getPrime());
		es.shutdown();
		System.out.println("This took " + (System.currentTimeMillis() - startTime) + " milliseconds.");
	}
	
}
