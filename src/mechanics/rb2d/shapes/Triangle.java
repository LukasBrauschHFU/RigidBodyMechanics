package mechanics.rb2d.shapes;

import de.physolator.usr.components.Vector2D;

public class Triangle extends Polygon {
	

	public Triangle() {
		this(1,1);
	}
	
	public Triangle(double length) {
		this(length, (Math.sqrt(3)/2)*length);
	}

	public Triangle(double groundEdgeLength, double heigth) {
		super(new Vector2D[] { new Vector2D(-groundEdgeLength / 2, heigth / 2), new Vector2D(groundEdgeLength / 2, heigth / 2),
				new Vector2D(0, -heigth / 2) });
	}
}
