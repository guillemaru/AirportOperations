/**
* an AirportWay that has an status ("TAKE-OFF" or "LANDING") and checks if an aircraft is allowed to enter
*/

public class Runway extends AirportWay {
	
	private String status;
	public Runway(String status) {
		super();
		this.status = status;
		}
	
	public boolean isAircraftAllowed() {
		
		if (this.gettotalPlanesAirport() >= AirportWay.maxCapacity) {
			return false;
		}
		else {
			return true;
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
