package mechanics.rb2d.shapes;

import de.physolator.usr.components.Vector2D;
import de.physolator.usr.components.VectorMath;

public class Pentagon extends Polygon {

//	public Polygon poly;

	public Pentagon() {
		this(1);
	}

	public Pentagon(double length) {
		super(null);
		double diameter = length / 2 * (1 + Math.sqrt(5));
		
		double r_c = length/(2*Math.sin(Math.toRadians(36)));
		double r_i = length/(2*Math.tan(Math.toRadians(36)));

		Vector2D a = new Vector2D(-length / 2, -r_i);
		Vector2D b = new Vector2D(length / 2, -r_i);

		Vector2D c = new Vector2D(diameter / 2, Math.tan(Math.toRadians(18)) * (diameter / 2));

		Vector2D d = new Vector2D(0,r_c);
		Vector2D e = new Vector2D(-c.x, c.y);

		this.vertices = new Vector2D[] {VectorMath.mult(-1, a), VectorMath.mult(-1, b), VectorMath.mult(-1, c), VectorMath.mult(-1, d), VectorMath.mult(-1, e) };
	}
}
