/**
* This class observes all the planes, and when any plane wants to 
* do something new, the controller uses the method update to answer
* the request of the aircraft
* 
* The controller manages 3 lists: the planes that want to land but for the moment they cannot 
* (queue_LAND) the planes that want to take-off and are waiting at the taxiway (queue_TO) and
* the planes that are waiting at the parking station to start operations and enter the taxiway 
* (queue_TW)
*/


import java.util.*;

public class Controller implements Observer{
	private Runway runw;
	private Taxiway taxiw;
	private ArrayList<Plane> queue_LAND;
	private ArrayList<Plane> queue_TW;	
	private ArrayList<Plane> queue_TO;
		
	public Controller(Runway runw, Taxiway taxiw) {
		this.runw = runw;
		this.taxiw = taxiw;
		
		this.queue_LAND = new ArrayList<Plane>();
		this.queue_TO   = new ArrayList<Plane>();
		this.queue_TW   = new ArrayList<Plane>();
		
	}
	
	
	/**
	* Main function for the controller. It will be triggered when the status of a Plane is changed
	* This method should check for the 'interaction' property of the Plane and then decide the action to be performed
	*/
	@Override
	public void update(Observable planeObserved, Object nthng) {
		
		Plane plane = (Plane) planeObserved; //transform into the plane class
		String interaction = plane.getInteraction();
		
		switch (interaction) {
			case "TAXIWAY_ACCESS_ENTERING":
				// The plane wants to access the taxiway from ground. 
				// Add it to the taxiway queue in the 'last' position if it cannot enter
				
				if (taxiw.isAircraftAllowed()) {
					taxiw.addPlane(plane);
					System.out.println("GRANTED!! :)");
				}
				else {
					queue_TW.add(plane);
					System.out.println("DENIED... :(");
				}
				break;
				
			case "TAKEOFF_ACCESS":
				// The plane wants to access the runway from ground (but it's still on the tw).
				// Grant authorization only if planes are taking of and the status of the
				// runway is take-off
				if (runw.getStatus().equals("TAKEOFF") || runw.getStatus().equals("NULL")) {
					runw.addPlane(plane);
					taxiw.removePlane(plane);
					System.out.println("GRANTED!! :)");
				}
				else {
					queue_TO.add(plane);
					System.out.println("DENIED... :(");
				}
				break;
				
			case "TAKEOFF_SUCCESSFUL":
				// The plane took off and is no longer on the runway.
				// Remove it from the runway. No need for authorization in this interaction
				runw.removePlane(plane);		
				break;
				
			case "LANDING_ACCESS":
				// The plane wants to access the runway from air. 
				// Deny the interaction and put the plane in queue_LAND if the runway is in
				// take-off status or if it would exceed the capicity of the airport
				
				if ((runw.getStatus().equals("LANDING")) && (runw.isAircraftAllowed())) {
					runw.addPlane(plane);
					System.out.println("GRANTED!! :)");
				}
				else if (runw.getStatus().equals("TAKEOFF") || !runw.isAircraftAllowed()) {
					queue_LAND.add(plane);
					System.out.println("DENIED... :(");
				}
				else {
					runw.addPlane(plane);
					runw.setStatus("LANDING");
					System.out.println("GRANTED!! :)");
				}
					
				break;
				
			case "LANDING_SUCCESSFUL_TAXIWAY_ACCESS":
				// Always granted as it is not going to increase the capacity of the airport
				runw.removePlane(plane);
				taxiw.addPlane(plane);
				System.out.println("GRANTED!! :)");
				break;
				
			case "LEAVING_TAXIWAY":
				// The plane that just landed wants to leave the taxiway. Allow him to do so.
				// Remove it from the taxiway
				taxiw.removePlane(plane);
				break;
		}
		
		
		// After getting notified of a change, check if now the aircrafts are allowed to access 
		// runway or taxiway. 
		
		if (runw.getStatus().equals("LANDING")) { //all the planes that are in queue_LAND can land until the max capacity of airport is reached
			while (runw.isAircraftAllowed() && !queue_LAND.isEmpty()){				
				runw.addPlane(queue_LAND.get(0));
				queue_LAND.remove(0);
			}
		}
		
		// if no plane is in RW and there are some in the air waiting, first let (if any) take-off the planes from ground, and then
		// authorize the planes in the air to land until max capacity of the airport is reached
		if ((runw.getnOfPlanes() == 0) && (!queue_LAND.isEmpty())) {
			
			runw.setStatus(runw.getStatus().equals("TAKEOFF") ? "LANDING" : "TAKEOFF"); //change the status of the runway
			
			if (runw.getStatus().equals("TAKEOFF")) { //all the planes in the taxiway, going to runway
				while (!queue_TO.isEmpty()){				
					runw.addPlane(queue_TO.get(0));
					taxiw.removePlane(queue_TO.get(0));
					queue_TO.remove(0);
					
				}
					
				while (taxiw.isAircraftAllowed() && !queue_TW.isEmpty()){ //all the planes waiting to enter the taxiway, allowed to enter the taxiway until max capacity				
						taxiw.addPlane(queue_TW.get(0));
						queue_TW.remove(0);
					
				}
			}
			else { //allow the planes in the air to land until max capacity
				while (runw.isAircraftAllowed() && !queue_LAND.isEmpty()){				
					runw.addPlane(queue_LAND.get(0));
					queue_LAND.remove(0);
				}
			}
		}
		
		
		
	System.out.println("Queue Tawiway: "+queue_TW);
	System.out.println("Queue RUNWAY: "+queue_LAND);
	}
}
