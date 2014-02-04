package simulation.data;


/**
 * Class Vehicle
 * Represents on vehicle
 * @author Jean
 */
public class Vehicle {
        // Constructor of the vehicle
	private String constructor;
        // Model of the vehicle
	private String model;
        // Width of the vehicle in cm
	private int width;
        // Length of the vehicle in cm
	private int length;
        // Height of the vehicle in cm
	private int height;
        // Weight of the vehicle in kg
	private int weight;
        // Volume of the vehicle in m^3
	private int volume;
        // Maximum speed in km/h
	private int maxspeed;
	// Maximum acceleration and decederation m/s²
	private float maxacc;
	private float maxdec;
        // Other options of the vehicle (not used yet)
	private enum options{};
        // Age of the vehicle in month
	private int age;
        // Level of maintenance on a scale of 10 (10=new vehicule)
	// TODO include this field in testsets configuration
	private int maintenance = 1;
        // Actual position of the vehicle (see class VehiclePosition
        private VehiclePosition vehiclePosition;

    /**
     *  Creates a new Vehicle
     * @param constructor
     * @param model
     * @param width
     * @param length
     * @param height
     * @param weight
     * @param maxspeed
     * @param maxacc
     * @param maxdec
     * @param age
     */
    public Vehicle(String constructor, String model, int width, int length, int height, int weight, int maxspeed, float maxacc, float maxdec, int age/*, int maintenance*/) {
        this.constructor = constructor;
        this.model = model;
        this.width = width;
        this.length = length;
        this.height = height;
        this.weight = weight;
        this.volume = length*height*width;
        this.maxspeed = maxspeed;
        this.maxacc = maxacc;
        this.maxdec = maxdec;
        this.age = age;
        //this.maintenance = maintenance;
    }


    /**
     * Gets the constructor of the vehicle (String)
     * @return
     */
    public String getConstructor() {
        return constructor;
    }

    /**
     * Gets the model of the vehicle (String)
     * @return
     */
    public String getModel() {
        return model;
    }

    /**
     * Gets the width of the vehicle (in cm)
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the length of the vehicle (in cm)
     * @return
     */
    public int getLength() {
        return length;
    }

    /**
     * Gets the height of the vehicle (in cm)
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the weight of the vehicle (in kg)
     * @return
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Gets the volume of the vehicle (in m^3)
     * @return
     */
    public int getVolume() {
        return volume;
    }

    /**
     * Gets the maximum speed of the vehicle (in km/h)
     * @return
     */
    public int getMaxspeed() {
        return maxspeed;
    }

    /**
     * Gets the maximum acceleration of the vehicle (in m/s²)
     * @return
     */
    public float getMaxacc() {
        return maxacc;
    }

    /**
     * Gets the maximum deceleration of the vehicle (in m/s²)
     * @return
     */
    public float getMaxdec() {
        return maxdec;
    }


    /**
     * Gets the age of the vehicle (in months)
     * @return
     */
    public int getAge() {
        return age;
    }

    /**
     * Gets the level of maintenance of the vehicle (scale 0-10)
     * @return
     */
    public int getMaintenance() {
        return maintenance;
    }

    /**
     * Gets the current vehicule position (see class VehiclePosition)
     * @return
     */
    public VehiclePosition getVehiclePosition() {
        return vehiclePosition;
    }

    /**
     * Sets the current vehicule position (see class VehiclePosition)
     * @param vehiclePosition
     */
    public void setVehiclePosition(VehiclePosition vehiclePosition) {
        this.vehiclePosition = vehiclePosition;
    }
    
}
