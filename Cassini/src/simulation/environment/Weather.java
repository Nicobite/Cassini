/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.environment;

/**
 * Class Weather
 * Creates a weather for the GlobalEnvironment
 * @author Jean
 */
public class Weather {
    private int brightness;
    private int rain;
    private int temperature;
    private int wind;
    private int windDirection;
    
    private int awarenessOffset;
    private int kindnessOffset;
    private int aggressivityOffset;
    private int irritabilityOffset;

    /**
     * Creates new from weather 
     * @param brightness
     * @param rain
     * @param temperature
     * @param wind
     * @param windDirection
     */
    public Weather(int brightness, int rain, int temperature, int wind, int windDirection) {
        this.brightness = brightness;
        this.rain = rain;
        this.temperature = temperature;
        this.wind = wind;
        this.windDirection = windDirection;
        actualizeOffset();
    }
    
    /**
     * Actualises the comportemental offsets according to the weather
     */
    private void actualizeOffset(){
        this.awarenessOffset =  (this.rain - this.brightness + 50)/10;
        this.kindnessOffset = (this.brightness - this.rain - 50)/10;
        this.aggressivityOffset = (- this.brightness - this.rain + 50 )/10;
        this.irritabilityOffset = (this.rain - this.brightness + 50)/10;
    }
    
    /**
     * Gets the brightness (ratio 0-100)
     * @return
     */
    public int getBrightness() {
        return brightness;
    }

    /**
     * Sets the brightness (ratio 0-100)
     * @param brightness
     */
    public void setBrightness(int brightness) {
        this.brightness = brightness;
        actualizeOffset();
    }

    /**
     * Gets the rain (ratio 0-100)
     * @return
     */
    public int getRain() {
        return rain;
    }

    /**
     * Sets the rain (ratio 0-100)
     * @param rain
     */
    public void setRain(int rain) {
        this.rain = rain;
        actualizeOffset();
    }

    /**
     * Gets the current temperature (°C)
     * @return
     */
    public int getTemperature() {
        return temperature;
    }

    /**
     * Sets the current temperature (°C)
     * @param temperature
     */
    public void setTemperature(int temperature) {
        this.temperature = temperature;
        actualizeOffset();
    }

    /**
     * Gets the force of the wind (ratio 0-100)
     * @return
     */
    public int getWind() {
        return wind;
    }

    /**
     * Sets the force of the wind (ratio 0-100)
     * @param wind
     */
    public void setWind(int wind) {
        this.wind = wind;
        actualizeOffset();
    }

    /**
     * Gets the current direction of the wind (degrees => 0/360 mean North)
     * @return
     */
    public int getWindDirection() {
        return windDirection;
    }

    /**
     * Sets the current direction of the wind (degrees => 0/360 mean North)
     * @param windDirection
     */
    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
        actualizeOffset();
    }
    
    /**
     * Gets the awareness offset modified by the weather
     * @return
     */
    public int getAwarenessOffset() {
        return awarenessOffset;
    }

    /**
     * Gets the kindness offset modified by the weather
     * @return
     */
    public int getKindnessOffset() {
        return kindnessOffset;
    }

    /**
     * Gets the aggressivity offset modified by the weather
     * @return
     */
    public int getAggressivityOffset() {
        return aggressivityOffset;
    }

    /**
     * Gets the irritability offset modified by the weather
     * @return
     */
    public int getIrritabilityOffset() {
        return irritabilityOffset;
    }
    
    
    
}
