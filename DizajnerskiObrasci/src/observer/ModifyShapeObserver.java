package observer;

import helper.ShapeHelper;
import mvc.DrawingFrame;
import mvc.DrawingModel;

public class ModifyShapeObserver implements Observer {
	private DrawingFrame frame;
	
	public ModifyShapeObserver(DrawingFrame frame) {
		this.frame = frame;
	}

	@Override
	public void update(DrawingModel model) {
		if (ShapeHelper.countSelectedShapes(model) == 1) {
			frame.getTglbtnSelect().setSelected(true);
			frame.getTglbtnModify().setEnabled(true);
			frame.getTglbtnRemove().setEnabled(true);
			frame.getBtnToBack().setEnabled(true);
			frame.getBtnBringToBack().setEnabled(true);
			frame.getBtnToFront().setEnabled(true);
			frame.getBtnBringToFront().setEnabled(true);
		}
	}
}
