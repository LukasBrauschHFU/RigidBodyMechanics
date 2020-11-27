package InclinePlain;

import static java.lang.Math.cos;
import static java.lang.Math.signum;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import de.physolator.usr.components.Vector2D;
import de.physolator.usr.components.VectorMath;
import mechanics.rb2d.Impactpoint;

public class ballRollsOnInclineHandler implements Runnable {

	private Ball ball;
	private Incline il;
	protected double g = -9.81;
	private Impactpoint ip;


	public ballRollsOnInclineHandler(Ball ball, Incline il, Impactpoint ip) {
		this.ball = ball;
		this.il = il;
		this.ip = ip;
	}


	public void run() {
		ball.Fg.y = ball.m * ball.g;
		ball.Fg.x = 0;
		ball.ballState = BallState.ROLLING;
		if (ip.impactEdge == null) {
			if (il.ilVar == InclineVar.VAR1)
				rollVar1();
			if (il.ilVar == InclineVar.VAR2)
				rollVar2();
			if (il.ilVar == InclineVar.VAR3)
				rollVar3();
			if (il.ilVar == InclineVar.VAR4)
				rollVar4();
			if (il.ilVar == InclineVar.PLANE)
				rollPlane();
		} else {
			System.out.println("nicht null");
		}
	}

	public Vector2D rotateVector2D(Vector2D r, double rot) {
		return new Vector2D(r.x * cos(rot) - r.y * sin(rot), r.x * sin(rot) + r.y * cos(rot));
	}

	private void rollVar1() {
		double helper = VectorMath.angle(il.direction, ball.v);
		Vector2D vr = rotateVector2D(ball.v, -helper);
		ball.v.set(vr);
		ball.Fh.set(rotateVector2D(ball.Fg, toRadians(90) - il.alpha));
		Vector2D FhN = VectorMath.normalize(ball.Fh);
		double FgA = VectorMath.abs(ball.Fg);
		double FgAsin = FgA * sin(il.alpha);
		ball.Fh.set(VectorMath.mult(FgAsin, FhN));

		ball.Fn.set(rotateVector2D(ball.Fg, -il.alpha));
		Vector2D FnN = VectorMath.normalize(ball.Fn);
		double FgAcos = FgA * cos(il.alpha);
		ball.Fn.set(VectorMath.mult(FgAcos, FnN));

		double FnA = VectorMath.abs(ball.Fn);
		double Frx = FnA * cos(il.alpha) * ball.cr * (-signum(ball.Fh.x));
		double Fry = FnA * sin(il.alpha) * ball.cr * (-signum(ball.Fh.y));
		ball.Fr.set(Frx, Fry);

		ball.Fres.set(VectorMath.sub(ball.Fh, ball.Fr));

		ball.a.x = ball.Fh.x / ball.m;
		ball.a.y = ball.Fh.y / ball.m;
		
		ball.ballDir = BallDirection.RIGHT;
	}

	private void rollVar2() {
		ball.ballDir = BallDirection.LEFT;
		double helper = VectorMath.angle(il.direction, ball.v);
		System.out.println("helper "+helper);
		Vector2D vr = rotateVector2D(ball.v, -helper);
		vr = rotateVector2D(vr, Math.PI);
		ball.v.set(vr);
		ball.Fh.set(rotateVector2D(ball.Fg, toRadians(-90) + il.alpha));
		Vector2D FhN = VectorMath.normalize(ball.Fh);
		double FgA = VectorMath.abs(ball.Fg);
		double FgAsin = FgA * sin(il.alpha);
		ball.Fh.set(VectorMath.mult(FgAsin, FhN));

		ball.Fn.set(rotateVector2D(ball.Fg, il.alpha));
		Vector2D FnN = VectorMath.normalize(ball.Fn);
		double FgAcos = FgA * cos(il.alpha);
		ball.Fn.set(VectorMath.mult(FgAcos, FnN));

		double FnA = VectorMath.abs(ball.Fn);
		double Frx = FnA * cos(il.alpha) * ball.cr * (-signum(ball.Fh.x));
		double Fry = FnA * sin(il.alpha) * ball.cr * (-signum(ball.Fh.y));
		ball.Fr.set(Frx, Fry);

		ball.Fres.set(VectorMath.sub(ball.Fh, ball.Fr));

		double x = ball.Fh.x / ball.m;
		double y = ball.Fh.y / ball.m;

		ball.a.set(x, y);
	}

