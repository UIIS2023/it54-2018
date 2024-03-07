package command.modify;

import command.Command;
import shape.Rectangle;

public class ModifyRectangleCommand implements Command {
	private Rectangle beforeState;
	private Rectangle afterState;
	private Rectangle protoState;
	
	public ModifyRectangleCommand(Rectangle beforeState, Rectangle afterState) {
		this.beforeState = beforeState;
		this.afterState = afterState;
	}

	@Override
	public void execute() {
		protoState = beforeState.clone();
		
		beforeState.setUpperLeftPoint(afterState.getUpperLeftPoint());
		beforeState.setHeight(afterState.getHeight());
		beforeState.setWidth(afterState.getWidth());
		beforeState.setColor(afterState.getColor());
		beforeState.setInnerColor(afterState.getInnerColor());
	}

	@Override
	public void unexecute() {
		beforeState.setUpperLeftPoint(protoState.getUpperLeftPoint());
		beforeState.setHeight(protoState.getHeight());
		beforeState.setWidth(protoState.getWidth());
		beforeState.setColor(protoState.getColor());
		beforeState.setInnerColor(protoState.getInnerColor());
	}
	
	@Override
	public String toString() {
		return "[Modify] " + protoState.toString() + " to " + afterState.toString();
	}
}
