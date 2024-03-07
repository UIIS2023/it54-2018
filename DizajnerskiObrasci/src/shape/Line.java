package shape;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape {
	private static final long serialVersionUID = 1L;
	
	private Point startPoint;
	private Point endPoint;
	
	public Line() {
		//
	}
	
	public Line(Point startPoint, Point endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	
	public Line(Point startPoint, Point endPoint, Color color) {
		this(startPoint, endPoint);
		
		setColor(color);
	}
	
	public Line(Point startPoint, Point endPoint, Color color, boolean isSelected) {
		this(startPoint, endPoint);
		
		setColor(color);
		setSelected(isSelected);
	}
	
	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
		
		if (isSelected()) {
			selected(g);
		}
	}
	
	@Override
	public boolean contains(int x, int y) {
		return ((startPoint.distance(x, y) + (endPoint.distance(x, y)) - length()) <= 0.05);
	}
	
	@Override
	public void selected(Graphics g) {
		Color tempColor = getColor();
		
		g.setColor(Color.BLUE);
		g.drawRect(startPoint.getX() - 3, startPoint.getY() - 3 , 6, 6);
		g.drawRect(endPoint.getX() - 3, endPoint.getY() - 3, 6, 6);
		g.drawRect(middleOfLine().getX() - 3, middleOfLine().getY() - 3, 6, 6);
		g.setColor(tempColor);
	}
	
	@Override
	public boolean compareTo(Shape s) {
		return (s instanceof Line) ? ((Line) s).getStartPoint().compareTo(getStartPoint()) && ((Line) s).getEndPoint().compareTo(getEndPoint()) : false;
	}
	
	@Override
	public String toString() {
		return "LINE : x1=" + startPoint.getX() + " y1=" + startPoint.getY() + " x2=" + endPoint.getX() + " y2=" + endPoint.getY() + " color(" + getColor().getRed() + "," + getColor().getGreen() + "," + getColor().getBlue() + ") selected=" + isSelected();
	}
	
	@Override
	public Line clone() {
		return new Line(startPoint, endPoint, getColor());
	}
	
	public double length() {
		return startPoint.distance(endPoint.getX(), endPoint.getY());
	}
	
	public Point middleOfLine() {
		int middleByX = (startPoint.getX() + endPoint.getX()) / 2;
		int middleByY = (startPoint.getY() + endPoint.getY()) / 2;
		Point p = new Point(middleByX, middleByY, Color.BLACK);
		
		return p;
	}
}
