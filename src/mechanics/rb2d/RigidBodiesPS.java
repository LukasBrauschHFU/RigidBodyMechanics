package mechanics.rb2d;

import mechanics.tvg.MechanicsTVG;
//import Helper.ObjectPosition;
import InclinePlain.Ball;
import InclinePlain.BallState;
import InclinePlain.Incline;
import InclinePlain.ObjectMovement;
import InclinePlain.ObjectPosition;
import de.physolator.gui.swt.t;
import de.physolator.usr.*;
import de.physolator.usr.components.Vector2D;
import de.physolator.usr.tvg.events.KeyEventTable;
import de.physolator.usr.util.Colors;

public class RigidBodiesPS extends PhysicalSystem {

	public RigidBody[] rigidBodies;
	public Ball[] balls;
	public Incline[] inclines;

	public void initGraphicsComponents(GraphicsComponents g, Structure s, Recorder r, SimulationParameters sp) {
		MechanicsTVG mTVG = new MechanicsTVG(this, s, r);
		mTVG.geometry.setUserArea(-2, 8, -2, 5);
		mTVG.velocityScaling = 100;
		mTVG.accelerationScaling = 10;
		mTVG.angularVelocityScaling = 0.2;
		mTVG.angularAccelerationScaling = 0.2;
		mTVG.accelerationXScaling = 5;
		mTVG.accelerationYScaling = 5;
		mTVG.FnScaling = 1;
		mTVG.FgScaling = 1;
		mTVG.FhScaling = 10;
		mTVG.FresScaling = 5;
		mTVG.FlScaling = 1;
		mTVG.showAngularAcceleration = false;
		mTVG.showAngularDisplacement = false;
		mTVG.showAngularVelocity = false;
		mTVG.showLabels = true;
		mTVG.showPath = true;
		mTVG.showVelocity = true;
		mTVG.showAcceleration = false;
		mTVG.showAccelerationX = false;
		mTVG.showAccelerationY = false;
		mTVG.showFr = true;
		mTVG.showFn = false;
		mTVG.showFg = false;
		mTVG.showFh = false;
		mTVG.showFres = false;
		mTVG.showFl = false;
		g.addTVG(mTVG);
	}

	public RigidBodiesPS() {
		rigidBodies = new RigidBody[1];
		rigidBodies[0] = new RigidBody(Double.MAX_VALUE, new Vector2D(2, 1), new Vector2D(0, 0), new Vector2D(0, 0), 1, 0, 0, 0, 4, 3,
				0.9, false, false, true, false);
//		rigidBodies[1] = new RigidBody(Double.MAX_VALUE, new Vector2D(0, 1), new Vector2D(0, 0), new Vector2D(0, 0), 1, 0, 0, 0, 4, 0.9,
//				0.9, false, false, true, false);
//		rigidBodies[2] = new RigidBody(Double.MAX_VALUE, new Vector2D(0, -1), new Vector2D(0, 0), new Vector2D(0, 0), 1, 0, 0, 0, 4, 0.9,
//				0.9, false, false, true, false);
//		rigidBodies[3] = new RigidBody(Double.MAX_VALUE, new Vector2D(1, 0), new Vector2D(0, 0), new Vector2D(0, 0), 1, 0, 0, 0, 4, 0.9,
//				0.9, false, false, true, false);
//		rigidBodies[4] = new RigidBody(1, new Vector2D(0, 0), new Vector2D(3, 1), new Vector2D(0,0), 1, 40, 0.1, 0, 4, 0.2,
//				0.2, false, false, true, true);

		balls = new Ball[1];
		balls[0] = new Ball(1, new Vector2D(0.2, 1.7), new Vector2D(4, 0), new Vector2D(0, 0), 0.1, Math.PI / 4, -5, 0,
				0.2, 0.7, 0.03, BallState.FLYING, null);
//		balls[1] = new Ball(0.5, new Vector2D(1, 2.8), new Vector2D(0, -1), new Vector2D(0, 0), 1, 0.7, 0.05);

		inclines = new Incline[0];
////		inclines[0] = new Incline(new Vector2D(0, 0), 0, 10); // PLANE
//		inclines[0] = new Incline(new Vector2D(-3, 0), new Vector2D(-3, 3)); // ORTHOGONAL
//		inclines[1] = new Incline(new Vector2D(3, 0), new Vector2D(3, 3)); // ORTHOGONAL
//		inclines[2] = new Incline(new Vector2D(-3, 0), new Vector2D(3, 0)); // ORTHOGONAL
//		inclines[3] = new Incline(new Vector2D(3, 3), new Vector2D(-3, 3)); // ORTHOGONAL
////		inclines[0] = new Incline(new Vector2D(-3,3), new Vector2D(3,-3)); //VAR 1
////		inclines[0] = new Incline(new Vector2D(-3,-3), new Vector2D(3,3)); //VAR 2 
////		inclines[0] = new Incline(new Vector2D(3,-3), new Vector2D(-3,3)); //VAR 3
////		inclines[0] = new Incline(new Vector2D(3,3), new Vector2D(-3,-3)); //VAR 4 

	}

