# AirportOperations

(Developed by MARUGAN RUBIO, Guillermo and HORTAL SEGURA, Antonio)

This Java program has been designed to satisfy the specifications provided at the start of the project. Mainly, the program is able to control the access of airplanes to an airport with one runway and one taxiway, keeping into account some constraints that must be always fulfilled. The constraints may be summarized in the following table:

The system is to control planes on ground: taxiway and runway
FUN-1

The system controls the access to the runway for takeoff or landing
FUN-2

The number of planes on ground is limited to 20
CON-1

Aircraft are permanently assigned the authorization to access the runway and taxiway
OPE-1

A plane which is on ground (taxiway, runway) must be allowed to be there 
OPE-2

The runway is occupied for either takeoff or landing, but not both at the same time 
SAF-1

The takeoff or landing process of a plane takes some steps in which communication with the ground control center is required. It is the controller who decides if the requirements of the previous table are met and if the aircraft is allowed to enter or exit any of the runway/taxiway. Then, for an operation to be carried out by a plane, there needs to be some kind of contact- decision process that involves all the elements of the airport. In the next lines, we include how is more or less the order of the events that happen when an airplane is about to land or takeoff.
- First, the Plane must establish communication with the Controller. This is done through an Access Request message to the control room. The Controller will immediately add this Plane to a list of aircraft that are allowed to request further authorizations in order to perform the desired maneuvers. If the Access Request was not asked, further messages from the Plane would not be heard by the Controller.
- Our program has a particularity at this point: A Plane inherits from the Java Util class Observable, and the Controller implements the Observer interface. When an Access Request is demanded and accepted by the control segment, the Controller is set to observe any change in the Plane. This allows more of event-driven operations in the sense that the Controller is an action listener that only needs to perform a given task when the Plane contacts the tower.
- Now, the Plane that has already asked for communication, can request a new interaction to the Controller. As specified by the software requirements, these new interactions are sent in form of string messages whose purpose is to request for access to a particular Runway/Taxiway for taking off or landing. The Controller then listens (or observes) the changes in the Plane communication and is then triggered. He will take a decision according the status of the Runway/Taxiway.
- The Plane will be allowed to enter the Runway if:
• The number of planes in the airport does not exceed the maximum capacity, mainly 20. This constraint does not suppose a problem for airplanes taking off since the change from Taxiway to Runway does not alter the total amount of aircraft in the airport.
• The Runway is empty or if the planes in it are in the same mode (landing or taking off) as the given Plane (SAF-1). This avoids planes using the Runway to land and takeoff at the same time.
- The Plane will be allowed to access the Taxiway if:
• The number of planes in the airport does not exceed the maximum capacity, mainly 20. This constraint does not suppose a problem for airplanes landing since the change from Runway to Taxiway does not alter the total amount of aircraft in the airport.
- The Controller, when trigger, looks at the properties (capacity and mode) of the Runway and Taxiway and, if the specifications are met, gives a “Granted” authorization to the Plane, that starts to perform the desired action.
- If the requirements of the airport were not fulfilled, the Controller emits a “Denied” authorization, meaning that the Plane is not allowed to continue on its operation. The Controller adds him then to a specific queue (planes that want to access the Runway because they are about to land or take-off, or that want to access the Taxiway).
- Queue management is another addition to our software that was not specified by the original problem statement. We thought that if a Plane communicates with the Controller and is denied, should not keep contacting the Controller asking for the same permission until finally the conditions on the airport allow for the access. Rather, we thought that the Controller should keep the Plane in a queue and, when the Runway/Taxiway satisfy the constraints, contact it and allow him for its operation. This method, in our opinion, is better since the Plane does not have to keep repeating a message until the conditions are met for an authorization to be given.
- The queuing property of our airport adds another problem to the mix: the strategy of the Controller must be specified. By strategy we mean that the Controller must give priority to a certain type of queue when the Runway/Taxiway allow for more planes. Our Controller gives this priority to the aircraft that are in the queue of the same mode of the Runway (landing or take-off) and then to the ones on the queue for the Taxiway.

- When a Plane communicates with the tower, the Controller decides to grant or deny the authorization and then performs the queue management.
- Finally, when a Plane finishes operation, it sends a Terminate Operations message, and is automatically removed from the list of planes in contact with the Controller, so it is no longer ‘observed’.
In Bold we have marked the classes that make our software, and in Italic the interfaces of some of them. Mainly:
Controller implements Observer Plane inherits from Observable Runway/Taxiway inherit from Airportway
We forgot to mention that the Runway and Taxiway inherit from a general class, Airportway, that contains some methods common to both o them, such the one that checks for the number of planes currently in them.
In summary, our software was designed to take into account all the constraints in the problem statement and adds a couple of new features to it:
o Queuemanagement
o Event driven operations throughout Javas Observer/Observable interface.
We decided not to include any element from the Swing library because we thought that (except for a full GUI where the airport was drawn – too complicated) only a Button to read the next line of the .csv file made sense. However, we preferred our approach with the event-driven operation since it allows for real-time control of the airport: instead of reading from the .csv file, the controller listens to real time changes.

USERS GUIDE
Here we will briefly comment the methods from the classes that compose our software so that anyone can use it.
Main.java: Is the one to be executed. Contains the main method, where the lines of the .csv files are read. Planes are here created and, depending on the interaction written on the .csv line, their status changes.
Controller.java: The controller class. It implements the Observer interface with the method update, that is triggered every time the command ‘setChanged()’ appears in the object that is being observed (a Plane in this case). The controller is composed of the following methods:
• Constructor. Requires as inputs a Runway and a Taxiway to be controlled and defines the queues as empty.
• update. Actions that the controller performs when an event is triggered. Here we included the authorization process and the queue management.
Plane.java: The airplane class. Each aircraft is composed of a serial number (for identification purposes) and an interaction (the action that the plane wants to perform). The methods that compose this class are:
• changeInteraction. When a line of the .csv file concerning a plane is read, this method is called. It changes the interaction property and notifies the observer (controller) through the setChanged(() command.
• Getters/Setter for the properties.
Airportway.java: Abstract class from which the Runway and Taxiway inherit. It contains a static property (totalPlanesAirport) that holds the total amount of airplanes in the airport, as well as the maximum capacity in it. The methods that are common to both runway and taxiway are:
• addPlane. Increment by one the total number of planes in the airport and add the plane to the currentPlanes arraylist.
• removePlane. Subtract one from current number of planes and remove it from the arraylist.
• isAircraftAllowed. Check if the total number of aircraft in the airport does not exceed the maximum capacity.
Runway.java: Inherits from the Airportway abstract class and includes a status property that can be either LAND or TAKEOFF. It is accessed by the controller to change the mode of the runway.
Taxiway.java: Inherits from the abstract class Airportway. Does not include any other feature.
