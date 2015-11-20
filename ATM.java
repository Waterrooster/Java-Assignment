package atm;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

//Represents an automated teller machine

public class ATM implements Runnable
{
// no-argument ATM constructor initializes instance variables
	ArrayList<Integer> promotions = new ArrayList<>();
	ArrayList<String> data= new ArrayList<>();
	String message = "";
	JFrame promotionWindow;
	JFrame OptionFrame;
	JFrame DepositFrame;
	JFrame WithdrawalFrame;
	JFrame statementFrame;
	int withdrawalValue;
	JFrame modifiedStatement;
	boolean initCompleted = false;
	JTextField startDate;
	JTextField endDate;
	int amount;
	double depositAmount;
	JTextField depositInput;
	int CANCELED = 0;
	 boolean userAuthenticated= false; // whether user is authenticated
	 int currentAccountNumber;	
	 CashDispenser cashDispenser = new CashDispenser(); // ATM's cash dispenser
	 DepositSlot depositSlot = new DepositSlot(); // ATM's deposit slot
	 BankDatabase bankDatabase = new BankDatabase();
	 boolean showPromotions = true;
	 static String threadMessage = "";
	 String customerName;
	 long threadId;
	 long startTime;
	 long endTime;
	 long totalTime;
	 
public void setThreadId(long id) {
	threadId = id;
}

public void run()
{				
		startWindow();
		if(userAuthenticated)
		{
			performTransactions();
		}
		userAuthenticated = false;
		currentAccountNumber = 0;
}
private void performTransactions() 
{
   boolean userExited = false; // user has not chosen to exit

   // loop while user has not chosen option to exit system
   if( !userExited )
   {     
      // show main menu and get user selection
	   LoginFrame.setVisible(false);
	   OptionFrame.setVisible(true);
	   
   } // end while
} // end method performTransactions

JTextField usernameInput;
JTextField passwordInput;
JFrame LoginFrame;
	public void startWindow()
	{

		LoginFrame = new JFrame("Login Window");
		JPanel panel = new JPanel();
		
		JButton verify;

		LoginFrame.add(panel);
		
		JLabel label = new JLabel("Welcome");
			JLabel label1 = new JLabel("Account number");
			JLabel label2 = new JLabel("Password");
			usernameInput = new JTextField();
			passwordInput = new JPasswordField();
			verify = new JButton("Verify");
			panel.add(verify);
			
			GroupLayout layout = new GroupLayout(panel);
			   panel.setLayout(layout);
			   
			   // Turn on automatically adding gaps between components
			   layout.setAutoCreateGaps(true);
			   
			   // Turn on automatically creating gaps between components that touch
			   // the edge of the container and the container.
			   layout.setAutoCreateContainerGaps(true);
			   
			   // Create a sequential group for the horizontal axis.
			   
			   GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
			   
			   // The sequential group in turn contains two parallel groups.
			   // One parallel group contains the labels, the other the text fields.
			   // Putting the labels in a parallel group along the horizontal axis
			   // positions them at the same x location.
			   // Variable indentation is used to reinforce the level of grouping.
			   hGroup.addGroup(layout.createParallelGroup().
					   addComponent(label));
			   hGroup.addGroup(layout.createParallelGroup().
			            addComponent(label1).addComponent(label2));
			   hGroup.addGroup(layout.createParallelGroup().
			            addComponent(usernameInput).addComponent(passwordInput));
			   hGroup.addGroup(layout.createParallelGroup().addComponent(verify));
			   layout.setHorizontalGroup(hGroup);
			   
			   // Create a sequential group for the vertical axis.
			   GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
			   
			   // The sequential group contains two parallel groups that align
			   // the contents along the baseline. The first parallel group contains
			   // the first label and text field, and the second parallel group contains
			   // the second label and text field. By using a sequential group
			   // the labels and text fields are positioned vertically after one another.
			   vGroup.addGroup(layout.createParallelGroup(Alignment.CENTER).addComponent(label));
			   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
			            addComponent(label1).addComponent(usernameInput));
			   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
					   addComponent(label2).addComponent(passwordInput));
			   vGroup.addGroup(layout.createParallelGroup(Alignment.CENTER).addComponent(verify));
			   layout.setVerticalGroup(vGroup);
			   			   
			   LoginFrame.setVisible(true);
			   LoginFrame.setSize(400,200);
			   
			   Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			   LoginFrame.setLocation(dim.width/2-LoginFrame.getSize().width/2, dim.height/2-LoginFrame.getSize().height/2);
			   
			   verify.addActionListener(new ActionListener() {
				   public void actionPerformed(ActionEvent e) {
					   authenticate();
				   }
			   });
	}
	
	public void authenticate() {
		try
		{
			int accountNumber = Integer.parseInt(usernameInput.getText());// input account number
			   int pin = Integer.parseInt(passwordInput.getText()); // input PIN
			   
			   
			   // set userAuthenticated to boolean value returned by database
			   userAuthenticated = 
			      bankDatabase.authenticateUser( accountNumber, pin );
			   
			   // check whether authentication succeeded
			   if (userAuthenticated )
			   {
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					Date date = new Date();
					DateFormat time = new SimpleDateFormat("HH:mm:ss");
					Calendar cal = Calendar.getInstance();
					currentAccountNumber = accountNumber;
					customerName = bankDatabase.getName(currentAccountNumber);
					threadMessage += String.format("Thread id:  %d.  Customer name:  %s.  Thread state: %s. Start time: %s    %s %n",threadId,customerName,"Customer Login", dateFormat.format(date),time.format(cal.getTime()));
					startTime = cal.getTimeInMillis();
					LoginFrame.setVisible(false);
			      String promotions = String.format("%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n","1. Get 10% cash back offer on every purchase at shopping malls",
			    		  "2. Deposit 10,000$ within first 10 days of opening a new account and get 200$ back",
			    		  "3. Get 20% cashback on every purchase at dining services",
			    		  "4. Spend 500$ at any disney store with disney card and get 20% cashback",
			    		  "5. Create a new student account for your child and get 5% on every purchase on school suplies",
			    		  "6. Get a new travel rewards card and earn 5000 miles in Emirates airlines",
			    		  "7. Apply for new credit card before festival and get 10% discount on any purchase during festival.",
			    		  "8. Deposit 1000$ in your child student account and get 30% cashback on every purchase on school supplies",
			    		  "9. Fly around world with your travel credit card and get extra miles on every fly",
			    		  "10. Earn 9% cashback ");
			      
			      if(showPromotions)
			      {
				      JOptionPane.showMessageDialog(null,promotions);
			    	  startPromotionWindow();
			    	  showPromotions = false;
			      }
			      else
			      {
			    	  startOptionWindow();
			      }
			   } // end if
			   else
			   {
				   JOptionPane.showMessageDialog(null,"Invalid Account number or Pin. Please try again!");
			   }	
		}catch(Exception error){
			JOptionPane.showMessageDialog(null,"Please provide valid account details");
		}
	}
		public void startPromotionWindow()
		{
			 promotionWindow = new JFrame(customerName);
			 JButton submit;
			 JButton promo1;
			 JButton promo2;
			 JButton promo3;
			 JButton promo4;
			 JButton promo5;
			 JButton promo6;
			 JButton promo7;
			 JButton promo8;
			 JButton promo9;
			 JButton promo10;

			
			JPanel panel = new JPanel();
			
			promo1 = new JButton("Promo-1");
			promo2 = new JButton("Promo-2");
			promo3 = new JButton("Promo-3");
			promo4 = new JButton("Promo-4");
			promo5 = new JButton("Promo-5");
			promo6 = new JButton("Promo-6");
			promo7 = new JButton("Promo-7");
			promo8 = new JButton("Promo-8");
			promo9 = new JButton("Promo-9");
			promo10 = new JButton("Promo-10");
			submit = new JButton("Submit");
			
			promotionWindow.add(panel);
			panel.add(promo1);
			panel.add(promo2);
			panel.add(promo3);
			panel.add(promo4);
			panel.add(promo5);
			panel.add(promo6);
			panel.add(promo7);
			panel.add(promo8);
			panel.add(promo9);
			panel.add(promo10);
			panel.add(submit);
			
			   promotionWindow.setVisible(true);
			   promotionWindow.setSize(540,200);
			   Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			   promotionWindow.setLocation(dim.width/2-promotionWindow.getSize().width/2, dim.height/2-promotionWindow.getSize().height/2);
				  
			   promo1.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					promotion1();   
				   }
			   });   
			   promo2.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					promotion2();   
				   }
			   });   
			   promo3.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					promotion3();   
				   }
			   });  
			   promo4.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					promotion4();   
				   }
			   });   
			   promo5.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					promotion5();   
				   }
			   });   
			   promo6.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					promotion6();   
				   }
			   }); 
			   promo7.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					promotion7();   
				   }
			   });   
			   promo8.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					promotion8();   
				   }
			   });   
			   promo9.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					promotion9();   
				   }
			   });   
			   promo10.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					promotion10();   
				   }
			   });  
			   
			   submit.addActionListener(new ActionListener()
					   {
				   public void actionPerformed(ActionEvent e)
				   {
					   submitPromotions();
				   }
					   });
		}
		public void promotion1(){
			if(promotions.size()<5)
			{
				if(promotions.indexOf(1) == -1)
				{
					promotions.add(1);	
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please choose another promotion");
				}	
			}
			else
			{
				JOptionPane.showMessageDialog(null,"You can only choose 5 promotions.");
			}
		}
		public void promotion2(){
			if(promotions.size()<5)
			{
				if(promotions.indexOf(2) == -1)
				{
					promotions.add(2);	
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please choose another promotion");
				}	
			}
			else
			{
				JOptionPane.showMessageDialog(null,"You can only choose 5 promotions.");
			}
		}
		public void promotion3(){
			if(promotions.size()<5)
			{
				if(promotions.indexOf(3) == -1)
				{
					promotions.add(3);	
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please choose another promotion");
				}	
			}
			else
			{
				JOptionPane.showMessageDialog(null,"You can only choose 5 promotions.");
			}
		}
		public void promotion4(){
			if(promotions.size()<5)
			{
				if(promotions.indexOf(4) == -1)
				{
					promotions.add(4);	
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please choose another promotion");
				}	
			}
			else
			{
				JOptionPane.showMessageDialog(null,"You can only choose 5 promotions.");
			}
		}
		public void promotion5(){
			if(promotions.size()<5)
			{
				if(promotions.indexOf(5) == -1)
				{
					promotions.add(5);	
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please choose another promotion");
				}	
			}
			else
			{
				JOptionPane.showMessageDialog(null,"You can only choose 5 promotions.");
			}
		}
		public void promotion6(){
			if(promotions.size()<5)
			{
				if(promotions.indexOf(6) == -1)
				{
					promotions.add(6);	
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please choose another promotion");
				}	
			}
			else
			{
				JOptionPane.showMessageDialog(null,"You can only choose 5 promotions.");
			}
		}
		public void promotion7(){
			if(promotions.size()<5)
			{
				if(promotions.indexOf(7) == -1)
				{
					promotions.add(7);	
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please choose another promotion");
				}	
			}
			else
			{
				JOptionPane.showMessageDialog(null,"You can only choose 5 promotions.");
			}
		}
		public void promotion8(){
			if(promotions.size()<5)
			{
				if(promotions.indexOf(8) == -1)
				{
					promotions.add(8);	
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please choose another promotion");
				}	
			}
			else
			{
				JOptionPane.showMessageDialog(null,"You can only choose 5 promotions.");
			}
		}
		public void promotion9(){
			if(promotions.size()<5)
			{
				if(promotions.indexOf(9) == -1)
				{
					promotions.add(9);	
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please choose another promotion");
				}	
			}
			else
			{
				JOptionPane.showMessageDialog(null,"You can only choose 5 promotions.");
			}
		}
		public void promotion10(){
			if(promotions.size()<5)
			{
				if(promotions.indexOf(10) == -1)
				{
					promotions.add(10);	
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please choose another promotion");
				}	
			}
			else
			{
				JOptionPane.showMessageDialog(null,"You can only choose 5 promotions.");
			}
		}
		public void submitPromotions()
		{
			if(promotions.size()==5)
			{
				promotionWindow.setVisible(false);
				JOptionPane.showMessageDialog(null, "Thank you for choosing the promotions. We will inform you once the promotions are online.");
				startOptionWindow();
			    int timeout_ms = 10000;//10 * 1000
			    Timer timer = new Timer();

			    final String myCustomerName = customerName;
			    timer.schedule(new TimerTask() {
					@Override
					public void run() {
						ArrayList<String> promotionsDetail = new ArrayList<>();
						promotionsDetail.add("Get 10% cash back offer on every purchase at shopping malls");
						promotionsDetail.add("Deposit 10,000$ within first 10 days of opening a new account and get 200$ back");
						promotionsDetail.add("Get 20% cashback on every purchase at dining services");
						promotionsDetail.add("Spend 500$ at any disney store with disney card and get 20% cashback");
						promotionsDetail.add("Create a new student account for your child and get 5% on every purchase on school suplies");
						promotionsDetail.add("Get a new travel rewards card and earn 5000 miles in Emirates airlines");
						promotionsDetail.add("Apply for new credit card before festival and get 10% discount on any purchase during festival");
						promotionsDetail.add("Deposit 1000$ in your child student account and get 30% cashback on every purchase on school supplies");
						promotionsDetail.add("Fly around world with your travel credit card and get extra miles on every fly");
						promotionsDetail.add("Earn 9% cashback");
						String message = "";
						Random randomNumber = new Random();
						boolean promotionPresent = true;
						while(promotionPresent)
						{
							int randomNum = randomNumber.nextInt(5)+1;
							if(promotions.indexOf(randomNum)!= -1)
							{	
								message += String.format("Customer Name: %s%nPromotion is available!%n",myCustomerName);
								message += promotionsDetail.get(randomNum);
									JOptionPane.showMessageDialog(null, message);
								promotionPresent = false;
							}
						}
					}

			    }, timeout_ms);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Kindly choose 5 promotions");
			}
		}

		public void startOptionWindow()
		{
			OptionFrame = new JFrame(customerName);
			 JButton balance;
			 JButton deposit;
			 JButton withdrawal;
			 JButton exit;
			 JButton statement;
	
		JPanel panel = new JPanel();
		balance  = new JButton("Balance");
		deposit = new JButton("Deposit");
		withdrawal = new JButton("Withdrawal");
		exit = new JButton("Exit");
		statement = new JButton("Statement");
		OptionFrame.add(panel);
		OptionFrame.setSize(400,200);
		OptionFrame.setVisible(true);
		panel.add(balance);
		panel.add(deposit);
		panel.add(withdrawal);
		panel.add(exit);
		panel.add(statement);
		GroupLayout layout = new GroupLayout(panel);
		   panel.setLayout(layout);
		  layout.setAutoCreateGaps(true);
		  layout.setAutoCreateContainerGaps(true);
		  
		  GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		   hGroup.addGroup(layout.createParallelGroup().addComponent(balance).addComponent(deposit));
		   hGroup.addGroup(layout.createParallelGroup().addComponent(withdrawal).addComponent(statement));
		   hGroup.addGroup(layout.createParallelGroup().addComponent(exit));
		   layout.setHorizontalGroup(hGroup);
		   
		   GroupLayout.ParallelGroup vGroup = layout.createParallelGroup();
		  
		   vGroup.addGroup(layout.createSequentialGroup().addComponent(balance).addComponent(deposit));
		   vGroup.addGroup(layout.createSequentialGroup().addComponent(withdrawal).addComponent(statement));
		   vGroup.addGroup(layout.createSequentialGroup().addComponent(exit));
		   layout.setVerticalGroup(vGroup);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		OptionFrame.setLocation(dim.width/2-OptionFrame.getSize().width/2, dim.height/2-OptionFrame.getSize().height/2);
		
			deposit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					deposit();
				}
			});
			withdrawal.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					 withdrawal();
				}
			});
		balance.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				balance();
			}
		});
		statement.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				statement();
			}
		});
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date date = new Date();
				DateFormat time = new SimpleDateFormat("HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				endTime = System.currentTimeMillis();
				totalTime = endTime-startTime;
				
				long hours = TimeUnit.MILLISECONDS.toHours(totalTime);
				long mins = TimeUnit.MILLISECONDS.toMinutes(totalTime);
				long secs = TimeUnit.MILLISECONDS.toSeconds(totalTime);
				threadMessage += String.format("Thread id:  %d.  Customer name:  %s.  Thread state: %s. Endtime: %s    %s  Total time: %d:%d:%d %n",threadId,customerName,"Customer Logged out", dateFormat.format(date),time.format(cal.getTime()),hours,mins,secs);
				String message = String.format("%s%n%s%n%n","Exiting the system...","Thank you! Goodbye!");
				ATMCaseStudy.activeCustomers--;
				LoginFrame.setVisible(false);
				JOptionPane.showMessageDialog(null,message);
				JOptionPane.showMessageDialog(null,threadMessage);
				OptionFrame.setVisible(false);
				passwordInput.setText("");
				usernameInput.setText("");
			}
		});
		}
		public void deposit()
		{
			DepositFrame = new JFrame(customerName);
			OptionFrame.setVisible(false);
			JButton depositMoney = new JButton("Deposit");
			depositInput = new JTextField();
			depositInput.setText("");
			JPanel panel = new JPanel();
			DepositFrame.add(panel);
			JLabel label = new JLabel("Welcome");
			JLabel label1 = new JLabel("Enter deposit amount in CENTS");
			panel.add(depositMoney);
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
			   hGroup.addGroup(layout.createParallelGroup().addComponent(depositMoney));
			   layout.setHorizontalGroup(hGroup);
			   
			   GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
			  
			   vGroup.addGroup(layout.createParallelGroup(Alignment.CENTER).addComponent(label));
			   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
			            addComponent(label1));
			   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
					   addComponent(depositInput));
			   vGroup.addGroup(layout.createParallelGroup(Alignment.CENTER).addComponent(depositMoney));
			   layout.setVerticalGroup(vGroup);
			   
			   depositMoney.addActionListener(new ActionListener(){
					   public void actionPerformed(ActionEvent e){
						   depositMoney();
					   }
			   });

			   DepositFrame.setVisible(true);
			   DepositFrame.setSize(400,200);
			   
			   
			   Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			   DepositFrame.setLocation(dim.width/2-DepositFrame.getSize().width/2, dim.height/2-DepositFrame.getSize().height/2);
		}
		public void depositMoney()
		{
			depositAmount = promptForDepositAmount();
			String depositMessage = String.format("Your deposited amount is: $%.2f", depositAmount);
			JOptionPane.showMessageDialog(null, depositMessage);
			// check whether user entered a deposit amount or canceled
			if(depositAmount > 2000)
			{
				JOptionPane.showMessageDialog(null, "Sorry! I can't accept more than $2000");
				DepositFrame.setVisible(false);
				depositInput.setText("");
				OptionFrame.setVisible(true);
			}
			else
			{
				if( depositAmount != CANCELED )
				   {
				      // request deposit envelope containing specified amount
				      // receive deposit envelope
				      boolean envelopeReceived = depositSlot.isEnvelopeReceived();

				      // check whether deposit envelope was received
				      if ( envelopeReceived )
				      {  
				  		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
						Date date = new Date();
						DateFormat time = new SimpleDateFormat("HH:mm:ss");
						Calendar cal = Calendar.getInstance();

				    	  String depositNotification = String.format("%s%n%s%n%s","Your envelope has been received.","NOTE: The money just deposited will not be available until we ","verify the amount of any enclosed cash and your checks clear.");
				        JOptionPane.showMessageDialog(null, depositNotification);
						   
				         // credit account to reflect the deposit
				         bankDatabase.credit( currentAccountNumber, depositAmount ); 
				         
				 		Transaction foo = new BalanceInquiry(currentAccountNumber,bankDatabase);
						threadMessage += String.format("Thread id:  %d.  Customer name:  %s.  Thread state: %s. Start time: %s    %s %n",threadId,customerName,"Deposit", dateFormat.format(date),time.format(cal.getTime()));

						message += String.format("%n%s%n%s%nTransaction type: Deposit%nTransaction amount: $%.2f%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),depositAmount,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber)-depositAmount,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
								String localStatement = String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%.2f%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),depositAmount,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber)-depositAmount,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
								data.add(localStatement);
								DepositFrame.setVisible(false);
								depositInput.setText("");
								OptionFrame.setVisible(true);
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
				      DepositFrame.setVisible(false);
					  depositInput.setText("");
					  OptionFrame.setVisible(true);
				   }
			}

		}
		public double promptForDepositAmount()
		{
			int input=0;
			   try
			   {
				   input = Integer.parseInt(depositInput.getText());
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
		}
		public void withdrawal(){
			WithdrawalFrame = new JFrame(customerName);
			 JButton twenty;
			 JButton fourty;
			 JButton sixty;
			 JButton hundred;
			 JButton hundredTwenty;
			 JButton cancel;

			
			JPanel panel = new JPanel();
			WithdrawalFrame.add(panel);
			
			twenty = new JButton("Twenty");
			fourty = new JButton("Fourty");
			sixty = new JButton("Sixty");
			hundred = new JButton("Hundred");
			hundredTwenty = new JButton("Hundred Twenty");
			cancel = new JButton("Cancel");
			
			panel.add(twenty);
			panel.add(fourty);
			panel.add(sixty);
			panel.add(hundred);
			panel.add(hundredTwenty);
			panel.add(cancel);
			
			GroupLayout layout = new GroupLayout(panel);
			   panel.setLayout(layout);
			  layout.setAutoCreateGaps(true);
			  layout.setAutoCreateContainerGaps(true);
			  
			  GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
			   hGroup.addGroup(layout.createParallelGroup().addComponent(twenty).addComponent(fourty).addComponent(sixty));
			   hGroup.addGroup(layout.createParallelGroup().addComponent(hundred).addComponent(hundredTwenty).addComponent(cancel));
					   
			   layout.setHorizontalGroup(hGroup);
			   
			   GroupLayout.ParallelGroup vGroup = layout.createParallelGroup();
			  
			   vGroup.addGroup(layout.createSequentialGroup().addComponent(twenty).addComponent(fourty).addComponent(sixty));
			   vGroup.addGroup(layout.createSequentialGroup().addComponent(hundred).addComponent(hundredTwenty).addComponent(cancel));
			   layout.setVerticalGroup(vGroup);
			
			   OptionFrame.setVisible(false);
			   WithdrawalFrame.setVisible(true);
			   WithdrawalFrame.setSize(400,200);
			   
			   Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			   WithdrawalFrame.setLocation(dim.width/2-WithdrawalFrame.getSize().width/2, dim.height/2-WithdrawalFrame.getSize().height/2);
			   
			   twenty.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					   twenty();
				   }
			   });
			   fourty.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					   fourty();
				   }
			   });
			   sixty.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					   sixty();
				   }
			   });
			   hundred.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					   hundred();
				   }
			   });
			   hundredTwenty.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					   hundredTwenty();
				   }
			   });
			   cancel.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					   cancel();
				   }
			   });
		}
		public void cancel(){
			withdrawalValue = 0;
			execute(withdrawalValue);
			WithdrawalFrame.setVisible(false);
			OptionFrame.setVisible(true);
		}
		public void twenty(){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			DateFormat time = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();

			withdrawalValue = 20;
			execute(withdrawalValue);
			WithdrawalFrame.setVisible(false);
			OptionFrame.setVisible(true);
			Transaction foo = new BalanceInquiry(currentAccountNumber,bankDatabase);
			threadMessage += String.format("Thread id:  %d.  Customer name:  %s.  Thread state: %s. Start time: %s    %s %n",threadId,customerName,"Withdrawal", dateFormat.format(date),time.format(cal.getTime()));
			message += String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					String localStatement = String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					data.add(localStatement);
		}
		public void fourty(){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			DateFormat time = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();

			withdrawalValue = 40;
			execute(withdrawalValue);
			WithdrawalFrame.setVisible(false);
			OptionFrame.setVisible(true);
			Transaction foo = new BalanceInquiry(currentAccountNumber,bankDatabase);
			threadMessage += String.format("Thread id:  %d.  Customer name:  %s.  Thread state: %s. Start time: %s    %s %n",threadId,customerName,"Withdrawal", dateFormat.format(date),time.format(cal.getTime()));
			message += String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					String localStatement = String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					data.add(localStatement);
		}
		public void sixty(){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			DateFormat time = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();

			withdrawalValue = 60;
			execute(withdrawalValue);
			WithdrawalFrame.setVisible(false);
			OptionFrame.setVisible(true);
			Transaction foo = new BalanceInquiry(currentAccountNumber,bankDatabase);
			threadMessage += String.format("Thread id:  %d.  Customer name:  %s.  Thread state: %s. Start time: %s    %s %n",threadId,customerName,"Withdrawal", dateFormat.format(date),time.format(cal.getTime()));
	 		message += String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					String localStatement = String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					data.add(localStatement);
		}
		public void hundred(){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			DateFormat time = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();

			withdrawalValue = 100;
			execute(withdrawalValue);
			WithdrawalFrame.setVisible(false);
			OptionFrame.setVisible(true);
			Transaction foo = new BalanceInquiry(currentAccountNumber,bankDatabase);
			threadMessage += String.format("Thread id:  %d.  Customer name:  %s.  Thread state: %s. Start time: %s    %s %n",threadId,customerName,"Withdrawal", dateFormat.format(date),time.format(cal.getTime()));
	 		message += String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					String localStatement = String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					data.add(localStatement);
		}
		public void hundredTwenty(){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			DateFormat time = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();

			withdrawalValue = 120;
			execute(withdrawalValue);
			WithdrawalFrame.setVisible(false);
			OptionFrame.setVisible(true);
			Transaction foo = new BalanceInquiry(currentAccountNumber,bankDatabase);
			threadMessage += String.format("Thread id:  %d.  Customer name:  %s.  Thread state: %s. Start time: %s    %s %n",threadId,customerName,"Withdrawal", dateFormat.format(date),time.format(cal.getTime()));
	 		message += String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					String localStatement = String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					data.add(localStatement);
		}
		public void exceedTransaction()
		{
			JOptionPane.showMessageDialog(null, "You exceeded daily limit of 20 transactions");
		}
		public void balance(){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			DateFormat time = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();

			threadMessage += String.format("Thread id:  %d.  Customer name:  %s.  Thread state: %s. Start time: %s    %s %n",threadId,customerName,"Withdrawal", dateFormat.format(date),time.format(cal.getTime()));
			Transaction transaction = new BalanceInquiry(currentAccountNumber,bankDatabase);
			transaction.execute();
		}
		public void statement(){
			statementFrame = new JFrame(customerName);
			 JButton completeStatement;
			 JButton dateStatement;

			JPanel panel = new JPanel();
			completeStatement = new JButton("Complete Statement");
			dateStatement = new JButton("Select Dates");
			statementFrame.add(panel);
			panel.add(completeStatement);
			panel.add(dateStatement);
			OptionFrame.setVisible(false);
			
			statementFrame.setVisible(true);
			statementFrame.setSize(400, 200);
			
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			statementFrame.setLocation(dim.width/2-statementFrame.getSize().width/2, dim.height/2-statementFrame.getSize().height/2);
			
			completeStatement.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					completeStatement();
				}
			});
			dateStatement.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					statementBetweenDates();
				}
			});
		}
		public void completeStatement(){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			DateFormat time = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			threadMessage += String.format("Thread id:  %d.  Customer name:  %s.  Thread state: %s. Start time: %s    %s %n",threadId,customerName,"Viewed complete statement", dateFormat.format(date),time.format(cal.getTime()));

			JOptionPane.showMessageDialog(null,message);
			statementFrame.setVisible(false);
			OptionFrame.setVisible(true);
		}
		public void init()
		{
			 modifiedStatement = new JFrame(customerName);
			JPanel panel = new JPanel();
			JLabel label1 = new JLabel("Start Date");
			startDate = new JTextField();
			JLabel label2 = new JLabel("End Date");
			endDate = new JTextField();
			
			JButton generateStatement = new JButton("Generate");
			modifiedStatement.add(panel);
			panel.add(generateStatement);
			panel.add(label1);
			panel.add(startDate);
			panel.add(label2);
			panel.add(endDate);
			GroupLayout layout = new GroupLayout(panel);
			   panel.setLayout(layout);

			   layout.setAutoCreateGaps(true);
			   layout.setAutoCreateContainerGaps(true);
			   GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
			   
			   hGroup.addGroup(layout.createParallelGroup().
			            addComponent(label1).addComponent(label2));
			   hGroup.addGroup(layout.createParallelGroup().
			            addComponent(startDate).addComponent(endDate));
			   hGroup.addGroup(layout.createParallelGroup().addComponent(generateStatement));
			   
			   layout.setHorizontalGroup(hGroup);

			   GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
			  
			   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
			            addComponent(label1).addComponent(startDate));
			   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
					   addComponent(label2).addComponent(endDate));
			   vGroup.addGroup(layout.createParallelGroup(Alignment.CENTER).addComponent(generateStatement));
			   layout.setVerticalGroup(vGroup);
			  
			   JOptionPane.showMessageDialog(null,"Enter date in the format: dd/MM/yyyy");
			   statementFrame.setVisible(false);
			   modifiedStatement.setVisible(true);
			   modifiedStatement.setSize(400,200);
			   
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date date = new Date();
				DateFormat time = new SimpleDateFormat("HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				threadMessage += String.format("Thread id:  %d.  Customer name:  %s.  Thread state: %s. Start time: %s    %s %n",threadId,customerName,"Viewed statement", dateFormat.format(date),time.format(cal.getTime()));


				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				modifiedStatement.setLocation(dim.width/2-modifiedStatement.getSize().width/2, dim.height/2-modifiedStatement.getSize().height/2);
				generateStatement.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					   statementGeneration();
				   }
			   });
		}
		public void execute(int withdrawalValue)
		{
		   boolean cashDispensed = false; // cash was not dispensed yet
		   double availableBalance; // amount available for withdrawal
		   double totalBalance;
		   // get references to bank database and screen

		   // loop until cash is dispensed or the user cancels
		   do
		   {
		      // obtain a chosen withdrawal amount from the user 
		      amount = withdrawalValue;
		      
		      // check whether user chose a withdrawal amount or canceled
		      if ( amount != 0 )
		      {
		         // get available balance of account involved
		         availableBalance = 
		            bankDatabase.getAvailableBalance( currentAccountNumber );
		   
		         // check whether the user has enough money in the account 
		         if ( amount <= availableBalance )
		         {   
		            // check whether the cash dispenser has enough money
		            if ( cashDispenser.isSufficientCashAvailable( amount ) )
		            {
		               // update the account involved to reflect withdrawal
		               bankDatabase.debit( currentAccountNumber, amount );
		               
		               cashDispenser.dispenseCash( amount ); // dispense cash
		               cashDispensed = true; // cash was dispensed
		               // instruct user to take cash
		               // get the available balance for the account involved
		               availableBalance = 
		                  bankDatabase.getAvailableBalance( currentAccountNumber );

		               // get the total balance for the account involved
		               totalBalance = 
		                  bankDatabase.getTotalBalance( currentAccountNumber);
		               
		               String message = String.format("Please take your cash now. %nYour remaining balance is: $%.2f%nYour total balance is: $%.2f",availableBalance,totalBalance);
		               JOptionPane.showMessageDialog(null, message);
		            } // end if
		            else	// cash dispenser does not have enough cash
		            {
		            	String message = String.format("%s%n%n%s", "Insufficient cash available in the ","Please choose a smaller amount.");
		                JOptionPane.showMessageDialog(null,message);
		                WithdrawalFrame.setVisible(false);
		                OptionFrame.setVisible(true);
		            }
		            	
		         } // end if
		         else // not enough money available in user's account
		         {
		        	 String message = String.format("%s%n%n%s", "Insufficient funds in your account.","Please choose a smaller amount.");
		             JOptionPane.showMessageDialog(null,message);
		             cashDispensed = true;
		         } // end else
		      } // end if
		      else // user chose cancel menu option 
		      {
		    	JOptionPane.showMessageDialog(null, "Cancelling transaction...");
		    	cashDispensed = true;
		  		break;
		      } // end else
		   } while ( !cashDispensed );
	}
		public void statementGeneration(){
			 String statementMessage = "";
			 String startingDate;
			 String endingDate;

			
			String accountInformation = String.format("%nAccount number: ", currentAccountNumber);
			boolean performOperation = false;
			   startingDate = startDate.getText();
			   endingDate = endDate.getText();			   
			   if(CurrentDate.isThisDateValid(startingDate, "dd/MM/yyyy") && CurrentDate.isThisDateValid(endingDate, "dd/MM/yyyy"))
			   {
				   performOperation = true;
			   }
			   else
			   {
				   performOperation = false;
			   }
			   		   
			   if(performOperation)
			   { 
				  for(int i = 0; i< data.size(); i++)
				  {
					  String dateString = data.get(i);
					  String date = dateString.substring(1, 11);
					  try {
						if(CurrentDate.checkDatebetweenDates(startingDate, endingDate, date))
						{
							statementMessage += data.get(i);
							statementMessage += String.format("%n");
						}
					} catch (ParseException e1) {
						JOptionPane.showMessageDialog(null, "Error!");
					}
				  }
			   }
			   if(statementMessage == "")
			   {
			      JOptionPane.showMessageDialog(null, "No transactions made during this period.");
			   }
			   else
			   {
				   accountInformation+= statementMessage;
			   	   JOptionPane.showMessageDialog(null, accountInformation);
			   }
			   modifiedStatement.setVisible(false);
			   OptionFrame.setVisible(true);
			   startDate.setText("");
			   endDate.setText("");
			   statementMessage = "";
		}
		public void statementBetweenDates(){
			if(!initCompleted)
			{
				init();
				initCompleted = true;	
			}
			modifiedStatement.setVisible(true);
		}
	}