package command.modify;

import command.Command;
import shape.Donut;

public class ModifyDonutCommand implements Command {
	private Donut beforeDonut;
	private Donut afterDonut;
	private Donut protoDonut;
	
	public ModifyDonutCommand(Donut beforeDonut, Donut afterDonut) {
		this.beforeDonut = beforeDonut;
		this.afterDonut = afterDonut;
	}

	@Override
	public void execute() {
		protoDonut = beforeDonut.clone();
		
		beforeDonut.setCenter(afterDonut.getCenter());
		beforeDonut.setRadius(afterDonut.getRadius());
		beforeDonut.setInnerRadius(afterDonut.getInnerRadius());
		beforeDonut.setColor(afterDonut.getColor());
		beforeDonut.setInnerColor(afterDonut.getInnerColor());
	}

	@Override
	public void unexecute() {
		beforeDonut.setCenter(protoDonut.getCenter());
		beforeDonut.setRadius(protoDonut.getRadius());
		beforeDonut.setInnerRadius(protoDonut.getInnerRadius());
		beforeDonut.setColor(protoDonut.getColor());
		beforeDonut.setInnerColor(protoDonut.getInnerColor());
	}
	
	@Override
	public String toString() {
		return "[Modify] " + protoDonut.toString() + " to " + afterDonut.toString();
	}
}
