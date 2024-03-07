package command;

import mvc.DrawingModel;
import shape.Shape;

public class AddShapeCommand implements Command {
	private DrawingModel model;
	private Shape shape;
	
	public AddShapeCommand(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}
	
	@Override
	public void execute() {
		model.add(shape);
	}

	@Override
	public void unexecute() {
		model.remove(shape);
	}
	
	@Override
	public String toString() {
		return "[Add] " + shape.toString(); 
	}
}
