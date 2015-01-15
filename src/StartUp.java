import java.util.ArrayList;


public class StartUp {
	
	public static void main(String[] args) {
		
		SexySAT a = new SexySAT("src/files/single_2.cnf");
		SexySAT b = new SexySAT("src/files/single_3.cnf");
		SexySAT c = new SexySAT("src/files/single_10.cnf");
		SexySAT d = new SexySAT("src/files/single_15.cnf");
		SexySAT e = new SexySAT("src/files/single_20.cnf");
		
		ArrayList<SexySAT> single = new ArrayList<>();
		single.add(a);
		single.add(b);
		single.add(c);
		single.add(d);
		single.add(e);
		
		SexySAT.printAsciiArt();
		
		for(SexySAT s : single) {
			printSlashes();
			System.out.println("File: " + s.getPath() + "--- running now... \n");
			long startTime = System.currentTimeMillis();
			s.run();
			long stopTime = System.currentTimeMillis();
		    long elapsedTime = stopTime - startTime;
		    printTime(elapsedTime);
		    printSlashes();
		}
	    
	}
	
	static void printSlashes() {
		System.out.println("\n");
		System.out.println("/////////////////////////////////////////////////////////////");
		System.out.println("\n");
	}
	
	static void printTime(long elapsedTime) {
		System.out.println("It took " + elapsedTime + " ms");
	}

}
