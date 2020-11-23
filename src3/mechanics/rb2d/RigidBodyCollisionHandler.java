package mechanics.rb2d;

import de.physolator.usr.components.Vector2D;
import de.physolator.usr.components.VectorMath;
import de.physolator.usr.tvg.Shape;
import de.physolator.usr.tvg.TVG;
import mechanics.tvg.MechanicsTVG;

import static de.physolator.usr.tvg.Shape.POLYGON_LINE_LOOP;
import static java.lang.Math.*;

import java.util.Vector;

import javax.swing.CellRendererPane;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;

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
		System.out.println();
		rb1.collisionCounter++;
		rb2.collisionCounter++;
		System.out.println("Collision counter: " + rb1.collisionCounter);

		Vector2D p = ip.impactPoint;
		Vector2D collisionEdge = ip.impactEdge;
		Vector2D collisionEgeInverted = new Vector2D();
//		Vector2D edgeBefore = ip.edgeBefore;
		Vector2D edgeBefore = rb1.collisionEdgeBefore;
		double x = collisionEdge.x;
		double y = collisionEdge.y;
		

		System.out.println("Edge before: " + edgeBefore);
		System.out.println("Collition edge: " + collisionEdge);


			// Collision edge in Neg X Richtung
			if (x > y) {
				if ((x < 0 && y < 0) || (x > 0 && y < 0)) {
					collisionEdge.mult(-1);
				}
			}
			if (x < y && x < 0 && y < 0) {
				collisionEdge.mult(-1);
			}
		
//			if((x<0 && y<0) || (x<0 && y<0)) collisionEdge.mult(-1);
			System.out.println("Collision edge neg X: " + collisionEdge);

//Transformation
			// 1. r -p
			Vector2D r1m = VectorMath.sub(rb1.r, p);
			Vector2D r2m = VectorMath.sub(rb2.r, p);

			double rot = VectorMath.angle(new Vector2D(0, 1), collisionEdge);

			// 2. r mit rot um 0,0 drehen
			Vector2D r1mr = rotateVector2D(r1m, rot);
			Vector2D r2mr = rotateVector2D(r2m, rot);
			
			collisionEdge.set(rotateVector2D(collisionEdge, rot));

			// 3. v um rot drehen
			Vector2D v1r = rotateVector2D(rb1.v, rot);
			Vector2D v2r = rotateVector2D(rb2.v, rot);

			// 4. phi um rot drehen
			double phi1r = rb1.phi + rot;
			double phi2r = rb2.phi + rot;

//5. Berechnung
			double a1 = -r1mr.y;
			double a2 = -r2mr.y;
			System.out.println("a1 "+a1);
			System.out.println("a2 "+a2);

			double Fx = impulseFx(v1r.x, v2r.x, a1, a2);

			Vector2D V1r = new Vector2D(v1r.x - (Fx / rb1.m), v1r.y);
			Vector2D V2r = new Vector2D(v2r.x + (Fx / rb2.m), v2r.y);

			double Omega1 = rb1.omega - ((a1 * Fx) / rb1.I);
			double Omega2 = rb2.omega + ((a2 * Fx) / rb2.I);

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
			Vector2D r1m_r_ = VectorMath.add(r1mr_, p);
			Vector2D r2m_r_ = VectorMath.add(r2mr_, p);

			rb1.v.set(V1r);
			rb2.v.set(V2r);

			rb1.omega = Omega1;
			rb2.omega = Omega2;

			rb1.phi = phi1r;
			rb2.phi = phi2r;

			rb1.r.set(r1mr);
			rb2.r.set(r2mr);

			
//			rb1.v.set(V1r_);
//			rb2.v.set(V2r_);
//
//			rb1.omega = Omega1;
//			rb2.omega = Omega2;
//
//			rb1.phi = phi1r_;
//			rb2.phi = phi2r_;
//
//			rb1.r.set(r1mr);
//			rb2.r.set(r2mr);
	}

	private Vector2D rotateVector2D(Vector2D r, double rot) {
		return new Vector2D(r.x * cos(rot) - r.y * sin(rot), r.x * sin(rot) + r.y * cos(rot));
	}

	private double impulseFx(double v1x, double v2x, double a1, double a2) {
		double a1omega1 = a1 * rb1.omega;
		System.out.println("a1omega1 "+a1omega1);
		double a2omega2 = a2 * rb2.omega;
		System.out.println("a1omega2 "+a2omega2);
		double zaehler = v1x - v2x - a1omega1 + a2omega2;
		double nenner = (1 / rb1.m) + (1 / rb2.m) + (a1 * a1 / rb1.I) + (a2 * a2 / rb2.I);
		System.out.println("Zähler "+zaehler);
		System.out.println("nenner "+nenner);
		double Fx = (1 + k) * (zaehler / nenner);
		System.out.println("Fx "+Fx);
		return Fx;
	}

}
