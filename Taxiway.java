/**
* an AirportWay that is less complex than runway because it does not ave to check if planes are
* taking-off or landing
*/

public class Taxiway extends AirportWay{
	
	public Taxiway() {
		super();
		}
	
	public boolean isAircraftAllowed() {
		if (this.gettotalPlanesAirport() >= AirportWay.maxCapacity) {
			return false;
		}
		else {
			return true;
		}
	}
	

}
