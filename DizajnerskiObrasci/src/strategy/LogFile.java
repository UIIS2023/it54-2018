package strategy;

import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LogFile implements File {
	private DefaultListModel<String> dlm;  
	
	public LogFile(DefaultListModel<String> dlm) {
		this.dlm = dlm;
	}
	
	@Override
	public void save() {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
		
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.log", "log"));
		fileChooser.setAcceptAllFileFilterUsed(true);
		
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			java.io.File log = new java.io.File(fileChooser.getSelectedFile().getAbsolutePath());
			
			if (!log.exists()) {
				log = new java.io.File(fileChooser.getSelectedFile().getAbsolutePath() + ".log");
			} else {
				int choice = JOptionPane.showConfirmDialog(null, "Do you want to replace an existing log?", "Replace a log", JOptionPane.YES_NO_OPTION);
				
				if (choice == JOptionPane.NO_OPTION) {
					log = new java.io.File(fileChooser.getSelectedFile().getAbsolutePath() + ".log");
				}
			}
			
			try {
				PrintWriter pw = new PrintWriter(log);
				
				for (int i = 0; i < dlm.size(); i++) {
					pw.println(dlm.get(i));
				}
				
				pw.close();
				
				JOptionPane.showMessageDialog(null, "The file has been saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "The file cannot be saved!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
