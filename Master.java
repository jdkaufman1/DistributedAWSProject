import java.util.Scanner;

public class Master{
	
public static void main(String[] args) throws InterruptedException{
	Scanner scnr = new Scanner(System.in);
	
	Tracker T = new Tracker();
	
	int pNum = scnr.nextInt();
	while(T.getNprime() < pNum){
		Thread th1 = new Thread(new Searcher(T.getNprime(), T));
		Thread th2 = new Thread(new Searcher(T.getNprime(), T));
		Thread th3 = new Thread(new Searcher(T.getNprime(), T));
		Thread th4 = new Thread(new Searcher(T.getNprime(), T));
		//Searcher searcher = new Searcher(i); 
		//searcher.run();
		th1.run();
		th2.run();
		th3.run();
		th4.run();
		
	}
	
	
}


}
