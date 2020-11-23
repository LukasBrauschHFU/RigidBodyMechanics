package InclinePlain;

import de.physolator.usr.components.Vector2D;
import de.physolator.usr.components.VectorMath;

public class ballFallsHandler implements Runnable {
	private Ball ball;
	private Incline il;

	public ballFallsHandler(Ball ball, Incline il) {
		this.ball = ball;
		this.il = il;
	}
	public ballFallsHandler(Ball ball) {
		this.ball = ball;
	}

	public void run() {
		ball.ballState=BallState.FLYING;
		
	}
}
