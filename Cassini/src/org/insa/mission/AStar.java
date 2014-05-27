/*
* Copyright 2014 Juste Abel Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.insa.mission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.insa.controller.MainController;
import org.insa.core.roadnetwork.NextSection;
import org.insa.core.roadnetwork.Road;
import org.insa.core.roadnetwork.Section;
import org.insa.model.items.RoadsModel;
import org.insa.view.graphicmodel.GraphicSection;

/**
 * @author Abel Juste Ouedraogo, Guillaume Garzone, François Aïssaoui, Thomas Thiebaud
 * Class AStar
 */
public class AStar {
    private final  RoadsModel roads;
    private final  Section origin;
    private final  Section destination;
    //the heap
    private final  BinaryHeap<Label> heap;
    //Le labels
    private final   ArrayList<Label> labels;
    //fait correspondre un noeud à un label
    private final  HashMap< Section, Label> mapLabels;
    private Label labelDest;
    
    /**
     * Constructors
     * @param src Source
     * @param dest Destination
     */
    public AStar(Section src, Section dest) {
        this.roads = MainController.getInstance().getModel().getRoadModel();
        this.destination = dest;
        this.origin = src;
        this.heap = new BinaryHeap<>();
        this.labels = new ArrayList<>();
        this.mapLabels = new HashMap<>();
    }
    
    /**
     * Constructor
     * @param roads Road model
     * @param src Source
     * @param dest Destination
     */
    public AStar(RoadsModel roads, Section src, Section dest) {
        this.roads = roads;
        this.destination = dest;
        this.origin = src;
        this.heap = new BinaryHeap<>();
        this.labels = new ArrayList<>();
        this.mapLabels = new HashMap<>();
    }
    
    /**
     * get shortest path
     * @return Shortest path
     * @throws PathNotFoundException No path found
     */
    public Road getShortestPath() throws PathNotFoundException{
        run();
        if(labelDest.getCost() == Float.POSITIVE_INFINITY)
            throw new PathNotFoundException();
        Road road = new Road();
        road.addSection(destination);
        Label courant = labelDest;
        while(courant.getParent() != null){
            road.addSection(courant.getParent());
            courant = mapLabels.get(courant.getParent());
        }
        Collections.reverse(road.getGraphicRoad().getSections());
        return road;
    }
    
    /**
     * Initialize
     */
    private void init(){
        Label label;
        Section section;
        for(Road road : roads.getRoads()){
            for(GraphicSection s : road.getGraphicRoad().getSections()){
                section = s.getSection();
                label = new Label(section, destination);
                labels.add(label);
                mapLabels.put(section, label);
                //check if origin section
                if(section.isEqualTo(origin)){
                    label.setCost(0);
                    heap.insert(label);
                }
                if(section.isEqualTo(destination)){
                    labelDest = label;
                }
            }
        }
    }
    
    /**
     * Run
     */
    private void run() {
        double newCout = 0;
        Section succ;
        Label min, labSucc;
        init();
        while(!(heap.isEmpty()|| ((Label) labelDest).isMark())){
            min= heap.deleteMin();
            //heap.print();
            min.setMark(true);
            for(NextSection next : min.getCurrent().getSuccessors()){
                succ = next.getSection();
                labSucc= mapLabels.get(succ);
                if(!labSucc.isMark()){
                    newCout = min.getCost() + min.getCurrent().getLength();
                    if(newCout<labSucc.getCost()){
                        labSucc.setCost(newCout);
                        labSucc.setParent(min.getCurrent());
                    }
                    //si le sommet n'est pas dans le tas on l'y ajoute
                    if(!(heap.getMap().get(labSucc)!=null)){
                        //insertion des sommets non marqués dans le tas
                        heap.insert(labSucc);
                    }
                    //sinon on met à jour le tas
                    else{
                        //Mise à jour du tas, puisque le cout a diminué
                        heap.update(labSucc);
                    }
                }
            }
        }
    }
}
