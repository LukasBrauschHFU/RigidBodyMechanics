package mechanics.rb2d;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import de.physolator.usr.*;
import de.physolator.usr.components.Vector2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class RigidBody {

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

	@V(unit = "kg*m/s")
	public double E_kin;
	@V(unit = "kg*m/s")
	public double E_rot;
	@V(unit = "kg*m/s")
	public double E_rb;

	@Ignore
	public int color;
	@Ignore
	public Polygon polygon;
	@Ignore
	public BodyState state;
	@Ignore
	public boolean visible = true;

	public RigidBody(double m, Vector2D r, Vector2D v, Vector2D a, double I, double phi, double omega, double alpha,
			Polygon shape) {
		this.m = m;
		this.r = r;
		this.v = v;
		this.a = a;
		this.I = I;
		this.phi = phi;
		this.omega = omega;
		this.alpha = alpha;
		this.polygon = shape;
		this.state = BodyState.FLYING;
	}

	public void f(double t, double dt) {
		E_kin = 0.5 * m * v.abs() * v.abs();
		E_rot = 0.5 * I * omega * omega;
		E_rb = E_kin + E_rot;

	}

	public void collisionWithRigidBodyCheck(AfterEventDescription aed, RigidBody r2) {
		if (this.in(r2)) {
			Runnable handler = new RigidBodyCollisionHandler(this, r2, impactpoint(r2));
			aed.reportEvent(handler, "collision of rigidbodies: ", this.toString(), r2.toString());
		}
	}

	public Impactpoint impactpoint(RigidBody r2) {
		RigidBody r1 = this;

		Point2D.Double[] vertices1 = verticesToInertialSystem(r1.polygon.vertices, r1.phi, r1.r);
		Point2D.Double[] vertices2 = verticesToInertialSystem(r2.polygon.vertices, r2.phi, r2.r);

		Line2D.Double[] edges1 = getEdges(vertices1);
		Line2D.Double[] edges2 = getEdges(vertices2);

		Point2D.Double impactpoint = vertices1[0];
		Line2D.Double impactedge = edges2[0];
		double smallestDistance = Double.MAX_VALUE;
		for (int i = 0; i < edges1.length; i++) {
			for (int j = 0; j < vertices2.length; j++) {
				double distance = edges1[i].ptSegDist(vertices2[j]);
				if (distance < smallestDistance) {
					smallestDistance = distance;
					impactpoint = vertices2[j];
					impactedge = edges1[i];
				}
			}
		}

		for (int i = 0; i < edges2.length; i++) {
			for (int j = 0; j < vertices1.length; j++) {
				double distance = edges2[i].ptSegDist(vertices1[j]);
				if (distance < smallestDistance) {
					smallestDistance = distance;
					impactpoint = vertices1[j];
					impactedge = edges2[i];

				}
			}
		}

		Vector2D p = new Vector2D(impactpoint.x, impactpoint.y);
		Vector2D e = new Vector2D(impactedge.x2 - impactedge.x1, impactedge.y2 - impactedge.y1);
		return new Impactpoint(p, e);
	}

	public boolean in(RigidBody r2) {
		Point2D.Double[] vertices1 = verticesToInertialSystem(polygon.vertices, this.phi, this.r);
		Point2D.Double[] vertices2 = verticesToInertialSystem(r2.polygon.vertices, r2.phi, r2.r);

		Line2D.Double[] edges1 = getEdges(vertices1);
		Line2D.Double[] edges2 = getEdges(vertices2);

		for (Line2D.Double a : edges1) {
			for (Line2D.Double b : edges2) {
				if (a.intersectsLine(b)) {
					return true;
				}
			}
		}
		return false;
	}

	private Line2D.Double[] getEdges(Point2D.Double[] points) {
		Line2D.Double[] edges = new Line2D.Double[points.length];
		edges[0] = new Line2D.Double(points[points.length - 1], points[0]);
		for (int i = 1; i < points.length; i++) {
			edges[i] = new Line2D.Double(points[i], points[i - 1]);
		}
		return edges;
	}

	private Point2D.Double[] verticesToInertialSystem(Vector2D[] vertices, double phi, Vector2D translation) {
		Point2D.Double[] newVertices = new Point2D.Double[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			newVertices[i] = vertexToInertialSystem(vertices[i], phi, translation);
		}
		return newVertices;
	}

	private Point2D.Double vertexToInertialSystem(Vector2D vertex, double phi, Vector2D translation) {
		double x = translation.x + cos(phi) * vertex.x + sin(phi) * vertex.y;
		double y = translation.y + sin(phi) * vertex.x - cos(phi) * vertex.y;
		return new Point2D.Double(x, y);
	}

	public Vector2D rotateVector2D(Vector2D r, double rot) {
		return new Vector2D(r.x * cos(rot) - r.y * sin(rot), r.x * sin(rot) + r.y * cos(rot));
	}
}
