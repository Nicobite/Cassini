package simulation.data;


/**
 * Class Person
 * Representents a driver of a vehicle
 * @author Jean
 */
public class Person {
	private String name;
	private String surname;
	private int age;
        // social category of the person
	private enum category{};
        // for the following attributes : scale 0-100, average at 50
        // 
	private int awareness;
        //
	private int kindness;
        //
	private int aggressivity;
        //
	private int irritability;
        // Level of driving of the person
	private int levelOfDriving;
        // the person's vehicle
        private Vehicle mVehicule;

    /**
     * Creates a Person (driver of a vehicle)
     * @param name
     * @param surname
     * @param age
     * @param awareness
     * @param kindness
     * @param aggressivity
     * @param irritability
     * @param levelOfDriving
     */
    public Person(String name, String surname, int age, int awareness, int kindness, int aggressivity, int irritability, int levelOfDriving) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.awareness = awareness;
        this.kindness = kindness;
        this.aggressivity = aggressivity;
        this.irritability = irritability;
        this.levelOfDriving = levelOfDriving;
    }

    /**
     * Gets the name of a person (String)
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the surname of a person (String)
     * @return
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Gets the age of a person (int)
     * @return
     */
    public int getAge() {
        return age;
    }

    /**
     * Gets the awareness of a person (ratio 0-100)
     * @return
     */
    public int getAwareness() {
        return awareness;
    }

    /**
     * Gets the kindness of a person (ratio 0-100)
     * @return
     */
    public int getKindness() {
        return kindness;
    }

    /**
     * Gets the aggrissivity of a person (ratio 0-100)
     * @return
     */
    public int getAggressivity() {
        return aggressivity;
    }

    /**
     * Gets the irritability of a person (ratio 0-100)
     * @return
     */
    public int getIrritability() {
        return irritability;
    }

    /**
     * Gets the level of driving of a person (ratio 0-100)
     * @return
     */
    public int getLevelOfDriving() {
        return levelOfDriving;
    }

    /**
     * Gets the vehicle of a person
     * @return
     */
    public Vehicle getmVehicule() {
        return mVehicule;
    }

    /**
     * Sets the vehicle of a person
     * @param mVehicule
     */
    public void setmVehicule(Vehicle mVehicule) {
        this.mVehicule = mVehicule;
    }
        
}
