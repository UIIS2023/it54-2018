package observer;

import helper.ShapeHelper;
import mvc.DrawingFrame;
import mvc.DrawingModel;

public class RemoveShapeObserver implements Observer {
	private DrawingFrame frame;
	
	public RemoveShapeObserver(DrawingFrame frame) {
		this.frame = frame;
	}

	@Override
	public void update(DrawingModel model) {
		if (ShapeHelper.countSelectedShapes(model) > 1) {
			frame.getTglbtnSelect().setSelected(true);
			frame.getTglbtnModify().setEnabled(false);
			frame.getTglbtnRemove().setEnabled(true);
			frame.getBtnToBack().setEnabled(false);
			frame.getBtnBringToBack().setEnabled(false);
			frame.getBtnToFront().setEnabled(false);
			frame.getBtnBringToFront().setEnabled(false);
		}
	}
}
