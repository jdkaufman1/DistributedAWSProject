import java.math.BigInteger;

public class Searcher implements Runnable{

	public BigInteger num;
	
	public Searcher(int a) {
		num = toBig(a);
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
		System.out.println("Doing fermat test on " + n.toString());
		int[] coprimes = {0,0,0,0};
		int counter = 0;
		for(int i = 2; coprimes[coprimes.length - 1] == 0; i++) {
			if(n.gcd(toBig(i)).equals(toBig(1))) {
				coprimes[counter] = i;
				System.out.println(i + " is coprime.");
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
		if(num%2 == 0) {
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
		if(fermat_test(num)) {
			if(deterministic_test(num)) {
				//get a message back saying its prime
			}
		}
		//get a message back saying its not prime
	}

}
