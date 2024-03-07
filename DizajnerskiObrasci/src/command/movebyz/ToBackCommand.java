package command.movebyz;

import command.Command;
import mvc.DrawingModel;
import shape.Shape;

public class ToBackCommand implements Command {
	private DrawingModel model;
	private Shape shape;
	private int protoIndex;
	
	public ToBackCommand(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}

	@Override
	public void execute() {
		protoIndex = model.getShapes().indexOf(shape);
		Shape tmpShape = model.getShapes().get(protoIndex - 1);

		model.getShapes().set(protoIndex, tmpShape);
		model.getShapes().set(protoIndex - 1, shape);
	}

	@Override
	public void unexecute() {
		Shape tmpShape = model.getShapes().get(protoIndex);
		
		model.getShapes().set(protoIndex, shape);
		model.getShapes().set(protoIndex - 1, tmpShape);
	}
	
	@Override
	public String toString() {
		return "[ToBack] " + shape.toString();
	}
}
