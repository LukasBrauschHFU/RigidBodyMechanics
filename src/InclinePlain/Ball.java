package InclinePlain;

import de.physolator.usr.AfterEventDescription;
import de.physolator.usr.Ignore;
import de.physolator.usr.V;
import de.physolator.usr.components.Vector2D;
import de.physolator.usr.components.VectorMath;
import de.physolator.usr.tvg.Shape;
import de.physolator.usr.tvg.TVG;
import mechanics.rb2d.Impactpoint;
import static java.lang.Math.*;

import java.awt.geom.Line2D;

//import Helper.ObjectPosition;

public class Ball {
	public BallState ballState = BallState.FLYING;

	@V(unit = "kg")
	public double m;
	@V(unit = "m", derivative = "v")
	public Vector2D r = new Vector2D();
	@V(unit = "m/s", derivative = "a")
	public Vector2D v = new Vector2D();
	@V(unit = "m/s^2")
	public Vector2D a = new Vector2D();

	@V(unit = "kg m^2")
	public double I;
	@V(unit = "rad", derivative = "omega")
	public double phi;
	@V(unit = "rad / s", derivative = "alpha")
	public double omega;
	@V(unit = "rad / s^2")
	public double alpha;

	@V(unit = "m")
	public double rad = 0.4;

	@V(unit = "N")
	public Vector2D Fh = new Vector2D();
	@V(unit = "N")
	public Vector2D Fr = new Vector2D();
	@V(unit = "N")
	public Vector2D Fhn = new Vector2D();
	@V(unit = "N")
	public Vector2D Fres = new Vector2D();
	@V(unit = "N")
	public Vector2D Fn = new Vector2D();
	@V(unit = "N")
	public Vector2D Fg = new Vector2D();
	@V(unit = "N")
	public Vector2D Fl = new Vector2D();

	@Ignore
	@V(unit = "m/s^2")
	public double g = -9.81;
	@Ignore
	public int color;

	double A = PI * rad * rad;
	double rho = 1.2041;
	double k;
	double cr;
	public Vector2D distanceToLine = new Vector2D();
	public BallDirection ballDir;
	public ObjectPosition op;

	public Ball(double m, Vector2D r, Vector2D v, Vector2D a, double rad, double k, double cr) {
		this.r = r;
		this.v = v;
		this.a = a;
		this.m = m;
		this.rad = rad;
		this.k = k;
		this.cr = cr;
		this.ballState = ballState;
	}

	public Ball(double m, Vector2D r, Vector2D v, Vector2D a, double rad, double k, double cr, BallState ballState) {
		this.r = r;
		this.v = v;
		this.a = a;
		this.m = m;
		this.rad = rad;
		this.k = k;
		this.cr = cr;
		this.ballState = ballState;
	}

	public Ball(double m, Vector2D r, Vector2D v, Vector2D a, double I, double phi, double omega, double alpha,
			double rad, double k, double cr, BallState ballState) {
		this.r = r;
		this.v = v;
		this.a = a;
		this.m = m;
		this.rad = rad;
		this.k = k;
		this.cr = cr;
		this.I = I;
		this.phi = phi;
		this.omega = omega;
		this.alpha = alpha;
		this.ballState = ballState;
	}

	public Ball(double m, Vector2D r, Vector2D v, Vector2D a, double I, double phi, double omega, double alpha,
			double rad, double k, double cr, BallState ballState, ObjectPosition op) {
		this.r = r;
		this.v = v;
		this.a = a;
		this.m = m;
		this.rad = rad;
		this.k = k;
		this.cr = cr;
		this.I = I;
		this.phi = phi;
		this.omega = omega;
		this.alpha = alpha;
		this.ballState = ballState;
		this.op = op;
	}

	public Ball(double m, Vector2D r, double rad) {
		this.m = m;
		this.r = r;
		this.rad = rad;
	}

