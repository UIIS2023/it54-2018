package command.modify;

import command.Command;
import shape.Circle;

public class ModifyCircleCommand implements Command {
	private Circle beforeCircle;
	private Circle afterCircle;
	private Circle protoCircle;
	
	public ModifyCircleCommand(Circle beforeCircle, Circle afterCircle) {
		this.beforeCircle = beforeCircle;
		this.afterCircle = afterCircle;
	}

	@Override
	public void execute() {
		protoCircle = beforeCircle.clone();
		
		beforeCircle.setCenter(afterCircle.getCenter());
		beforeCircle.setRadius(afterCircle.getRadius());
		beforeCircle.setColor(afterCircle.getColor());
		beforeCircle.setInnerColor(afterCircle.getInnerColor());
	}

	@Override
	public void unexecute() {
		beforeCircle.setCenter(protoCircle.getCenter());
		beforeCircle.setRadius(protoCircle.getRadius());
		beforeCircle.setColor(protoCircle.getColor());
		beforeCircle.setInnerColor(protoCircle.getInnerColor());
	}
	
	@Override
	public String toString() {
		return "[Modify] " + protoCircle.toString() + " to " + afterCircle.toString();
	}
}
