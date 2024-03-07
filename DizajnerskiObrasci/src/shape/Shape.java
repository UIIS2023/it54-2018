package shape;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public abstract class Shape implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Color color;
	private boolean isSelected;
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public boolean isSelected() {
		return isSelected;
	}
	
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public abstract void draw(Graphics g);
	public abstract boolean contains(int x, int y);
	public abstract void selected(Graphics g);
	public abstract boolean compareTo(Shape s);
}
