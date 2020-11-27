package mechanics.rb2d;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import InclinePlain.Incline;
import de.physolator.usr.components.Vector2D;

public class Impactpoint {
	public Vector2D impactPoint;
	public Vector2D impactEdge;
		
	public Impactpoint(Vector2D impactPoint, Vector2D impactEdge) {
		this.impactPoint = impactPoint;
		this.impactEdge = impactEdge;
	}
	
	public Impactpoint(Vector2D impactPoint) {
		this.impactPoint = impactPoint;
	}

	public Impactpoint(Vector2D point, Incline tangente) {
		// TODO Auto-generated constructor stub
	}
}
