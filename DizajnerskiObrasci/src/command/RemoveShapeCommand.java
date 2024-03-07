package command;

import mvc.DrawingModel;
import shape.Shape;

public class RemoveShapeCommand implements Command {
	private DrawingModel model;
	private Shape shape;
	
	public RemoveShapeCommand(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}

	@Override
	public void execute() {
		model.remove(shape);
	}

	@Override
	public void unexecute() {
		model.add(shape);
	}
	
	@Override
	public String toString() {
		return "[Remove] " + shape.toString();
	}
}
