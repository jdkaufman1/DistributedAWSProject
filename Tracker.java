public class Tracker {

	static int nprime;
	static int primeNum;
	
	
	public synchronized void checkReplace(int n) {
		if(n>primeNum) {
			primeNum= n;
		}
	}
	
	public synchronized void incrementNum() {
		nprime++;
	}

	public static int getPrimeNum() {
		return primeNum;
	}

	public static void setPrimeNum(int primeNum) {
		Tracker.primeNum = primeNum;
	}
	
}