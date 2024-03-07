package shape;

import java.awt.Color;
import java.awt.Graphics;

public class Point extends Shape {
	private static final long serialVersionUID = 1L;
	
	private int x;
	private int y;
	
	public Point() {
		//
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(int x, int y, Color color) {
		this(x, y);
		
		setColor(color);
	}
	
	public Point(int x, int y, Color color, boolean isSelected) {
		this(x, y);
		
		setColor(color);
		setSelected(isSelected);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawLine(x - 2, y, x + 2, y);
		g.drawLine(x, y - 2, x, y + 2);
		
		if (isSelected()) {
			selected(g);
		}
	}
	
	@Override
	public boolean contains(int x, int y) {
		return distance(x, y) <= 3;
	}

	@Override
	public void selected(Graphics g) {
		Color tempColor = getColor();
		
		g.setColor(Color.BLUE);
		g.drawRect(this.x - 3, this.y - 3, 6, 6);
		g.setColor(tempColor);
	}
	
	@Override
	public boolean compareTo(Shape s) {
		return (s instanceof Point) ? ((Point) s).getX() == getX() && ((Point) s).getY() == getY() : false;
	}
	
	@Override
	public String toString() {
		return "POINT : x=" + x + " y=" + y + " color(" + getColor().getRed() + "," + getColor().getGreen() + "," + getColor().getBlue() + ") selected=" + isSelected();
	}
	
	@Override
	public Point clone() {
		return new Point(x, y, getColor());
	}
	
	public double distance(int x, int y) {
		double dx = this.x - x;
		double dy = this.y - y;
		double d = Math.sqrt(dx * dx + dy * dy);
		
		return d;
	}
}
