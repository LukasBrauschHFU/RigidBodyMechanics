package InclinePlain;

import java.awt.event.KeyEvent;

import Helper.ObjectPosition;
import de.physolator.usr.components.Vector2D;
import de.physolator.usr.components.VectorMath;
import de.physolator.usr.tvg.TVG;
import de.physolator.usr.tvg.events.EventHandler;
import de.physolator.usr.tvg.events.KeyEventTable;

public class Incline {
	public Vector2D start = new Vector2D();
	public Vector2D end = new Vector2D();
	public double a;
	public double b;
	public double c;
	public double alpha;
	public InclineVar ilVar;
	double startX, startY, endX, endY;
	public Vector2D direction;
	public ObjectPosition position;
	public double m = 0;
	protected Ball ball;
	public boolean interaction = false;
	public boolean translation = false;
	public boolean rotation = false;
	
	public Incline(Vector2D start, Vector2D end) {
		this.start = start;
		this.end = end;
		this.direction = VectorMath.sub(end, start);
		this.ball = new Ball(10e100, start, 0.01);
		this.ilVar = getInclineVar(start, end);
		if (ilVar == InclineVar.PLANE)
			alpha = 0;
		if (ilVar == InclineVar.VAR1) {
			a = start.y - end.y;
			b = end.x - start.x;
		}
		if (ilVar == InclineVar.VAR2) {
			a = end.y - start.y;
			b = end.x - start.x;
		}
		if (ilVar == InclineVar.VAR3) {
			a = end.y - start.y;
			b = start.x - end.x;
		}
		if (ilVar == InclineVar.VAR4) {
			a = start.y - end.y;
			b = start.x - end.x;
		}
	}
	
	public Incline(Vector2D start, double angle, double length) {
		this.start = start;
		this.end = new Vector2D(Math.cos(Math.toRadians(angle)) * length + start.x,
				Math.sin(Math.toRadians(angle)) * length + start.y);
		this.direction = VectorMath.sub(end, start);
		System.out.println(start);
		System.out.println(end);
		this.ilVar = getInclineVar(start, end);
		this.alpha = angle;
		if(position == ObjectPosition.FIXED) {
			m = 10e100;
		}
		if (ilVar == InclineVar.PLANE)
			alpha = 0;
		if (ilVar == InclineVar.VAR1) {
			a = start.y - end.y;
			b = end.x - start.x;
		}
		if (ilVar == InclineVar.VAR2) {
			a = end.y - start.y;
			b = end.x - start.x;
		}
		if (ilVar == InclineVar.VAR3) {
			a = end.y - start.y;
			b = start.x - end.x;
		}
		if (ilVar == InclineVar.VAR4) {
			a = start.y - end.y;
			b = start.x - end.x;
		}
	
	}

	public Incline(Vector2D start, double angle, double length, boolean interaction, boolean translation, boolean rotation) {
		this.start = start;
		this.end = new Vector2D(Math.cos(Math.toRadians(angle)) * length + start.x,
				Math.sin(Math.toRadians(angle)) * length + start.y);
		this.direction = VectorMath.sub(end, start);
		System.out.println(start);
		System.out.println(end);
		this.ilVar = getInclineVar(start, end);
		this.alpha = angle;
		if(position == ObjectPosition.FIXED) {
			m = 10e100;
		}
		if (ilVar == InclineVar.PLANE)
			alpha = 0;
		if (ilVar == InclineVar.VAR1) {
			a = start.y - end.y;
			b = end.x - start.x;
		}
		if (ilVar == InclineVar.VAR2) {
			a = end.y - start.y;
			b = end.x - start.x;
		}
		if (ilVar == InclineVar.VAR3) {
			a = end.y - start.y;
			b = start.x - end.x;
		}
		if (ilVar == InclineVar.VAR4) {
			a = start.y - end.y;
			b = start.x - end.x;
		}
		this.interaction = interaction;
		this.translation = translation;
		this.rotation = rotation;
		
	}

	public Incline(double startX, double startY, double endX, double endY, boolean interaction, boolean translation, boolean rotation) {
		this.start.set(startX, startY);
		this.end.set(endX, endY);
		this.direction = VectorMath.sub(end, start);
		this.ilVar = getInclineVar(start, end);
		this.position = position;
		if(position == ObjectPosition.FIXED) {
			m = 10e100;
		}
		if (ilVar == InclineVar.PLANE)
			alpha = 0;
		if (ilVar == InclineVar.VAR1) {
			a = start.y - end.y;
			b = end.x - start.x;
		}
		if (ilVar == InclineVar.VAR2) {
			a = end.y - start.y;
			b = end.x - start.x;
		}
		if (ilVar == InclineVar.VAR3) {
			a = end.y - start.y;
			b = start.x - end.x;
		}
		if (ilVar == InclineVar.VAR4) {
			a = start.y - end.y;
			b = start.x - end.x;
		}
		this.interaction = interaction;
		this.translation = translation;
		this.rotation = rotation;
	}
	
	public InclineVar getInclineVar(Vector2D start, Vector2D end) {
		if (start.x < end.x && start.y > end.y)
			return InclineVar.VAR1;
		if (start.x < end.x && start.y < end.y)
			return InclineVar.VAR2;
		if (start.x > end.x && start.y < end.y)
			this.ilVar = InclineVar.VAR3;
		if (start.x > end.x && start.y > end.y)
			return InclineVar.VAR4;
		if (start.y == end.y)
			return InclineVar.PLANE;
		return null;
	}

	public double getInclineAngle(Vector2D start, Vector2D end) {
		if (ilVar == InclineVar.PLANE)
			alpha = 0;
		if (ilVar == InclineVar.VAR1) {
			a = start.y - end.y;
			b = end.x - start.x;
		}
		if (ilVar == InclineVar.VAR2) {
			a = end.y - start.y;
			b = end.x - start.x;
		}
		if (ilVar == InclineVar.VAR3) {
			a = end.y - start.y;
			b = start.x - end.x;
		}
		if (ilVar == InclineVar.VAR4) {
			a = start.y - end.y;
			b = start.x - end.x;
		}
		return Math.atan(a / b);
	}
	
	public void paint(TVG tvg, Vector2D start, Vector2D end) {
		tvg.drawLine(start, end);
	}
	
	

	
}
