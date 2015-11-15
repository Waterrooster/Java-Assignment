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
//ATM.java
//Represents an automated teller machine

public class ATM implements Runnable
{
	Account account;
// no-argument ATM constructor initializes instance variables
	 ArrayList<Integer> promotions = new ArrayList<>();
	 ArrayList<String> data= new ArrayList<>();
	 String message = "";
	JFrame promotionWindow;
	JFrame frame2;
	JFrame frame4;
	JFrame statementFrame;
	int withdrawalValue;
	JFrame modifiedStatement;
	boolean initCompleted = false;
	JTextField startDate;
	JTextField endDate;


	 boolean userAuthenticated= false; // whether user is authenticated
	 int currentAccountNumber;	 
	 CashDispenser cashDispenser = new CashDispenser(); // ATM's cash dispenser
	 DepositSlot depositSlot = new DepositSlot(); // ATM's deposit slot
	 BankDatabase bankDatabase = new BankDatabase();

	 public ATM()
	 {
		 
	 }
public ATM(Account acc)
{
	this.account = acc;
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
// attempts to authenticate user against database

// display the main menu and perform transactions
private void performTransactions() 
{
   
   boolean userExited = false; // user has not chosen to exit

   // loop while user has not chosen option to exit system
   if( !userExited )
   {     
      // show main menu and get user selection
	   frame.setVisible(false);
	   frame2.setVisible(true);
	   
   } // end while
} // end method performTransactions

JTextField usernameInput;
JTextField passwordInput;
JFrame frame;
	public void startWindow()
	{

		frame = new JFrame("Bank window");
		JPanel panel = new JPanel();
		
		JButton verify;

		frame.add(panel);
		
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
			   			   
			   frame.setVisible(true);
			   frame.setSize(400,200);
			   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			   
			   Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			   frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
			   
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
				   currentAccountNumber = accountNumber;
			      frame.setVisible(false);
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
			      JOptionPane.showMessageDialog(null,promotions);
			      
//			      customerMultiThreading.runThread();
//			      JOptionPane.showMessageDialog(null, customerMultiThreading.message);
			      startPromotionWindow();			      
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
			 promotionWindow = new JFrame("Promotions");
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
			   promotionWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
			    timer.schedule(displayPromotion(), timeout_ms);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Kindly choose 5 promotions");
			}
		}
		public void startOptionWindow()
		{
			frame2 = new JFrame("Option Window");
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
		frame2.add(panel);
		frame2.setSize(400,200);
		frame2.setVisible(true);
		
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
		frame2.setLocation(dim.width/2-frame2.getSize().width/2, dim.height/2-frame2.getSize().height/2);
		
			deposit.addActionListener(new Deposit(currentAccountNumber,bankDatabase,depositSlot));
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
				String message = String.format("%s%n%s%n%n","Exiting the system...","Thank you! Goodbye!");
				JOptionPane.showMessageDialog(null,message);
				frame2.setVisible(false);
				frame.setVisible(true);
				passwordInput.setText("");
				usernameInput.setText("");
			}
		});
		
		}
		public void withdrawal(){
			frame4 = new JFrame("Withdrawal Money");
			 JButton twenty;
			 JButton fourty;
			 JButton sixty;
			 JButton hundred;
			 JButton hundredTwenty;
			 JButton cancel;

			
			JPanel panel = new JPanel();
			frame4.add(panel);
			
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
			
			   frame2.setVisible(false);
			   frame4.setVisible(true);
			   frame4.setSize(400,200);
			   
			   Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			   frame4.setLocation(dim.width/2-frame4.getSize().width/2, dim.height/2-frame4.getSize().height/2);
			   
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
			Withdrawal withdrawCash = new Withdrawal(currentAccountNumber,bankDatabase, cashDispenser,withdrawalValue);		
			withdrawCash.execute();
			frame4.setVisible(false);
			frame2.setVisible(true);
		}
		public void twenty(){
			withdrawalValue = 20;
			Withdrawal withdrawCash = new Withdrawal(currentAccountNumber,bankDatabase, cashDispenser,withdrawalValue);
			withdrawCash.execute();
			frame4.setVisible(false);
			frame2.setVisible(true);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			DateFormat time = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();			
			Transaction foo = new BalanceInquiry(currentAccountNumber,bankDatabase);
			message += String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					String localStatement = String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					data.add(localStatement);
		}
		public void fourty(){

			withdrawalValue = 40;
			Withdrawal withdrawCash = new Withdrawal(currentAccountNumber,bankDatabase, cashDispenser,withdrawalValue);
			withdrawCash.execute();
			frame4.setVisible(false);
			frame2.setVisible(true);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			DateFormat time = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();			
			Transaction foo = new BalanceInquiry(currentAccountNumber,bankDatabase);
			message += String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					String localStatement = String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					data.add(localStatement);
		}
		public void sixty(){

			withdrawalValue = 60;
			Withdrawal withdrawCash = new Withdrawal(currentAccountNumber,bankDatabase, cashDispenser,withdrawalValue);
			withdrawCash.execute();
			frame4.setVisible(false);
			frame2.setVisible(true);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			DateFormat time = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();			
			Transaction foo = new BalanceInquiry(currentAccountNumber,bankDatabase);
			message += String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					String localStatement = String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					data.add(localStatement);
		}
		public void hundred(){

			withdrawalValue = 100;
			Withdrawal withdrawCash = new Withdrawal(currentAccountNumber,bankDatabase, cashDispenser,withdrawalValue);
			withdrawCash.execute();
			frame4.setVisible(false);
			frame2.setVisible(true);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			DateFormat time = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();			
			Transaction foo = new BalanceInquiry(currentAccountNumber,bankDatabase);
			message += String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					String localStatement = String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					data.add(localStatement);
		}
		public void hundredTwenty(){

			withdrawalValue = 120;
			Withdrawal withdrawCash = new Withdrawal(currentAccountNumber,bankDatabase, cashDispenser,withdrawalValue);
			withdrawCash.execute();
			frame4.setVisible(false);
			frame2.setVisible(true);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			DateFormat time = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();			
			Transaction foo = new BalanceInquiry(currentAccountNumber,bankDatabase);
			message += String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					String localStatement = String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					data.add(localStatement);
		}
		public void exceedTransaction()
		{
			JOptionPane.showMessageDialog(null, "You exceeded daily limit of 20 transactions");
		}
		public void balance(){
			Transaction transaction = new BalanceInquiry(currentAccountNumber,bankDatabase);
			transaction.execute();
		}
		public void statement(){
			statementFrame = new JFrame("Statement Frame");
			 JButton completeStatement;
			 JButton dateStatement;

			JPanel panel = new JPanel();
			completeStatement = new JButton("Complete Statement");
			dateStatement = new JButton("Select Dates");
			statementFrame.add(panel);
			panel.add(completeStatement);
			panel.add(dateStatement);
			frame2.setVisible(false);
			
			statementFrame.setVisible(true);
			statementFrame.setSize(400, 200);
			
			statementFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
			JOptionPane.showMessageDialog(null,message);
			statementFrame.setVisible(false);
			frame2.setVisible(true);
		}
		public void init()
		{

			 modifiedStatement = new JFrame("Statement between dates");
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
			   modifiedStatement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			   
			   
			   Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			   modifiedStatement.setLocation(dim.width/2-modifiedStatement.getSize().width/2, dim.height/2-modifiedStatement.getSize().height/2);
			   generateStatement.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					   statementGeneration();
				   }
			   });
		}
		public void statementGeneration(){
			 String statementMessage = "";
			 String startingDate;
			 String endingDate;

			
			String accountInformation = String.format("%nAccount number: ", currentAccountNumber);
			boolean performOperation = false;
			   startingDate = startDate.getText();
			
			   System.out.println("starting date is : "+startingDate);
			   
			   endingDate = endDate.getText();
			   
			   System.out.println("ending date is: "+endingDate);
			   
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
			   frame2.setVisible(true);
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
		
		public TimerTask displayPromotion()
		{
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
					message += String.format("%n%s%n%n", "                  "
							+ "Promotion available!!");
					message += promotionsDetail.get(randomNum);
					JOptionPane.showMessageDialog(null, message);
					promotionPresent = false;
				}
			}
			return null;
		}	
	}