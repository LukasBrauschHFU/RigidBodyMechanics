package InclinePlain;

import de.physolator.usr.components.Vector2D;
import mechanics.rb2d.Impactpoint;

public class ballOnInclineEdgeHandler implements Runnable {
	public Ball ball;
	public Impactpoint ip;

	public ballOnInclineEdgeHandler(Ball ball, Impactpoint ip) {
		this.ball = ball;
		this.ip = ip;
	}
	public ballOnInclineEdgeHandler(Ball ball) {
		this.ball = ball;
	}
	@Override
	public void run() {
		ball.a.y = ball.g;
		ball.ballState = BallState.FLYING;
	}
}