	public void f(double t, double dt) {
		if (ballState == BallState.FLYING) {
			a.y = g;
		}
		if (ballState == BallState.STOP) {
			v.set(0, 0);
			a.set(0, 0);
			omega = 0;
			alpha = 0;
		}
		if (op == ObjectPosition.FIXED) {
			v.set(0, 0);
			a.set(0, 0);
		}
		if (v.x > 0)
			ballDir = BallDirection.RIGHT;
		if (v.x < 0)
			ballDir = BallDirection.LEFT;
		if (v.x == 0)
			ballDir = BallDirection.NONE;
	}

	// Bouncing - WORKS
	public void collisionWidthInclineCheck(AfterEventDescription aed, Incline il) {
		Vector2D vectorToLine = vectorToLine(il);
		double distanceToLine = vectorToLine.abs();
		Vector2D ip = VectorMath.footOfPerpendicular(r, il.start, il.direction);
		if (il.ilVar == InclineVar.PLANE) {
			if ((r.x - vectorToLine.x > il.start.x && r.x - vectorToLine.x < il.end.x)
					|| (r.x - vectorToLine.x < il.start.x && r.x - vectorToLine.x > il.end.x)) {
				if (vectorToLine.abs() <= rad) {
					Runnable handler = new InclinePlainCollisionHandler(this, il, new Impactpoint(ip));
//					Runnable handler = new InclinePlainCollisionHandler(this, il);
					aed.reportEvent(handler, "collision ball and line" + this.toString() + "  " + il.toString());
				}
			}
		}
		if (il.ilVar == InclineVar.ORTHOGONAL) {
			if ((r.y - vectorToLine.y > il.start.y && r.y - vectorToLine.y < il.end.y)
					|| (r.y - vectorToLine.y < il.start.y && r.y - vectorToLine.y > il.end.y)) {
				if (vectorToLine.abs() <= rad) {
					Runnable handler = new InclinePlainCollisionHandler(this, il, new Impactpoint(ip));
//					Runnable handler = new InclinePlainCollisionHandler(this, il);
					aed.reportEvent(handler, "collision ball and line" + this.toString() + "  " + il.toString());
				}
			}
		}
		if (il.ilVar == InclineVar.VAR1 || il.ilVar == InclineVar.VAR2) {
			if (r.x - vectorToLine.x >= il.start.x && r.x - vectorToLine.x <= il.end.x) {
				if (vectorToLine.abs() <= rad) {
					Runnable handler = new InclinePlainCollisionHandler(this, il, new Impactpoint(ip));
//					Runnable handler = new InclinePlainCollisionHandler(this, il);
					aed.reportEvent(handler, "collision ball and line" + this.toString() + "  " + il.toString());
				}
			}
		}
		if (il.ilVar == InclineVar.VAR3 || il.ilVar == InclineVar.VAR4)
			if (r.x - vectorToLine.x <= il.start.x && r.x - vectorToLine.x >= il.end.x) {
				if (vectorToLine.abs() <= rad) {
					Runnable handler = new InclinePlainCollisionHandler(this, il, new Impactpoint(ip));
//					Runnable handler = new InclinePlainCollisionHandler(this, il);
					aed.reportEvent(handler, "collision ball and line" + this.toString() + "  " + il.toString());
				}
			}
	}

