package mechanics.rb2d;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import de.physolator.usr.components.Vector2D;
import de.physolator.usr.tvg.Shape;
import de.physolator.usr.tvg.TVG;

public class RigidBodyCollisionHandler2 implements Runnable{

	private double k = 1;
	private RigidBody r1;
	private RigidBody r2;
	private Impactpoint ip;
	
	
	public RigidBodyCollisionHandler2(RigidBody r1, RigidBody r2, Impactpoint ip) {
		this.r1 = r1;
		this.r2 = r2;
		this.ip = ip;
	}

	@Override
	public void run() {
		Vector2D p = new Vector2D(ip.impactPoint.x, ip.impactPoint.y);
		Vector2D collisionEdge = ip.impactEdge;
		collisionEdge.normalize();
		double rot = -collisionEdge.angleToVector(new Vector2D(0,1));
		Vector2D translation = new Vector2D(-p.x,-p.y);
	
		Vector2D r1R = new Vector2D(r1.r.x, r1.r.y);
		Vector2D r2R = new Vector2D(r2.r.x, r2.r.y);
		
		r1R.add(translation);
		r2R.add(translation);
		r1R = rotate(r1R, rot);
		r2R = rotate(r2R, rot);
		Vector2D r1V = rotate(r1.v, rot);
		Vector2D r2V = rotate(r2.v, rot);
		double v1x = r1V.x;
		double v1y = r1V.y;
		double v2x = r2V.x;
		double v2y = r2V.y;
		double a1 = r1R.y;
		double a2 = r2R.y;
		double omega1 = r1.omega;
		double omega2 = r2.omega;
		double Fx = impulseFx(v1x, v2x, a1, a2);
		double v1x_ = v1x - (Fx / r1.m);
		double v1y_ = v1y;
		double v2x_ = v2x + (Fx / r2.m);
		double v2y_ = v2y;
		double omega1_ = omega1 - ((a1*Fx)/r1.I);
		double omega2_ = omega2 + ((a2*Fx)/r2.I);
		
		//System.out.println("a1 = " + a1 + " , a2 = " + a2);
		//System.out.println("Stoﬂkante = " + collisionEdge.toString());
		//System.out.println("Stoﬂpunkt = " + p.toString());
		//System.out.println("rot = " + toDegrees(rot) + " , trans = " + translation.toString());
		
		Vector2D r1V_ = new Vector2D(v1x_, v1y_);
		r1.v.set(rotate(r1V_, -rot));
		r1.omega = omega1_;
		Vector2D r2V_ = new Vector2D(v2x_, v2y_);
		r2.v.set(rotate(r2V_, -rot));
		r2.omega = omega2_;
	}
	
	private Vector2D rotate(Vector2D a, double phi) {
		return new Vector2D(cos(phi)*a.x - sin(phi)*a.y, sin(phi)*a.x + cos(phi)*a.y);
	}
	
	private double impulseFx(double v1x, double v2x, double a1, double a2) {
		return ((1+k) * (v1x - v2x - a1*r1.omega + a2*r2.omega))
				/((1/r1.m) + (1/r2.m) + ((a1*a1)/r1.I) + ((a2*a2)/r2.I));
	}
	
	public void paintRigidBodyProjection(TVG tvg, Vertex[] vertices, Vector2D position, double phi) {
		tvg.beginShape(Shape.POLYGON_LINE_LOOP);
		for (Vertex e : vertices)
			tvg.vertex(position.x + cos(phi) * e.position.x + sin(phi) * e.position.y,
					position.y + sin(phi) * e.position.x - cos(phi) * e.position.y);
		tvg.endShape();
	}

}
