package mechanics.tvg;

import de.physolator.usr.StructureElement;

public class SP2D {
	
	public StructureElement r1x, r1y, r2x, r2y, Fx, Fy, broken;
	
	public String name;
	
	public SP2D(StructureElement r1x, StructureElement r1y, StructureElement r2x, StructureElement r2y,
			StructureElement Fx, StructureElement Fy, StructureElement broken, String name) {
		super();
		this.r1x = r1x;
		this.r1y = r1y;
		this.r2x = r2x;
		this.r2y = r2y;
		this.Fx = Fx;
		this.Fy = Fy;
		this.broken = broken;
		this.name = name;
	}
		
}
