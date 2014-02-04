package simulation.data;



/**
 * Class Behavior
 * Describes a behavior for a Person
 * @author Jean
 */
public class Behavior {
        // Ratio of type
	private float effAwarness;
	private float effKindness;
	private float effIrritability;
	private float effAggressivity;
        private Person person;

    /**
     * Creates a new behavior for a Person, with the given ratios of awarness, kindness, irritability and aggressivity
     * @param effAwarness
     * @param effKindness
     * @param effIrritability
     * @param effAggressivity
     * @param mPerson
     */
    public Behavior(float effAwarness, float effKindness, float effIrritability, float effAggressivity, Person mPerson) {
        this.effAwarness = effAwarness;
        this.effKindness = effKindness;
        this.effIrritability = effIrritability;
        this.effAggressivity = effAggressivity;
        this.person = mPerson;
    }
    
    

    /**
     * Gets the ratio of awarness of the Person associated with this behavior (0-100)
     * @return
     */
    public float getEffAwarness() {
        return effAwarness;
    }

    /**
     * Gets the Person associated with this behavior 
     * @return
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Gets the ratio of kindness of the Person associated with this behavior (0-100)
     * @return
     */
    public float getEffKindness() {
        return effKindness;
    }

    /**
     * Gets the ratio of irritability of the Person associated with this behavior (0-100)
     * @return
     */
    public float getEffIrritability() {
        return effIrritability;
    }

    /**
     * Gets the ratio of aggressivity of the Person associated with this behavior (0-100)
     * @return
     */
    public float getEffAggressivity() {
        return effAggressivity;
    }

    /**
     * Sets the ratio of awarness of the Person associated with this behavior (0-100)
     * @param effAwarness
     */
    public void setEffAwarness(float effAwarness) {
        this.effAwarness = effAwarness;
    }

    /**
     * Sets the ratio of irritability of the Person associated with this behavior (0-100)
     * @param effIrritability
     */
    public void setEffIrritability(float effIrritability) {
        this.effIrritability = effIrritability;
    }

    /**
     * Sets the ratio of aggressivity of the Person associated with this behavior (0-100)
     * @param effAggressivity
     */
    public void setEffAggressivity(float effAggressivity) {
        this.effAggressivity = effAggressivity;
    }
     
    /**
     * Sets the ratio of kindness of the Person associated with this behavior (0-100)
     * @param effKindness
     */
    public void setEffKindness(float effKindness) {
        this.effKindness = effKindness;
    }
    
    /**
     * Actualizes the behavior
     */
    public void actualize(){
        //tof tof
    }
}