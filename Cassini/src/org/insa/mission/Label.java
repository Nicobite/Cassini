package org.insa.mission;

import org.insa.core.roadnetwork.Node;
import org.insa.core.roadnetwork.Section;
import org.insa.view.graphicmodel.GraphicSection;

/**
 * Label pour l'algo A Star
 */
public class Label implements Comparable<Label>{
    private Section current;
    private boolean marque;
    private double cout;
    private Section pere;
    private double estimation;
    
    public Label() {
    }
    /**
     * Associe une section a un label
     * @param current
     * @param dest
     */
    public Label(Section current, Section dest) {
        this.current = current;
        this.marque = false;//marquage à faux
        this.cout = Float.POSITIVE_INFINITY;// infini
        this.pere = null;//pas de pere
        //used just to get the distance between the current node and the destination one
        GraphicSection s = new GraphicSection(current.getGraphicSection().getSourceNode(), 
                dest.getGraphicSection().getTargetNode());
        this.estimation = s.getSection().getLength();
    }      
    
    //Des getters et setters
    public Section getCurrent() {
        return current;
    }

    public void setCurrent(Section current) {
        this.current = current;
    }

    public Section getPere() {
        return pere;
    }

    public void setPere(Section pere) {
        this.pere = pere;
    }
        
    
    public boolean isMarque() {
        return marque;
    }
    public void setMarque(boolean marque) {
        this.marque = marque;
    }
    public double getCout() {
        return cout;
    }
    public void setCout(double cout) {
        
        this.cout = cout;
    }

    public double getEstimation() {
        return estimation;
    }

    public void setEstimation(double estimation) {
        this.estimation = estimation;
    }
    
    @Override
    public int compareTo(Label lab) {
        int val=(int)(1000.0*(this.getCout()+this.estimation)-1000.0*(lab.getCout()+lab.getEstimation()));
        //On prend la plus petite estimation en cas d'égalité
        if(val==0){
            val=(int)(1000.0*this.getEstimation()-1000.0*lab.getEstimation());
        }
        return val;
    }
    @Override
    public String toString(){
        return this.current.getId()+" cout = "+cout;
    }
    
}


