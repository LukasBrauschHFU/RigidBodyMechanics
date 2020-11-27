package mechanics.rb2d;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import InclinePlain.Incline;
import de.physolator.usr.components.Vector2D;
import de.physolator.usr.components.VectorMath;

public class collisionRbInclineHandler implements Runnable {
	private Incline il;
	private RigidBody rb;
	private Vector2D ip;

	public collisionRbInclineHandler(RigidBody rb, Incline il, Vector2D ip) {
		this.rb = rb;
		this.il = il;
		this.ip = ip;
	}

	@Override
	public void run() {
		rb.v.set(0,0);
		rb.omega = 0;
		rb.state = BodyState.STOP;
//		System.out.println("Handler");
//		Vector2D rm = VectorMath.sub(rb.r, ip);
//
//		double rot = VectorMath.angle(new Vector2D(0, 1), il.direction);
//		
//		Vector2D rmr = rotateVector2D(rm, rot);
//		Vector2D vr = rotateVector2D(rb.v, rot);
//		double phir = rb.phi + rot;
//		
//		double a = -rmr.y;
//		
//		double Fx = impulseFx(vr.x, 0, a, 0);
//		
//		Vector2D Vr = new Vector2D(vr.x - (Fx / rb.m), vr.y);
//		double Omega = rb.omega - ((a * Fx) / rb.I);
//		
//		double phir_ = phir - rot;
//		
//		Vector2D Vr_ = rotateVector2D(Vr, -rot);
//		
//		Vector2D rmr_ = rotateVector2D(rmr, -rot);
//		Vector2D rm_r_ = VectorMath.add(rmr_, ip);
//		
//		
////		rb.v.set(Vr_);
////		rb.omega = Omega;
////		rb.phi = phir_;
////		rb.r.set(rm_r_);
//		
//		rb.v.set(Vr);
//		rb.omega = Omega;
//		rb.phi = phir;
//		rb.r.set(rmr);

		
	}
	
	private Vector2D rotateVector2D(Vector2D r, double rot) {
		return new Vector2D(r.x * cos(rot) - r.y * sin(rot), r.x * sin(rot) + r.y * cos(rot));
	}
	
	private double impulseFx(double v1x, double v2x, double a1, double a2) {
		double a1omega1 = a1 * rb.omega;
		System.out.println("a1omega1 "+a1omega1);

		double zaehler = v1x - v2x - a1omega1 ;
		double nenner = (1 / rb.m) + (1 / rb.m) + (a1 * a1 / rb.I);
		 nenner =1;
		System.out.println("Zähler "+zaehler);
		System.out.println("nenner "+nenner);
		double Fx = (1 + 0.5) * (zaehler / nenner);
		System.out.println("Fx "+Fx);
		return Fx;
	}

}
