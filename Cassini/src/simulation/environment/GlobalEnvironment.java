/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.environment;

/**
 * Class Global Environment
 * Describes the environment of a map
 * @author Jean
 */
public class GlobalEnvironment {
    private static int time;
    private static Weather weather;
    private static int avereageBrightness;

    /**
     * Gets the current time in the environment
     * @return
     */
    public static int getTime() {
        return time;
    }

    /**
     * Updates the weather in the current environment
     */
    private static void actualizeWheather(){
        if (GlobalEnvironment.time >= 0 && GlobalEnvironment.time < 21600000){
           GlobalEnvironment.weather.setBrightness(GlobalEnvironment.avereageBrightness);
        }
        else if (GlobalEnvironment.time >= 21600000 && GlobalEnvironment.time < 43200000){
           GlobalEnvironment.weather.setBrightness((int)(GlobalEnvironment.avereageBrightness+(GlobalEnvironment.time/10e6)));
        }
        else if (GlobalEnvironment.time >= 43200000 && GlobalEnvironment.time < 64800000){
           GlobalEnvironment.weather.setBrightness((int)(GlobalEnvironment.avereageBrightness+((64800000-GlobalEnvironment.time)/10e6)));
        }
        else if (GlobalEnvironment.time >= 64800000){
           GlobalEnvironment.weather.setBrightness(GlobalEnvironment.avereageBrightness);
        }
    }
    
    /**
     * Sets the time in the environment
     * @param time
     */
    public static void setTime(int time) {
        if (time >86400000){
            time-=86400000;
        }
        GlobalEnvironment.time = time;
        actualizeWheather();
    }

    /**
     * Gets the current weather
     * @return
     */
    public static Weather getWeather() {
        return weather;
    }

    /**
     * Sets the current weather
     * @param weather
     */
    public static void setWeather(Weather weather) {
        GlobalEnvironment.weather = weather;
    }
    
    
    
}
