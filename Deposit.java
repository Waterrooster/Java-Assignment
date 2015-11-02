package atm;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//Deposit.java
//Represents a deposit ATM transaction

public class Deposit extends Transaction implements ActionListener
{

protected  JFrame frame3 = new JFrame("Deposit window");
protected static JTextField depositInput;
protected  JButton deposit;
private  double amount; // amount to deposit
private DepositSlot depositSlot; // reference to deposit slot
private final static int CANCELED = 0; // constant for cancel option

private static boolean actionListenerAdded = false;

// Deposit constructor
public Deposit( int userAccountNumber, BankDatabase atmBankDatabase, DepositSlot atmDepositSlot )
{
   // initialize superclass variables
   super( userAccountNumber, atmBankDatabase );
   deposit = new JButton("Deposit");
   depositInput = new JTextField();
   depositInput.setText("");
   // initialize references to keypad and deposit slot
   depositSlot = atmDepositSlot;
} // end Deposit constructor

public void actionPerformed(ActionEvent e)
{
		OptionWindow.frame2.setVisible(false);
		JPanel panel = new JPanel();
		frame3.add(panel);
		
		JLabel label = new JLabel("Welcome");
		JLabel label1 = new JLabel("Enter deposit amount in CENTS");
		panel.add(deposit);
		depositInput.setToolTipText("Deposit amount");
		GroupLayout layout = new GroupLayout(panel);
		   panel.setLayout(layout);
		  layout.setAutoCreateGaps(true);
		  layout.setAutoCreateContainerGaps(true);
		  
		  GroupLayout.ParallelGroup hGroup = layout.createParallelGroup();
		   hGroup.addGroup(layout.createParallelGroup().addComponent(label));
		   hGroup.addGroup(layout.createParallelGroup().
		            addComponent(label1));
		   hGroup.addGroup(layout.createParallelGroup().
		            addComponent(depositInput));
		   hGroup.addGroup(layout.createParallelGroup().addComponent(deposit));
		   layout.setHorizontalGroup(hGroup);
		   
		   GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		  
		   vGroup.addGroup(layout.createParallelGroup(Alignment.CENTER).addComponent(label));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(label1));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
				   addComponent(depositInput));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.CENTER).addComponent(deposit));
		   layout.setVerticalGroup(vGroup);
		   
		   if(!Deposit.actionListenerAdded) {
			   deposit.addActionListener(new depositMoney());
			   Deposit.actionListenerAdded = true;
		   }

		   frame3.setVisible(true);
		   frame3.setSize(400,200);
		   frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   
		   
		   Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		   frame3.setLocation(dim.width/2-frame3.getSize().width/2, dim.height/2-frame3.getSize().height/2);

}

class depositMoney implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		BankDatabase bankDatabase = getBankDatabase(); // get reference
		
		
		amount = promptForDepositAmount(); // get deposit amount from user
		String depositAmount = String.format("Your deposited amount is: $%.2f", amount);
		JOptionPane.showMessageDialog(null, depositAmount);
		// check whether user entered a deposit amount or canceled
		if(amount > 2000)
		{
			JOptionPane.showMessageDialog(null, "Sorry! I can't accept more than $2000");
			frame3.setVisible(false);
			depositInput.setText("");
			OptionWindow.frame2.setVisible(true);
		}
		else
		{
			if( amount != CANCELED )
			   {
			      // request deposit envelope containing specified amount
			      // receive deposit envelope
			      boolean envelopeReceived = depositSlot.isEnvelopeReceived();

			      // check whether deposit envelope was received
			      if ( envelopeReceived )
			      {  
			    	  String message = String.format("%s%n%s%n%s","Your envelope has been received.","NOTE: The money just deposited will not be available until we ","verify the amount of any enclosed cash and your checks clear.");
			        JOptionPane.showMessageDialog(null, message);
					   
			         // credit account to reflect the deposit
			         bankDatabase.credit( getAccountNumber(), amount ); 
			         
			 		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			 		Date date = new Date();
			 		DateFormat time = new SimpleDateFormat("HH:mm:ss");
			 		Calendar cal = Calendar.getInstance();

			 		Transaction foo = new BalanceInquiry(ATM.currentAccountNumber,ATM.bankDatabase);
					ATM.message += String.format("%n%s%n%s%nTransaction type: Deposit%nTransaction amount: $%.2f%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),amount,foo.getAvailableBalance(ATM.currentAccountNumber),foo.getTotalBalance(ATM.currentAccountNumber)-amount,foo.getAvailableBalance(ATM.currentAccountNumber),foo.getTotalBalance(ATM.currentAccountNumber));
							String localStatement = String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%.2f%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),amount,foo.getAvailableBalance(ATM.currentAccountNumber),foo.getTotalBalance(ATM.currentAccountNumber)-amount,foo.getAvailableBalance(ATM.currentAccountNumber),foo.getTotalBalance(ATM.currentAccountNumber));
							ATM.data.add(localStatement);
							frame3.setVisible(false);
							depositInput.setText("");
							OptionWindow.frame2.setVisible(true);
			      } // end if
			      else // deposit envelope not received
			      {
			    	  String message = String.format("%s%n","You did not insert an envelope, so the ATM has canceled your transaction." );
			         JOptionPane.showMessageDialog(null, message );
			      } // end else
			      
			   } // end if 
			   else // user canceled instead of entering amount
			   {
				   String message = String.format("%s", "Canceling transaction...");
			      JOptionPane.showMessageDialog(null,message );
			      frame3.setVisible(false);
				  depositInput.setText("");
				  OptionWindow.frame2.setVisible(true);
			   } 
		}
	}		
}
// perform transaction
public void execute() 
{
   
} // end method execute

// prompt user to enter a deposit amount in cents 
private double promptForDepositAmount()
{
   // check whether the user canceled or entered a valid amount
	
   int input=0;
   try
   {
	   input = Integer.parseInt(Deposit.depositInput.getText());
   }catch(Exception e){
	   JOptionPane.showMessageDialog(null, "Enter valid amount");
   }
   if(input<0)
   {
	   JOptionPane.showMessageDialog(null, "Enter valid amount");
	   return CANCELED;
   }
   else
   {
	   if (input == 0 )
	   {
		   return CANCELED; 
	   }
	    else
	   {
	      return (double)input/100; // return dollar amount
	   } // end else
   }
	   
} // end method promptForDepositAmount

@Override
public double getAvailableBalance(int userAccountNumber) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public double getTotalBalance(int userAccountNumber) {
	// TODO Auto-generated method stub
	return 0;
}


} // end class Deposit
