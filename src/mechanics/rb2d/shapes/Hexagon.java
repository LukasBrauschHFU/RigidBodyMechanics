package mechanics.rb2d.shapes;

import de.physolator.usr.components.Vector2D;
import de.physolator.usr.components.VectorMath;

public class Hexagon extends Polygon {

	public Hexagon() {
		this(1);
	}

	public Hexagon(double length) {
		super(null);

		double r_u = length;
		double r_i = length * (Math.sqrt(3)/2);

		Vector2D a = new Vector2D(-length / 2, -r_i);
		Vector2D b = new Vector2D(length / 2, a.y);

		Vector2D c = new Vector2D(r_u, 0);

		Vector2D d = new Vector2D(b.x, r_i);
		Vector2D e = new Vector2D(a.x, r_i);
		
		Vector2D f = new Vector2D(-r_u, 0);

		this.vertices = new Vector2D[] { VectorMath.mult(-1, a), VectorMath.mult(-1, b), VectorMath.mult(-1, c),
				VectorMath.mult(-1, d), VectorMath.mult(-1, e), VectorMath.mult(-1, f) };
	}
}
