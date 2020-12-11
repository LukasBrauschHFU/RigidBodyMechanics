package mechanics.rb2d;

import de.physolator.usr.components.Vector2D;
import de.physolator.usr.components.VectorMath;

import static java.lang.Math.*;

public class RigidBodyCollisionHandler implements Runnable {

	private double k = 1;
	private RigidBody rb1;
	private RigidBody rb2;
	private Impactpoint ip;

	public RigidBodyCollisionHandler(RigidBody r1, RigidBody r2, Impactpoint ip) {
		this.rb1 = r1;
		this.rb2 = r2;
		this.ip = ip;
	}

	@Override
	public void run() {
		Vector2D p = ip.impactPoint;
		Vector2D collisionEdge = VectorMath.normalize(ip.impactEdge);

		// 1. Transformation in das Stoßkoordinatensystem
		Vector2D r1m = VectorMath.sub(rb1.r, p);
		Vector2D r2m = VectorMath.sub(rb2.r, p);

		double rot = VectorMath.angle(new Vector2D(0, 1), collisionEdge);
		if (collisionEdge.x < 0)
			rot = -rot;

		Vector2D r1mr = rotateVector2D(r1m, rot);
		Vector2D r2mr = rotateVector2D(r2m, rot);

		Vector2D v1r = rotateVector2D(rb1.v, rot);
		Vector2D v2r = rotateVector2D(rb2.v, rot);
		
		//Zustandsbestimmung nach Stoss
		if((Math.abs(v1r.x)+(Math.abs(v2r.x)) < 0.03)) {
			rb1.state = BodyState.STOPPED;
			rb2.state = BodyState.STOPPED;
		}

		// 2. Berechnung der neuen Größen im Stoßkoordinatensystem
		double a1 = -r1mr.y;
		double a2 = -r2mr.y;

		double Fx = impulseFx(v1r.x, v2r.x, a1, a2);

		Vector2D V1r = new Vector2D(v1r.x - (Fx / rb1.m), v1r.y);
		Vector2D V2r = new Vector2D(v2r.x + (Fx / rb2.m), v2r.y);	

		double Omega1 = rb1.omega + ((a1 * Fx) / rb1.I);
		double Omega2 = rb2.omega - ((a2 * Fx) / rb2.I);
		

		// 3. Ruecktransformation ins Inertialsystem
		Vector2D V1r_ = rotateVector2D(V1r, -rot);
		Vector2D V2r_ = rotateVector2D(V2r, -rot);
		

		// 4. Setzen der neuen Werte

		// Stosskoordinatensystem
		// rb1.v.set(V1r);
		// rb2.v.set(V2r);
		//
		// rb1.omega = Omega1;
		// rb2.omega = Omega2;
		//
		// rb1.phi = phi1r;
		// rb2.phi = phi2r;
		//
		// rb1.r.set(r1mr);
		// rb2.r.set(r2mr);

		// Normale Koordinatensystem
		rb1.v.set(V1r_);
		rb2.v.set(V2r_);

		rb1.omega = Omega1;
		rb2.omega = Omega2;

	}

	private Vector2D rotateVector2D(Vector2D r, double rot) {
		return new Vector2D(r.x * cos(rot) - r.y * sin(rot), r.x * sin(rot) + r.y * cos(rot));
	}

	private double impulseFx(double v1x, double v2x, double a1, double a2) {
		double a1omega1 = a1 * rb1.omega;
		double a2omega2 = a2 * rb2.omega;
		double zaehler = v1x - v2x - a1omega1 + a2omega2;
		double nenner = (1 / rb1.m) + (1 / rb2.m) + (a1 * a1 / rb1.I) + (a2 * a2 / rb2.I);
		double Fx = (1 + k) * (zaehler / nenner);
		return Fx;
	}
}
