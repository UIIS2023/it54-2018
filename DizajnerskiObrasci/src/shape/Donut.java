package shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Donut extends Circle {
	private static final long serialVersionUID = 1L;
	
	private int innerRadius;
	
	public Donut() {
		//
	}
	
	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius);
		
		this.innerRadius = innerRadius;
	}
	
	public Donut(Point center, int radius, int innerRadius, Color color, Color innerColor) {
		this(center, radius, innerRadius);
		
		setColor(color);
		setInnerColor(innerColor);
	}
	
	public Donut(Point center, int radius, int innerRadius, Color color, Color innerColor, boolean isSelected) {
		this(center, radius, innerRadius);
		
		setColor(color);
		setInnerColor(innerColor);
		setSelected(isSelected);
	}
	
	public int getInnerRadius() {
		return innerRadius;
	}

	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		
		super.fill(g);
		
		g.setColor(Color.WHITE);
		g.fillOval(getCenter().getX() - getInnerRadius(), getCenter().getY() - getInnerRadius(), getInnerRadius() * 2, getInnerRadius() * 2);
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		
		Area donut = createCircletWithHole();
		
		g2d.setColor(getInnerColor());
		g2d.fill(donut);
		g2d.setColor(getColor());
		g2d.draw(donut);
		
		if (isSelected()) {
			selected(g2d);
		}
		
		g2d.dispose();
	}
	
	@Override
	public boolean contains(int x, int y) {
		return super.contains(x, y) && getCenter().distance(x, y) > innerRadius;
	}
	
	@Override
	public boolean compareTo(Shape s) {
		return (s instanceof Donut) ? ((Donut) s).getCenter().compareTo(getCenter()) && ((Donut) s).getRadius() == getRadius() && ((Donut) s).getInnerRadius() == getInnerRadius() : false;
	}
	
	@Override
	public String toString() {
		return "DONUT : x=" + getCenter().getX() + " y=" + getCenter().getY() + " outerR=" + getRadius() + " innerR=" + innerRadius + " outer(" + getColor().getRed() + "," + getColor().getGreen() + "," + getColor().getBlue() + ") inner(" + getInnerColor().getRed() + "," + getInnerColor().getGreen() + "," + getInnerColor().getBlue() + ") selected=" + isSelected();
	}
	
	@Override
	public Donut clone() {
		return new Donut(getCenter(), getRadius(), innerRadius, getColor(), getInnerColor());
	}
	
	private Area createCircletWithHole() {
		Area area = new Area(new Ellipse2D.Double(getCenter().getX() - getRadius(), getCenter().getY() - getRadius(), getRadius() * 2, getRadius() * 2));
		
		area.subtract(new Area(new Ellipse2D.Double(getCenter().getX() - innerRadius, getCenter().getY() - innerRadius, innerRadius * 2, innerRadius * 2)));
		
		return area;
	}
}
