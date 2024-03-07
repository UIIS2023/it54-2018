package dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class DonutDialog extends JDialog implements Validable {
	private static final long serialVersionUID = 1L;

	public boolean isOK;
	
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtRadius;
	private JTextField txtInnerRadius;
	private JTextField txtColor;
	private JTextField txtInnerColor;
	
	private JCheckBox chckbxColor;
	private JCheckBox chckbxInnerColor;
	
	public DonutDialog() {
		setTitle("Add/Edit Donut");
		setModal(true);
		setBounds(new Rectangle(100, 100, 512, 256));
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
		
		txtRadius = new JTextField();
		txtRadius.setColumns(10);
		
		JLabel lblOuter = new JLabel("Outer");
		
		JLabel lblRadius = new JLabel("RADIUS");
		lblRadius.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRadius.setHorizontalTextPosition(SwingConstants.LEADING);
		lblRadius.setForeground(Color.RED);
		lblRadius.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		JLabel lblInner = new JLabel("Inner");
		
		txtInnerRadius = new JTextField();
		txtInnerRadius.setColumns(10);
		
		JLabel lblGraphic = new JLabel("GRAPHIC");
		lblGraphic.setForeground(Color.RED);
		lblGraphic.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		JLabel lblColor = new JLabel("Color");
		
		JButton btnColor = new JButton("Choose...");
		btnColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color donutColor = JColorChooser.showDialog(getContentPane(), "Choose a color", Color.BLACK);
				
				if (donutColor != null) {
					txtColor.setBackground(donutColor);
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
				Color donutColor = JColorChooser.showDialog(getContentPane(), "Choose a color", Color.WHITE);
				
				if (donutColor != null) {
					txtInnerColor.setBackground(donutColor);
				}
			}
		});
		
		txtInnerColor = new JTextField();
		txtInnerColor.setEditable(false);
		txtInnerColor.setBackground(Color.WHITE);
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
				} else if (Integer.parseInt(txtInnerRadius.getText().trim()) >= Integer.parseInt(txtRadius.getText().trim())) {
					isOK = false;

					setVisible(true);
					JOptionPane.showMessageDialog(null, "Inner radius must be smaller than outer radius!", "Error", JOptionPane.ERROR_MESSAGE);
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
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblInnerColor, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(txtInnerColor, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnInnerColor))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(lblY, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblX, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
									.addComponent(lblGraphic, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(lblColor, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(txtX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
									.addComponent(txtY, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txtColor, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnColor)))))
						.addComponent(lblCenter, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(chckbxInnerColor, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
							.addComponent(btnConfirm)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCancel))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblInner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(txtInnerRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblOuter, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblRadius, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)))
						.addComponent(chckbxColor, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCenter)
						.addComponent(lblRadius))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblX)
						.addComponent(txtX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblOuter))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblY)
						.addComponent(txtY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtInnerRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblInner))
					.addGap(18)
					.addComponent(lblGraphic)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblColor)
						.addComponent(btnColor)
						.addComponent(txtColor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(chckbxColor))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInnerColor)
						.addComponent(btnInnerColor)
						.addComponent(txtInnerColor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnConfirm)
						.addComponent(btnCancel)
						.addComponent(chckbxInnerColor))
					.addContainerGap(29, Short.MAX_VALUE))
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

	public JTextField getTxtInnerRadius() {
		return txtInnerRadius;
	}

	public void setTxtInnerRadius(JTextField txtInnerRadius) {
		this.txtInnerRadius = txtInnerRadius;
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
		return txtX.getText().trim().isEmpty() || txtY.getText().trim().isEmpty() || txtInnerRadius.getText().trim().isEmpty() || txtRadius.getText().trim().isEmpty();
	}
	
	@Override
	public boolean isNumeric() {
		try {
			Integer.parseInt(txtX.getText().trim());
			Integer.parseInt(txtY.getText().trim());
			Integer.parseInt(txtInnerRadius.getText().trim());
			Integer.parseInt(txtRadius.getText().trim());
			
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public boolean isPositive() {
		return Integer.parseInt(txtInnerRadius.getText().trim()) > 0 && Integer.parseInt(txtRadius.getText().trim()) > 0;
	}
}
