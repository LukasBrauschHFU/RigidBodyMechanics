package InclinePlain;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import Helper.ObjectPosition;
import de.physolator.usr.components.Vector2D;
import de.physolator.usr.components.VectorMath;
import mechanics.rb2d.Impactpoint;

public class collisionBallBallHandler implements Runnable {
	private Ball b1;
	private Ball b2;
	private Impactpoint ip;

	public collisionBallBallHandler(Ball b1, Ball b2, Impactpoint ip) {
		this.b1 = b1;
		this.b2 = b2;
		this.ip = ip;
	}

	public void run() {
//		System.out.println("hi");
//		// vs: Geschwindigkeit des Schwerpunkts
//		Vector2D vs = VectorMath.add(VectorMath.mult(b1.m / (b1.m + b2.m), b1.v),
//				VectorMath.mult(b2.m / (b1.m + b2.m), b2.v));
//		// v1 und v2: Geschwindigkeiten im Schwerpunktsystem (Ursprung=Schwerpunkt) vor
//		// dem Stoß
//		Vector2D v1 = VectorMath.sub(b1.v, vs);
//		Vector2D v2 = VectorMath.sub(b2.v, vs);
//		// double pp = pabs() + pball.abs();
//		Vector2D normal = VectorMath.sub(b1.r, b2.r);
//		normal.set(-normal.y, normal.x);
//		// v1b und v2b: Geschwindigkeiten im Schwerpunktsystem (Ursprung=Schwerpunkt)
//		// nach dem Stoß
//		Vector2D v1b = VectorMath.reflectionToAxis(v1, normal);
//		Vector2D v2b = VectorMath.reflectionToAxis(v2, normal);
//		// nachfolgende Reduktion der Geschwindigkeiten beim realen Stoß (k=1 :
//		// elastisch, k=0 : plastisch)
//		v1b.mult(b1.k);
//		v2b.mult(b2.k);
//		v1b.add(vs);
//		v2b.add(vs);
//		b1.v.set(v1b);
//		b2.v.set(v2b);
		b1.a.y = -9.81;
		b2.a.y = -9.81;
		b1.ballState = BallState.FLYING;
		b2.ballState = BallState.FLYING;
		
		Vector2D r1m = VectorMath.sub(b1.r, ip.impactPoint);
		Vector2D r2m = VectorMath.sub(b2.r, ip.impactPoint);

		double rot = VectorMath.angle(new Vector2D(0, 1), ip.impactEdge);

		// 2. r mit rot um 0,0 drehen
		Vector2D r1mr = rotateVector2D(r1m, rot);
		Vector2D r2mr = rotateVector2D(r2m, rot);
		
		Vector2D collisionEdge = rotateVector2D(ip.impactEdge, rot);

		// 3. v um rot drehen
		Vector2D v1r = rotateVector2D(b1.v, rot);
		Vector2D v2r = rotateVector2D(b2.v, rot);

		// 4. phi um rot drehen
		double phi1r = b1.phi + rot;
		double phi2r = b2.phi + rot;

//5. Berechnung
		double a1 = -r1mr.y;
		double a2 = -r2mr.y;
		System.out.println("a1 "+a1);
		System.out.println("a2 "+a2);

		double Fx = impulseFx(v1r.x, v2r.x, a1, a2);

		Vector2D V1r = new Vector2D(v1r.x - (Fx / b1.m), v1r.y);
		Vector2D V2r = new Vector2D(v2r.x + (Fx / b2.m), v2r.y);

		double Omega1 = b1.omega;
		double Omega2 = b2.omega;

//Ruecktransformation
		// 6. phi um -rot drehen
		double phi1r_ = phi1r - rot;
		double phi2r_ = phi2r - rot;

		// 7. v um -rot drehen
		Vector2D V1r_ = rotateVector2D(V1r, -rot);
		Vector2D V2r_ = rotateVector2D(V2r, -rot);

		// 8. r um -rot drehen
		Vector2D r1mr_ = rotateVector2D(r1mr, -rot);
		Vector2D r2mr_ = rotateVector2D(r2mr, -rot);

		// 9. r auf ursprünglichen Punkt zurückverschieben
		Vector2D r1m_r_ = VectorMath.add(r1mr_, ip.impactPoint);
		Vector2D r2m_r_ = VectorMath.add(r2mr_, ip.impactPoint);

		b1.v.set(V1r_);
		b2.v.set(V2r_);

		b1.omega = Omega1;
		b2.omega = Omega2;

//		b1.phi = phi1r_;
//		b2.phi = phi2r_;

		b1.r.set(r1m_r_);
		b2.r.set(r2m_r_);
		
		

	}
	
	private Vector2D rotateVector2D(Vector2D r, double rot) {
		return new Vector2D(r.x * cos(rot) - r.y * sin(rot), r.x * sin(rot) + r.y * cos(rot));
	}

	private double impulseFx(double v1x, double v2x, double a1, double a2) {
		double a1omega1 = 0;
		System.out.println("a1omega1 "+a1omega1);
		double a2omega2 = 0;
		System.out.println("a1omega2 "+a2omega2);
		double zaehler = v1x - v2x;
		double nenner = (1 / b1.m) + (1 / b2.m);
		System.out.println("Zähler "+zaehler);
		System.out.println("nenner "+nenner);
		double Fx = (1 + b1.k) * (zaehler / nenner);
		System.out.println("Fx "+Fx);
		return Fx;
	}

}
