package command.movebyz;

import command.Command;
import mvc.DrawingModel;
import shape.Shape;

public class BringToFrontCommand implements Command {
	private DrawingModel model;
	private Shape shape;
	private int protoIndex;
	
	public BringToFrontCommand(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}

	@Override
	public void execute() {
		protoIndex = model.getShapes().indexOf(shape);

		model.getShapes().remove(shape);
		model.getShapes().add(shape);
	}

	@Override
	public void unexecute() {		
		model.getShapes().remove(shape);
		model.getShapes().add(protoIndex, shape);
	}
	
	@Override
	public String toString() {
		return "[BringToFront] " + shape.toString();
	}
}
