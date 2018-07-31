package Gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

/**
 * This class will be connected to the view.
 * It will be linked to the gui model...
 * @author victo
 *
 */
class Controller implements  ActionListener
{
	
	GuiModel G_model;
	
	public Controller(GuiModel arg)
	{
		this.G_model = arg;
	}
	@Override
	
	
	/**
	 * When the start botton is pressed,this method will reports action to the model
	 * 
	 * <ul>
	 * <li>1. Direct output to another package if possible
	 * <li>2. tries to let the model to deal with it. 
	 * <li>3. It will get the http link if detected. 
	 * </ul>
	 * 
	 */
	public void actionPerformed(ActionEvent e) 
	{
		
		System.out.println(e.getActionCommand());
		//GuiModel.println(e.getActionCommand());
		String s = e.getActionCommand();
		switch(s)
		{
			case "Start":
				Start();
				break;
			case "ApproveSelection":
				ApproveSelection();
				break;
			case "CancelSelection":
				CancelSelection();
				break;
			case "Stop":
				stop();
				break;
			case "Continue":
				stop();
				break;
			case "ClipBoard Import":
				importFromClipBoard();
				break;
			default: 
				System.out.println(e.getActionCommand()+" is not implemented. " );
		}
	}
	

	private void importFromClipBoard() 
	{
		final String content =Untilities.sys.ClipBoard.getClipBoard();
		this.G_model.importFromClipBoard(content); 
		
	}
	private void stop() {
		this.G_model.StopBottonPressed();
		
	}
	private void CancelSelection() 
	{
		this.G_model.removeDirectory();
		this.G_model.closeFileChooser();
		
	}
	private void ApproveSelection() 
	{
		this.G_model.setDirectory();
		this.G_model.closeFileChooser();
	}
	private void Start() 
	{
		this.G_model.startExecute();
	}
	
	
	
	
	
}
