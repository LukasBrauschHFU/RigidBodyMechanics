package mechanics.rb2d;

import mechanics.tvg.MechanicsTVG;
import de.physolator.usr.*;
import de.physolator.usr.components.Vector2D;

public class RigidBodiesPS extends PhysicalSystem {

	public RigidBody[] rigidBodies;

	@V(unit = "kg*m/s")
	public double E_gesamt;

	public RigidBodiesPS() {
		test1();
	}

	public void test1() {
		rigidBodies = new RigidBody[2];
		rigidBodies[0] = new RigidBody(1, new Vector2D(-1, 0), new Vector2D(1, 0), new Vector2D(0, 0), 0.5, 10, 0, 0,
				new Polygon());
		rigidBodies[1] = new RigidBody(1, new Vector2D(1, 0), new Vector2D(-0.5, 0), new Vector2D(0, 0), 0.5, 0, 0, 0,
				new Polygon());
	}

	@Override
	public void f(double t, double dt) {
		double E_gesamt_temp = 0;
		for (RigidBody rb : rigidBodies) {
			rb.f(t, dt);
			E_gesamt_temp += rb.E_rb;
		}
		E_gesamt = E_gesamt_temp;
	}

	@Override
	public void g(double t, AfterEventDescription aed) {
		for (int i = 0; i < rigidBodies.length - 1; i = i + 1) {
			for (int j = rigidBodies.length - 1; j > i; j = j - 1) {
				rigidBodies[i].collisionWithRigidBodyCheck(aed, rigidBodies[j]);
			}
		}
	}

	@Override
	public void initSimulationParameters(SimulationParameters s) {
		s.fastMotionFactor = 1;
	}

	@Override
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

	public static void main(String args[]) {
		start();
	}
}