	@Override
	public void f(double t, double dt) {
		for (RigidBody rb : rigidBodies) {
			rb.f(t, dt);
		}
		for (Ball b : balls) {
			b.f(t, dt);
		}
	}

	@Override
	public void g(double t, AfterEventDescription aed) {
		for (int i = 0; i < rigidBodies.length - 1; i = i + 1) {
			if (rigidBodies[i].translation)
				for (int j = rigidBodies.length - 1; j > i; j = j - 1) {
					rigidBodies[i].collisionWithRigidBodyCheck(aed, rigidBodies[j]);
				}
		}

		// RB on Incline
		for (int i = 0; i < rigidBodies.length; i++) {
			if (rigidBodies[i].translation)
				for (int j = 0; j < inclines.length; j++) {
					rigidBodies[i].RbCollisionWidthInclineCheck(aed, inclines[j]);
				}
		}

		// RB starts Falling
		for (int i = 0; i < rigidBodies.length; i++) {
			for (int j = 0; j < inclines.length; j++) {
				rigidBodies[i].RbStartsFallingCheck(aed, inclines[j]);
			}
		}
		for (int i = 0; i < rigidBodies.length; i++) {
			for (int j = 0; j < balls.length; j++) {
				rigidBodies[i].RbCollisionWidthBallCheck(aed, balls[j]);
			}
		}

		// Bouncing
		for (int i = 0; i < balls.length; i++) {
			for (int j = 0; j < inclines.length; j++) {
				balls[i].collisionWidthInclineCheck(aed, inclines[j]);
			}
		}

//		// Rolling
		for (int i = 0; i < balls.length; i++) {
			for (int j = 0; j < inclines.length; j++) {
				
				balls[i].ballRollsOnInclineCheck(aed, inclines[j]);
			}
		}
		// Ball stops rolling
		for (int i = 0; i < balls.length; i++) {
			for (int j = 0; j < inclines.length; j++) {
				balls[i].ballStopsRollingOnInclineCheck(aed, inclines[j]);
			}
		}

////		// Ball on incline Edge
//		for (int i = 0; i < balls.length; i++) {
//			for (int j = 0; j < inclines.length; j++) {
//				balls[i].ballOnInclineEdgeCheck(aed, inclines[j]);
//			}
//		}
//
//		// Ball is Falling
////		for (int i = 0; i < balls.length; i++) {
////			for (int j = 0; j < inclines.length; j++)
////				balls[i].ballFallsCheck(aed, inclines[j]);
////		}
//
//		// Collision Ball Ball
//		for (int i = 0; i < balls.length; i++) {
//			for (int j = balls.length - 1; j > i; j--)
//				balls[i].collisionBallBallCheck(aed, balls[j]);
//		}
//
//		// Ball stops rolling
//		for (int i = 0; i < balls.length; i++) {
//			balls[i].ballStopsRollingCheck(aed);
//		}
	}

	@Override
	public void initSimulationParameters(SimulationParameters s) {
		s.fastMotionFactor = 1;
	}

	public static void main(String args[]) {
		start();
	}
}
