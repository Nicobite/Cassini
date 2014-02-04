/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

import config.data.ConfigData;
import config.data.DataType;
import config.files.ConfigFile;
import config.types.vehicles.DriverConfig;
import config.types.vehicles.TestSetItem;
import config.types.vehicles.VehicleConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import simulation.data.Driving;
import simulation.data.FieldOfVision;
import simulation.data.Person;
import simulation.data.Vehicle;
import simulation.data.Behavior;
import simulation.data.Memory;
import simulation.generator.DataGenerator;
import simulation.data.VehiclePosition;

/**
 *
 * @author Sylvain
 */
public class SimulationThread extends Thread {
	// ordonnancing data

	private File map;
	private ConfigFile testsetFile;
	private boolean end = false;
	private volatile Order order = Order.IDLE;
	private volatile Status status = Status.NOT_DEFINED_YET;
	private volatile int progression = -1;
	// simulation data
	private ArrayList<Vehicle> vehicles = null;
	private ArrayList<Person> persons = null;
	private ArrayList<Driving> drivings = null;
	private ArrayList<Behavior> behaviors = null;
	private ArrayList<FieldOfVision> fieldsOfVision = null;
	private ArrayList<VehiclePosition> vehiclePositions = null;
        private ArrayList<Memory> memories =null;
        //TODO : move to parameters
	private final long ITERATION_PERIOD = 100;	// ms

	private void ConfigData(ConfigFile testsetFile) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public enum Order {

		IDLE, LOAD, RUN, RESTART, UNLOAD
	};

	public enum Status {

		ERROR("erreur"), //TODO define some things here
		NOT_DEFINED_YET("indéfini"),
		INITIALIZED("initialisé", Order.LOAD),
		LOADING("chargement en cours", Order.UNLOAD),
		LOADED("chargement OK", Order.RUN, Order.UNLOAD),
		RUNNING("simulation en cours", Order.IDLE, Order.RESTART, Order.UNLOAD),
		PAUSED("en pause", Order.RUN, Order.RESTART, Order.UNLOAD),
		RESTARTING("redémarrage en cours"),
		UNLOADING("libération des ressources en cours");
		/* ORGANISATION OF STATUS (given in chronological order)
		 * status starts with NOT_DEFINED_YET
		 * note that ERROR status can be set at any time
		 * 
		 * once initialized : INITIALIZED
		 * call to load() : LOADING then LOADED (loads a map, environment and vehicles/drivers data in memory)
		 * call to launch() : RUNNING (starts the simulation)
		 * call to pause() : PAUSED	(pauses the simulation)
		 * call to launch() : RUNNING (runs the simulation from the point it was paused)
		 * 
		 * the two following orders allow to get back :
		 * call to restart() while RUNNING or PAUSED : RESTARTING then back to LOADED state (vehicles positions and parameters are reset to their initial values)
		 * call to unload() while LOADING, LOADED, RUNNING or PAUSED: UNLOADING then back to INITIALIZED state (unload frees memory)
		 * 
		 * getProgression() gives a percentage of the progression while LOADING, RESTARTING, or UNLOADING.
		 * It gives 100 when INITIALIZED, LOADED.
		 * It gives -1 while NOT_DEFINED_YET, RUNNING or PAUSED
		 * Stays at the previous value when status is ERROR.
		 */
		/* STATUS INTERRUPTIBLE
		 * ERROR		yes
		 * INITIALIZED	yes
		 * LOADING		yes
		 * LOADED		yes
		 * RUNNING		yes
		 * PAUSED		yes
		 * RESTARTING	no
		 * UNLOADING	no
		 */
		private Order[] validOrders;
		private String stringStatus;

		private Status(String stringStatus, Order... validOrders) {
			this.validOrders = validOrders;
			this.stringStatus = stringStatus;
		}

		public boolean isValidOrder(Order order) {
			for (Order o : validOrders) {
				if (order == o) {
					return true;
				}
			}
			return false;
		}

		public String toHumanReadableString() {
			return stringStatus;
		}
	};

	public SimulationThread(File map, ConfigFile testsetFile) {
		this.map = map;
		this.testsetFile = testsetFile;
		status = Status.INITIALIZED;
		progression = 100;
	}

	private synchronized boolean setOrder(Order order) {
		if (!status.isValidOrder(order)) {
			// log
			return false;
		}
		this.order = order;
		this.notifyAll();
		return true;
	}

	public boolean load() {
		return setOrder(Order.LOAD);
	}

	public boolean unload() {
		return setOrder(Order.UNLOAD);
	}

	public boolean launch() {
		return setOrder(Order.RUN);
	}

	public boolean restart() {
		return setOrder(Order.RESTART);
	}

	public boolean pause() {
		return setOrder(Order.IDLE);
	}

	public synchronized Order getOrder() {
		return order;
	}

	public synchronized Status getStatus() {
		return status;
	}

	public synchronized int getProgression() {
		return progression;
	}

	public void run() {
		long lastIterationTime = 0;

		while (!end) {
			if (order == Order.IDLE) {
				if (status == Status.RUNNING) {
					status = Status.PAUSED;
				}
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
					};
				}
			} else if (order == Order.LOAD) {
				if (status == Status.INITIALIZED) {
					status = Status.LOADING;

					performAPieceOfLoad();

					if (true/*done*/) {
						order = Order.IDLE;
						status = Status.LOADED;
					}
				} else {
					//error and update status
					order = Order.IDLE;
				}
			} else if (order == Order.UNLOAD) {
				status = Status.UNLOADING;
				// TODO update variable "progression"

				// all unloading must be done at once
				vehicles = null;
				persons = null;

				order = Order.IDLE;
				status = Status.INITIALIZED;
			} else if (order == Order.RESTART) {
				status = Status.RESTARTING;
				// TODO some restart stuff here, all unloading must be done at once
				// don't forget to update variable "progression"

				// this is temporary restart (a reload actually)
				performAPieceOfLoad();

				order = Order.IDLE;
				status = Status.LOADED;
			} else if (order == Order.RUN) {
				status = Status.RUNNING;
			}
			progression = -1;

