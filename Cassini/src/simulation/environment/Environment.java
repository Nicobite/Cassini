/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.environment;

import java.util.ArrayList;
import simulation.Coordinates;

/**
 * Class Environment
 * Describes the environment of a road
 * @author Jean
 */
public class Environment {

    	private int awarenessOffset;
	private int kindnessOffset;
	private int aggressivityOffset;
	private int irritabilityOffset;

        
    /**
     * Defines a new environment
     * @param awarenessOffset
     * @param kindnessOffset
     * @param aggressivityOffset
     * @param irritabilityOffset
     */
    public Environment(int awarenessOffset, int kindnessOffset, int aggressivityOffset, int irritabilityOffset) {
        this.awarenessOffset = awarenessOffset;
        this.kindnessOffset = kindnessOffset;
        this.aggressivityOffset = aggressivityOffset;
        this.irritabilityOffset = irritabilityOffset;
    }
        
    /**
     * Gets the minimum level of awareness in this environment
     * @return
     */
    public int getAwarenessOffset() {
        return awarenessOffset;
    }

    /**
     * Sets the offset added to the awareness in this environment
     * @param awarenessOffset
     */
    public void setAwarenessOffset(int awarenessOffset) {
        this.awarenessOffset = awarenessOffset;
    }

    /**
     * Gets the offset added to the kindness in this environment
     * @return
     */
    public int getKindnessOffset() {
        return kindnessOffset;
    }

    /**
     * Sets the offset added to the kindness in this environment
     * @param kindnessOffset
     */
    public void setKindnessOffset(int kindnessOffset) {
        this.kindnessOffset = kindnessOffset;
    }

    /**
     * Gets the offset added to the agressivity in this environment
     * @return
     */
    public int getAggressivityOffset() {
        return aggressivityOffset;
    }

    /**
     * Sets the offset added to the agressivity in this environment
     * @param aggressivityOffset
     */
    public void setAggressivityOffset(int aggressivityOffset) {
        this.aggressivityOffset = aggressivityOffset;
    }

    /**
     * Gets the offset added to the irritability in this environment
     * @return
     */
    public int getIrritabilityOffset() {
        return irritabilityOffset;
    }

    /**
     * Sets the offset added to the irritability in this environment
     * @param irritabilityOffset
     */
    public void setIrritabilityOffset(int irritabilityOffset) {
        this.irritabilityOffset = irritabilityOffset;
    }
        
        
}
