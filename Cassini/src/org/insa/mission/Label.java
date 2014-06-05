package org.insa.mission;

import org.insa.core.roadnetwork.Section;
import org.insa.view.graphicmodel.GraphicSection;

/**
 * @author Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
 * Label pour l'algo A Star
 */
public class Label implements Comparable<Label> {
    private Section current;
    private boolean mark;
    private double cost;
    private Section parent;
    private double estimation;
    
    /**
     * default constructor
     */
    public Label() {
        //Empty for the moment
    }
    
    /**
     * Associe une section a un label
     * @param current Current section
     * @param dest Next section
     */
    public Label(Section current, Section dest) {
        this.current = current;
        this.mark = false;//marquage à faux
        this.cost = Float.POSITIVE_INFINITY;// infini
        this.parent = null;//pas de pere
        //used just to get the distance between the current node and the destination one
        GraphicSection s = new GraphicSection(current.getGraphicSection().getSourceNode(), dest.getGraphicSection().getTargetNode());
        this.estimation = s.getSection().getLength();
    }      

    /**
     * Get current section
     * @return Current section
     */
    public Section getCurrent() {
        return current;
    }

    /**
     * Is mark
     * @return true if marked, false otherwise
     */
    public boolean isMark() {
        return mark;
    }

    /**
     * Get cost
     * @return Cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * Get parent
     * @return Parent
     */
    public Section getParent() {
        return parent;
    }

    /**
     * Get estimation
     * @return Estimation
     */
    public double getEstimation() {
        return estimation;
    }

    /**
     * Set current
     * @param current New current 
     */
    public void setCurrent(Section current) {
        this.current = current;
    }

    /**
     * Set mark
     * @param marque New mark
     */
    public void setMark(boolean marque) {
        this.mark = marque;
    }

    /**
     * Set cost
     * @param cost New cost
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Set parent
     * @param parent New parent 
     */
    public void setParent(Section parent) {
        this.parent = parent;
    }

    /**
     * Set estimation
     * @param estimation New estimation 
     */
    public void setEstimation(double estimation) {
        this.estimation = estimation;
    }
    
    @Override
    public int compareTo(Label lab) {
        int val=(int)(1000.0*(this.getCost()+this.estimation)-1000.0*(lab.getCost()+lab.getEstimation()));
        //On prend la plus petite estimation en cas d'égalité
        if(val==0){
            val=(int)(1000.0*this.getEstimation()-1000.0*lab.getEstimation());
        }
        return val;
    }
    
    @Override
    public String toString(){
        return this.current.getId()+" cout = "+cost;
    } 
    
    
}


