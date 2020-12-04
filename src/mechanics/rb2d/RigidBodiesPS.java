package mechanics.rb2d;

import mechanics.rb2d.shapes.Circle;
import mechanics.rb2d.shapes.Polygon;
import mechanics.rb2d.shapes.Rectangle;
import mechanics.tvg.MechanicsTVG;
import de.physolator.usr.*;
import de.physolator.usr.components.Vector2D;

public class RigidBodiesPS extends PhysicalSystem {

	public RigidBody[] rigidBodies;

	@V(unit = "kg*m/s")
	public double E_gesamt;

	public RigidBodiesPS() {
		test6();
	}

	private void test6() {
		rigidBodies = new RigidBody[2];
		rigidBodies[0] = new RigidBody(1, new Vector2D(-1, 0), new Vector2D(0, 0), new Vector2D(0, 0), 0.5, -2, 0, 0,
				new Rectangle());
		rigidBodies[1] = new RigidBody(1, new Vector2D(2.5, -1), new Vector2D(-1, 0), new Vector2D(0, 0), 0.5, 10, 0, 0,
				new Circle(1));
	}

	private void test5() {
		rigidBodies = new RigidBody[6];
		rigidBodies[0] = new RigidBody(Double.MAX_VALUE, new Vector2D(-1.1, 0), new Vector2D(0, 0), new Vector2D(0, 0), Double.MAX_VALUE, 5, 0, 0,
				new Rectangle());
		rigidBodies[1] = new RigidBody(Double.MAX_VALUE, new Vector2D(1.1, 0), new Vector2D(0, 0), new Vector2D(0, 0), Double.MAX_VALUE, 0, 0, 0,
				new Rectangle());
		rigidBodies[2] = new RigidBody(Double.MAX_VALUE, new Vector2D(0, 1.1), new Vector2D(0, 0), new Vector2D(0, 0), Double.MAX_VALUE, 0, 0, 0,
				new Rectangle());
		rigidBodies[3] = new RigidBody(Double.MAX_VALUE, new Vector2D(0, -1.1), new Vector2D(0, 0), new Vector2D(0, 0), Double.MAX_VALUE, 0, 0, 0,
				new Rectangle());
		rigidBodies[4] = new RigidBody(1, new Vector2D(0.4, -0.2), new Vector2D(-0.5, 0), new Vector2D(0, 0), 0.25, 0, 0, 0,
				new Polygon(new Vector2D[] {new Vector2D(-0.1, 0.1), new Vector2D(0.1, 0.1), new Vector2D(0.1, -0.1),
						new Vector2D(-0.1, -0.1)}));
		rigidBodies[5] = new RigidBody(1, new Vector2D(-0.4, 0.2), new Vector2D(-0.5, 0), new Vector2D(0, 0), 0.25, 0, 0, 0,
				new Polygon(new Vector2D[] {new Vector2D(-0.1, 0.1), new Vector2D(0.1, 0.1), new Vector2D(0.1, -0.1),
						new Vector2D(-0.1, -0.1)}));
		
	}

	private void test4() {
		rigidBodies = new RigidBody[6];
		rigidBodies[0] = new RigidBody(Double.MAX_VALUE, new Vector2D(-1.1, 0), new Vector2D(0, 0), new Vector2D(0, 0), Double.MAX_VALUE, 5, 0, 0,
				new Rectangle());
		rigidBodies[1] = new RigidBody(Double.MAX_VALUE, new Vector2D(1.1, 0), new Vector2D(0, 0), new Vector2D(0, 0), Double.MAX_VALUE, 0, 0, 0,
				new Rectangle());
		rigidBodies[2] = new RigidBody(Double.MAX_VALUE, new Vector2D(0, 1.1), new Vector2D(0, 0), new Vector2D(0, 0), Double.MAX_VALUE, 0, 0, 0,
				new Rectangle());
		rigidBodies[3] = new RigidBody(Double.MAX_VALUE, new Vector2D(0, -1.1), new Vector2D(0, 0), new Vector2D(0, 0), Double.MAX_VALUE, 0, 0, 0,
				new Rectangle());
		rigidBodies[4] = new RigidBody(1, new Vector2D(0.4, -0.2), new Vector2D(-0.5, 0), new Vector2D(0, 0), 0.25, 0, 0, 0,
				new Polygon(new Vector2D[] { new Vector2D(0.1, -0.1), new Vector2D(-0.1, -0.1), new Vector2D(0, 0.2) }));
		rigidBodies[5] = new RigidBody(1, new Vector2D(-0.4, 0.2), new Vector2D(-0.5, 0), new Vector2D(0, 0), 0.25, 0, 0, 0,
				new Polygon(new Vector2D[] { new Vector2D(-0.1, 0.1), new Vector2D(0.1, 0.1), new Vector2D(0.1, -0.1),
						new Vector2D(-0.1, -0.1), new Vector2D(-0.2, -0.2), new Vector2D(-0.3, -0.3) }));
		
	}

	public void test1() {
		rigidBodies = new RigidBody[2];
		rigidBodies[0] = new RigidBody(1, new Vector2D(-1, 0), new Vector2D(1, 0), new Vector2D(0, 0), 0.5, 10, 0, 0,
				new Rectangle());
		rigidBodies[1] = new RigidBody(1, new Vector2D(1, 0), new Vector2D(-0.5, 0), new Vector2D(0, 0), 0.5, 0, 0, 0,
				new Rectangle());
	}
	
	public void test2() {
		rigidBodies = new RigidBody[2];
		rigidBodies[0] = new RigidBody(1, new Vector2D(-1, 0), new Vector2D(1, 0), new Vector2D(0, 0), 0.5, 10, 0, 0,
				new Circle(2));
		rigidBodies[1] = new RigidBody(1, new Vector2D(2.5, 0), new Vector2D(-1, 0), new Vector2D(0, 0), 0.5, 10, 0, 0,
				new Circle(1));
		
	}
	
	public void test3() {
		rigidBodies = new RigidBody[5];
		rigidBodies[0] = new RigidBody(Double.MAX_VALUE, new Vector2D(-2, 0), new Vector2D(0, 0), new Vector2D(0, 0), Double.MAX_VALUE, 10, 0, 0,
				new Circle(1.3));
		rigidBodies[1] = new RigidBody(Double.MAX_VALUE, new Vector2D(2, 0), new Vector2D(0, 0), new Vector2D(0, 0), Double.MAX_VALUE, 10, 1, 0,
				new Circle(1.3));
		rigidBodies[2] = new RigidBody(Double.MAX_VALUE, new Vector2D(0, 2), new Vector2D(0, 0), new Vector2D(0, 0), Double.MAX_VALUE, 10, 0, 0,
				new Circle(1.3));
		rigidBodies[3] = new RigidBody(Double.MAX_VALUE, new Vector2D(0,-2), new Vector2D(0, 0), new Vector2D(0, 0), Double.MAX_VALUE, 10, 0, 0,
				new Circle(1.3));
		rigidBodies[4] = new RigidBody(1, new Vector2D(0, 0), new Vector2D(1, 1.1), new Vector2D(0, 0), 0.5, 10, 0, 0,
				new Circle(0.1));
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
		mTVG.geometry.setUserArea(-2, 2, -2, 2);
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
