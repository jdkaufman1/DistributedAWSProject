
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
	
}
