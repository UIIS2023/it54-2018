package observer;

import helper.ShapeHelper;
import mvc.DrawingFrame;
import mvc.DrawingModel;

public class NoShapeObserver implements Observer {
	private DrawingFrame frame;
	
	public NoShapeObserver(DrawingFrame frame) {
		this.frame = frame;
	}

	@Override
	public void update(DrawingModel model) {
		if (ShapeHelper.countSelectedShapes(model) == 0) {
			frame.getTglbtnSelect().setSelected(false);
			frame.getTglbtnModify().setEnabled(false);
			frame.getTglbtnRemove().setEnabled(false);
			frame.getBtnToBack().setEnabled(false);
			frame.getBtnBringToBack().setEnabled(false);
			frame.getBtnToFront().setEnabled(false);
			frame.getBtnBringToFront().setEnabled(false);
		}
	}
}
