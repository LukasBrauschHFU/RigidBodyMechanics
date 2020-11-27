package mechanics.tvg;

import InclinePlain.Ball;

public class BallDC2D extends DrawableComponent2D{
	
	public Ball b;
	
	public BallDC2D(MechanicsTVG mTVG, String name, Ball b){
		super(mTVG, name);
		this.b = b;
	}
	
	@Override
	public void paint() {
		mTVG.style.strokeWidth = 2;
		b.paint(mTVG, b.r, b.rad);
	}
}
