import java.math.BigInteger;

public class Searcher implements Runnable{

	private BigInteger num;
	private Tracker track;
	private int startNum;
	private int howManyThreads;
	
	public Searcher(int n, int c, Tracker t) {
		//System.out.println("Checking " + a);
		//num = toBig(a);
		//System.out.println("Created a searcher starting at " + n);
		startNum = n;
		howManyThreads = c;
		track = t;
	}
	
	public static BigInteger toBig(int n) {
		//System.out.println("Turning " + n + " to big");
		return BigInteger.valueOf(n);
	}
	
	public static boolean compCheck(BigInteger n, int a) {
		//System.out.println("Trying to check");
		BigInteger result = toBig(a).modPow(n.subtract(toBig(1)), n);
		if(!result.equals(toBig(1))) {
			return true;
		}
		return false;
	}
	
	public static boolean fermat_test(BigInteger n) {
		if(n.intValue() == 2) {
			return true;
		}
		//System.out.println("Doing fermat test on " + n.toString());
		int[] coprimes = {0,0,0,0};
		int counter = 0;
		for(int i = 2; coprimes[coprimes.length - 1] == 0; i++) {
			if(n.gcd(toBig(i)).equals(toBig(1))) {
				coprimes[counter] = i;
				//System.out.println(i + " is coprime.");
				counter++;
			}
		}
		
		for(int i = 0; i < coprimes.length; i++) {
			int a = coprimes[i];
			
			
			if(compCheck(n,a)) 
				return false;
			
		}
		
		return true;
	}
	
	public static boolean deterministic_test(BigInteger n) {
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
		//track.startThread();
		//System.out.println("My starting number is " + startNum);
		//System.out.println(track.isDone());
		while(!track.isDone()) {
			num = toBig(startNum);
			if(fermat_test(num)) {
				if(deterministic_test(num)) {
					track.incrementNum();
					track.checkReplace(num.intValue());
				}
			}
			System.out.println(startNum);
			startNum += howManyThreads*2;
			track.await();
		}
		
		
		//System.out.println("Finished.");
		//track.endThread();
		//get a message back saying its not prime
	}

}
