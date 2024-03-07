package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import command.AddShapeCommand;
import command.Command;
import command.RemoveShapeCommand;
import command.SelectShapeCommand;
import command.modify.ModifyCircleCommand;
import command.modify.ModifyDonutCommand;
import command.modify.ModifyHexagonCommand;
import command.modify.ModifyLineCommand;
import command.modify.ModifyPointCommand;
import command.modify.ModifyRectangleCommand;
import command.movebyz.BringToBackCommand;
import command.movebyz.BringToFrontCommand;
import command.movebyz.ToBackCommand;
import command.movebyz.ToFrontCommand;
import dialog.CircleDialog;
import dialog.DonutDialog;
import dialog.HexagonDialog;
import dialog.LineDialog;
import dialog.PointDialog;
import dialog.RectangleDialog;
import helper.ShapeHelper;
import helper.ShapeStatus;
import observer.ModifyShapeObserver;
import observer.NoShapeObserver;
import observer.RemoveShapeObserver;
import observer.SelectedShape;
import shape.Circle;
import shape.Donut;
import shape.HexagonAdapter;
import shape.Line;
import shape.Point;
import shape.Rectangle;
import shape.Shape;
import strategy.DrawFile;
import strategy.FileManager;
import strategy.LogFile;

public class DrawingController {
	private DrawingModel model;
	private DrawingFrame frame;
	
	private SelectedShape selectedShape;
	
	private Stack<Command> undo = new Stack<Command>();
	private Stack<Command> redo = new Stack<Command>();
	
	private ArrayList<Point> points = new ArrayList<Point>();
	
	private FileInputStream fis;
	private ObjectInputStream ois;
	private FileReader fr;
	private BufferedReader br;
	
	private String currentLogLine;
	
	private Command currentCommandLine = null;
	private Shape currentShape = null;
	
	private boolean flag;

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		
		selectedShape = new SelectedShape();
		
