package strategy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import shape.Shape;

public class DrawFile implements File {
	private ArrayList<Shape> shapes;
	
	public DrawFile(ArrayList<Shape> shapes) {
		this.shapes = shapes;
	}
	
	@Override
	public void save() {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
		
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.ser", "ser"));
		fileChooser.setAcceptAllFileFilterUsed(true);
		
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			java.io.File draw = new java.io.File(fileChooser.getSelectedFile().getAbsolutePath());
			
			if (!draw.exists()) {
				draw = new java.io.File(fileChooser.getSelectedFile().getAbsolutePath() + ".ser");
			} else {
				int choice = JOptionPane.showConfirmDialog(null, "Do you want to replace a existing draw?", "Replace a draw", JOptionPane.YES_NO_OPTION);
				
				if (choice == JOptionPane.NO_OPTION) {
					draw = new java.io.File(fileChooser.getSelectedFile().getAbsolutePath() + ".ser");
				}
			}
			
			try { 
				FileOutputStream fos = new FileOutputStream(draw);
				ObjectOutputStream oos = new ObjectOutputStream(fos);

				oos.writeObject(shapes);
				
				oos.close();
				fos.close();

				JOptionPane.showMessageDialog(null, "The file has been saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "The file cannot be saved!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
