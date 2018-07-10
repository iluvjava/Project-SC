package Gui;

import java.awt.EventQueue;
import java.io.File;
import java.util.EventListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JSpinner;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JCheckBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTree;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.JMenu;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.JLayeredPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.Window.Type;
import net.miginfocom.swing.MigLayout;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.TextArea;

public class ScraperGui implements View{

	private JFrame frame;
	private JTextField textField;
	private JSpinner spinner;
	private JButton btnStart;
	private JMenuItem mntmOpenFilechooser;
	private Object G_mainListener;
	private JPanel panel;
	private FileChooserDialog G_Filechooserdialogwin;
	private JScrollPane scrollPane;
	private TextArea textArea;
	private JButton btnStop;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScraperGui window = new ScraperGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ScraperGui() {
		try {
			UIManager.setLookAndFeel(
				    UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // this code will get the native look and feel of the 
				//system. 
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setAutoRequestFocus(false);
		frame.setBounds(100, 100, 966, 661);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		btnStart = new JButton("Start");
		frame.getContentPane().add(btnStart, BorderLayout.SOUTH);
		btnStart.setToolTipText(ToolTips.StartBottonTips.ToolTips);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		textField = new JTextField();
		textField.setToolTipText(ToolTips.TextFiledTips.ToolTips);  // Tool Tip!
		panel.add(textField);
		textField.setColumns(50);
		
		spinner = new JSpinner();
		spinner.setToolTipText(ToolTips.SwingSpinnerTips.ToolTips);
		
		panel.add(spinner);
		spinner.setModel(new SpinnerNumberModel(15, 1, 500, 1));
		
		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		textArea = new TextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		scrollPane.setRowHeaderView(btnStop);
		
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmOpenFilechooser = new JMenuItem("Open FileChooser");
		
		
		// Can throw null exception.
		mntmOpenFilechooser.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(G_Filechooserdialogwin==null)
				{
				FileChooserDialog temp = new FileChooserDialog();
				
				System.out.println("Adding listener to dialog:"+G_mainListener);
				temp.getFileChooser().addActionListener((ActionListener) G_mainListener);
				
				G_Filechooserdialogwin = temp;
				temp.setVisible(true);
				}
				else
				{
					G_Filechooserdialogwin.setVisible(true);
				}
			}
		});
		
		
		mntmOpenFilechooser.setIcon(new ImageIcon(ScraperGui.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		mntmOpenFilechooser.setSelectedIcon(new ImageIcon(ScraperGui.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		mnFile.add(mntmOpenFilechooser);
		
		
	}
	
	
	/**
	 * Return that component that can help the user to choose a file. 
	 * @return
	 */
	
	
	/**
	 * Return the numeric selection component of the window
	 * @return
	 */
	public JSpinner getSpinner() {
		return spinner;
	}
	
	/**
	 * Return input text field of the component
	 * @return
	 */
	public JTextField getTextField() {
		return textField;
	}
	

	
	/**
	 * 
	 * @return
	 * The start button of the window. 
	 */
	public JButton getBtnStart() {
		return btnStart;
	}
	public JMenuItem getMntmOpenFilechooser() {
		return mntmOpenFilechooser;
	}

	public JFrame getFrame() {
		return frame;
	}

	@Override
	public View addListener(Object l) 
	{
		if(l instanceof ActionListener)
		{
			this.btnStart.addActionListener((ActionListener) l);
			this.btnStop.addActionListener((ActionListener) l);
		}
		
		this.G_mainListener = l;
		return this;
	}

	
	
	/**
	 * 
	 * @return
	 * The file chooser object to the model, null if it haven't been opened yet.
	 */
	public JFileChooser getFileChooser()
	{
		if(this.G_Filechooserdialogwin == null)return null;
		return this.G_Filechooserdialogwin.getFileChooser();
	}
	
	public FileChooserDialog getFileChooserDialog()
	{
		return G_Filechooserdialogwin;
	}
	public TextArea getTextArea() {
		return textArea;
	}

	
	public JButton getBtnPause() {
		return btnStop;
	}
}
