package command.movebyz;

import command.Command;
import mvc.DrawingModel;
import shape.Shape;

public class BringToBackCommand implements Command {
	private DrawingModel model;
	private Shape shape;
	private int protoIndex;
	
	public BringToBackCommand(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}
	
	@Override
	public void execute() {
		protoIndex = model.getShapes().indexOf(shape);

		model.remove(shape);
		model.getShapes().add(0, shape);
	}

	@Override
	public void unexecute() {
		model.getShapes().remove(shape);
		model.getShapes().add(protoIndex, shape);
	}
	
	@Override
	public String toString() {
		return "[BringToBack] " + shape.toString();
	}
}
