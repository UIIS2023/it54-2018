package shape;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends SurfaceShape {
	private static final long serialVersionUID = 1L;
	
	private Point upperLeftPoint;
	private int height;
	private int width;
	
	public Rectangle() {
		//
	}
	
	public Rectangle(Point upperLeftPoint, int height, int width) {
		this.upperLeftPoint = upperLeftPoint;
		this.height = height;
		this.width = width;
	}
	
	public Rectangle(Point upperLeftPoint, int height, int width, Color color, Color innerColor) {
		this(upperLeftPoint, height, width);
		
		setColor(color);
		setInnerColor(innerColor);
	}
	
	public Rectangle(Point upperLeftPoint, int height, int width, Color color, Color innerColor, boolean isSelected) {
		this(upperLeftPoint, height, width);
		
		setColor(color);
		setInnerColor(innerColor);
		setSelected(isSelected);
	}
	
	public Point getUpperLeftPoint() {
		return upperLeftPoint;
	}

	public void setUpperLeftPoint(Point upperLeftPoint) {
		this.upperLeftPoint = upperLeftPoint;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		g.fillRect(upperLeftPoint.getX() + 1, upperLeftPoint.getY() + 1, width - 1, height - 1);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawRect(upperLeftPoint.getX(), upperLeftPoint.getY(), width, height);
		fill(g);
		
		if (isSelected()) {
			selected(g);
		}
	}
	
	@Override
	public boolean contains(int x, int y) {
		return (upperLeftPoint.getX() <= x && getUpperLeftPoint().getY() <= y && x <= getUpperLeftPoint().getX() + width && y <= getUpperLeftPoint().getY() + height);
	}
	
	@Override
	public void selected(Graphics g) {
		Color tempColor = getColor();
		
		g.setColor(Color.BLUE);
		g.drawRect(upperLeftPoint.getX() - 3, upperLeftPoint.getY() - 3, 6, 6);
		g.drawRect(upperLeftPoint.getX() + width - 3, upperLeftPoint.getY() - 3, 6, 6);
		g.drawRect(upperLeftPoint.getX() - 3, upperLeftPoint.getY() + height - 3, 6, 6);
		g.drawRect(upperLeftPoint.getX() + width - 3, upperLeftPoint.getY() + height - 3, 6, 6);
		g.setColor(tempColor);
	}
	
	@Override
	public boolean compareTo(Shape s) {
		return (s instanceof Rectangle) ? ((Rectangle) s).getUpperLeftPoint().compareTo(getUpperLeftPoint()) && ((Rectangle) s).getWidth() == getWidth() && ((Rectangle) s).getHeight() == getHeight() : false;
	}
	
	@Override
	public String toString() {
		return "RECTANGLE : x=" + upperLeftPoint.getX() + " y=" + upperLeftPoint.getY() + " width=" + width + " height=" + height + " outer(" + getColor().getRed() + "," + getColor().getGreen() + "," + getColor().getBlue() + ") inner(" + getInnerColor().getRed() + "," + getInnerColor().getGreen() + "," + getInnerColor().getBlue() + ") selected=" + isSelected();
	}
	
	@Override
	public Rectangle clone() {
		return new Rectangle(upperLeftPoint, height, width, getColor(), getInnerColor());
	}
}
