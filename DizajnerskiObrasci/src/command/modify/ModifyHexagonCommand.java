package command.modify;

import command.Command;
import shape.HexagonAdapter;

public class ModifyHexagonCommand implements Command {
	private HexagonAdapter beforeHexagon;
	private HexagonAdapter afterHexagon;
	private HexagonAdapter protoHexagon;
	
	public ModifyHexagonCommand(HexagonAdapter beforeHexagon, HexagonAdapter afterHexagon) {
		this.beforeHexagon = beforeHexagon;
		this.afterHexagon = afterHexagon;
	}

	@Override
	public void execute() {
		protoHexagon = beforeHexagon.clone();
		
		beforeHexagon.setHexagon(afterHexagon.getHexagon());
	}

	@Override
	public void unexecute() {
		beforeHexagon.setHexagon(protoHexagon.getHexagon());
	}
	
	@Override
	public String toString() {
		return "[Modify] " + protoHexagon.toString() + " to " + afterHexagon.toString();
	}
}
