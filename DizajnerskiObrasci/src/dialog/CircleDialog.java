package dialog;

import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JColorChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class CircleDialog extends JDialog implements Validable {
	private static final long serialVersionUID = 1L;

	public boolean isOK;
	
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtRadius;
	private JTextField txtColor;
	private JTextField txtInnerColor;
	
	private JCheckBox chckbxColor;
	private JCheckBox chckbxInnerColor;
	
	public CircleDialog() {
		setTitle("Add/Edit Circle");
		setModal(true);
		setBounds(new Rectangle(100, 100, 512, 246));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		JLabel lblCenter = new JLabel("CENTER");
		lblCenter.setForeground(Color.RED);
		lblCenter.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		JLabel lblX = new JLabel("Coordinate of X");
		
		txtX = new JTextField();
		txtX.setColumns(10);
		
		JLabel lblY = new JLabel("Coordinate of Y");
		
		txtY = new JTextField();
		txtY.setColumns(10);
		
		JLabel lblRadius = new JLabel("Radius");
		
		txtRadius = new JTextField();
		txtRadius.setColumns(10);
		
		JLabel lblGraphic = new JLabel("GRAPHIC");
		lblGraphic.setForeground(Color.RED);
		lblGraphic.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		JLabel lblColor = new JLabel("Color");
		
		JButton btnColor = new JButton("Choose...");
		btnColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color circleColor = JColorChooser.showDialog(getContentPane(), "Choose a color", Color.BLACK);
				
				if (circleColor != null) {
					txtColor.setBackground(circleColor);
				}
			}
		});
		
		txtColor = new JTextField();
		txtColor.setBackground(Color.BLACK);
		txtColor.setEditable(false);
		txtColor.setColumns(10);
		
		JLabel lblInnerColor = new JLabel("Inner color");
		
		JButton btnInnerColor = new JButton("Choose...");
		btnInnerColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color circleColor = JColorChooser.showDialog(getContentPane(), "Choose a color", Color.WHITE);
				
				if (circleColor != null) {
					txtInnerColor.setBackground(circleColor);
				}
			}
		});
		
		txtInnerColor = new JTextField();
		txtInnerColor.setBackground(Color.WHITE);
		txtInnerColor.setEditable(false);
		txtInnerColor.setColumns(10);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isValidInput()) {
					isOK = false;
					
					setVisible(true);
					JOptionPane.showMessageDialog(null, "All fields must be filled in!", "Error", JOptionPane.ERROR_MESSAGE);
				} else if (!isNumeric()) {
					isOK = false;
					
					setVisible(true);
					JOptionPane.showMessageDialog(null, "All fields must be numeric!", "Error", JOptionPane.ERROR_MESSAGE);
				} else if (!isPositive()) {
					isOK = false;
					
					setVisible(true);
					JOptionPane.showMessageDialog(null, "All fields must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					isOK = true;
					
					dispose();
				}
			}
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		chckbxColor = new JCheckBox("As active");
		chckbxInnerColor = new JCheckBox("As active");
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCenter, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(lblInnerColor, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblY, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblX, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
									.addComponent(lblGraphic, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblColor, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(txtColor, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtInnerColor, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
									.addGap(6)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(btnInnerColor)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(chckbxInnerColor, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(btnColor)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(chckbxColor, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
									.addComponent(btnConfirm)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnCancel))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(txtY, Alignment.LEADING)
										.addComponent(txtX, Alignment.LEADING))
									.addPreferredGap(ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
									.addComponent(lblRadius, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblCenter)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblX)
						.addComponent(txtX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRadius)
						.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblY)
						.addComponent(txtY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(lblGraphic)
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblColor)
						.addComponent(txtColor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnColor)
						.addComponent(chckbxColor))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInnerColor)
						.addComponent(txtInnerColor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnInnerColor)
						.addComponent(chckbxInnerColor)
						.addComponent(btnCancel)
						.addComponent(btnConfirm))
					.addContainerGap(41, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
	}

	public JTextField getTxtX() {
		return txtX;
	}

	public void setTxtX(JTextField txtX) {
		this.txtX = txtX;
	}

	public JTextField getTxtY() {
		return txtY;
	}

	public void setTxtY(JTextField txtY) {
		this.txtY = txtY;
	}

	public JTextField getTxtRadius() {
		return txtRadius;
	}

	public void setTxtRadius(JTextField txtRadius) {
		this.txtRadius = txtRadius;
	}

	public JTextField getTxtColor() {
		return txtColor;
	}

	public void setTxtColor(JTextField txtColor) {
		this.txtColor = txtColor;
	}

	public JTextField getTxtInnerColor() {
		return txtInnerColor;
	}

	public void setTxtInnerColor(JTextField txtInnerColor) {
		this.txtInnerColor = txtInnerColor;
	}
	
	public JCheckBox getChckbxColor() {
		return chckbxColor;
	}

	public void setChckbxColor(JCheckBox chckbxColor) {
		this.chckbxColor = chckbxColor;
	}

	public JCheckBox getChckbxInnerColor() {
		return chckbxInnerColor;
	}

	public void setChckbxInnerColor(JCheckBox chckbxInnerColor) {
		this.chckbxInnerColor = chckbxInnerColor;
	}

	@Override
	public boolean isValidInput() {
		return txtX.getText().trim().isEmpty() || txtY.getText().trim().isEmpty() || txtRadius.getText().trim().isEmpty();
	}
	
	@Override
	public boolean isNumeric() {
		try {
			Integer.parseInt(txtX.getText().trim());
			Integer.parseInt(txtY.getText().trim());
			Integer.parseInt(txtRadius.getText().trim());
			
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public boolean isPositive() {
		return Integer.parseInt(txtRadius.getText().trim()) > 0;
	}
}
