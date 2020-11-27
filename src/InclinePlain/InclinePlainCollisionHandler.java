package InclinePlain;

import static java.lang.Math.*;
import de.physolator.usr.components.Vector2D;
import de.physolator.usr.components.VectorMath;
import de.physolator.usr.tvg.Shape;
import de.physolator.usr.tvg.TVG;
import mechanics.rb2d.Impactpoint;

public class InclinePlainCollisionHandler implements Runnable {
	private Ball ball;
	private Incline il;
	private Impactpoint ip;
	protected double g = -9.81;

	public InclinePlainCollisionHandler(Ball ball, Incline il, Impactpoint ip) {
		this.ball = ball;
		this.il = il;
		this.ip = ip;
	}

	public void run() {
		double rot;
		ball.ballState = BallState.FLYING;

		Vector2D direction = checkDirection(il.direction);
		if (il.ilVar == InclineVar.PLANE) {
			rot = Math.PI / 2 - il.alpha;
		} else {
			rot = VectorMath.angle(new Vector2D(0, 1), direction);
		}

		Vector2D rm = VectorMath.sub(ball.r, ip.impactPoint);
		Vector2D rmr = rotateVector2D(rm, rot);
		Vector2D vr = rotateVector2D(ball.v, rot);
		double phir = ball.phi + rot;

		double Omega = (ball.I * ball.omega - ball.m * ball.rad * vr.y) / (ball.I + ball.m * ball.rad * ball.rad);
		double Vx = -ball.k * vr.x;
		double Vy = -ball.rad * Omega;
		Vector2D Vr = new Vector2D(Vx, Vy);

		double Phir_ = phir - rot;
		Vector2D Vr_ = rotateVector2D(Vr, -rot);
		Vector2D rmr_ = rotateVector2D(rmr, -rot);
		Vector2D rm_r_ = VectorMath.add(rmr_, ip.impactPoint);

		ball.omega = Omega;
		ball.v.set(Vr_);
		ball.r.set(rm_r_);
		ball.phi = Phir_;
	}

	private Vector2D checkDirection(Vector2D direction) {
		if ((direction.x < 0 && direction.y < 0) || (direction.x < 0 && direction.y > 0)) {
			System.out.println("Rotate Direction");
			return rotateVector2D(direction, Math.PI);
		}
		return direction;
	}

	private Vector2D rotateVector2D(Vector2D r, double rot) {
		return new Vector2D(r.x * cos(rot) - r.y * sin(rot), r.x * sin(rot) + r.y * cos(rot));
	}
}
