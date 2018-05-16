import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException,InterruptedException {
		// Declaration of new variables: creation of a Runway, Taxiway, Controller, and a list where
		// planes can be added
		ArrayList<Plane> planesList = new ArrayList<Plane>();
		Runway rw = new Runway("NULL");
		Taxiway tw = new Taxiway();
		Controller controller = new Controller(rw,tw);
		
		// Read the csv file
		Scanner scanner = new Scanner(new File("/Users/guille/Desktop/MAE1/JAVA/Airport Ground Operations/planes.csv"));
		while(scanner.hasNextLine()){
			
			String line = scanner.nextLine();
			String[] lineArray = line.split(";");
			System.out.println(line);

			if(lineArray[1].equals("ACCESS_REQUEST")) {	//put the new plane on the list; the controller will observe it	
				planesList.add(new Plane(lineArray[0],lineArray[1]));
				planesList.get(planesList.size() - 1).addObserver(controller);
				
			} 
			else if(lineArray[1].equals("TERMINATE_OPERATIONS")) {
				int idx = 0; //this is used because we cannot erase an element of planesList during the for loop
				for(Plane p: planesList) {
					if(p.getCode().equals(lineArray[0])) {
						idx = planesList.indexOf(p); 
					}
				}
				planesList.remove(idx);	
			} 
			else { //let's see if the controller approves the request
				for(Plane p: planesList) {
					if(p.getCode().equals(lineArray[0])) {
						System.out.println("- Authorization for this request: ");
						p.changeInteraction(lineArray[1]); // this enables the method update of the controller
						
					}
				}
			}
			System.out.println("Number of planes in runway: "+rw.getnOfPlanes());
			System.out.println("Number of planes in taxiway: "+tw.getnOfPlanes());
			System.out.println();
        }
		
        scanner.close();

	}

}
