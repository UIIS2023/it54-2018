package command;

import shape.Shape;

public class SelectShapeCommand implements Command {
	private Shape shape;
	
	public SelectShapeCommand(Shape shape) {
		this.shape = shape;
	}

	@Override
	public void execute() {
		shape.setSelected(!shape.isSelected());
	}

	@Override
	public void unexecute() {
		shape.setSelected(!shape.isSelected());
	}
	
	@Override
	public String toString() {
		return "[Select] " + shape.toString();
	}
}
