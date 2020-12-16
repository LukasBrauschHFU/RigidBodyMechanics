package mechanics.rb2d;

import de.physolator.usr.components.Vector2D;
import de.physolator.usr.components.VectorMath;

import static java.lang.Math.*;

public class RigidBodyCollisionHandler implements Runnable {

	private double k = 0.6;
	private RigidBody rb_p;
	private RigidBody rb_e;
	private Impactpoint ip;
	private boolean showInfo = false;
	private boolean impactCoords = true;

	public RigidBodyCollisionHandler(Impactpoint ip) {
		this.rb_p = ip.rb_point;
		this.rb_e = ip.rb_edge;
		this.ip = ip;
	}

	@Override
	public void run() {
		if (showInfo) {
			System.out.println("Collision between:");
			System.out.println(rb_p);
			System.out.println(rb_e);
		}

		Vector2D p = ip.impactPoint;
		Vector2D collisionEdge = VectorMath.normalize(ip.impactEdge);

		// 1. Transformation in das Stoßkoordinatensystem
		Vector2D r1m = VectorMath.sub(rb_p.r, p);
		Vector2D r2m = VectorMath.sub(rb_e.r, p);

		double rot = Math.atan2(1, 0) - Math.atan2(collisionEdge.y, collisionEdge.x);

		System.out.println("Rot " + toDegrees(rot));

		if (rot < Math.PI)
			rot += Math.PI;

		Vector2D r1mr = rotateVector2D(r1m, rot);
		Vector2D r2mr = rotateVector2D(r2m, rot);

		Vector2D v1r = rotateVector2D(rb_p.v, rot);
		Vector2D v2r = rotateVector2D(rb_e.v, rot);

		// Zustandsbestimmung nach Stoss
		if ((Math.abs(v1r.x) + (Math.abs(v2r.x)) < 0.001)) {
			rb_p.state = BodyState.STOPPED;
			rb_e.state = BodyState.STOPPED;
			if (this.showInfo)
				System.out.println("Stopped: " + "v1r.x=" + v1r.x + ", v2r.x=" + v2r.x);
		}

		// 2. Berechnung der neuen Größen im Stoßkoordinatensystem
		double a1 = -r1mr.y;
		double a2 = -r2mr.y;

		double Fx = impulseFx(v1r.x, v2r.x, a1, a2);

		Vector2D V1r = new Vector2D(v1r.x - (Fx / rb_p.m), v1r.y);
		Vector2D V2r = new Vector2D(v2r.x + (Fx / rb_e.m), v2r.y);

		double Omega1 = rb_p.omega + ((a1 * Fx) / rb_p.I);
		double Omega2 = rb_e.omega - ((a2 * Fx) / rb_e.I);

		// 3. Ruecktransformation ins Inertialsystem
		Vector2D V1r_ = rotateVector2D(V1r, -rot);
		Vector2D V2r_ = rotateVector2D(V2r, -rot);

		// 4. Setzen der neuen Werte

		if (impactCoords) {
			// Stosskoordinatensystem
			rb_p.v.set(V1r);
			rb_e.v.set(V2r);

			rb_p.omega = Omega1;
			rb_e.omega = Omega2;

			rb_p.phi = rb_p.phi + rot;
			rb_e.phi = rb_e.phi + rot;

			rb_p.r.set(r1mr);
			rb_e.r.set(r2mr);
		} else {

			// Normale Koordinatensystem
			rb_p.v.set(V1r_);
			rb_e.v.set(V2r_);

			rb_p.omega = Omega1;
			rb_e.omega = Omega2;
		}

	}

	private Vector2D rotateVector2D(Vector2D r, double rot) {
		return new Vector2D(r.x * cos(rot) - r.y * sin(rot), r.x * sin(rot) + r.y * cos(rot));
	}

	private double impulseFx(double v1x, double v2x, double a1, double a2) {
		double a1omega1 = a1 * rb_p.omega;
		double a2omega2 = a2 * rb_e.omega;
		double zaehler = v1x - v2x - a1omega1 + a2omega2;
		double nenner = (1 / rb_p.m) + (1 / rb_e.m) + (a1 * a1 / rb_p.I) + (a2 * a2 / rb_e.I);
		double Fx = (1 + k) * (zaehler / nenner);
		return Fx;
	}
}
