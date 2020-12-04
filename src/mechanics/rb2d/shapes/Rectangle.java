package mechanics.rb2d.shapes;

import de.physolator.usr.components.Vector2D;

public class Rectangle extends Quad {
	public Rectangle() {
		this(1);
	}

	public Rectangle(double length) {
		this(length, 1);
	}

	public Rectangle(double length, double height) {
		super(new Vector2D(-length / 2, height / 2), new Vector2D(length / 2, height / 2),
				new Vector2D(length / 2, -height / 2), new Vector2D(-length / 2, -height / 2));
	}
}
