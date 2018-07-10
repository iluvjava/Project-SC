package Gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import java.awt.Window.Type;
import java.io.File;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Dialog.ModalityType;


/**
 * A file chooser component contains in a dialog frame and asks for user input.
 * @author Dashie
 *
 */
public class FileChooserDialog extends JDialog {
	private JFileChooser fileChooser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FileChooserDialog dialog = new FileChooserDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FileChooserDialog() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		
		
		setResizable(false);
		setType(Type.POPUP);
		setAlwaysOnTop(true);
		setBounds(100, 100, 1239, 714);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(1);
			getContentPane().add(fileChooser);
			fileChooser.setCurrentDirectory(new File(""));
		}	
		this.requestFocusInWindow();
	}

	public JFileChooser getFileChooser() {
		return fileChooser;
	}
}
