package mechanics.rb2d;

import de.physolator.usr.components.Vector2D;
import de.physolator.usr.tvg.*;

import static java.lang.Math.*;

public class Polygon {
	public Vector2D[] vertices;

	public Polygon() {
		this(new Vector2D[] { new Vector2D(-0.5, 0.5), new Vector2D(0.5, 0.5), new Vector2D(0.5, -0.5),
				new Vector2D(-0.5, -0.5) });
	}

	public Polygon(Vector2D[] vertexList) {
		vertices = vertexList;
	}

	public void paint(TVG tvg, Vector2D position, double phi) {
		tvg.beginShape(Shape.POLYGON_LINE_LOOP);
		for (Vector2D e : vertices)
			tvg.vertex(position.x + cos(phi) * e.x + sin(phi) * e.y,
					position.y + sin(phi) * e.x - cos(phi) * e.y);
		tvg.endShape();
	}
}
