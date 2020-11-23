package InclinePlain;

public class ballStopsRollingHandler implements Runnable {
	public Ball ball;

	public ballStopsRollingHandler(Ball ball) {
		this.ball = ball;
	}

	@Override
	public void run() {
		ball.v.set(0, 0);
		ball.a.set(0, 0);
		ball.omega = 0;
		ball.alpha = 0;
		
		ball.ballState = BallState.STOP;
	}

}
