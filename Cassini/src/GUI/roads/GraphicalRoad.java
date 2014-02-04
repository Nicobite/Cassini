/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.roads;

import config.types.roads.CoordinatesConfig;
import config.types.roads.RoadConfig;
import config.types.roads.RoadWayConfig;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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


public class GraphicalRoad {
	private final RoadConfig road;
	private CoordinatesConfig firstCoord = null;
	private CoordinatesConfig lastCoord = null;
	private Point firstCoordRightCorner = null;
	private Point firstCoordLeftCorner = null;
	private Point lastCoordRightCorner = null;
	private Point lastCoordLeftCorner = null;
	float delta;
	private Polygon background = null;
	private ArrayList<RoadWayConfig> ways = null;
	private int waysWidthRight = 0;
	private int waysWidthLeft = 0;
	boolean modified = true;
	
	public GraphicalRoad (RoadConfig road) {
		this.road = road;
	}
	
	public void draw(Graphics g) {
		if (modified) {
			calculate();
			modified = false;
		}
		Color previousColor = g.getColor();
		g.setColor(Color.GRAY);
		g.drawPolygon(background);
		
		g.setColor(previousColor);
	}
	
	private void calculate() {
		retrieveCoordinates();
		retrieveWays();
		defineBackground();
	}
	
	private void retrieveCoordinates() {
		HashMap<Integer, CoordinatesConfig> coordinates = road.getCoordinates();
		Iterator<Map.Entry<Integer, CoordinatesConfig>> coordsIterator = coordinates.entrySet().iterator();
		int maxIndex = 0;
		while (coordsIterator.hasNext()) {
			Map.Entry<Integer, CoordinatesConfig> entry = coordsIterator.next();
			if (entry.getKey() > maxIndex) {
				maxIndex = entry.getKey();
				lastCoord = entry.getValue();
			}
		}
		firstCoord = coordinates.get(0);
		// TODO [fast] exceptions
		/*if (firstCoord == null)
			throw new Exception();
		if (lastCoord == null)
			throw new Exception();*/
		delta = (float)(lastCoord.getY() - firstCoord.getY()) / (float)(lastCoord.getX() - firstCoord.getX());
	}
	
	private void retrieveWays() {
		ways = new ArrayList();
		boolean noChange = false;
		while(ways.size() != road.getRoadWays().size() && !noChange) {
			noChange = true;
			HashMap<Integer, RoadWayConfig> waysHashMap = road.getRoadWays();
			Iterator<Map.Entry<Integer, RoadWayConfig>> waysIterator = waysHashMap.entrySet().iterator();
			int lastMaxIndex = -1;
			while (waysIterator.hasNext()) {
				Map.Entry<Integer, RoadWayConfig> entry = waysIterator.next();
				if (entry.getKey() == lastMaxIndex + 1) {
					ways.add(entry.getValue());
					lastMaxIndex ++;
					noChange = false;
				}
			}
		}
		// TODO [fast] another exception here
		//if (noChange = true) throw new Exception();
		
		waysWidthRight = 0;
		waysWidthLeft = 0;
		for (RoadWayConfig way : ways) {
			if (way.getDirection() == 1)
				waysWidthRight += way.getWidth();
			else if (way.getDirection() == -1)
				waysWidthLeft += way.getWidth();
		}
	}
	
	private void defineBackground() {
		float delta = this.delta;
		delta = -(1/delta);
		if (firstCoord.getY() > lastCoord.getY())
			delta *= -1;
		
		firstCoordRightCorner = new Point ((int) (firstCoord.getX() + Math.cos(delta*90) * waysWidthRight), (int) (firstCoord.getY() + Math.sin(delta*90) * waysWidthRight));
		firstCoordLeftCorner = new Point ((int) (firstCoord.getX() - Math.cos(delta*90) * waysWidthLeft), (int) (firstCoord.getY() - Math.sin(delta*90) * waysWidthLeft));
		lastCoordRightCorner = new Point ((int) (lastCoord.getX() + Math.cos(delta*90) * waysWidthRight), (int) (lastCoord.getY() + Math.sin(delta*90) * waysWidthRight));
		lastCoordLeftCorner = new Point ((int) (lastCoord.getX() - Math.cos(delta*90) * waysWidthLeft), (int) (lastCoord.getY() - Math.sin(delta*90) * waysWidthLeft));
		
		System.out.println((firstCoord.getX()));
		System.out.println((waysWidthRight));
		System.out.println((delta*90));
		System.out.println((Math.cos(delta*90)));
		
		System.out.println((firstCoord.getX() + Math.cos(delta*90) * waysWidthRight));
		System.out.println((firstCoord.getY() + Math.sin(delta*90) * waysWidthRight));
		System.out.println((firstCoord.getX() - Math.cos(delta*90) * waysWidthLeft));
		System.out.println((firstCoord.getY() - Math.sin(delta*90) * waysWidthLeft));
		System.out.println((lastCoord.getX() + Math.cos(delta*90) * waysWidthRight));
		System.out.println((lastCoord.getY() + Math.sin(delta*90) * waysWidthRight));
		System.out.println((lastCoord.getX() - Math.cos(delta*90) * waysWidthLeft));
		System.out.println((lastCoord.getY() - Math.sin(delta*90) * waysWidthLeft));
		
		background = new Polygon();
		background.addPoint((int) firstCoordRightCorner.getX(), (int) firstCoordRightCorner.getY());
		background.addPoint((int) firstCoordLeftCorner.getX(), (int) firstCoordLeftCorner.getY());
		background.addPoint((int) lastCoordLeftCorner.getX(), (int) lastCoordLeftCorner.getY());
		background.addPoint((int) lastCoordRightCorner.getX(), (int) lastCoordRightCorner.getY());
	}
}