	private void rollVar3() {
		double helper = VectorMath.angle(il.direction, ball.v);
		Vector2D vr = rotateVector2D(ball.v, helper);
		vr = rotateVector2D(vr, Math.PI);
		ball.v.set(vr);
		ball.Fh.set(rotateVector2D(ball.Fg, toRadians(90) - il.alpha));
		Vector2D FhN = VectorMath.normalize(ball.Fh);
		double FgA = VectorMath.abs(ball.Fg);
		double FgAsin = FgA * sin(il.alpha);
		ball.Fh.set(VectorMath.mult(FgAsin, FhN));

		ball.Fn.set(rotateVector2D(ball.Fg, -il.alpha));
		Vector2D FnN = VectorMath.normalize(ball.Fn);
		double FgAcos = FgA * cos(il.alpha);
		ball.Fn.set(VectorMath.mult(FgAcos, FnN));

		double FnA = VectorMath.abs(ball.Fn);
		double Frx = FnA * cos(il.alpha) * ball.cr * (-signum(ball.Fh.x));
		double Fry = FnA * sin(il.alpha) * ball.cr * (-signum(ball.Fh.y));
		ball.Fr.set(Frx, Fry);

		ball.Fres.set(VectorMath.sub(ball.Fh, ball.Fr));

		ball.a.x = ball.Fh.x / ball.m;
		ball.a.y = ball.Fh.y / ball.m;

		ball.ballDir = BallDirection.RIGHT;
	}

	private void rollVar4() {
		double helper = VectorMath.angle(il.direction, ball.v);
		Vector2D vr = rotateVector2D(ball.v, helper);
		ball.v.set(vr);
		ball.Fh.set(rotateVector2D(ball.Fg, toRadians(-90) + il.alpha));
		Vector2D FhN = VectorMath.normalize(ball.Fh);
		double FgA = VectorMath.abs(ball.Fg);
		double FgAsin = FgA * sin(il.alpha);
		ball.Fh.set(VectorMath.mult(FgAsin, FhN));

		ball.Fn.set(rotateVector2D(ball.Fg, il.alpha));
		Vector2D FnN = VectorMath.normalize(ball.Fn);
		double FgAcos = FgA * cos(il.alpha);
		ball.Fn.set(VectorMath.mult(FgAcos, FnN));

		double FnA = VectorMath.abs(ball.Fn);
		double Frx = FnA * cos(il.alpha) * ball.cr * (-signum(ball.Fh.x));
		double Fry = FnA * sin(il.alpha) * ball.cr * (-signum(ball.Fh.y));
		ball.Fr.set(Frx, Fry);

		ball.Fres.set(VectorMath.sub(ball.Fh, ball.Fr));

		double x = ball.Fh.x / ball.m;
		double y = ball.Fh.y / ball.m;

		ball.a.set(x, y);
		ball.ballDir = BallDirection.LEFT;
	}

	private void rollPlane() {
		System.out.println(ball.cr);
		ball.Fr.x = ball.g * ball.m * ball.cr *(signum(ball.v.x));
		ball.v.y = 0;
		ball.a.set(ball.Fr.x / ball.m, 0);
		if ((ball.ballDir == BallDirection.LEFT && ball.v.x >= 0)
				|| (ball.ballDir == BallDirection.RIGHT && ball.v.x <= 0)) {
			ball.a.set(0, 0);
			ball.v.set(0, 0);
			ball.Fr.set(0, 0);
			ball.omega = 0;
			ball.alpha = 0;
			ball.ballDir = BallDirection.NONE;
			ball.ballState = BallState.STOP;
		}
	}
}
