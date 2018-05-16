/**
* Super-class for taxiway and runway having a list of the planes currently inside
*/

import java.util.*;

public abstract class AirportWay {
	
	private ArrayList<Plane> currentPlanes;
	private static int totalPlanesAirport;
	protected final static int maxCapacity = 20;
	
	
	public AirportWay() {
		AirportWay.totalPlanesAirport = 0;
		this.currentPlanes = new ArrayList<Plane>();
	}

	public int getnOfPlanes() {
		return this.currentPlanes.size();
	}
	
	public int gettotalPlanesAirport() {
		return totalPlanesAirport;
	}
	
	public void addPlane(Plane newPlane) {
		this.currentPlanes.add(newPlane);
		AirportWay.totalPlanesAirport ++;
	}
	
	public void removePlane(Plane plane) {
		if (this.currentPlanes.size() >= 0) {
			this.currentPlanes.remove(plane);
			AirportWay.totalPlanesAirport --;
		}	
	}

	public int getAirportcapacity() {
		return maxCapacity;
	}
	
	public ArrayList<Plane> getCurrentPlanes() {
		return this.currentPlanes;
	}
	
	
	
}
