/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <The Simulation Team> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy us a beer (or a Mojito for Julie)
 * in return.
 * Guillaume Blanc & Gabriel Charlemagne & Jonathan Fernandez & Julie Marti
 * ----------------------------------------------------------------------------
 */
package old.project2012.model.junction;

import java.awt.Polygon;
import old.project2012.model.Coordinates;
import old.project2012.model.Maths;
import old.project2012.model.road.RoadSection;



/**
 *
 * @author Djou
 */
public class AccelerationLane extends CrossRoad{
    private int lengthLine = 3000;
    private int widthLine = 222;
    private int betweenLines = 3500;


    @Override
    public void addRoadSection (RoadSection road, boolean begin) {
        RoadSign rs ;
        if(roadSections.size() >= 2)
        {
            rs = new RoadSign(RoadSign.Sign.ACCELERATION_LANE);
        }
        else{
            rs = new RoadSign(RoadSign.Sign.NONE);
        }
        super.roadSigns.add(rs);
        roadSections.add(road);
        beginOfRoadSections.add(begin);
        if (begin) {
            road.setBeginJunction(this);
        }
        else{
            road.setEndJunction(this);
        }
    }
    public int getBetweenLines() {
        return betweenLines;
    }

    public int getLengthLine() {
        return lengthLine;
    }

    public int getWidthLine() {
        return widthLine;
    }
    public RoadSection getBeginRoadSection(){
        return(this.getRoadSection(0));
    }
    public RoadSection getEndRoadSection(){
        return(this.getRoadSection(1));
    }
    public RoadSection getALRoadSection(){
        return(this.getRoadSection(2));
    }
    public boolean getBeginOfBeginRoadSection(){
        return(this.getBeginOfRoadSection(0));
    }
    public boolean getBeginOfEndRoadSection(){
        return(this.getBeginOfRoadSection(1));
    }
    public boolean getBeginOfALRoadSection(){
        return(this.getBeginOfRoadSection(2));
    }
    public boolean isInALWay(Coordinates pos){
        //Calcul du polygone dessinant la voie d'insertion
        Coordinates c1,c2,c3,c4;
        //on récupère le point séparant la voie d'entrée avec la voie d'insertion
        if(getBeginOfBeginRoadSection() == true)
        {
          c1 = getBeginRoadSection().getOpposedBeginCoordinates();
        }
        else{
          c1 = getBeginRoadSection().getEndCoordinates();
        }
        //On récupère le point définissant la fin de la RoadSection de la voie d'insertion côté entrée
        //On récupère le point définissant la fin de la RoadSection de la voie d'insertion côté sortie
        if(getBeginOfALRoadSection() == true)
        {
          c2 = getALRoadSection().getCoordinate(0);
          c3 = getALRoadSection().getOpposedBeginCoordinates();
        }
        else{
          c2 = getALRoadSection().getOpposedEndCoordinates(); 
          c3 = getALRoadSection().getEndCoordinates();
        }
        //On récupère le point séparant la voie d'insertion de la voie de sortie
        if(getBeginOfEndRoadSection() == true)
        {
          c4 = getEndRoadSection().getCoordinate(0);
        }
        else{
          c4 = getEndRoadSection().getOpposedEndCoordinates();
        }
        
        //on regarde si la position de la voiture est dans le poly ainsi formé
        Polygon poly = new Polygon();
        
        poly.addPoint(c1.getX(), c1.getY());
        poly.addPoint(c2.getX(), c2.getY());
        poly.addPoint(c3.getX(), c3.getY());
        poly.addPoint(c4.getX(), c4.getY());
         /*
        System.out.println("c1 : ("+ c1.getX() +", "+ c1.getY() +")");
        System.out.println("c2 : ("+ c2.getX() +", "+ c2.getY() +")");
        System.out.println("c3 : ("+ c3.getX() +", "+ c3.getY() +")");
        System.out.println("c4 : ("+ c4.getX() +", "+ c4.getY() +")");
        System.out.println("ve : ("+ pos.getX() +", "+ pos.getY() +")");
          * 
          */
         

        return(poly.contains(pos.getX(), pos.getY()));
    }
}
