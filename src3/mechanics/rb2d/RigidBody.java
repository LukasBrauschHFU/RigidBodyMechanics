package mechanics.rb2d;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static mechanics.rb2d.BodyState.*;

import de.physolator.usr.*;
import de.physolator.usr.components.Vector2D;
import de.physolator.usr.components.VectorMath;
import de.physolator.usr.util.parameter.Parameter;
import de.physolator.usr.util.parameter.Slider;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Vector;

import javax.lang.model.type.IntersectionType;

import InclinePlain.Ball;
import InclinePlain.BallState;
import InclinePlain.Incline;
import InclinePlain.InclineVar;
import InclinePlain.collisionBallBallHandler;

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

	public double mur;
	public double mu;

	@Ignore
	public double g = -9.81;
	@Ignore
	public int color;
	@Ignore
	public Polygon polygon;
	@Ignore
	public double cornerNumber;
	@Ignore
	public double edgeLength;
	@Ignore
	public double edgeWidth;
	@Ignore
	public boolean interaction;
	@Ignore
	public boolean translation;
	@Ignore
	public boolean rotation;
	@Ignore 
	public boolean gravity;

	public BodyState state;
	public Vector2D Fg = new Vector2D();
	public Vector2D Fh = new Vector2D();
	public Vector2D Fr = new Vector2D();
	public Vector2D Fn = new Vector2D();
	public Vector2D Fres = new Vector2D();
	public Vector2D Fhr = new Vector2D();

	public int collisionCounter = 0;
	public Vector2D collisionEdgeBefore = new Vector2D();

	public boolean visible = true;

	public RigidBody(double m, double rx, double ry, double vx, double vy, double ax, double ay, 
			double I, double phi, double omega, double alpha, int cornerNumber, double edgeLength,
			boolean interaction, boolean translation, boolean rotation) {
		this.m =m;
		this.r.set(rx,ry);
		this.v.set(vx,vy);
		this.a.set(ax,ay);
		this.I=I;
		this.phi=phi;
		this.omega=omega;
		this.alpha=alpha;	
		this.polygon = new Polygon(new Vector2D(rx,ry), cornerNumber, edgeLength);
		this.interaction = interaction;
		this.translation = translation;
		this.rotation = rotation;
		if(translation && rotation) {
			state =BodyState.FLYING;
		}
	}
	
	public RigidBody(double m, double rx, double ry, double vx, double vy, double ax, double ay, 
			double I, double phi, double omega, double alpha, int cornerNumber, double edgeLength) {
		this.m =m;
		this.r.set(rx,ry);
		this.v.set(vx,vy);
		this.a.set(ax,ay);
		this.I=I;
		this.phi=phi;
		this.omega=omega;
		this.alpha=alpha;	
		this.polygon = new Polygon(new Vector2D(rx,ry), cornerNumber, edgeLength);
		this.state = FLYING;
		this.interaction = true;
		this.translation = true;
		this.rotation = true;
	}
	public RigidBody(double m, Vector2D r, Vector2D v, Vector2D a, 
			double I, double phi, double omega, double alpha, int cornerNumber, double edgeLength,
			boolean gravity, boolean interaction, boolean translation, boolean rotation) {
		this.m =m;
		this.r.set(r.x,r.y);
		this.v.set(v.x,v.y);
		this.a.set(a.x,a.y);
		this.I=I;
		this.phi=Math.toRadians(phi);
		this.omega=omega;
		this.alpha=alpha;	
		this.polygon = new Polygon(r, cornerNumber, edgeLength);
		this.gravity = gravity;
		this.interaction = interaction;
		this.translation = translation;
		this.rotation = rotation;
	}
	public RigidBody(double m, Vector2D r, Vector2D v, Vector2D a, 
			double I, double phi, double omega, double alpha, int cornerNumber, double edgeLength, double edgeWidth, 
			boolean gravity, boolean interaction, boolean translation, boolean rotation) {
		this.m =m;
		this.r.set(r.x,r.y);
		this.v.set(v.x,v.y);
		this.a.set(a.x,a.y);
		this.I=I;
		this.phi=Math.toRadians(phi);
		this.omega=omega;
		this.alpha=alpha;	
		this.polygon = new Polygon(r, cornerNumber, edgeLength, edgeWidth);
		this.gravity = gravity;
		this.interaction = interaction;
		this.translation = translation;
		this.rotation = rotation;
		if ((translation || rotation) && v.abs()>0) {
			this.state = BodyState.FLYING;
		}
	}
	public RigidBody(double m, Vector2D r, Vector2D v, Vector2D a, 
			double I, double phi, double omega, double alpha, int cornerNumber, double edgeLength) {
		this.m =m;
		this.r.set(r.x,r.y);
		this.v.set(v.x,v.y);
		this.a.set(a.x,a.y);
		this.I=I;
		this.phi=phi;
		this.omega=omega;
		this.alpha=alpha;	
		this.polygon = new Polygon(r, cornerNumber, edgeLength);
		this.state = FLYING;
		this.interaction = false;
		this.translation = false;
		this.rotation = false;
		
	}

	public void f(double t, double dt) {
		if(!translation) {
			v.set(0,0);
			a.set(0,0);
		}
		if(!rotation) {
			omega = 0;
			alpha = 0;
		}
		if(gravity) {
			a.y = g;
		}
		if(state == STOP) {
			v.set(0,0);
			a.set(0,0);
			omega = 0;
			alpha = 0;
		}

	}

	public void collisionWithRigidBodyCheck(AfterEventDescription aed, RigidBody r2) {
		if (this.in(r2) && (collisionCounter < 10)) {
			// if (this.in(r2)) {
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

	private Point2D.Double[] verticesToInertialSystem(Vertex[] vertices, double phi, Vector2D translation) {
		Point2D.Double[] newVertices = new Point2D.Double[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			newVertices[i] = vertexToInertialSystem(vertices[i], phi, translation);
		}
		return newVertices;
	}

	private Point2D.Double vertexToInertialSystem(Vertex vertex, double phi, Vector2D translation) {
		double x = translation.x + cos(phi) * vertex.position.x + sin(phi) * vertex.position.y;
		double y = translation.y + sin(phi) * vertex.position.x - cos(phi) * vertex.position.y;
		return new Point2D.Double(x, y);
	}

	public void RbCollisionWidthInclineCheck(AfterEventDescription aed, Incline il) {
		
		if (state == BodyState.FLYING) {
			if ((r.x >= il.start.x && r.x <= il.end.x) || (r.x <= il.start.x && r.x >= il.end.x))
				if (il.ilVar == InclineVar.PLANE) {
					if ((r.x - distanceToLine.x <= il.start.x && r.x - distanceToLine.x >= il.end.x)
							|| (r.x - distanceToLine.x >= il.start.x && r.x - distanceToLine.x <= il.end.x)) {
						Vector2D pointOnEdge = new Vector2D();
						Line2D.Double closestEdge;
						if (parallelToInclineCheck(il, this, pointOnEdge)) {
							setDistanceToLine(il);
							if (distanceToLine.abs() - VectorMath.sub(r, pointOnEdge).abs() <= 0) {
								Runnable handler = new rbSlidesOnInclineHandler(this, il);
								aed.reportEvent(handler, "rb touching", this.toString(), il.toString());
							}
						} else {
							Vector2D closestVertex = getClosestVertex(this, il);
							double distance = VectorMath.perpendicular(r, il.start, il.direction).abs();

							double yDistanceCenterVertex = r.y - closestVertex.y;
							if (distance <= yDistanceCenterVertex) {
								Runnable handler = new collisionRbInclineHandler(this, il, closestVertex); //Exzentrische ST��e einf�gen
								aed.reportEvent(handler, "rb touching", this.toString(), il.toString());
							}
						}
					}
				} else {
					double inclineAngle = VectorMath.angle(new Vector2D(1, 0), il.direction);
					Point2D.Double[] vertices = verticesToInertialSystem(polygon.vertices, this.phi, this.r);
					Line2D.Double[] edges = getEdges(vertices);
					
					for (int i = 0; i < vertices.length; i++) {
						System.out.println(edges[i]);
						Vector2D edgeStart = new Vector2D(edges[i].x1, edges[i].y1);
						Vector2D edgeEnd = new Vector2D(edges[i].x2, edges[i].y2);
						Vector2D edgeDirection = VectorMath.sub(edgeEnd, edgeStart);
						double edgeAngle = VectorMath.angle(new Vector2D(1, 0), edgeDirection) - Math.PI;

						if (il.ilVar == InclineVar.VAR2 || il.ilVar == InclineVar.VAR1) {
							edgeAngle = -edgeAngle;
						}
						if (il.ilVar == InclineVar.VAR3 || il.ilVar == InclineVar.VAR4
								|| il.ilVar == InclineVar.PLANE) {
							edgeAngle = edgeAngle + Math.PI;
						}

						if (Math.round(edgeAngle * 10e5) * 10e-5 == Math.round(inclineAngle * 10e5) * 10e-5) {
							Vector2D vertexVector = new Vector2D(edges[i].x1, edges[i].y1);
							Vector2D pointOnEdge = VectorMath.footOfPerpendicular(r, vertexVector, edgeDirection);
							System.out.println("POE "+pointOnEdge);

							setDistanceToLine(il);
							System.out.println(distanceToLine.abs());
							if (distanceToLine.abs() - VectorMath.sub(r, pointOnEdge).abs() <= 0) {
								Runnable handler = new rbSlidesOnInclineHandler(this, il);
								aed.reportEvent(handler, "rb slides", this.toString(), il.toString());
							}
						}
					}
				}
		}

	}

	public void RbCollisionWidthBallCheck(AfterEventDescription aed, Ball ball) {
		Point2D.Double[] vertices = verticesToInertialSystem(polygon.vertices, this.phi, this.r);
		Line2D.Double[] edges = getEdges(vertices);
//		Vector2D[] pointOnEdge;
		double smallestDistance = Double.MAX_VALUE;
		double smallestDistanceBefore;
		int edgeNumber = 0;
		Vector2D collisionPoint = null;
		for (int i = 0; i < edges.length; i++) {
			Vector2D edgeStart = new Vector2D(edges[i].x1, edges[i].y1);
			Vector2D edgeEnd = new Vector2D(edges[i].x2, edges[i].y2);
			Vector2D edgeDirection = VectorMath.sub(edgeEnd, edgeStart);

			Vector2D distanceEdgeCircle = VectorMath.perpendicular(ball.r, edgeStart, edgeDirection);

			if (distanceEdgeCircle.abs() < smallestDistance) {
				smallestDistanceBefore = smallestDistance;
				edgeNumber = i;
				smallestDistance = distanceEdgeCircle.abs() + ball.rad;
				if (smallestDistance <= 0 || smallestDistanceBefore <= smallestDistance) {
					collisionPoint = VectorMath.footOfPerpendicular(ball.r, edgeStart, edgeDirection);
				}
			}
		}

//			if (vectorRbBall.abs() - ball.rad <= 0) {
//				System.out.println("touch");
//				Runnable handler = new RbCollisionWidthBallHandler(this, ball, impactpoint(ball));
//				aed.reportEvent(handler, "rb touching", this.toString(), ball.toString());
//			}
//		}
	}

	public Vector2D distanceToLine = new Vector2D();

	public void setDistanceToLine(Incline il) {
		distanceToLine.set(VectorMath.perpendicular(this.r, il.start, il.direction));
	}

	public Vector2D rotateVector2D(Vector2D r, double rot) {
		return new Vector2D(r.x * cos(rot) - r.y * sin(rot), r.x * sin(rot) + r.y * cos(rot));
	}

	public Line2D.Double inliceToLineDouble(Incline il) {
		return new Line2D.Double(il.start.x, il.start.y, il.end.x, il.end.y);
	}

	private boolean parallelToInclineCheck(Incline il, RigidBody rb, Vector2D pointOnEdge) {
		double inclineAngle = VectorMath.angle(new Vector2D(1, 0), il.direction);
		Point2D.Double[] vertices = verticesToInertialSystem(polygon.vertices, this.phi, this.r);
		Line2D.Double[] edges = getEdges(vertices);
		for (int i = 0; i < vertices.length; i++) {
			Vector2D edgeStart = new Vector2D(edges[i].x1, edges[i].y1);
			Vector2D edgeEnd = new Vector2D(edges[i].x2, edges[i].y2);
			Vector2D edgeDirection = VectorMath.sub(edgeEnd, edgeStart);
			double edgeAngle = VectorMath.angle(new Vector2D(1, 0), edgeDirection) - Math.PI;

			if (il.ilVar == InclineVar.VAR2 || il.ilVar == InclineVar.VAR1) {
				edgeAngle = -edgeAngle;
			}
			if (il.ilVar == InclineVar.VAR3 || il.ilVar == InclineVar.VAR4 || il.ilVar == InclineVar.PLANE) {
				edgeAngle = edgeAngle + Math.PI;
			}

			if (Math.round(edgeAngle * 10e5) * 10e-5 == Math.round(inclineAngle * 10e5) * 10e-5) {
				Vector2D vertexVector = new Vector2D(edges[i].x1, edges[i].y1);

				pointOnEdge = VectorMath.footOfPerpendicular(r, vertexVector, edgeDirection);
				return true;
			}
		}
		return false;
	}

	private Vector2D getClosestVertex(RigidBody rb, Incline il) {
		Vector2D closestVertex = null;
		Point2D.Double[] vertices = verticesToInertialSystem(polygon.vertices, phi, r);
		double smallestDistance = Double.MAX_VALUE;
		for (int i = 0; i < vertices.length; i++) {
			Vector2D vertexToVector = new Vector2D(vertices[i].x, vertices[i].y);
			double distance = (VectorMath.perpendicular(vertexToVector, il.start, il.direction)).abs();
			if (distance < smallestDistance) {
				smallestDistance = distance;
				closestVertex = new Vector2D(vertices[i].x, vertices[i].y);
			}
		}

		return closestVertex;
	}

	private Line2D.Double getClosestEdge(Incline il) {
		Point2D.Double[] vertices = verticesToInertialSystem(polygon.vertices, this.phi, this.r);
		Line2D.Double[] edges = getEdges(vertices);
		Vector2D closestVertex = getClosestVertex(this, il);
		Point2D.Double closestVertexPoint = new Point2D.Double(closestVertex.x, closestVertex.y);
		for (int i = 0; i < vertices.length; i++) {
			Vector2D edgeStart = new Vector2D(edges[i].x1, edges[i].y1);
			Vector2D edgeEnd = new Vector2D(edges[i].x2, edges[i].y2);
			Vector2D edgeDirection = VectorMath.sub(edgeEnd, edgeStart);
			if (Line2D.ptSegDist(edgeStart.x, edgeStart.y, edgeEnd.x, edgeEnd.y, closestVertex.x, closestVertex.y) == 0)
				if (VectorMath.angle(edgeDirection, il.direction) == 0 || VectorMath.angle(edgeDirection, il.direction) == Math.PI)
					return new Line2D.Double(edgeStart.x, edgeStart.y, edgeEnd.x, edgeEnd.y);
		}
		return null;
	}

	public void RbStartsFallingCheck(AfterEventDescription aed, Incline il) {
		if (state == SLIDING) {
			Line2D.Double closestEdge = getClosestEdge(il);
			if ((r.x < il.start.x && r.x < il.end.x) || (r.x > il.start.x && r.x > il.end.x)) {
				Runnable handler = new RsStartsFallingHandler(this, il, closestEdge);
				aed.reportEvent(handler, "rb starts falling", this.toString(), il.toString());
			}
		}
	}
}