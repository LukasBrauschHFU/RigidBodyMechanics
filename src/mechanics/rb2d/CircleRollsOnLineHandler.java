package mechanics.rb2d;


import static java.lang.Math.signum;

import de.physolator.usr.V;
import de.physolator.usr.components.Vector2D;

public class CircleRollsOnLineHandler implements Runnable{
	
	private RigidBody circle;
	private Vector2D il;
	private Vector2D ip;
	
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
	
	public CircleRollsOnLineHandler(RigidBody circle, Impactpoint ip) {
		this.circle = circle;
		this.il = ip.impactEdge;
		this.ip = ip.impactPoint;
	}
	
	public void run() {
		Fg.y = circle.m * -9.81;
		Fg.x = 0;
		circle.state = BodyState.ROLLING;
		System.out.println("IE "+il);
		
		Fr.x = -9.81 * circle.m * circle.friction *(signum(circle.v.x));
		circle.v.y = 0;
		circle.a.set(Fr.x / circle.m, 0);
		}
	}
		
		
//		if (il == null) {
////			if (il.ilVar == InclineVar.VAR1)
////				rollVar1();
////			if (il.ilVar == InclineVar.VAR2)
////				rollVar2();
////			if (il.ilVar == InclineVar.VAR3)
////				rollVar3();
////			if (il.ilVar == InclineVar.VAR4)
////				rollVar4();
//			if (il.ilVar == InclineVar.PLANE)
//				rollPlane();
//		} else {
//			System.out.println("nicht null");
//		}
//	}
	
//	private void rollPlane() {
//		System.out.println("rll");
//		Fr.x = -9.81 * circle.m * circle.friction *(signum(circle.v.x));
//		circle.v.y = 0;
//		circle.a.set(Fr.x / circle.m, 0);
////		if ((circle.direction == BodyDirection.LEFT && circle.v.x >= 0)
////				|| (circle.direction == BodyDirection.RIGHT && circle.v.x <= 0)) {
////			circle.a.set(0, 0);
////			circle.v.set(0, 0);
////			Fr.set(0, 0);
////			circle.omega = 0;
////			circle.alpha = 0;
////			circle.direction = BodyDirection.NONE;
////			circle.state = BodyState.STOP;
////		}
//	}
//}
