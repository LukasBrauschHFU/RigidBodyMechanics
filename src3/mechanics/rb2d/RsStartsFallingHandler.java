package mechanics.rb2d;

import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;

import InclinePlain.Ball;
import InclinePlain.Incline;
import de.physolator.usr.components.Vector2D;
import de.physolator.usr.components.VectorMath;

public class RsStartsFallingHandler implements Runnable {
	private RigidBody rb;
	private Incline il;
	private Line2D.Double closestEdge;
	private double a;

	public RsStartsFallingHandler(RigidBody rb, Incline il, Line2D.Double closestEdge) {
		this.rb = rb;
		this.il = il;
		this.closestEdge = closestEdge;
	}

	@Override
	public void run() {

	}

}
