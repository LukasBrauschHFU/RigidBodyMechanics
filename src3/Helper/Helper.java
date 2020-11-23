package Helper;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import InclinePlain.Incline;
import de.physolator.usr.components.Vector2D;
import de.physolator.usr.components.VectorMath;

public class Helper {
	public double getDistanceBodyToLine(Vector2D pos, Incline il) {
		Vector2D u = new Vector2D(il.start.x - il.end.x, il.start.y - il.end.y);
		Vector2D p = VectorMath.perpendicular(pos, il.start, u);
		return p.abs();
	}
	public Vector2D rotateVector2D(Vector2D r, double rot) {
		return new Vector2D(r.x * cos(rot) - r.y * sin(rot), r.x * sin(rot) + r.y * cos(rot));
	}
	
}