			// time management
			long timeSinceLastIteration = System.currentTimeMillis() - lastIterationTime;
			if (timeSinceLastIteration < ITERATION_PERIOD) {
				try {
					sleep(ITERATION_PERIOD - timeSinceLastIteration);
				} catch (InterruptedException e) {/*TODO catch an InterruptedException*/

				}
			} else {
				System.out.println("Laggy day, we are " + (timeSinceLastIteration - ITERATION_PERIOD) + " ms late");	// TODO log lag better
			}
			performAPieceOfRun();
		}
	}

	private void performAPieceOfLoad() {
		// TODO make this piece of work not too long to allow checking of cancel
		// TODO update variable "progression"

		//Work done on 12/05/2013 : add the loading of Driving, Behaviour & FieldOFVision
		try {
			// loading test set
			ConfigData cd = new ConfigData(testsetFile);
			ArrayList<Object> itemsConfigs = cd.getObjects();

			// loading associated vehicles
			cd = new ConfigData(testsetFile.getLinkedFile(DataType.DRIVER_VEHICLE_ASSOCIATIONS).getLinkedFile(DataType.VEHICLES));
			ArrayList<Object> vehiclesConfigs = cd.getObjects();
			HashMap<Integer, VehicleConfig> vehiclesIndexed = new HashMap<>();
			for (Object o : vehiclesConfigs) {
				VehicleConfig v = (VehicleConfig) o;
				vehiclesIndexed.put(v.getId(), v);
			}

			// loading associated drivers
			cd = new ConfigData(testsetFile.getLinkedFile(DataType.DRIVER_VEHICLE_ASSOCIATIONS).getLinkedFile(DataType.DRIVERS));
			ArrayList<Object> driversConfigs = cd.getObjects();
			HashMap<Integer, DriverConfig> driversIndexed = new HashMap<>();
			for (Object o : driversConfigs) {
				DriverConfig d = (DriverConfig) o;
				driversIndexed.put(d.getId(), d);
			}

			// converting config classes to simulation classes
			vehicles = new ArrayList<>(itemsConfigs.size());
			persons = new ArrayList<>(itemsConfigs.size());

			drivings = new ArrayList<>(itemsConfigs.size());
			behaviors = new ArrayList<>(itemsConfigs.size());
			fieldsOfVision = new ArrayList<>(itemsConfigs.size());
                        memories = new ArrayList<>(itemsConfigs.size());


			for (int i = 0; i < itemsConfigs.size(); i++) {
				TestSetItem item = (TestSetItem) itemsConfigs.get(i);
				VehicleConfig vehicleConfig = vehiclesIndexed.get(item.getVid());
				DriverConfig driverConfig = driversIndexed.get(item.getVid());

				// TODO make that better
				if (vehicleConfig == null) {
					throw new Exception("Le jeu de test fait rÃ©fÃ©rence Ã  un ID de vÃ©hicule inexistant : " + item.getVid());
				}
				if (driverConfig == null) {
					throw new Exception("Le jeu de test fait rÃ©fÃ©rence Ã  un ID de conducteur inexistant : " + item.getDid());
				}

				Vehicle vehicle = vehicleConfig.generateVehicle();
				Person person = driverConfig.generatePerson();

				person.setmVehicule(vehicle);

				Behavior behavior;
				behavior = DataGenerator.generateBehavior(person);
				Driving driving;
				driving = DataGenerator.generateDriving(vehicle, behavior);
				FieldOfVision fieldOfVision = null;
				VehiclePosition vehiclePosition = null;
                                Memory memory = null;

				vehicles.add(vehicle);
				persons.add(person);
				drivings.add(driving);
				behaviors.add(behavior);
				fieldsOfVision.add(fieldOfVision);
				vehiclePositions.add(vehiclePosition);
                                memories.add(memory);

			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO catch exception
		}
	}

	private void performAPieceOfRun() {
		// @ Jean : here stands the bottom of the stairway to heaven !
		// one iteration per execution of this function
		// this function executes every ITERATION_PERIOD milliseconds (a final field of this class - to be improved !)
		// if you don't use all this time, it's a nominal case
		// if time's up, a message appears in the console
		// you have two ArrayList containing your data : vehicles and persons
		// each person is already linked to his vehicle (funtion aPerson.setmVehicule(vehicle))
		// if you need to initialize more objects, or do some more stuff before beginning, do it at the end of the performAPieceOfLoad() method, right above
		// all your classes are in the package simulation.data
		// Vehicule was too French (at least I supposed so) and renamed to Vehicle
		// Behaviour was too American, and is now Behavior
		// to launch all that process, you'd better use the GUI because you need the ConfigFiles to be linked altogether. That's already done in the GUI
		// see with Laure, she should have started to do some things ;) tell her you need a play button !
		// may the force be with you !
            
                for(int i = 0; i < vehicles.size();i++){
                    //TODO : vehiclePosition.move, behavior.actualize,  driving.selectAction, driving.realizeAction
                    vehiclePositions.get(i).move(drivings.get(i));
                    behaviors.get(i).actualize();
                    /*TODO uncomment
					drivings.get(i).selectAction();
                    drivings.get(i).realizeAction();
                    fieldsOfVision.get(i).actualize();
                    memories.get(i).actualize();*/
                }
            
            
	}
}
