package mvc;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.border.EtchedBorder;

import helper.ShapeStatus;

import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Toolkit;

public class DrawingFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private DrawingController controller;
	private DrawingView view = new DrawingView();
	
	private DefaultListModel<String> dlm = new DefaultListModel<String>();
	
	private JPanel shapePanel;
	private JPanel northPanel;
	private JPanel eastPanel;
	private JPanel logPanel;

	private JToggleButton tglbtnPoint;
	private JToggleButton tglbtnLine;
	private JToggleButton tglbtnRectangle;
	private JToggleButton tglbtnCircle;
	private JToggleButton tglbtnDonut;
	private JToggleButton tglbtnHexagon;
	private JToggleButton tglbtnSelect;
	private JToggleButton tglbtnModify;
	private JToggleButton tglbtnRemove;
	
	private JButton btnToBack;
	private JButton btnBringToBack;
	private JButton btnToFront;
	private JButton btnBringToFront;
	private JButton btnUndo;
	private JButton btnRedo;
	private JButton btnColor;
	private JButton btnInnerColor;
	private JButton btnSwapColor;
	private JButton btnNextLine;
	
	private ButtonGroup bgShapeAndOptions;
	private ButtonGroup bgMode;

	private JLabel lblMode;
	private JLabel lblShape;
	private JLabel lblPosition;
	private JLabel lblColor;
	private JLabel lblInnerColor;
	
	private JTextField txtColor;
	private JTextField txtInnerColor;
	
	private JScrollPane scrollPane;
	
	private JList<String> logList;
	
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmNew;
	private JMenu mnOpen;
	private JMenuItem mntmOpenDraw;
	private JMenuItem mntmOpenLog;
	private JMenu mnSaveAs;
	private JMenuItem mntmSaveDraw;
	private JMenuItem mntmSaveLog;
	private JMenuItem mntmExit;
	
	private Color color;
	private Color innerColor;
	
	public ShapeStatus choosenShape;
	
	public DrawingFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(DrawingFrame.class.getResource("/assets/icon.png")));
		setBounds(new Rectangle(50, 50, 1280, 720));
		setTitle("IT54/2018 Brckalo Milan");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		color = Color.BLACK;
		innerColor = Color.WHITE;
		
		shapePanel = new JPanel();
		shapePanel.setBorder(new EmptyBorder(10, 0, 10, 0));
		shapePanel.setBackground(Color.LIGHT_GRAY);
		
		northPanel = new JPanel();
		northPanel.setBorder(new EmptyBorder(0, 0, 10, 10));
		northPanel.setBackground(Color.GRAY);
		
		eastPanel = new JPanel();
		eastPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
		eastPanel.setBackground(Color.LIGHT_GRAY);
		
		logPanel = new JPanel();
		logPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		lblMode = new JLabel("Mode:");
		lblShape = new JLabel("Shape:");
		lblPosition = new JLabel("Position:");
		lblColor = new JLabel("Color:");
		lblInnerColor = new JLabel("Inner color:");
		JLabel lblLog = new JLabel("Log:");
		
		getContentPane().add(view, BorderLayout.CENTER);
		getContentPane().add(shapePanel, BorderLayout.WEST);
		getContentPane().add(northPanel, BorderLayout.NORTH);
		getContentPane().add(eastPanel, BorderLayout.EAST);
		getContentPane().add(logPanel, BorderLayout.SOUTH);
		
		tglbtnPoint = new JToggleButton("Point");
		tglbtnLine = new JToggleButton("Line");
		tglbtnRectangle = new JToggleButton("Rectangle");
		tglbtnCircle = new JToggleButton("Circle");
		tglbtnDonut = new JToggleButton("Donut");
		tglbtnHexagon = new JToggleButton("Hexagon");
		
		tglbtnSelect = new JToggleButton("Select");
		tglbtnModify = new JToggleButton("Modify");
		tglbtnModify.setEnabled(false);
		tglbtnRemove = new JToggleButton("Remove");
		tglbtnRemove.setEnabled(false);
		
		bgShapeAndOptions = new ButtonGroup();
		bgMode = new ButtonGroup();
		
		bgShapeAndOptions.add(tglbtnPoint);
		bgShapeAndOptions.add(tglbtnLine);
		bgShapeAndOptions.add(tglbtnRectangle);
		bgShapeAndOptions.add(tglbtnCircle);
		bgShapeAndOptions.add(tglbtnDonut);
		bgShapeAndOptions.add(tglbtnHexagon);
		bgShapeAndOptions.add(tglbtnSelect);
		
		bgMode.add(tglbtnModify);
		bgMode.add(tglbtnRemove);
		
		tglbtnPoint.setIcon(new ImageIcon(getClass().getResource("/assets/point.png")));
		tglbtnPoint.setHorizontalAlignment(SwingConstants.LEFT);
		tglbtnPoint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				choosenShape = ShapeStatus.POINT;
			}
		});
		
		tglbtnLine.setIcon(new ImageIcon(getClass().getResource("/assets/line.png")));
		tglbtnLine.setHorizontalAlignment(SwingConstants.LEFT);
		tglbtnLine.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				choosenShape = ShapeStatus.LINE;
			}
		});
		
		tglbtnRectangle.setIcon(new ImageIcon(getClass().getResource("/assets/rectangle.png")));
		tglbtnRectangle.setHorizontalAlignment(SwingConstants.LEFT);
		tglbtnRectangle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				choosenShape = ShapeStatus.RECTANGLE;
			}
		});
		
		tglbtnCircle.setIcon(new ImageIcon(getClass().getResource("/assets/circle.png")));
		tglbtnCircle.setHorizontalAlignment(SwingConstants.LEFT);
		tglbtnCircle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				choosenShape = ShapeStatus.CIRCLE;
			}
		});
		
		tglbtnDonut.setIcon(new ImageIcon(getClass().getResource("/assets/donut.png")));
		tglbtnDonut.setHorizontalAlignment(SwingConstants.LEFT);
		tglbtnDonut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				choosenShape = ShapeStatus.DONUT;
			}
		});
		
		tglbtnHexagon.setIcon(new ImageIcon(getClass().getResource("/assets/hexagon.png")));
		tglbtnHexagon.setHorizontalAlignment(SwingConstants.LEFT);
		tglbtnHexagon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				choosenShape = ShapeStatus.HEXAGON;
			}
		});
		
		tglbtnSelect.setIcon(new ImageIcon(getClass().getResource("/assets/select.png")));
		tglbtnSelect.setHorizontalAlignment(SwingConstants.LEFT);
		tglbtnSelect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.clearSelection();
			}
		});
		
		tglbtnModify.setIcon(new ImageIcon(getClass().getResource("/assets/modify.png")));
		tglbtnModify.setHorizontalAlignment(SwingConstants.LEFT);
		tglbtnModify.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.modifyShape();
			}
		});
		
		tglbtnRemove.setIcon(new ImageIcon(getClass().getResource("/assets/remove.png")));
		tglbtnRemove.setHorizontalAlignment(SwingConstants.LEFT);
		tglbtnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.removeShape();
			}
		});
		

		btnToBack = new JButton("To Back");
		btnToBack.setEnabled(false);
		btnToBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.toBack();
			}
		});
		
		btnBringToBack = new JButton("Bring To Back");
		btnBringToBack.setEnabled(false);
		btnBringToBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.bringToBack();
			}
		});
		
		btnToFront = new JButton("To Front");
		btnToFront.setEnabled(false);
		btnToFront.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.toFront();
			}
		});
		
		btnBringToFront = new JButton("Bring To Front");
		btnBringToFront.setEnabled(false);
		btnBringToFront.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.bringToFront();
			}
		});
		
		btnUndo = new JButton("Undo");
		btnUndo.setEnabled(false);
		btnUndo.setIcon(new ImageIcon(getClass().getResource("/assets/undo.png")));
		btnUndo.setHorizontalAlignment(SwingConstants.LEFT);
		btnUndo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.undo();
			}
		});
		
		btnRedo = new JButton("Redo");
		btnRedo.setEnabled(false);
		btnRedo.setIcon(new ImageIcon(getClass().getResource("/assets/redo.png")));
		btnRedo.setHorizontalAlignment(SwingConstants.LEFT);
		btnRedo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.redo();
			}
		});
		
		btnColor = new JButton("");
		btnColor.setIcon(new ImageIcon(DrawingFrame.class.getResource("/assets/color-wheel.png")));
		btnColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = JColorChooser.showDialog(getContentPane(), "Choose a color", color);
				
				if (color != null) {
					txtColor.setBackground(color);
				}
			}
		});
		
		btnInnerColor = new JButton("");
		btnInnerColor.setIcon(new ImageIcon(DrawingFrame.class.getResource("/assets/color-wheel.png")));
		btnInnerColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				innerColor = JColorChooser.showDialog(getContentPane(), "Choose a color", innerColor);
				
				if (innerColor != null) {
					txtInnerColor.setBackground(innerColor);
				}
			}
		});
		
		btnSwapColor = new JButton("");
		btnSwapColor.setIcon(new ImageIcon(DrawingFrame.class.getResource("/assets/swap.png")));
		btnSwapColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color tmpColor = txtColor.getBackground();
				color = txtInnerColor.getBackground();
				innerColor = tmpColor;
				
				txtColor.setBackground(color);
				txtInnerColor.setBackground(innerColor);
			}
		});
		
		btnNextLine = new JButton("Next Line");
		btnNextLine.setEnabled(false);
		btnNextLine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.interpreter();
			}
		});
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.create();
			}
		});
		mntmNew.setIcon(new ImageIcon(DrawingFrame.class.getResource("/assets/new.png")));
		mnFile.add(mntmNew);
		
		mnOpen = new JMenu("Open");
		mnOpen.setIcon(new ImageIcon(DrawingFrame.class.getResource("/assets/open.png")));
		mnFile.add(mnOpen);
		
		mntmOpenDraw = new JMenuItem("Draw");
		mntmOpenDraw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.openDraw();
			}
		});
		mntmOpenDraw.setIcon(new ImageIcon(DrawingFrame.class.getResource("/assets/draw.png")));
		mnOpen.add(mntmOpenDraw);
		
		mntmOpenLog = new JMenuItem("Log");
		mntmOpenLog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.openLog();
			}
		});
		mntmOpenLog.setIcon(new ImageIcon(DrawingFrame.class.getResource("/assets/log.png")));
		mnOpen.add(mntmOpenLog);
		
		mnSaveAs = new JMenu("Save as");
		mnSaveAs.setIcon(new ImageIcon(DrawingFrame.class.getResource("/assets/save.png")));
		mnFile.add(mnSaveAs);
		
		mntmSaveDraw = new JMenuItem("Draw");
		mntmSaveDraw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveAsDraw();
			}
		});
		mntmSaveDraw.setIcon(new ImageIcon(DrawingFrame.class.getResource("/assets/draw.png")));
		mnSaveAs.add(mntmSaveDraw);
		
		mntmSaveLog = new JMenuItem("Log");
		mntmSaveLog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveAsLog();
			}
		});
		mntmSaveLog.setIcon(new ImageIcon(DrawingFrame.class.getResource("/assets/log.png")));
		mnSaveAs.add(mntmSaveLog);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.setIcon(new ImageIcon(DrawingFrame.class.getResource("/assets/exit.png")));
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "Do you want to close the app?", "Close an application", JOptionPane.YES_NO_OPTION);
				
				if (choice == JOptionPane.YES_OPTION) {
					System.exit(EXIT_ON_CLOSE);
				}
			}
		});
		mnFile.add(mntmExit);
		
		txtColor = new JTextField();
		txtColor.setBackground(Color.BLACK);
		txtColor.setEditable(false);
		txtColor.setColumns(10);
		
		txtInnerColor = new JTextField();
		txtInnerColor.setEditable(false);
		txtInnerColor.setColumns(10);
		txtInnerColor.setBackground(Color.WHITE);
		
		logPanel.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		
		logPanel.add(scrollPane);
		
		logList = new JList<String>();
		
		scrollPane.setViewportView(logList);
		logList.setModel(dlm);
		
		GroupLayout gl_shapePanel = new GroupLayout(shapePanel);
		gl_shapePanel.setHorizontalGroup(
			gl_shapePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_shapePanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_shapePanel.createParallelGroup(Alignment.LEADING)
						.addComponent(tglbtnPoint, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
						.addComponent(tglbtnLine, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
						.addComponent(tglbtnRectangle, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
						.addComponent(tglbtnCircle, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
						.addComponent(tglbtnDonut, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
						.addComponent(tglbtnHexagon, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
						.addComponent(lblShape, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_shapePanel.setVerticalGroup(
			gl_shapePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_shapePanel.createSequentialGroup()
					.addComponent(lblShape)
					.addGap(7)
					.addComponent(tglbtnPoint)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tglbtnLine)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tglbtnRectangle)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tglbtnCircle)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tglbtnDonut)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tglbtnHexagon)
					.addContainerGap(350, Short.MAX_VALUE))
		);
		shapePanel.setLayout(gl_shapePanel);
		
		GroupLayout gl_northPanel = new GroupLayout(northPanel);
		gl_northPanel.setHorizontalGroup(
			gl_northPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_northPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMode, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tglbtnSelect, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tglbtnModify, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tglbtnRemove, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblColor, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnColor)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtColor, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblInnerColor)
					.addGap(18)
					.addComponent(btnInnerColor)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtInnerColor, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnSwapColor)
					.addPreferredGap(ComponentPlacement.RELATED, 214, Short.MAX_VALUE)
					.addComponent(btnRedo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnUndo))
		);
		gl_northPanel.setVerticalGroup(
			gl_northPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_northPanel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_northPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_northPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(tglbtnSelect)
							.addComponent(lblMode)
							.addComponent(tglbtnModify)
							.addComponent(tglbtnRemove)
							.addComponent(lblColor)
							.addComponent(btnColor, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtColor, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblInnerColor))
						.addGroup(gl_northPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(txtInnerColor, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnInnerColor, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnUndo, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnRedo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnSwapColor, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))))
		);
		northPanel.setLayout(gl_northPanel);
		
		GroupLayout gl_eastPanel = new GroupLayout(eastPanel);
		gl_eastPanel.setHorizontalGroup(
			gl_eastPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_eastPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_eastPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnBringToBack, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
						.addComponent(btnToBack, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
						.addComponent(lblPosition, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
						.addComponent(btnToFront, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
						.addComponent(btnBringToFront, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
						.addComponent(lblLog, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
						.addComponent(btnNextLine, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_eastPanel.setVerticalGroup(
			gl_eastPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_eastPanel.createSequentialGroup()
					.addComponent(lblPosition)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnToBack)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnBringToBack)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnToFront)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnBringToFront)
					.addGap(18)
					.addComponent(lblLog)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNextLine)
					.addContainerGap(275, Short.MAX_VALUE))
		);
		eastPanel.setLayout(gl_eastPanel);
		
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isDrawMode() && choosenShape != null) {
					controller.drawShape(choosenShape, e);
				} else if (isSelectMode()) {
					controller.selectShape(e);
				} else {
					//
				}
			}
		});
	}

	public DrawingController getController() {
		return controller;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}

	public DrawingView getView() {
		return view;
	}

	public void setView(DrawingView view) {
		this.view = view;
	}

	public DefaultListModel<String> getDlm() {
		return dlm;
	}

	public void setDlm(DefaultListModel<String> dlm) {
		this.dlm = dlm;
	}

	public JToggleButton getTglbtnPoint() {
		return tglbtnPoint;
	}

	public void setTglbtnPoint(JToggleButton tglbtnPoint) {
		this.tglbtnPoint = tglbtnPoint;
	}

	public JToggleButton getTglbtnLine() {
		return tglbtnLine;
	}

	public void setTglbtnLine(JToggleButton tglbtnLine) {
		this.tglbtnLine = tglbtnLine;
	}

	public JToggleButton getTglbtnRectangle() {
		return tglbtnRectangle;
	}

	public void setTglbtnRectangle(JToggleButton tglbtnRectangle) {
		this.tglbtnRectangle = tglbtnRectangle;
	}

	public JToggleButton getTglbtnCircle() {
		return tglbtnCircle;
	}

	public void setTglbtnCircle(JToggleButton tglbtnCircle) {
		this.tglbtnCircle = tglbtnCircle;
	}

	public JToggleButton getTglbtnDonut() {
		return tglbtnDonut;
	}

	public void setTglbtnDonut(JToggleButton tglbtnDonut) {
		this.tglbtnDonut = tglbtnDonut;
	}

	public JToggleButton getTglbtnHexagon() {
		return tglbtnHexagon;
	}

	public void setTglbtnHexagon(JToggleButton tglbtnHexagon) {
		this.tglbtnHexagon = tglbtnHexagon;
	}

	public JToggleButton getTglbtnSelect() {
		return tglbtnSelect;
	}

	public void setTglbtnSelect(JToggleButton tglbtnSelect) {
		this.tglbtnSelect = tglbtnSelect;
	}

	public JToggleButton getTglbtnModify() {
		return tglbtnModify;
	}

	public void setTglbtnModify(JToggleButton tglbtnModify) {
		this.tglbtnModify = tglbtnModify;
	}

	public JToggleButton getTglbtnRemove() {
		return tglbtnRemove;
	}

	public void setTglbtnRemove(JToggleButton tglbtnRemove) {
		this.tglbtnRemove = tglbtnRemove;
	}
	
	public JButton getBtnToBack() {
		return btnToBack;
	}

	public void setBtnToBack(JButton btnToBack) {
		this.btnToBack = btnToBack;
	}

	public JButton getBtnBringToBack() {
		return btnBringToBack;
	}

	public void setBtnBringToBack(JButton btnBringToBack) {
		this.btnBringToBack = btnBringToBack;
	}

	public JButton getBtnToFront() {
		return btnToFront;
	}

	public void setBtnToFront(JButton btnToFront) {
		this.btnToFront = btnToFront;
	}

	public JButton getBtnBringToFront() {
		return btnBringToFront;
	}

	public void setBtnBringToFront(JButton btnBringToFront) {
		this.btnBringToFront = btnBringToFront;
	}

	public JButton getBtnUndo() {
		return btnUndo;
	}

	public void setBtnUndo(JButton btnUndo) {
		this.btnUndo = btnUndo;
	}

	public JButton getBtnRedo() {
		return btnRedo;
	}

	public void setBtnRedo(JButton btnRedo) {
		this.btnRedo = btnRedo;
	}

	public JButton getBtnColor() {
		return btnColor;
	}

	public void setBtnColor(JButton btnColor) {
		this.btnColor = btnColor;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public void setBtnInnerColor(JButton btnInnerColor) {
		this.btnInnerColor = btnInnerColor;
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

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public JList<String> getLogList() {
		return logList;
	}

	public void setLogList(JList<String> logList) {
		this.logList = logList;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getInnerColor() {
		return innerColor;
	}

	public void setInnerColor(Color innerColor) {
		this.innerColor = innerColor;
	}
	
	public JButton getBtnSwapColor() {
		return btnSwapColor;
	}

	public void setBtnSwapColor(JButton btnSwapColor) {
		this.btnSwapColor = btnSwapColor;
	}

	public JButton getBtnNextLine() {
		return btnNextLine;
	}

	public void setBtnNextLine(JButton btnNextLine) {
		this.btnNextLine = btnNextLine;
	}

	public ButtonGroup getBgShapeAndOptions() {
		return bgShapeAndOptions;
	}

	public void setBgShapeAndOptions(ButtonGroup bgShapeAndOptions) {
		this.bgShapeAndOptions = bgShapeAndOptions;
	}

	public ButtonGroup getBgMode() {
		return bgMode;
	}

	public void setBgMode(ButtonGroup bgMode) {
		this.bgMode = bgMode;
	}

	private boolean isDrawMode() {
		return tglbtnPoint.isSelected() || tglbtnLine.isSelected() || tglbtnRectangle.isSelected() || tglbtnCircle.isSelected() || tglbtnDonut.isSelected() || tglbtnHexagon.isSelected(); 
	}
	
	private boolean isSelectMode() {
		return tglbtnSelect.isSelected();
	}
	
	public void updateLogList(String log) {
		this.dlm.addElement(log);
	}
}
