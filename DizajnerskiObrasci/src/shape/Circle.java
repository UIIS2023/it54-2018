package shape;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends SurfaceShape {
	private static final long serialVersionUID = 1L;
	
	private Point center;
	private int radius;

	public Circle() {
		//
	}
	
	public Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;
	}

	public Circle(Point center, int radius, Color color, Color innerColor) {
		this(center, radius);
		
		setColor(color);
		setInnerColor(innerColor);
	}
	
	public Circle(Point center, int radius, Color color, Color innerColor, boolean isSelected) {
		this(center, radius);
		
		setColor(color);
		setInnerColor(innerColor);
		setSelected(isSelected);
	}
	
	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		g.fillOval(center.getX() - radius + 1, center.getY() - radius + 1, radius * 2 - 2, radius * 2 - 2);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawOval(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2);
		fill(g);
		
		if (isSelected()) {
			selected(g);
		}
	}
	
	@Override
	public boolean contains(int x, int y) {
		return center.distance(x, y) <= radius;
	}
	
	@Override
	public void selected(Graphics g) {
		Color tempColor = getColor();
		
		g.setColor(Color.BLUE);
		g.drawRect(center.getX() - 3, center.getY() - 3, 6, 6);
		g.drawRect(center.getX() - radius - 3, center.getY() - 3, 6, 6);
		g.drawRect(center.getX() + radius - 3, center.getY() - 3, 6, 6);
		g.drawRect(center.getX() - 3, center.getY() - radius - 3, 6, 6);
		g.drawRect(center.getX() - 3, center.getY() + radius - 3, 6, 6);
		g.setColor(tempColor);
	}
	
	@Override
	public boolean compareTo(Shape s) {
		return (s instanceof Circle) ? ((Circle) s).getCenter().compareTo(getCenter()) && ((Circle) s).getRadius() == getRadius() : false;
	}
	
	@Override
	public String toString() {
		return "CIRCLE : x=" + center.getX() + " y=" + center.getY() + " radius=" + radius + " outer(" + +getColor().getRed() + "," + getColor().getGreen() + "," + getColor().getBlue() + ") inner(" + getInnerColor().getRed() + "," + getInnerColor().getGreen() + "," + getInnerColor().getBlue() + ") selected=" + isSelected(); 
	}
	
	@Override
	public Circle clone() {
		return new Circle(center, radius, getColor(), getInnerColor());
	}
}
