package command.modify;

import command.Command;
import shape.Line;

public class ModifyLineCommand implements Command {
	private Line beforeLine;
	private Line afterLine;
	private Line protoLine;
	
	public ModifyLineCommand(Line beforeLine, Line afterLine) {
		this.beforeLine = beforeLine;
		this.afterLine = afterLine;
	}

	@Override
	public void execute() {
		protoLine = beforeLine.clone();
		
		beforeLine.setStartPoint(afterLine.getStartPoint());
		beforeLine.setEndPoint(afterLine.getEndPoint());
		beforeLine.setColor(afterLine.getColor());
	}

	@Override
	public void unexecute() {
		beforeLine.setStartPoint(protoLine.getStartPoint());
		beforeLine.setEndPoint(protoLine.getEndPoint());
		beforeLine.setColor(protoLine.getColor());
	}
	
	@Override
	public String toString() {
		return "[Modify] " + protoLine.toString() + " to " + afterLine.toString();
	}
}