	public void ballRollsOnInclineCheck(AfterEventDescription aed, Incline il) {
		Vector2D vectorToLine = vectorToLine(il);
		double distanceToLine = vectorToLine.abs();
		if ((distanceToLine > rad || vectorToLine.y <= 0 || ballState == BallState.STOP) || abs(v.y) > 1)
			return;
		if (ballState == BallState.FLYING) {

			if (il.ilVar == InclineVar.VAR1 || il.ilVar == InclineVar.VAR2)
				if ((r.x - vectorToLine.x >= il.start.x && r.x - vectorToLine.x <= il.end.x)) {
					Vector2D point = new Vector2D(r.x, r.y);
					Impactpoint ip = new Impactpoint(point);
					Runnable handler = new ballRollsOnInclineHandler(this, il, ip);
					aed.reportEvent(handler, "ball rolls on line" + this.toString() + "  " + il.toString());
				}
			if (il.ilVar == InclineVar.VAR3 || il.ilVar == InclineVar.VAR4)
				if (r.x + vectorToLine.x <= il.start.x && r.x - vectorToLine.x >= il.end.x) {
					Vector2D point = new Vector2D(r.x, r.y);
					Impactpoint ip = new Impactpoint(point);
					Runnable handler = new ballRollsOnInclineHandler(this, il, ip);
					aed.reportEvent(handler, "ball rolls on line" + this.toString() + "  " + il.toString());
				}
			if (il.ilVar == InclineVar.PLANE)
				if (((r.x - vectorToLine.x < il.start.x && r.x - vectorToLine.x > il.end.x)
						|| (r.x - vectorToLine.x > il.start.x && r.x - vectorToLine.x < il.end.x))) {
					Vector2D point = new Vector2D(r.x - vectorToLine.x, r.y - vectorToLine.y);
					Impactpoint ip = new Impactpoint(point);
					Runnable handler = new ballRollsOnInclineHandler(this, il, ip);
					aed.reportEvent(handler, "ball rolls on line" + this.toString() + "  " + il.toString());
				}
			if (il.ilVar == InclineVar.ORTHOGONAL)
				if (distanceToLine < vectorToLine.y) {
					Vector2D point = new Vector2D(r.x, r.y);
					Impactpoint ip = new Impactpoint(point);
					Runnable handler = new ballRollsOnInclineHandler(this, il, ip);
					aed.reportEvent(handler, "ball rolls on line" + this.toString() + "  " + il.toString());
				}
		}
	}

	public void ballStopsRollingOnInclineCheck(AfterEventDescription aed, Incline il) {
		Vector2D vectorToLine = vectorToLine(il);
		double distanceToLine = vectorToLine.abs();
		
		System.out.println(vectorToLine.y);
		if (ballState == BallState.FLYING || abs(v.y) > 1)
			return;
		if (ballState == BallState.ROLLING) {
			System.out.println("Hier 1");
			if (il.ilVar == InclineVar.PLANE) {
				System.out.println("Hier 2");
				if ((ballDir == BallDirection.LEFT && v.x >= -0.1) || (ballDir == BallDirection.RIGHT && v.x <= 0.01)) {
					System.out.println("Hier 3");
					Runnable handler = new ballStopsRollingOnInclineCheck(this);
					aed.reportEvent(handler, "ball rolls on line" + this.toString() + "  " + il.toString());
				}
			}
		}
	}

	public Vector2D vectorToLine(Incline il) {
		return VectorMath.perpendicular(r, il.start, il.direction);
	}
//
//	// Rollen - Bleibt auf kanke liegen bei VAR3 und VAR4
//	public void ballFallsCheck(AfterEventDescription aed, Incline il) {
////		Vector2D ip = VectorMath.footOfPerpendicular(r, il.start, il.direction);
//////		if (distanceToLine.abs() < rad || ballState == BallState.FLYING) {
//////			return;
//////		}
////		if (il.ilVar == InclineVar.VAR1 || il.ilVar == InclineVar.VAR2)
////			if ((r.x + abs(distanceToLine.x) < il.start.x || r.x - abs(distanceToLine.x) > il.end.x)) {
////				Runnable handler = new ballFallsHandler(this);
////				aed.reportEvent(handler, "ball falls: ", this.toString());
////			}
////
////		if (il.ilVar == InclineVar.VAR3 || il.ilVar == InclineVar.VAR4) {
////			if (r.x - abs(distanceToLine.x) > il.start.x || r.x + abs(distanceToLine.x) < il.end.x) {
////				Runnable handler = new ballFallsHandler(this);
////				aed.reportEvent(handler, "ball falls: ", this.toString());
////
////			}
////		}
////		if (il.ilVar == InclineVar.PLANE) {
//////			if (r.x + rad < il.start.x  && r.x - rad < il.end.x) {
////			double distToStart = VectorMath.dist(r, il.start);
////			Vector2D vectorToStart = VectorMath.sub(r, il.start);
////			if (r.x < il.start.x && r.y >il.start.y) {
////				if (distToStart > rad && checkPointInsideCircle(r, il.start)) {
////					Runnable handler = new ballFallsHandler(this);
////					aed.reportEvent(handler, "ball falls: ", this.toString());
////				}
////			}
////		}
//	}
//
//	public void collisionBallBallCheck(AfterEventDescription aed, Ball ball2) {
//		double distance = VectorMath.dist(r, ball2.r);
//		Vector2D point = new Vector2D(r.x - distanceToLine.x, r.y - distanceToLine.y);
//		Vector2D tangente = VectorMath.perpendicular(point, VectorMath.sub(r, ball2.r));
//		Impactpoint ip = new Impactpoint(point, tangente);
//
//		if (distance <= (rad + ball2.rad)) {
//			Runnable handler = new collisionBallBallHandler(this, ball2, ip);
//			aed.reportEvent(handler, "balls touching" + " " + this.toString() + " " + ball2.toString());
//
//		}
//
//	}
//
//	public void ballStopsRollingCheck(AfterEventDescription aed) {
////		if (abs(v.x) <= 0.01) {
////			Runnable handler = new ballStopsRollingHandler(this);
////			aed.reportEvent(handler, "ball stopps rolling", this.toString());
////		}
//
//	}