		selectedShape.addObserver(new ModifyShapeObserver(frame));
		selectedShape.addObserver(new RemoveShapeObserver(frame));
		selectedShape.addObserver(new NoShapeObserver(frame));
	}
	
	public void drawShape(ShapeStatus shape, MouseEvent e) {
		clearSelection();
		
		switch (shape) {
			case POINT:
				drawPoint(e);
				
				break;
			case LINE:
				drawLine(e);
				
				break;
			case RECTANGLE:
				drawRectangle(e);
				
				break;
			case CIRCLE:
				drawCircle(e);
				
				break;
			case DONUT:
				drawDonut(e);
				
				break;
			case HEXAGON:
				drawHexagon(e);
				
				break;
			default:
				break;
		}
		
		frame.getBtnUndo().setEnabled(!undo.isEmpty());
		frame.getBtnRedo().setEnabled(!redo.isEmpty());
	}
	
	public void drawPoint(MouseEvent e) {
		Point point = new Point(e.getX(), e.getY(), frame.getColor());
		Command asc = new AddShapeCommand(model, point);
		
		asc.execute();
		
		undo.push(asc);
		redo.clear();
		
		frame.getView().repaint();
		frame.updateLogList(asc.toString());
		
		selectedShape.setModel(model);
	}
	
	public void drawLine(MouseEvent e) {
		points.add(new Point(e.getX(), e.getY()));
		
		if (points.size() < 2) {
			return;
		}
		
		Point startPoint = new Point(points.get(0).getX(), points.get(0).getY());
		Point endPoint = new Point(points.get(1).getX(), points.get(1).getY());
		Line line = new Line(startPoint, endPoint, frame.getColor());
		Command asc = new AddShapeCommand(model, line);
		
		asc.execute();
		
		undo.push(asc);
		redo.clear();
		
		frame.getView().repaint();
		frame.updateLogList(asc.toString());
		
		selectedShape.setModel(model);
		
		points.clear();
	}
	
	public void drawRectangle(MouseEvent e) {
		Point upperLeftPoint = new Point(e.getX(), e.getY());
		RectangleDialog rectangleDlg = new RectangleDialog();
		
		rectangleDlg.getTxtX().setText(Integer.toString(upperLeftPoint.getX()));
		rectangleDlg.getTxtY().setText(Integer.toString(upperLeftPoint.getY()));
		rectangleDlg.getTxtColor().setBackground(frame.getColor());
		rectangleDlg.getTxtInnerColor().setBackground(frame.getInnerColor());
		rectangleDlg.setVisible(true);
		
		if (rectangleDlg.isOK) {
			upperLeftPoint.setX(Integer.parseInt(rectangleDlg.getTxtX().getText()));
			upperLeftPoint.setY(Integer.parseInt(rectangleDlg.getTxtY().getText()));
			
			int height = Integer.parseInt(rectangleDlg.getTxtHeight().getText());
			int width = Integer.parseInt(rectangleDlg.getTxtWidth().getText());
			Color color = rectangleDlg.getTxtColor().getBackground();
			Color innerColor = rectangleDlg.getTxtInnerColor().getBackground();
			Rectangle rectangle = new Rectangle(upperLeftPoint, height, width, color, innerColor);
			Command asc = new AddShapeCommand(model, rectangle);
			
			asc.execute();
			
			undo.push(asc);
			redo.clear();
			
			frame.getView().repaint();
			frame.updateLogList(asc.toString());
			
			selectedShape.setModel(model);
			
			changeActiveColorFromDialog(
					rectangleDlg.getChckbxColor().isSelected(),
					rectangleDlg.getChckbxInnerColor().isSelected(),
					rectangleDlg.getTxtColor().getBackground(),
					rectangleDlg.getTxtInnerColor().getBackground()
			);
		}
	}

	public void drawCircle(MouseEvent e) {
		Point center = new Point(e.getX(), e.getY());
		CircleDialog circleDlg = new CircleDialog();
		
		circleDlg.getTxtX().setText(Integer.toString(center.getX()));
		circleDlg.getTxtY().setText(Integer.toString(center.getY()));
		circleDlg.getTxtColor().setBackground(frame.getColor());
		circleDlg.getTxtInnerColor().setBackground(frame.getInnerColor());
		circleDlg.setVisible(true);
		
		if (circleDlg.isOK) {
			center.setX(Integer.parseInt(circleDlg.getTxtX().getText()));
			center.setY(Integer.parseInt(circleDlg.getTxtY().getText()));
			
			int radius = Integer.parseInt(circleDlg.getTxtRadius().getText());
			Color color = circleDlg.getTxtColor().getBackground();
			Color innerColor = circleDlg.getTxtInnerColor().getBackground();
			Circle circle = new Circle(new Point(center.getX(), center.getY()), radius, color, innerColor);
			Command asc = new AddShapeCommand(model, circle);
			
			asc.execute();
			
			undo.push(asc);
			redo.clear();
			
			frame.getView().repaint();
			frame.updateLogList(asc.toString());
			
			selectedShape.setModel(model);
			
			changeActiveColorFromDialog(
					circleDlg.getChckbxColor().isSelected(),
					circleDlg.getChckbxInnerColor().isSelected(),
					circleDlg.getTxtColor().getBackground(),
					circleDlg.getTxtInnerColor().getBackground()
			);
		}
	}
	
	public void drawDonut(MouseEvent e) {
		Point center = new Point(e.getX(), e.getY());
		DonutDialog donutDlg = new DonutDialog();
		
		donutDlg.getTxtX().setText(Integer.toString(center.getX()));
		donutDlg.getTxtY().setText(Integer.toString(center.getY()));
		donutDlg.getTxtColor().setBackground(frame.getColor());
		donutDlg.getTxtInnerColor().setBackground(frame.getInnerColor());
		donutDlg.setVisible(true);
		
		if (donutDlg.isOK) {
			center.setX(Integer.parseInt(donutDlg.getTxtX().getText()));
			center.setY(Integer.parseInt(donutDlg.getTxtY().getText()));
			
			int radius = Integer.parseInt(donutDlg.getTxtRadius().getText());
			int innerRadius = Integer.parseInt(donutDlg.getTxtInnerRadius().getText());
			Color color = donutDlg.getTxtColor().getBackground();
			Color innerColor = donutDlg.getTxtInnerColor().getBackground();
			Donut donut = new Donut(center, radius, innerRadius, color, innerColor);
			Command asc = new AddShapeCommand(model, donut);
			
			asc.execute();
			
			undo.push(asc);
			redo.clear();
			
			frame.getView().repaint();
			frame.updateLogList(asc.toString());
			
			selectedShape.setModel(model);

			changeActiveColorFromDialog(
					donutDlg.getChckbxColor().isSelected(),
					donutDlg.getChckbxInnerColor().isSelected(),
					donutDlg.getTxtColor().getBackground(),
					donutDlg.getTxtInnerColor().getBackground()
			);
		}
	}
	
	public void drawHexagon(MouseEvent e) {
		Point center = new Point(e.getX(), e.getY());
		HexagonDialog hexagonDlg = new HexagonDialog();
		
		hexagonDlg.getTxtX().setText(Integer.toString(center.getX()));
		hexagonDlg.getTxtY().setText(Integer.toString(center.getY()));
		hexagonDlg.getTxtColor().setBackground(frame.getColor());
		hexagonDlg.getTxtInnerColor().setBackground(frame.getInnerColor());
		hexagonDlg.setVisible(true);
		
		if (hexagonDlg.isOK) {
			center.setX(Integer.parseInt(hexagonDlg.getTxtX().getText()));
			center.setY(Integer.parseInt(hexagonDlg.getTxtY().getText()));
			
			int radius = Integer.parseInt(hexagonDlg.getTxtRadius().getText());
			Color color = hexagonDlg.getTxtColor().getBackground();
			Color innerColor = hexagonDlg.getTxtInnerColor().getBackground();
			HexagonAdapter hexagon = new HexagonAdapter(center, radius, color, innerColor);
			Command asc = new AddShapeCommand(model, hexagon);
			
			asc.execute();
			
			undo.push(asc);
			redo.clear();
			
			frame.getView().repaint();
			frame.updateLogList(asc.toString());
			
			selectedShape.setModel(model);
			
			changeActiveColorFromDialog(
					hexagonDlg.getChckbxColor().isSelected(),
					hexagonDlg.getChckbxInnerColor().isSelected(),
					hexagonDlg.getTxtColor().getBackground(),
					hexagonDlg.getTxtInnerColor().getBackground()
			);
		}
	}
	
	public void selectShape(MouseEvent e) {
		ListIterator<Shape> it = model.getShapes().listIterator(model.getShapes().size());
		
		while (it.hasPrevious()) {
			Shape tmpShape = it.previous();
			
			if (tmpShape.contains(e.getX(), e.getY())) {
				Command ssc = new SelectShapeCommand(tmpShape);
				
				if (!tmpShape.isSelected()) {
					ssc.execute();
				} else {
					ssc.unexecute();
				}
				
				undo.push(ssc);
				redo.clear();
				
				frame.getView().repaint();
				frame.updateLogList(ssc.toString());
				
				break;
			}
		}
		
		selectedShape.setModel(model);
	}
	
	public void modifyShape() {
		ListIterator<Shape> it = model.getShapes().listIterator(model.getShapes().size());
		
		while (it.hasPrevious()) {
			Shape tmpShape = it.previous();
			
			if (tmpShape.isSelected()) {
				if (tmpShape instanceof Point) {
					modifyPoint((Point) tmpShape);
				} else if (tmpShape instanceof Line) {
					modifyLine((Line) tmpShape);
				} else if (tmpShape instanceof Rectangle) {
					modifyRectangle((Rectangle) tmpShape);
				} else if (tmpShape instanceof Donut) {
					modifyDonut((Donut) tmpShape);
				} else if (tmpShape instanceof Circle) {
					modifyCircle((Circle) tmpShape);
				} else {
					modifyHexagon((HexagonAdapter) tmpShape);
				}
			}
		}
		
		frame.getTglbtnModify().setSelected(true);
		frame.getBgMode().clearSelection();
		frame.getBtnUndo().setEnabled(!undo.isEmpty());
		frame.getBtnRedo().setEnabled(!redo.isEmpty());
	}
	
	private void modifyPoint(Point point) {
		PointDialog pointDlg = new PointDialog();
		
		pointDlg.getTxtX().setText(Integer.toString(point.getX()));
		pointDlg.getTxtY().setText(Integer.toString(point.getY()));
		pointDlg.getTxtColor().setBackground(point.getColor());
		pointDlg.setVisible(true);
		
		if (pointDlg.isOK) {
			Point newPoint = new Point();
			
			newPoint.setX(Integer.parseInt(pointDlg.getTxtX().getText()));
			newPoint.setY(Integer.parseInt(pointDlg.getTxtY().getText()));
			newPoint.setColor(pointDlg.getTxtColor().getBackground());
			
			Command mpc = new ModifyPointCommand(point, newPoint);
			
			mpc.execute();
			
			undo.push(mpc);
			redo.clear();
			
			frame.getView().repaint();
			frame.updateLogList(mpc.toString());
		}
	}
	
	private void modifyLine(Line line) {
		LineDialog lineDlg = new LineDialog();
		
		lineDlg.getTxtStartX().setText(Integer.toString(line.getStartPoint().getX()));
		lineDlg.getTxtStartY().setText(Integer.toString(line.getStartPoint().getY()));
		lineDlg.getTxtEndX().setText(Integer.toString(line.getEndPoint().getX()));
		lineDlg.getTxtEndY().setText(Integer.toString(line.getEndPoint().getY()));
		lineDlg.getTxtColor().setBackground(line.getColor());
		lineDlg.setVisible(true);
		
		if (lineDlg.isOK) {
			Line newLine = new Line();
			
			newLine.setStartPoint(new Point(Integer.parseInt(lineDlg.getTxtStartX().getText()), Integer.parseInt(lineDlg.getTxtStartY().getText())));
			newLine.setEndPoint(new Point(Integer.parseInt(lineDlg.getTxtEndX().getText()), Integer.parseInt(lineDlg.getTxtEndY().getText())));
			newLine.setColor(lineDlg.getTxtColor().getBackground());
			
			Command mlc = new ModifyLineCommand(line, newLine);
			
			mlc.execute();
			
			undo.push(mlc);
			redo.clear();
			
			frame.getView().repaint();
			frame.updateLogList(mlc.toString());
		}
 	}
	
	private void modifyRectangle(Rectangle rectangle) {
		RectangleDialog rectangleDlg = new RectangleDialog();
		
		rectangleDlg.getTxtX().setText(Integer.toString(rectangle.getUpperLeftPoint().getX()));
		rectangleDlg.getTxtY().setText(Integer.toString(rectangle.getUpperLeftPoint().getY()));
		rectangleDlg.getTxtHeight().setText(Integer.toString(rectangle.getHeight()));
		rectangleDlg.getTxtWidth().setText(Integer.toString(rectangle.getWidth()));
		rectangleDlg.getTxtColor().setBackground(rectangle.getColor());
		rectangleDlg.getTxtInnerColor().setBackground(rectangle.getInnerColor());
		rectangleDlg.setVisible(true);
		
		if (rectangleDlg.isOK) {
			Rectangle newRectangle = new Rectangle();
			
			newRectangle.setUpperLeftPoint(new Point(Integer.parseInt(rectangleDlg.getTxtX().getText()), Integer.parseInt(rectangleDlg.getTxtY().getText())));
			newRectangle.setHeight(Integer.parseInt(rectangleDlg.getTxtHeight().getText()));
			newRectangle.setWidth(Integer.parseInt(rectangleDlg.getTxtWidth().getText()));
			newRectangle.setColor(rectangleDlg.getTxtColor().getBackground());
			newRectangle.setInnerColor(rectangleDlg.getTxtInnerColor().getBackground());
			
			Command mrc = new ModifyRectangleCommand(rectangle, newRectangle);
			
			mrc.execute();
			
			undo.push(mrc);
			redo.clear();
			
			frame.getView().repaint();
			frame.updateLogList(mrc.toString());
			
			changeActiveColorFromDialog(
					rectangleDlg.getChckbxColor().isSelected(),
					rectangleDlg.getChckbxInnerColor().isSelected(),
					rectangleDlg.getTxtColor().getBackground(),
					rectangleDlg.getTxtInnerColor().getBackground()
			);
		}
	}
	
	private void modifyCircle(Circle circle) {
		CircleDialog circleDlg = new CircleDialog();
		
		circleDlg.getTxtX().setText(Integer.toString(circle.getCenter().getX()));
		circleDlg.getTxtY().setText(Integer.toString(circle.getCenter().getY()));
		circleDlg.getTxtRadius().setText(Integer.toString(circle.getRadius()));
		circleDlg.getTxtColor().setBackground(circle.getColor());
		circleDlg.getTxtInnerColor().setBackground(circle.getInnerColor());
		circleDlg.setVisible(true);
		
		if (circleDlg.isOK) {
			Circle newCircle = new Circle();
			
			newCircle.setCenter(new Point(Integer.parseInt(circleDlg.getTxtX().getText()), Integer.parseInt(circleDlg.getTxtY().getText())));
			newCircle.setRadius(Integer.parseInt(circleDlg.getTxtRadius().getText()));
			newCircle.setColor(circleDlg.getTxtColor().getBackground());
			newCircle.setInnerColor(circleDlg.getTxtInnerColor().getBackground());
			
			Command mcc = new ModifyCircleCommand(circle, newCircle);
			
			mcc.execute();
			
			undo.push(mcc);
			redo.clear();
			
			frame.getView().repaint();
			frame.updateLogList(mcc.toString());
			
			changeActiveColorFromDialog(
					circleDlg.getChckbxColor().isSelected(),
					circleDlg.getChckbxInnerColor().isSelected(),
					circleDlg.getTxtColor().getBackground(),
					circleDlg.getTxtInnerColor().getBackground()
			);
		}
	}
	
	private void modifyDonut(Donut donut) {
		DonutDialog donutDlg = new DonutDialog();
		
		donutDlg.getTxtX().setText(Integer.toString(donut.getCenter().getX()));
		donutDlg.getTxtY().setText(Integer.toString(donut.getCenter().getY()));
		donutDlg.getTxtRadius().setText(Integer.toString(donut.getRadius()));
		donutDlg.getTxtInnerRadius().setText(Integer.toString(donut.getInnerRadius()));
		donutDlg.getTxtColor().setBackground(donut.getColor());
		donutDlg.getTxtInnerColor().setBackground(donut.getInnerColor());
		donutDlg.setVisible(true);	
		
		if (donutDlg.isOK) {
			Donut newDonut = new Donut();
			
			newDonut.setCenter(new Point(Integer.parseInt(donutDlg.getTxtX().getText()), Integer.parseInt(donutDlg.getTxtY().getText())));
			newDonut.setRadius(Integer.parseInt(donutDlg.getTxtRadius().getText()));
			newDonut.setInnerRadius(Integer.parseInt(donutDlg.getTxtInnerRadius().getText()));
			newDonut.setColor(donutDlg.getTxtColor().getBackground());
			newDonut.setInnerColor(donutDlg.getTxtInnerColor().getBackground());
			
			Command mdc = new ModifyDonutCommand(donut, newDonut);
			
			mdc.execute();
			
			undo.push(mdc);
			redo.clear();
			
			frame.getView().repaint();
			frame.updateLogList(mdc.toString());
			
			changeActiveColorFromDialog(
					donutDlg.getChckbxColor().isSelected(),
					donutDlg.getChckbxInnerColor().isSelected(),
					donutDlg.getTxtColor().getBackground(),
					donutDlg.getTxtInnerColor().getBackground()
			);
		}
	}
	
	private void modifyHexagon(HexagonAdapter hexagon) {
		HexagonDialog hexagonDlg = new HexagonDialog();
		
		hexagonDlg.getTxtX().setText(Integer.toString(hexagon.getX()));
		hexagonDlg.getTxtY().setText(Integer.toString(hexagon.getY()));
		hexagonDlg.getTxtRadius().setText(Integer.toString(hexagon.getRadius()));
		hexagonDlg.getTxtColor().setBackground(hexagon.getColor());
		hexagonDlg.getTxtInnerColor().setBackground(hexagon.getInnerColor());
		hexagonDlg.setVisible(true);
		
		if (hexagonDlg.isOK) {
			int x = Integer.parseInt(hexagonDlg.getTxtX().getText());
			int y = Integer.parseInt(hexagonDlg.getTxtY().getText());
			int radius = Integer.parseInt(hexagonDlg.getTxtRadius().getText());
			Color color = hexagonDlg.getTxtColor().getBackground();
			Color innerColor = hexagonDlg.getTxtInnerColor().getBackground();
			HexagonAdapter newHexagon = new HexagonAdapter(x, y, radius, color, innerColor);
			
			newHexagon.setSelected(hexagon.isSelected());
			
			Command mhc = new ModifyHexagonCommand(hexagon, newHexagon);
			
			mhc.execute();
			
			undo.push(mhc);
			redo.clear();
			
			frame.getView().repaint();
			frame.updateLogList(mhc.toString());
			
			changeActiveColorFromDialog(
					hexagonDlg.getChckbxColor().isSelected(),
					hexagonDlg.getChckbxInnerColor().isSelected(),
					hexagonDlg.getTxtColor().getBackground(),
					hexagonDlg.getTxtInnerColor().getBackground()
			);
		}
	}
	
	public void removeShape() {
		int choice = JOptionPane.showConfirmDialog(null, "You have " + ShapeHelper.countSelectedShapes(model) + " selected shapes. Are you sure?", "Delete a shape", JOptionPane.YES_NO_OPTION);
		
		if (choice == JOptionPane.YES_OPTION) {
			ListIterator<Shape> it = model.getShapes().listIterator(model.getShapes().size());
			
			while (it.hasPrevious()) {
				Shape tmpShape = it.previous();
				
				if (tmpShape.isSelected()) {
					it.remove();
					
					Command rsc = new RemoveShapeCommand(model, tmpShape);
					
					rsc.execute();
					
					undo.push(rsc);
					redo.clear();
					
					frame.getView().repaint();
					frame.updateLogList(rsc.toString());
				}
			}
			
			selectedShape.setModel(model);
		}
		
		frame.getBgMode().clearSelection();
		frame.getBgShapeAndOptions().clearSelection();
		frame.getBtnUndo().setEnabled(!undo.isEmpty());
		frame.getBtnRedo().setEnabled(!redo.isEmpty());
	}
	
	public void toBack() {
		ListIterator<Shape> it = model.getShapes().listIterator(model.getShapes().size());
		
		while (it.hasPrevious()) {
			Shape tmpShape = it.previous();
			
			if (tmpShape.isSelected() && model.getShapes().indexOf(tmpShape) != 0) {
				Command tbc = new ToBackCommand(model, tmpShape);
				
				tbc.execute();
				
				undo.push(tbc);
				redo.clear();
				
				frame.getView().repaint();
				frame.updateLogList(tbc.toString());
				
				break;
			}
			
			frame.getBtnUndo().setEnabled(!undo.isEmpty());
			frame.getBtnRedo().setEnabled(!redo.isEmpty());
			
			selectedShape.setModel(model);
		}
	}
	
	public void bringToBack() {
		ListIterator<Shape> it = model.getShapes().listIterator(model.getShapes().size());
		
		while (it.hasPrevious()) {
			Shape tmpShape = it.previous();
			
			if (tmpShape.isSelected() && model.getShapes().indexOf(tmpShape) != 0) {
				Command btbc = new BringToBackCommand(model, tmpShape);
				
				btbc.execute();
				
				undo.push(btbc);
				redo.clear();
				
				frame.getView().repaint();
				frame.updateLogList(btbc.toString());
				
				break;
			}
			
			frame.getBtnUndo().setEnabled(!undo.isEmpty());
			frame.getBtnRedo().setEnabled(!redo.isEmpty());
			
			selectedShape.setModel(model);
		}
	}
	
	public void toFront() {
		ListIterator<Shape> it = model.getShapes().listIterator(model.getShapes().size());
		
		while (it.hasPrevious()) {
			Shape tmpShape = it.previous();
			
			if (tmpShape.isSelected() && model.getShapes().indexOf(tmpShape) != model.getShapes().size() - 1) {
				Command tfc = new ToFrontCommand(model, tmpShape);
				
				tfc.execute();
				
				undo.push(tfc);
				redo.clear();
				
				frame.getView().repaint();
				frame.updateLogList(tfc.toString());
				
				break;
			}
		}
		
		frame.getBtnUndo().setEnabled(!undo.isEmpty());
		frame.getBtnRedo().setEnabled(!redo.isEmpty());
		
		selectedShape.setModel(model);
	}
	
	public void bringToFront() {
		ListIterator<Shape> it = model.getShapes().listIterator(model.getShapes().size());
		
		while (it.hasPrevious()) {
			Shape tmpShape = it.previous();
			
			if (tmpShape.isSelected() && model.getShapes().indexOf(tmpShape) != model.getShapes().size() - 1) {
				Command btfc = new BringToFrontCommand(model, tmpShape);
				
				btfc.execute();
				
				undo.push(btfc);
				redo.clear();
				
				frame.getView().repaint();
				frame.updateLogList(btfc.toString());
				
				break;
			}
		}
		
		frame.getBtnUndo().setEnabled(!undo.isEmpty());
		frame.getBtnRedo().setEnabled(!redo.isEmpty());
		
		selectedShape.setModel(model);
	}
	
	public void undo() {
		frame.getBgMode().clearSelection();
		frame.getBgShapeAndOptions().clearSelection();
		frame.updateLogList("[Undo] " + undo.peek().toString());
		
		redo.push(undo.peek());
		undo.peek().unexecute();
		undo.pop();
		
		frame.getView().repaint();
		frame.getBtnUndo().setEnabled(!undo.isEmpty());
		frame.getBtnRedo().setEnabled(!redo.isEmpty());
		
		selectedShape.setModel(model);
	}
	
	public void redo() {
		frame.getBgMode().clearSelection();
		frame.getBgShapeAndOptions().clearSelection();
		frame.updateLogList("[Redo] " + redo.peek().toString());
		
		undo.push(redo.peek());
		redo.peek().execute();
		redo.pop();
		
		frame.getView().repaint();
		frame.getBtnUndo().setEnabled(!undo.isEmpty());
		frame.getBtnRedo().setEnabled(!redo.isEmpty());
		
		selectedShape.setModel(model);
	}
	
	public void create() {
		if (!model.getShapes().isEmpty()) {
			int choice1 = JOptionPane.showConfirmDialog(null, "Do you want to save before you create a new one?", "Create a new", JOptionPane.YES_NO_OPTION);
			
			if (choice1 == JOptionPane.YES_OPTION) {
				Object[] options = { "As draw", "As log" };
				int choice2 = JOptionPane.showOptionDialog(null, "How do you want to save?", "Save as something", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				
				if (choice2 == JOptionPane.YES_OPTION) {
					saveAsDraw();
				} else if (choice2 == JOptionPane.NO_OPTION) {
					saveAsLog();
				} else {
					return;
				}
			} else if (choice1 == JOptionPane.NO_OPTION)  {
				//
			} else {
				return;
			}
			
			model.getShapes().clear();
			
			undo.clear();
			redo.clear();
			
			frame.getDlm().clear();
			frame.getBtnUndo().setEnabled(!undo.isEmpty());
			frame.getBtnRedo().setEnabled(!redo.isEmpty());
			frame.getBgMode().clearSelection();
			frame.getBgShapeAndOptions().clearSelection();
			frame.getView().repaint();
			
			selectedShape.setModel(model);
		}
	}
	
	public void openDraw() {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
		
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.ser", "ser"));
		fileChooser.setAcceptAllFileFilterUsed(true);
		
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			java.io.File draw = fileChooser.getSelectedFile(); 
			
			try { 
				fis = new FileInputStream(draw);
				ois = new ObjectInputStream(fis);
				
				model.getShapes().clear();
				
				undo.clear();
				redo.clear();
				
				frame.getDlm().clear();
				frame.getBtnUndo().setEnabled(!undo.isEmpty());
				frame.getBtnRedo().setEnabled(!redo.isEmpty());
				frame.getBgMode().clearSelection();
				frame.getBgShapeAndOptions().clearSelection();
				frame.getView().repaint();
				
				ArrayList<Shape> arrShape = (ArrayList<Shape>) ois.readObject();
				
				for (Shape shape : arrShape) {
					model.add(shape);
					
					selectedShape.setModel(model);
				}
				
				ois.close();
				fis.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "The file cannot be opened!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void openLog() {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
		
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.log", "log"));
		fileChooser.setAcceptAllFileFilterUsed(true);
		
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			java.io.File log = fileChooser.getSelectedFile(); 
			
			try { 
				fr = new FileReader(log);
				br = new BufferedReader(fr);
				
				model.getShapes().clear();
				
				undo.clear();
				redo.clear();
				
				frame.getDlm().clear();
				frame.getBtnUndo().setEnabled(!undo.isEmpty());
				frame.getBtnRedo().setEnabled(!redo.isEmpty());
				frame.getBgMode().clearSelection();
				frame.getBgShapeAndOptions().clearSelection();
				frame.getBtnNextLine().setEnabled(true);
				frame.getView().repaint();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "The file cannot be opened!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void interpreter() {	
		try {
			currentLogLine = br.readLine();
			
			if (currentLogLine != null) {
				br.mark(1);
				
				if(br.readLine() == null) {
					frame.getBtnNextLine().setEnabled(false);
					br.close();
				} else {
					br.reset();
				}
				
				String[] decompLog = currentLogLine.split(" ");
				
				switch (bracketrim(decompLog[0])) {
					case "Add":
						switch (decompLog[1]) {
							case "POINT":	
								Point point = new Point(
										readVal(decompLog[3]),
										readVal(decompLog[4]), 
										rgb2Color(decompLog[5]),
										Boolean.parseBoolean(decompLog[6])
								);
								currentCommandLine = new AddShapeCommand(model, point);
								
								break;
							case "LINE":
								Line line = new Line(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										new Point(readVal(decompLog[5]), readVal(decompLog[6])),
										rgb2Color(decompLog[7]),
										Boolean.parseBoolean(decompLog[8])
								);
								currentCommandLine = new AddShapeCommand(model, line);
								
								break;
							case "RECTANGLE":
								Rectangle rectangle = new Rectangle(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										readVal(decompLog[6]),
										readVal(decompLog[5]),
										rgb2Color(decompLog[7]),
										rgb2Color(decompLog[8]),
										Boolean.parseBoolean(decompLog[9])
								);
								currentCommandLine = new AddShapeCommand(model, rectangle);
								
								break;
							case "DONUT":
								Donut donut = new Donut(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										readVal(decompLog[5]),
										readVal(decompLog[6]),
										rgb2Color(decompLog[7]),
										rgb2Color(decompLog[8]),
										Boolean.parseBoolean(decompLog[9])
								);
								currentCommandLine = new AddShapeCommand(model, donut);
								
								break;
							case "CIRCLE":
								Circle circle = new Circle(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										readVal(decompLog[5]),
										rgb2Color(decompLog[6]),
										rgb2Color(decompLog[7]),
										Boolean.parseBoolean(decompLog[8])
								);
								currentCommandLine = new AddShapeCommand(model, circle);
								
								break;
							case "HEXAGON":
								HexagonAdapter hexagon = new HexagonAdapter(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])), 
										readVal(decompLog[5]),
										rgb2Color(decompLog[6]),
										rgb2Color(decompLog[7]),
										Boolean.parseBoolean(decompLog[8])
								);
								currentCommandLine = new AddShapeCommand(model, hexagon);
								
								break;
							default:
								flag = true;
								
								break;
						}
						
						executeLogLine(false);
						
						break;
					case "Modify":
						switch (decompLog[1]) {
							case "POINT":
								Point beforePoint = new Point(
										readVal(decompLog[3]),
										readVal(decompLog[4]), 
										rgb2Color(decompLog[5]),
										Boolean.parseBoolean(decompLog[6])
								);
								Point afterPoint = new Point(
										readVal(decompLog[10]),
										readVal(decompLog[11]), 
										rgb2Color(decompLog[12]),
										Boolean.parseBoolean(decompLog[13])
								);
								currentCommandLine = new ModifyPointCommand((Point) findShapeInModel(beforePoint), afterPoint);
								
								break;
							case "LINE":
								Line beforeLine = new Line(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										new Point(readVal(decompLog[5]), readVal(decompLog[6])),
										rgb2Color(decompLog[7]),
										Boolean.parseBoolean(decompLog[8])
								);
								Line afterLine = new Line(
										new Point(readVal(decompLog[12]), readVal(decompLog[13])),
										new Point(readVal(decompLog[14]), readVal(decompLog[15])),
										rgb2Color(decompLog[16]),
										Boolean.parseBoolean(decompLog[17])
								);
								currentCommandLine = new ModifyLineCommand((Line) findShapeInModel(beforeLine), afterLine);
								
								break;
							case "RECTANGLE":
								Rectangle beforeRectangle = new Rectangle(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										readVal(decompLog[6]),
										readVal(decompLog[5]),
										rgb2Color(decompLog[7]),
										rgb2Color(decompLog[8]),
										Boolean.parseBoolean(decompLog[9])
								);
								Rectangle afterRectangle = new Rectangle(
										new Point(readVal(decompLog[13]), readVal(decompLog[14])),
										readVal(decompLog[16]),
										readVal(decompLog[15]),
										rgb2Color(decompLog[17]),
										rgb2Color(decompLog[18]),
										Boolean.parseBoolean(decompLog[19])
								);
								currentCommandLine = new ModifyRectangleCommand((Rectangle) findShapeInModel(beforeRectangle), afterRectangle);
								
								break;
							case "DONUT":
								Donut beforeDonut = new Donut(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										readVal(decompLog[5]),
										readVal(decompLog[6]),
										rgb2Color(decompLog[7]),
										rgb2Color(decompLog[8]),
										Boolean.parseBoolean(decompLog[9])
								);
								Donut afterDonut = new Donut(
										new Point(readVal(decompLog[13]), readVal(decompLog[14])),
										readVal(decompLog[15]),
										readVal(decompLog[16]),
										rgb2Color(decompLog[17]),
										rgb2Color(decompLog[18]),
										Boolean.parseBoolean(decompLog[19])
								);
								currentCommandLine = new ModifyDonutCommand((Donut) findShapeInModel(beforeDonut), afterDonut);
								
								break;
							case "CIRCLE":	
								Circle	beforeCircle = new Circle(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										readVal(decompLog[5]),
										rgb2Color(decompLog[6]),
										rgb2Color(decompLog[7]),
										Boolean.parseBoolean(decompLog[8])
								);
								Circle	afterCircle = new Circle(
										new Point(readVal(decompLog[12]), readVal(decompLog[13])),
										readVal(decompLog[14]),
										rgb2Color(decompLog[15]),
										rgb2Color(decompLog[16]),
										Boolean.parseBoolean(decompLog[17])
								);
								
								currentCommandLine = new ModifyCircleCommand((Circle) findShapeInModel(beforeCircle), afterCircle);
								
								break;
							case "HEXAGON":
								HexagonAdapter beforeHexagon = new HexagonAdapter(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])), 
										readVal(decompLog[5]),
										rgb2Color(decompLog[6]),
										rgb2Color(decompLog[7]),
										Boolean.parseBoolean(decompLog[8])
								);
								HexagonAdapter afterHexagon = new HexagonAdapter(
										new Point(readVal(decompLog[12]), readVal(decompLog[13])), 
										readVal(decompLog[14]),
										rgb2Color(decompLog[15]),
										rgb2Color(decompLog[16]),
										!Boolean.parseBoolean(decompLog[17])
								);
								
								currentCommandLine = new ModifyHexagonCommand((HexagonAdapter) findShapeInModel(beforeHexagon), afterHexagon);
								
								break;
							default:
								flag = true;
								
								break;
						}
						
						executeLogLine(false);
						
						break;
					case "Remove":
						ListIterator<Shape> it = model.getShapes().listIterator(model.getShapes().size());
						
						while (it.hasPrevious()) {
							Shape tmpShape = it.previous();
							
							if (tmpShape.isSelected()) {
								it.remove();
								
								Command rsc = new RemoveShapeCommand(model, tmpShape);
								
								rsc.execute();
								
								undo.push(rsc);
								redo.clear();
								
								frame.getView().repaint();
								frame.updateLogList(rsc.toString());
								
								break;
							}
						}
						
						selectedShape.setModel(model);
						
						break;
					case "Select":
						switch (decompLog[1]) {
							case "POINT":
								Point point = new Point(
										readVal(decompLog[3]),
										readVal(decompLog[4]), 
										rgb2Color(decompLog[5]),
										Boolean.parseBoolean(decompLog[6])
								);
								currentCommandLine = new SelectShapeCommand((Point) findShapeInModel(point, ShapeStatus.POINT));
								
								break;
							case "LINE":
								Line line = new Line(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										new Point(readVal(decompLog[5]), readVal(decompLog[6])),
										rgb2Color(decompLog[7]),
										Boolean.parseBoolean(decompLog[8])
								);
								currentCommandLine = new SelectShapeCommand((Line) findShapeInModel(line, ShapeStatus.LINE));
								
								break;
							case "RECTANGLE":
								Rectangle rectangle = new Rectangle(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										readVal(decompLog[6]),
										readVal(decompLog[5]),
										rgb2Color(decompLog[7]),
										rgb2Color(decompLog[8]),
										Boolean.parseBoolean(decompLog[9])
								);
								currentCommandLine = new SelectShapeCommand((Rectangle) findShapeInModel(rectangle, ShapeStatus.RECTANGLE));
								
								break;
							case "DONUT":
								Donut donut = new Donut(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										readVal(decompLog[5]),
										readVal(decompLog[6]),
										rgb2Color(decompLog[7]),
										rgb2Color(decompLog[8]),
										Boolean.parseBoolean(decompLog[9])
								);
								currentCommandLine = new SelectShapeCommand((Donut) findShapeInModel(donut, ShapeStatus.DONUT));
								
								break;
							case "CIRCLE":
								Circle circle = new Circle(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										readVal(decompLog[5]),
										rgb2Color(decompLog[6]),
										rgb2Color(decompLog[7]),
										Boolean.parseBoolean(decompLog[8])
								);
								currentCommandLine = new SelectShapeCommand((Circle) findShapeInModel(circle, ShapeStatus.CIRCLE));
								
								break;
							case "HEXAGON": 
								HexagonAdapter hexagon = new HexagonAdapter(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])), 
										readVal(decompLog[5]),
										rgb2Color(decompLog[6]),
										rgb2Color(decompLog[7]),
										Boolean.parseBoolean(decompLog[8])
								);
								currentCommandLine = new SelectShapeCommand((HexagonAdapter) findShapeInModel(hexagon, ShapeStatus.HEXAGON));
								
								break;
							default:
								flag = true;
								
								break;
						}
						
						executeLogLine(true);
						
						break;
					case "ToBack":
						switch (decompLog[1]) {
							case "POINT":
								Point point = new Point(
										readVal(decompLog[3]),
										readVal(decompLog[4]), 
										rgb2Color(decompLog[5]),
										Boolean.parseBoolean(decompLog[6])
								);
								currentCommandLine = new ToBackCommand(model, (Point) findShapeInModel(point));
								
								break;
							case "LINE":
								Line line = new Line(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										new Point(readVal(decompLog[5]), readVal(decompLog[6])),
										rgb2Color(decompLog[7]),
										Boolean.parseBoolean(decompLog[8])
								);
								currentCommandLine = new ToBackCommand(model, (Line) findShapeInModel(line));
								
								break;
							case "RECTANGLE":
								Rectangle rectangle = new Rectangle(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										readVal(decompLog[6]),
										readVal(decompLog[5]),
										rgb2Color(decompLog[7]),
										rgb2Color(decompLog[8]),
										Boolean.parseBoolean(decompLog[9])
								);
								currentCommandLine = new ToBackCommand(model, (Rectangle) findShapeInModel(rectangle));
								
								break;
							case "DONUT":
								Donut donut = new Donut(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										readVal(decompLog[5]),
										readVal(decompLog[6]),
										rgb2Color(decompLog[7]),
										rgb2Color(decompLog[8]),
										Boolean.parseBoolean(decompLog[9])
								);
								currentCommandLine = new ToBackCommand(model, (Donut) findShapeInModel(donut));
								
								break;
							case "CIRCLE":
								Circle circle = new Circle(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										readVal(decompLog[5]),
										rgb2Color(decompLog[6]),
										rgb2Color(decompLog[7]),
										Boolean.parseBoolean(decompLog[8])
								);
								currentCommandLine = new ToBackCommand(model, (Circle) findShapeInModel(circle));
								
								break;
							case "HEXAGON":
								HexagonAdapter hexagon = new HexagonAdapter(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])), 
										readVal(decompLog[5]),
										rgb2Color(decompLog[6]),
										rgb2Color(decompLog[7]),
										Boolean.parseBoolean(decompLog[8])
								);
								currentCommandLine = new ToBackCommand(model, (HexagonAdapter) findShapeInModel(hexagon));
								
								break;
							default:
								flag = true;
								
								break;
						}
						
						executeLogLine(false);
						
						break;
					case "BringToBack":
						switch (decompLog[1]) {
							case "POINT":
								Point point = new Point(
										readVal(decompLog[3]),
										readVal(decompLog[4]), 
										rgb2Color(decompLog[5]),
										Boolean.parseBoolean(decompLog[6])
								);
								currentCommandLine = new BringToBackCommand(model, (Point) findShapeInModel(point));
								
								break;
							case "LINE":
								Line line = new Line(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										new Point(readVal(decompLog[5]), readVal(decompLog[6])),
										rgb2Color(decompLog[7]),
										Boolean.parseBoolean(decompLog[8])
								);
								currentCommandLine = new BringToBackCommand(model, (Line) findShapeInModel(line));
								
								break;
							case "RECTANGLE":
								Rectangle rectangle = new Rectangle(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										readVal(decompLog[6]),
										readVal(decompLog[5]),
										rgb2Color(decompLog[7]),
										rgb2Color(decompLog[8]),
										Boolean.parseBoolean(decompLog[9])
								);
								currentCommandLine = new BringToBackCommand(model, (Rectangle) findShapeInModel(rectangle));
								
								break;
							case "DONUT":
								Donut donut = new Donut(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										readVal(decompLog[5]),
										readVal(decompLog[6]),
										rgb2Color(decompLog[7]),
										rgb2Color(decompLog[8]),
										Boolean.parseBoolean(decompLog[9])
								);
								currentCommandLine = new BringToBackCommand(model, (Donut) findShapeInModel(donut));
								
								break;
							case "CIRCLE":
								Circle circle = new Circle(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										readVal(decompLog[5]),
										rgb2Color(decompLog[6]),
										rgb2Color(decompLog[7]),
										Boolean.parseBoolean(decompLog[8])
								);
								currentCommandLine = new BringToBackCommand(model, (Circle) findShapeInModel(circle));
								
								break;
							case "HEXAGON":
								HexagonAdapter hexagon = new HexagonAdapter(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])), 
										readVal(decompLog[5]),
										rgb2Color(decompLog[6]),
										rgb2Color(decompLog[7]),
										Boolean.parseBoolean(decompLog[8])
								);
								currentCommandLine = new BringToBackCommand(model, (HexagonAdapter) findShapeInModel(hexagon));
								
								break;
							default:
								flag = true;
								
								break;
						}
						
						executeLogLine(false);
						
						break;
					case "ToFront":
						switch (decompLog[1]) {
							case "POINT":
								Point point = new Point(
										readVal(decompLog[3]),
										readVal(decompLog[4]), 
										rgb2Color(decompLog[5]),
										Boolean.parseBoolean(decompLog[6])
								);
								currentCommandLine = new ToFrontCommand(model, (Point) findShapeInModel(point));
								
								break;
							case "LINE":
								Line line = new Line(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										new Point(readVal(decompLog[5]), readVal(decompLog[6])),
										rgb2Color(decompLog[7]),
										Boolean.parseBoolean(decompLog[8])
								);
								currentCommandLine = new ToFrontCommand(model, (Line) findShapeInModel(line));
								
								break;
							case "RECTANGLE":
								Rectangle rectangle = new Rectangle(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										readVal(decompLog[6]),
										readVal(decompLog[5]),
										rgb2Color(decompLog[7]),
										rgb2Color(decompLog[8]),
										Boolean.parseBoolean(decompLog[9])
								);
								currentCommandLine = new ToFrontCommand(model, (Rectangle) findShapeInModel(rectangle));
								
								break;
							case "DONUT":
								Donut donut = new Donut(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										readVal(decompLog[5]),
										readVal(decompLog[6]),
										rgb2Color(decompLog[7]),
										rgb2Color(decompLog[8]),
										Boolean.parseBoolean(decompLog[9])
								);
								currentCommandLine = new ToFrontCommand(model, (Donut) findShapeInModel(donut));
								
								break;
							case "CIRCLE":
								Circle circle = new Circle(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										readVal(decompLog[5]),
										rgb2Color(decompLog[6]),
										rgb2Color(decompLog[7]),
										Boolean.parseBoolean(decompLog[8])
								);
								currentCommandLine = new ToFrontCommand(model, (Circle) findShapeInModel(circle));
								
								break;
							case "HEXAGON":
								HexagonAdapter hexagon = new HexagonAdapter(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])), 
										readVal(decompLog[5]),
										rgb2Color(decompLog[6]),
										rgb2Color(decompLog[7]),
										Boolean.parseBoolean(decompLog[8])
								);
								currentCommandLine = new ToFrontCommand(model, (HexagonAdapter) findShapeInModel(hexagon));
								
								break;
							default:
								flag = true;
								
								break;
						}
						
						executeLogLine(false);
						
						break;
					case "BringToFront":
						switch (decompLog[1]) {
							case "POINT":
								Point point = new Point(
										readVal(decompLog[3]),
										readVal(decompLog[4]), 
										rgb2Color(decompLog[5]),
										Boolean.parseBoolean(decompLog[6])
								);
								currentCommandLine = new BringToFrontCommand(model, (Point) findShapeInModel(point));
								
								break;
							case "LINE":
								Line line = new Line(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										new Point(readVal(decompLog[5]), readVal(decompLog[6])),
										rgb2Color(decompLog[7]),
										Boolean.parseBoolean(decompLog[8])
								);
								currentCommandLine = new BringToFrontCommand(model, (Line) findShapeInModel(line));
								
								break;
							case "RECTANGLE":
								Rectangle rectangle = new Rectangle(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										readVal(decompLog[6]),
										readVal(decompLog[5]),
										rgb2Color(decompLog[7]),
										rgb2Color(decompLog[8]),
										Boolean.parseBoolean(decompLog[9])
								);
								currentCommandLine = new BringToFrontCommand(model, (Rectangle) findShapeInModel(rectangle));
								
								break;
							case "DONUT":
								Donut donut = new Donut(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										readVal(decompLog[5]),
										readVal(decompLog[6]),
										rgb2Color(decompLog[7]),
										rgb2Color(decompLog[8]),
										Boolean.parseBoolean(decompLog[9])
								);
								currentCommandLine = new BringToFrontCommand(model, (Donut) findShapeInModel(donut));
								
								break;
							case "CIRCLE":
								Circle circle = new Circle(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])),
										readVal(decompLog[5]),
										rgb2Color(decompLog[6]),
										rgb2Color(decompLog[7]),
										Boolean.parseBoolean(decompLog[8])
								);
								currentCommandLine = new BringToFrontCommand(model, (Circle) findShapeInModel(circle));
								
								break;
							case "HEXAGON":
								HexagonAdapter hexagon = new HexagonAdapter(
										new Point(readVal(decompLog[3]), readVal(decompLog[4])), 
										readVal(decompLog[5]),
										rgb2Color(decompLog[6]),
										rgb2Color(decompLog[7]),
										Boolean.parseBoolean(decompLog[8])
								);
								currentCommandLine = new BringToFrontCommand(model, (HexagonAdapter) findShapeInModel(hexagon));
								
								break;
							default:
								flag = true;
								
								break;
						}
						
						executeLogLine(false);
						
						break;
					case "Undo":
						undo();
						
						break;
					case "Redo":
						redo();
						
						break;
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "The log is not valid!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void saveAsDraw() {
		int choice = JOptionPane.showConfirmDialog(null, "Do you want to save as a draw?", "Save a draw", JOptionPane.YES_NO_OPTION);
		
		if (choice == JOptionPane.YES_OPTION) {
			FileManager fm = new FileManager(new DrawFile(model.getShapes()));
			
			fm.save();
		}
	}
	
	public void saveAsLog() {
		int choice = JOptionPane.showConfirmDialog(null, "Do you want to save as a log?", "Save a log", JOptionPane.YES_NO_OPTION);
		
		if (choice == JOptionPane.YES_OPTION) {
			FileManager fm = new FileManager(new LogFile(frame.getDlm()));
			
			fm.save();
		}
	}
	
	public void clearSelection() {
		ListIterator<Shape> it = model.getShapes().listIterator(model.getShapes().size());
		
		while (it.hasPrevious()) {
			Shape tmpShape = it.previous();
			
			if (tmpShape.isSelected()) {
				Command ssc = new SelectShapeCommand(tmpShape);
				
				ssc.unexecute();
				
				undo.push(ssc);
				redo.clear();
				
				frame.getView().repaint();
				frame.updateLogList(ssc.toString());
				
				selectedShape.setModel(model);
			}
		}
	}
	
	private String bracketrim(String text) {
		return text.substring(1, text.length() - 1);
	}
	
	private int readVal(String variable) {
		return Integer.parseInt(variable.split("=")[1]);
	}
	
	private Color rgb2Color(String rgb) {
		String[] el = rgb.substring(6, rgb.length() - 1).split(",");
		
		int red = Integer.parseInt(el[0]);
		int green = Integer.parseInt(el[1]);
		int blue = Integer.parseInt(el[2]);
		
		return new Color(red, green, blue);
	}
	
	private Shape findShapeInModel(Shape shape) {
		ListIterator<Shape> it = model.getShapes().listIterator(model.getShapes().size());
		
		while (it.hasPrevious()) {
			Shape tmpShape = it.previous();
			
			if (tmpShape.compareTo(shape)) {
				return tmpShape;
			}
		}
		
		return null;
	}
	
	private Shape findShapeInModel(Shape shapeObj, ShapeStatus shapeStatus) {
		ListIterator<Shape> it = model.getShapes().listIterator(model.getShapes().size());
		
		while (it.hasPrevious()) {
			Shape tmpShape = it.previous();
			
			switch (shapeStatus) {
				case POINT:
					if (tmpShape instanceof Point) {
						if (tmpShape.compareTo(shapeObj)) {
							currentShape = tmpShape;
							
							return tmpShape;
						}
					}
					
					break;
				case LINE:
					if (tmpShape instanceof Line) {
						if (tmpShape.compareTo(shapeObj)) {
							currentShape = tmpShape;
							
							return tmpShape;
						}
					}
					
					break;
				case RECTANGLE:
					if (tmpShape instanceof Rectangle) {
						if (tmpShape.compareTo(shapeObj)) {
							currentShape = tmpShape;
							
							return tmpShape;
						}
					}
					
					break;
				case DONUT:
					if (tmpShape instanceof Donut) {
						if (tmpShape.compareTo(shapeObj)) {
							currentShape = tmpShape;
							
							return tmpShape;
						}
					}
					
					break;
				case CIRCLE:
					if (tmpShape instanceof Circle) {
						if (tmpShape.compareTo(shapeObj)) {
							currentShape = tmpShape;
							
							return tmpShape;
						}
					}
					
					break;
				case HEXAGON:
					if (tmpShape instanceof HexagonAdapter) {
						if (tmpShape.compareTo(shapeObj)) {
							currentShape = tmpShape;
							
							return tmpShape;
						}
					}
					
					break;
				default:
					break;
			}
		}
		
		return null;
	}
	
	private void executeLogLine(boolean isSelectShapeCommand) {
		if (!flag) {
			flag = false;
			
			if (isSelectShapeCommand) {
				if (!currentShape.isSelected()) {
					currentCommandLine.execute();
				} else {
					currentCommandLine.unexecute();
				}
				
				currentShape = null;
				
				frame.getTglbtnSelect().setSelected(true);
			} else {
				currentCommandLine.execute();
			}
			
			undo.push(currentCommandLine);
			redo.clear();
			
			frame.getView().repaint();
			frame.updateLogList(currentCommandLine.toString());
			frame.getBtnUndo().setEnabled(!undo.isEmpty());
			frame.getBtnRedo().setEnabled(!redo.isEmpty());
			
			selectedShape.setModel(model);
			
			currentCommandLine = null;
		}
	}
	
	private void changeActiveColorFromDialog(boolean colorStatus, boolean innerColorStatus, Color color, Color innerColor) {
		if (colorStatus) {
			frame.setColor(color);
			frame.getTxtColor().setBackground(color);
		}
		
		if (innerColorStatus) {
			frame.setInnerColor(innerColor);
			frame.getTxtInnerColor().setBackground(innerColor);
		}
	}
}
