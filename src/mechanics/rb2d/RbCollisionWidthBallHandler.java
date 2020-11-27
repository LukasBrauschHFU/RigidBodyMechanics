package mechanics.rb2d;

import InclinePlain.Ball;
import InclinePlain.BallState;

public class RbCollisionWidthBallHandler implements Runnable {

	RigidBody rb;
	Ball ball;
	Impactpoint ip;

	public RbCollisionWidthBallHandler(RigidBody rb, Ball ball, Impactpoint ip) {
		this.rb = rb;
		this.ball = ball;
		this.ip = ip;
	}

	public void run() {
		
	}

}
