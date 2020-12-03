package mechanics.rb2d;

import de.physolator.usr.components.Vector2D;
import de.physolator.usr.tvg.*;

import static java.lang.Math.*;

public class Polygon {
	public Vertex[] vertices;

	public Polygon() {
		this(new Vertex[]{new Vertex(-0.5, 0.5),new Vertex(0.5, 0.5),new Vertex(0.5, -0.5),new Vertex(-0.5, -0.5)});
	}
	
	public Polygon(Vertex[] vertexList) {
		vertices = vertexList;
	}


	public void paint(TVG tvg, Vector2D position, double phi) {
		tvg.beginShape(Shape.POLYGON_LINE_LOOP);
		for (Vertex e : vertices)
			tvg.vertex(position.x + cos(phi) * e.position.x + sin(phi) * e.position.y,
					position.y + sin(phi) * e.position.x - cos(phi) * e.position.y);
		tvg.endShape();
	}
}
