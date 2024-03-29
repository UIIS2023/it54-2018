package mvc;

import java.util.ArrayList;

import shape.Shape;

public class DrawingModel {
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	
	public DrawingModel() {
		//
	}

	public ArrayList<Shape> getShapes() {
		return shapes;
	}

	public void add(Shape p) {
		shapes.add(p);
	}

	public void remove(Shape p) {
		shapes.remove(p);
	}

	public Shape get(int index) {
		return shapes.get(index);
	}
}
