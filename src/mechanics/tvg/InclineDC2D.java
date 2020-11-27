package mechanics.tvg;

import java.awt.event.KeyEvent;

import InclinePlain.Incline;
import de.physolator.usr.tvg.events.EventHandler;
import de.physolator.usr.tvg.events.KeyEventTable;

public class InclineDC2D extends DrawableComponent2D{
	public Incline il;

	public InclineDC2D(MechanicsTVG mTVG, String name, Incline il){
		super(mTVG, name);
		this.il = il;
	}

	@Override
	public void paint() {
		mTVG.style.strokeWidth = 2;
		il.paint(mTVG, il.start, il.end);
//		il.paint(mTVG, il.start, il.alpha, il.c);
	}
	

}

