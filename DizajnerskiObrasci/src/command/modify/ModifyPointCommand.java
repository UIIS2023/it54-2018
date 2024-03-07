package command.modify;

import command.Command;
import shape.Point;

public class ModifyPointCommand implements Command {
	private Point beforePoint;
	private Point afterPoint;
	private Point protoPoint;
	
	public ModifyPointCommand(Point beforePoint, Point afterPoint) {
		this.beforePoint = beforePoint;
		this.afterPoint = afterPoint;
	}

	@Override
	public void execute() {
		protoPoint = beforePoint.clone();
		
		beforePoint.setX(afterPoint.getX());
		beforePoint.setY(afterPoint.getY());
		beforePoint.setColor(afterPoint.getColor());
	}

	@Override
	public void unexecute() {
		beforePoint.setX(protoPoint.getX());
		beforePoint.setY(protoPoint.getY());
		beforePoint.setColor(protoPoint.getColor());
	}
	
	@Override
	public String toString() {
		return "[Modify] " + protoPoint.toString() + " to " + afterPoint.toString();
	}
}
