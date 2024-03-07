package helper;

import java.util.Iterator;

import mvc.DrawingModel;
import shape.Shape;

public class ShapeHelper {
	public static int countSelectedShapes(DrawingModel model) {
		int totalSelectedShapes = 0;
		Iterator<Shape> it = model.getShapes().iterator();
		
		while (it.hasNext()) {
			if (it.next().isSelected()) {
				totalSelectedShapes++;
			}
		}
		
		return totalSelectedShapes;
	}
}
