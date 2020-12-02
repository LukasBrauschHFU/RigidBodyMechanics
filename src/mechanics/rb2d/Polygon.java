package mechanics.rb2d;

import de.physolator.usr.components.Vector2D;
import de.physolator.usr.components.VectorMath;
import de.physolator.usr.tvg.*;
import de.physolator.usr.util.parameter.Parameter;
import de.physolator.usr.util.parameter.Slider;
import mechanics.tvg.MechanicsTVG;

import static java.lang.Math.*;

public class Polygon {
	public Vertex[] vertices;

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
