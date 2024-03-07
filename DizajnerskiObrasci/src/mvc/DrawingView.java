package mvc;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JPanel;

import shape.Shape;

public class DrawingView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private DrawingModel model = new DrawingModel();
	
	public DrawingView() {
		setBackground(Color.WHITE);
	}

	public void setModel(DrawingModel model) {
		this.model = model;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Iterator<Shape> it = model.getShapes().iterator();
		
		while (it.hasNext()) {
			it.next().draw(g);
		}
	}
}
