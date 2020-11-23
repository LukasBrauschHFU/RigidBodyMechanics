package mechanics.rb2d;

import de.physolator.usr.components.Vector2D;

public class Vertex{
	public Vector2D position = new Vector2D();

	public Vertex(double x, double y) {
		position.set(x,y);
	}
}
