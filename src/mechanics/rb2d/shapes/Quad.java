package mechanics.rb2d.shapes;

import de.physolator.usr.components.Vector2D;

public class Quad extends Polygon{
	public Quad(Vector2D a, Vector2D b, Vector2D c, Vector2D d) {
		super(new Vector2D[] { a, b, c, d });		
	}
}
