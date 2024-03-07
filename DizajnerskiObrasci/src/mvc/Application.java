package mvc;

public class Application {

	public static void main(String[] args) {
		DrawingModel model = new DrawingModel();
		DrawingFrame frame = new DrawingFrame();
		DrawingController controller = new DrawingController(model, frame);
		
		frame.getView().setModel(model);
		frame.setController(controller);
		frame.setVisible(true);
	}
}
