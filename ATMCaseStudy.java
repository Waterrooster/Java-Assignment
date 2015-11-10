package atm;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ATMCaseStudy
{
	protected static JFrame multiThreadingFrame = new JFrame("Customer Multi Threading.");
	protected static JPanel multiThreadingPanel = new JPanel();
	protected static JButton newCustomerThread;
	protected static JButton closeWindow;
	protected static int windowCount = 0;		
// main method creates and runs the ATM
public static void main( String[] args )
{
	newCustomerThread = new JButton("New customer");
	closeWindow = new JButton("Close");
	multiThreadingFrame.add(multiThreadingPanel);
	multiThreadingPanel.add(newCustomerThread);
	multiThreadingPanel.add(closeWindow);
	
	if(windowCount<9)
	{
		newCustomerThread.addActionListener(new AtmMain());
		windowCount++;
	}
	else
	{
		newCustomerThread.addActionListener(new messageWindow());
	}
	closeWindow.addActionListener(new closeWindow());

	ATMCaseStudy.multiThreadingFrame.setVisible(true);
	ATMCaseStudy.multiThreadingFrame.setSize(400, 400);

} // end main
} // end class ATMCaseStudy 


