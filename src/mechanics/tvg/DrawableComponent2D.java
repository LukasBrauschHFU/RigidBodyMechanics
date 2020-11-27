package mechanics.tvg;


public abstract class DrawableComponent2D {
	
	protected MechanicsTVG mTVG;
	protected String name;

	public DrawableComponent2D(MechanicsTVG mTVG, String name) {
		this.mTVG = mTVG;
		this.name = name;
	}

	
	public abstract void paint();



}
