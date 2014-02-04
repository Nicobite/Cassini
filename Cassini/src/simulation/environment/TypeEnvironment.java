/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.environment;

/**
 * Class TypeEnvironment Represents a generic type of environment
 *
 * @author Jean
 */
public class TypeEnvironment {

    private int awarenessOffset;
    private int kindnessOffset;
    private int aggressivityOffset;
    private int irritabilityOffset;

    /**
     * Creates a new TypeEnvironnement from the given offsets
     * @param awarenessOffset
     * @param kindnessOffset
     * @param aggressivityOffset
     * @param irritabilityOffset
     */
    public TypeEnvironment(int awarenessOffset, int kindnessOffset, int aggressivityOffset, int irritabilityOffset) {
        this.awarenessOffset = awarenessOffset;
        this.kindnessOffset = kindnessOffset;
        this.aggressivityOffset = aggressivityOffset;
        this.irritabilityOffset = irritabilityOffset;
    }

    /**
     *
     * @return
     */
    public int getAwarenessOffset() {
        return awarenessOffset;
    }

    /**
     *
     * @param awarenessOffset
     */
    public void setAwarenessOffset(int awarenessOffset) {
        this.awarenessOffset = awarenessOffset;
    }

    /**
     *
     * @return
     */
    public int getKindnessOffset() {
        return kindnessOffset;
    }

    /**
     *
     * @param kindnessOffset
     */
    public void setKindnessOffset(int kindnessOffset) {
        this.kindnessOffset = kindnessOffset;
    }

    /**
     *
     * @return
     */
    public int getAggressivityOffset() {
        return aggressivityOffset;
    }

    /**
     *
     * @param aggressivityOffset
     */
    public void setAggressivityOffset(int aggressivityOffset) {
        this.aggressivityOffset = aggressivityOffset;
    }

    /**
     *
     * @return
     */
    public int getIrritabilityOffset() {
        return irritabilityOffset;
    }

    /**
     *
     * @param irritabilityOffset
     */
    public void setIrritabilityOffset(int irritabilityOffset) {
        this.irritabilityOffset = irritabilityOffset;
    }
}
