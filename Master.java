import java.util.Scanner;

public class Master{
	
public static void main(String[] args){
	Scanner scnr = new Scanner(System.in);
	
	Thread t = new Thread();
	
	int pNum = scnr.nextInt();
	for(int i = 1; i <= pNum; i++){
		Thread th1 = new Thread(new Searcher(i));
		Thread th2 = new Thread(new Searcher(i));
		Thread th3 = new Thread(new Searcher(i));
		Thread th4 = new Thread(new Searcher(i));
		//Searcher searcher = new Searcher(i); 
		//searcher.run();
		th1.run();
		th2.run();
		th3.run();
		th4.run();
	
	}
	
	
}


}
