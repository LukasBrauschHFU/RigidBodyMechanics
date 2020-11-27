package mechanics.rb2d;

import de.physolator.usr.components.Vector2D;
import de.physolator.usr.components.VectorMath;
import de.physolator.usr.tvg.*;
import de.physolator.usr.util.parameter.Parameter;
import de.physolator.usr.util.parameter.Slider;

import static java.lang.Math.*;

public class Polygon {
	public Vertex[] vertices;
	public Vertex vertex;
	public double cornerNumber;
	public boolean hasIntersection;
	public double edgeLength;
	public double edgeWidth;
	public Vector2D r;

	public Polygon(Vector2D r, int cornerNumber, double edgeLength) {
		double cornerAngle = 2 * Math.PI / cornerNumber;
		this.vertices = new Vertex[cornerNumber];
		if (cornerNumber == 3) {
			double halfEdge = edgeLength / 2;
			double ri = (tan(Math.PI / 6) * edgeLength) / 2;
			double h = sin(Math.PI / 3) * edgeLength;
			double ax = r.x - halfEdge;
			double ay = -ri;
			double bx = ax + edgeLength;
			double by = ay;
			double cx = 0;
			double cy = h - ri;
			System.out.println(bx - ax);
			this.vertices[0] = new Vertex(ax, ay);
			this.vertices[1] = new Vertex(bx, by);
			this.vertices[2] = new Vertex(cx, cy);
		}
		if (cornerNumber == 4) {
			double halfEdge = edgeLength / 2;
			double ay = r.y - r.y - halfEdge;
			double ax = r.x - r.x - halfEdge;
			double bx = ax + edgeLength;
			double by = ay;
			double cx = bx;
			double cy = by + edgeLength;
			double dx = ax;
			double dy = cy;
			this.vertices[0] = new Vertex(ax, ay);
			this.vertices[1] = new Vertex(bx, by);
			this.vertices[2] = new Vertex(cx, cy);
			this.vertices[3] = new Vertex(dx, dy);
		}
//		if (cornerNumber == 5) {
//			double halfEdge = edgeLength / 2;
//			double ax = halfEdge;
//			double ay = -tan(toRadians(54))*halfEdge;
//			double bx = ax +edgeLength;
//			double by = ay;
//			double cx;
//			double cy;
//			double dx;
//			double dy;
//			double ex;
//			double ey;
//			this.vertices[0] = new Vertex(ax, ay);
//			this.vertices[1] = new Vertex(bx, by);
//			this.vertices[2] = new Vertex(3, 4);
//			this.vertices[3] = new Vertex(5, 6);
//			this.vertices[4] = new Vertex(7, 8);
//		}
//		vertices = vertexList;
	}
	
	public Polygon(Vector2D r, int cornerNumber, double edgeLength, double edgeWidth) {
		double cornerAngle = 2 * Math.PI / cornerNumber;
		this.vertices = new Vertex[cornerNumber];
		if (cornerNumber == 4) {
			double halfWidth = edgeWidth / 2;
			double halfLength = edgeLength / 2;
			double ax = r.x - r.x - halfLength;
			double ay = r.y - r.y - halfWidth;
			double bx = ax + edgeLength;
			double by = ay;
			double cx = bx;
			double cy = by + edgeWidth;
			double dx = ax;
			double dy = cy;
			System.out.println(ax+" "+ ay+" "+ bx+" "+by+" "+cx+" "+cy+" "+dx+" "+dy);
			this.vertices[0] = new Vertex(-ax, -ay);
			this.vertices[1] = new Vertex(-bx, -by);
			this.vertices[2] = new Vertex(-cx, -cy);
			this.vertices[3] = new Vertex(-dx, -dy);
		}
	}

	public void paint(TVG tvg, Vector2D position, double phi) {
		tvg.beginShape(Shape.POLYGON_LINE_LOOP);
		for (Vertex e : vertices)
			tvg.vertex(position.x + cos(phi) * e.position.x + sin(phi) * e.position.y,
					position.y + sin(phi) * e.position.x - cos(phi) * e.position.y);
		tvg.endShape();
	}

}