	public void paint(TVG tvg, Vector2D r, double rad) {
		Vector2D rm = VectorMath.sub(r, r);
		tvg.beginTransformationTranslation(r);
		tvg.beginTransformationRotation(phi);
		tvg.beginShape(Shape.POLYGON_LINE_LOOP);
		tvg.drawCircle(rm, rad);
		tvg.drawLine(rm.x, rm.y, rotateVector2D(rm, omega).x, rotateVector2D(rm, omega).y - rad);
		tvg.endShape();
		tvg.endTransformation();
		tvg.endTransformation();
	}

	public Vector2D rotateVector2D(Vector2D r, double rot) {
		return new Vector2D(r.x * cos(rot) - r.y * sin(rot), r.x * sin(rot) + r.y * cos(rot));
	}

//	private boolean checkPointInsideCircle(Vector2D p1, Vector2D p2) {
//		Vector2D vectorCenterToLineEdge = VectorMath.sub(p1, p2);
//		System.out.println(vectorCenterToLineEdge);
//		if (vectorCenterToLineEdge.abs() <= rad + 0.1)
//			return true;
//		return false;
//	}
//
//	public void ballOnInclineEdgeCheck(AfterEventDescription aed, Incline il) {
////		if(checkPointInsideCircle(il.start, r) && r.x <= il.start.x) {
////			Vector2D vectorToStart = VectorMath.sub(il.start, r);
////			Vector2D tangenteDirection = VectorMath.normalize(VectorMath.perpendicular(r, il.start, vectorToStart));
////			System.out.println("tangende direction "+tangenteDirection);
////			Runnable handler = new ballOnInclineEdgeHandler(this);
////			aed.reportEvent(handler, "Ball on InclineEdge " + this.toString());
////		}
//
//		if (checkPointInsideCircle(il.start, r) && r.x >= il.start.x-0.1) {
//			System.out.println("true");
//			Vector2D vectorToStart = VectorMath.sub(il.start, r);
//			Vector2D tangenteDirection = VectorMath.perpendicular(il.start, vectorToStart);
//			Vector2D tangenteStart = il.start;
//			Vector2D tangenteEnd = VectorMath.add(tangenteStart,VectorMath.mult(-rad,VectorMath.normalize(tangenteDirection)));
//			Incline tangente = new Incline(tangenteStart, tangenteEnd);
//			Vector2D point = new Vector2D(r.x, r.y);
//		
//			Runnable handler = new ballOnInclineEdgeHandler(this, new Impactpoint(point, tangente));
//			aed.reportEvent(handler, "Ball on InclineEdge " + this.toString());
////			Runnable handler = new ballFallsHandler(this, il);
////			aed.reportEvent(handler, "ball falls from line: ");
//		}
////		}
//
//	}

}
