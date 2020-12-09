package mechanics.rb2d.misc;



import java.awt.geom.Point2D;
import java.util.List;

import de.physolator.usr.components.Vector2D;
import geometry.RandomPolygon;
import mechanics.rb2d.shapes.Polygon;

public class RandomPolygonBuilder {
	
	public Polygon getPolygon(int n) {
		List<Point2D.Double> polygonPoints = RandomPolygon.generate_polygon(n);
		Vector2D[] vertices = new Vector2D[polygonPoints.size()];
		for(int i = 0; i < polygonPoints.size(); i++) {
			vertices[i] = new Vector2D(polygonPoints.get(i).x, polygonPoints.get(i).y);
		}
		Polygon polygon = new Polygon(vertices);
		return polygon;
	}
}
