package shape;

import java.awt.Color;
import java.awt.Graphics;

import hexagon.Hexagon;

public class HexagonAdapter extends SurfaceShape {
	private static final long serialVersionUID = 1L;
	
	private Hexagon hexagon;
	
	public HexagonAdapter() {
		super();
	}

	public HexagonAdapter(Hexagon hexagon) {
		this.hexagon = hexagon;
	}
	 
	public HexagonAdapter(Hexagon hexagon, Color color, Color innerColor) {
		this(hexagon);
		
		setColor(color);
		setInnerColor(innerColor);
	}
	
	public HexagonAdapter(int x, int y, int radius) {
		this.hexagon = new Hexagon(x, y, radius);
	}
	
	public HexagonAdapter(int x, int y, int radius, Color color, Color innerColor) {
		this(x, y, radius);

		setColor(color);
		setInnerColor(innerColor);
	}
	
	public HexagonAdapter(Point center, int radius, Color color, Color innerColor) {
		this(center.getX(), center.getY(), radius);
		
		setColor(color);
		setInnerColor(innerColor);
	}
	
	public HexagonAdapter(Point center, int radius, Color color, Color innerColor, boolean isSelected) {
		this(center.getX(), center.getY(), radius);
		
		setColor(color);
		setInnerColor(innerColor);
		setSelected(isSelected);
	}
	
	public Hexagon getHexagon() {
		return hexagon;
	}

	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}
	
	public int getX() {
		return hexagon.getX();
	}
	
	public void setX(int x) {
		hexagon.setX(x);
	}
	
	public int getY() {
		return hexagon.getY();
	}
	
	public void setY(int y) {
		hexagon.setY(y);
	}
	
	public Point getCenter() {
		return new Point(getX(), getY());
	}
	
	public void setCenter(Point center) {
		hexagon.setX(center.getX());
		hexagon.setY(center.getY());
	}
	
	public void setCenter(int x, int y) {
		hexagon.setX(x);
		hexagon.setY(y);
	}
	
	public int getRadius() {
		return hexagon.getR();
	}
	
	public void setRadius(int radius) {
		hexagon.setR(radius);
	}
	
	public boolean isSelected() {
		return hexagon.isSelected();
	}
	
	public void setSelected(boolean isSelected) {
		this.hexagon.setSelected(isSelected);
	}
	
	public Color getColor() {
		return hexagon.getBorderColor();
	}
	
	public void setColor(Color color) {
		hexagon.setBorderColor(color);
	}
	
	public Color getInnerColor() {
		return hexagon.getAreaColor();
	}
	
	public void setInnerColor(Color innerColor) {
		hexagon.setAreaColor(innerColor);
	}

	@Override
	public void fill(Graphics g) {
		//
	}

	@Override
	public void draw(Graphics g) {
		hexagon.paint(g);
	}

	@Override
	public boolean contains(int x, int y) {
		return hexagon.doesContain(x, y);
	}

	@Override
	public void selected(Graphics g) {
		//
	}
	
	@Override
	public boolean compareTo(Shape s) {
		return (s instanceof HexagonAdapter) ? ((HexagonAdapter) s).getX() == getX() && ((HexagonAdapter) s).getY() == getY() && ((HexagonAdapter) s).getRadius() == getRadius() : false;
	}
	
	@Override
	public String toString() {
		return "HEXAGON : x=" + getX() + " y=" + getY() + " radius=" + getRadius() + " outer(" + getColor().getRed() + "," + getColor().getGreen() + "," + getColor().getBlue() + ") inner(" + getInnerColor().getRed() + "," + getInnerColor().getGreen() + "," + getInnerColor().getBlue() + ") selected=" + isSelected();
	}
	
	@Override
	public HexagonAdapter clone() {
		return new HexagonAdapter(getHexagon());
	}
}
