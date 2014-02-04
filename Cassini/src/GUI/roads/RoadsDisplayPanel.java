/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.roads;

import config.files.ConfigFile;
import config.types.roads.JunctionConfig;
import config.types.roads.RoadConfig;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Sylvain
 */

// THIS
// IS
// JUST
// A
// DRAFT
// !!!

// TODO this is some experiemental code to display roads, but it is not complete (not even tested or functional)


public class RoadsDisplayPanel extends JPanel {
	private ArrayList<GraphicalRoad> roads;
	private ArrayList<GraphicalJunction> junctions;
	
	public RoadsDisplayPanel (ArrayList<RoadConfig> roads, ArrayList<JunctionConfig> junctions) {
		this.roads = new ArrayList();
		this.junctions = new ArrayList();
		
		for (RoadConfig road : roads)
			this.roads.add(new GraphicalRoad(road));
		
		for (JunctionConfig junction : junctions)
			this.junctions.add(new GraphicalJunction(junction));
	}
		
	public void paintComponent (Graphics g) {
		for (GraphicalRoad road : roads)
			road.draw(g);

		for (GraphicalJunction junction : junctions)
			junction.draw(g);
	}
}
