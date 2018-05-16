/**
* This class can instantiate planes that have a code
* and an interaction that can be changed by a new line of the .csv file
* the consequence of a new interaction would give work to the controller
* (observer of the planes) to authorize or deny the new interaction
*/

import java.util.*;

public class Plane extends Observable{
	private String code;
	private String interaction;
	//Possible values for interaction:
	// "TAXIWAY_ACCESS_ENTERING"
	// "TAKEOFF_ACCESS"
	// "TAKEOFF_SUCCESSFUL "
	// "LANDING_ACCESS"
	// "LANDING_SUCCESSFUL_TAXIWAY_ACCESS "
	// "LEAVING_TAXIWAY "
	
	
	public void changeInteraction(String interaction) {
		this.interaction = interaction;
		setChanged();
		notifyObservers();
	}
	
	@Override
	public String toString() {
		return "Plane [code=" + code + ", interaction=" + interaction + "]";
	}

	public Plane(String code, String interaction) {
		this.code = code;
		this.interaction = interaction;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getInteraction() {
		return interaction;
	}


	public void setInteraction(String interaction) {
		this.interaction = interaction;
	}

	
}


