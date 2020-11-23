package mechanics.rb2d;

import static java.lang.Math.cos;
import static java.lang.Math.signum;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import InclinePlain.Ball;
import InclinePlain.Incline;
import InclinePlain.InclineVar;
import de.physolator.usr.components.Vector2D;
import de.physolator.usr.components.VectorMath;

public class rbSlidesOnInclineHandler implements Runnable {
	private RigidBody rb;
	private Incline il;

	public rbSlidesOnInclineHandler(RigidBody rb, Incline il) {
		this.rb = rb;
		this.il = il;
	}

	public void run() {
		rb.v.y = 0;
		rb.a.set(0,0);
		rb.Fg.set(0, rb.m*rb.g);

		if (il.ilVar == InclineVar.VAR1) {
			rb.Fh.set(rotateVector2D(rb.Fg, toRadians(90) - il.alpha));
			
			
			Vector2D FhN = VectorMath.normalize(rb.Fh);
			double FgA = VectorMath.abs(rb.Fg);
			double FgAsin = FgA * sin(il.alpha);
			rb.Fh.set(VectorMath.mult(FgAsin, FhN));

			rb.Fn.set(rotateVector2D(rb.Fg, -il.alpha));
			Vector2D FnN = VectorMath.normalize(rb.Fn);
			double FgAcos = FgA * cos(il.alpha);
			rb.Fn.set(VectorMath.mult(FgAcos, FnN));

			rb.Fhr.set(VectorMath.mult(rb.mur, rb.Fn));
			if (rb.Fhr.abs() < rb.Fh.abs()) {
				double FnA = VectorMath.abs(rb.Fn);
				double Frx = FnA * cos(il.alpha) * rb.mu * (-signum(rb.Fh.x));
				double Fry = FnA * sin(il.alpha) * rb.mu * (-signum(rb.Fh.y));
				rb.Fr.set(Frx, Fry);

				rb.Fres.set(VectorMath.sub(rb.Fh, rb.Fr));
				System.out.println("Fres "+rb.Fh);

				double x = -rb.Fh.x / rb.m;
				double y = -rb.Fh.y / rb.m;

				rb.a.set(x, y);
				rb.a.set(rotateVector2D(rb.a, 2 * il.alpha));
				rb.state = BodyState.SLIDING;
			} else
				rb.state = BodyState.STOP;
		}

		if (il.ilVar == InclineVar.VAR2) {
//			double helper = VectorMath.angle(il.direction, rb.v);
//			System.out.println("helper " + helper);
//			Vector2D vr = rotateVector2D(rb.v, -helper);
//			vr = rotateVector2D(vr, Math.PI);
//			rb.v.set(vr);
			
			rb.Fh.set(rotateVector2D(rb.Fg, il.alpha));
			System.out.println(rb.Fh);
			
			Vector2D FhN = VectorMath.normalize(rb.Fh);
			double FgA = VectorMath.abs(rb.Fg);
			double FgAsin = FgA * sin(il.alpha);
			rb.Fh.set(VectorMath.mult(FgAsin, FhN));

			rb.Fn.set(rotateVector2D(rb.Fg, il.alpha));
			Vector2D FnN = VectorMath.normalize(rb.Fn);
			double FgAcos = FgA * cos(il.alpha);
			rb.Fn.set(VectorMath.mult(FgAcos, FnN));

			rb.Fhr.set(VectorMath.mult(rb.mur, rb.Fn));
//			System.out.println("FH " + rb.Fh.abs());
//			System.out.println("fhr " + rb.Fhr.abs());
			if (rb.Fhr.abs() < rb.Fh.abs()) {
				double FnA = VectorMath.abs(rb.Fn);
				double Frx = FnA * cos(il.alpha) * rb.mu * (-signum(rb.Fh.x));
				double Fry = FnA * sin(il.alpha) * rb.mu * (-signum(rb.Fh.y));
				rb.Fr.set(Frx, Fry);

				rb.Fres.set(VectorMath.sub(rb.Fh, rb.Fr));

				double x = rb.Fres.x / rb.m;
				double y = rb.Fres.y / rb.m;

				rb.a.set(x, y);
				rb.state = BodyState.SLIDING;
			} else
				rb.state = BodyState.STOP;
		}

		if (il.ilVar == InclineVar.VAR3 || il.ilVar == InclineVar.VAR4) {
			rb.Fh.set(rotateVector2D(rb.Fg, toRadians(-90) + il.alpha));
			Vector2D FhN = VectorMath.normalize(rb.Fh);
			double FgA = VectorMath.abs(rb.Fg);
			double FgAsin = FgA * sin(il.alpha);
			rb.Fh.set(VectorMath.mult(FgAsin, FhN));

			rb.Fn.set(rotateVector2D(rb.Fg, il.alpha));
			Vector2D FnN = VectorMath.normalize(rb.Fn);
			double FgAcos = FgA * cos(il.alpha);
			rb.Fn.set(VectorMath.mult(FgAcos, FnN));

			rb.Fhr.set(VectorMath.mult(rb.mur, rb.Fn));
//			System.out.println("FH " + rb.Fh.abs());
//			System.out.println("fhr " + rb.Fhr.abs());
			if (rb.Fhr.abs() < rb.Fh.abs()) {
				double FnA = VectorMath.abs(rb.Fn);
				double Frx = FnA * cos(il.alpha) * rb.mu * (-signum(rb.Fh.x));
				double Fry = FnA * sin(il.alpha) * rb.mu * (-signum(rb.Fh.y));
				rb.Fr.set(Frx, Fry);

				rb.Fres.set(VectorMath.sub(rb.Fh, rb.Fr));

				double x = rb.Fh.x / rb.m;
				double y = rb.Fh.y / rb.m;

				rb.a.set(x, y);
				rb.state = BodyState.SLIDING;
			} else
				rb.state = BodyState.STOP;
		}

		if (il.ilVar == InclineVar.PLANE)
			rb.state = BodyState.STOP;
	}

	public Vector2D rotateVector2D(Vector2D r, double rot) {
		return new Vector2D(r.x * cos(rot) - r.y * sin(rot), r.x * sin(rot) + r.y * cos(rot));
	}


}
