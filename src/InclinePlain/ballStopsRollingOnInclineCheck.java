package InclinePlain;

public class ballStopsRollingOnInclineCheck implements Runnable {
	private Ball ball;
	
	public ballStopsRollingOnInclineCheck(Ball ball) {
		this.ball = ball;
	}
	@Override
	public void run() {
		ball.a.set(0, 0);
		ball.v.set(0, 0);
		ball.Fr.set(0, 0);
		ball.omega = 0;
		ball.alpha = 0;
		ball.ballDir = BallDirection.NONE;
		ball.ballState = BallState.STOP;
	}

}
