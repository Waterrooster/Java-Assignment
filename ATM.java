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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.*;

public class ATM implements Runnable
{
// no-argument ATM constructor initializes instance variables
	ArrayList<Integer> promotions = new ArrayList<>();
	ArrayList<String> data= new ArrayList<>();
	String message = "";
	 String customerName;
	JFrame AtmWindow = new JFrame(customerName);
	
	JPanel LoginPanel = new JPanel();
	JPanel OptionWindowPanel = new JPanel();
	JPanel PromotionWindowPanel = new JPanel();
	JPanel BalanceWindowPanel = new JPanel();
	JPanel DepositWindowPanel = new JPanel();
	JPanel WithdrawalWindowPanel = new JPanel();
	JPanel StatementWindowPanel	= new JPanel();
	JPanel ModifiedWindowPanel = new JPanel();
	JPanel CreateCustomerPanel = new JPanel();
	
	JTextField customerFirstNameInput;
	JTextField customerLastNameInput ;
	JTextField customerAccountNumberInput;
	JPasswordField customerPinInput ;
	JPasswordField customerPinVerificationInput;
	JTextField customerBalanceInput;
	
	int withdrawalValue;
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
	 boolean promotionsSelected = true;
	 static String threadMessage = "";
	 String threadId;
	 long startTime;
	 long endTime;
	 long totalTime;
	 JTextField usernameInput;
	 JTextField passwordInput;
	 ATMCaseStudy atmObject;
	 
	 String createCustomerErrorMessage = "";
	 
//public void setThreadId(long id) {
//	threadId = id;
//}

	public ATM(ATMCaseStudy atmObject) {
		this.atmObject = atmObject;
		AtmWindow.setSize(400, 200);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		AtmWindow.setLocation(dim.width/2-AtmWindow.getSize().width/2, dim.height/2-AtmWindow.getSize().height/2);
		
		createLoginPanel();
		createOptionPanel();
		createStatementPanel();
		createModifiedPanel();
		createPromotionPanel();
		createDepositPanel();
		createWithdrawalPanel();
		createNewCustomerPanel();
	}

	
	JPanel currentPanel;
	
public void showLoginWindow() {
	AtmWindow.setTitle("");
	usernameInput.setText("");
	passwordInput.setText("");
	AtmWindow.setVisible(true);
	AtmWindow.getContentPane().removeAll();
	AtmWindow.revalidate();
	AtmWindow.repaint();
	showPanel(LoginPanel);	
}

public void run()
{					
	showLoginWindow();
}

public void hidePanel(JPanel panelName)
{
	AtmWindow.getContentPane().removeAll();
	AtmWindow.revalidate();
	AtmWindow.repaint();
}
public void showPanel(JPanel panelName)
{
	currentPanel = panelName;
	AtmWindow.getContentPane().add(panelName);
	AtmWindow.revalidate();
	AtmWindow.repaint();
}
public void newCustomer()
{
	AtmWindow.getContentPane().removeAll();
	AtmWindow.revalidate();
	AtmWindow.repaint();
	usernameInput.setText("");
	passwordInput.setText("");
	AtmWindow.setTitle("");
	showPanel(LoginPanel);
	atmObject.customerList.setSelectedItem("New customer");
}
 void createLoginPanel()
{
	JButton verify;		
	JLabel label = new JLabel("Welcome");
		JLabel label1 = new JLabel("Account number");
		JLabel label2 = new JLabel("Password");
		JButton createCustomer = new JButton("Create Customer");
		
		usernameInput = new JTextField();
		passwordInput = new JPasswordField();
		verify = new JButton("Verify");
		LoginPanel.add(verify);
		
		GroupLayout layout = new GroupLayout(LoginPanel);
		   LoginPanel.setLayout(layout);
		   
		   layout.setAutoCreateGaps(true);
		   
		   layout.setAutoCreateContainerGaps(true);
		   GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		   hGroup.addGroup(layout.createParallelGroup().
				   addComponent(label));
		   hGroup.addGroup(layout.createParallelGroup().
		            addComponent(label1).addComponent(label2).addComponent(createCustomer));
		   hGroup.addGroup(layout.createParallelGroup().
		            addComponent(usernameInput).addComponent(passwordInput).addComponent(verify));
		   layout.setHorizontalGroup(hGroup);
		   
		   
		   GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		   
		   vGroup.addGroup(layout.createParallelGroup(Alignment.CENTER).addComponent(label));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
		            addComponent(label1).addComponent(usernameInput));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).
				   addComponent(label2).addComponent(passwordInput));
		   vGroup.addGroup(layout.createParallelGroup(Alignment.CENTER).addComponent(createCustomer).addComponent(verify));
		   layout.setVerticalGroup(vGroup);
		   
		   createCustomer.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent e)
			   {
				   createCustomerPanel();
			   }
		   });
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

			   userAuthenticated = 
			      bankDatabase.authenticateUser( accountNumber, pin );

			   if (userAuthenticated )
			   {
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					Date date = new Date();
					DateFormat time = new SimpleDateFormat("HH:mm:ss");
					Calendar cal = Calendar.getInstance();
					currentAccountNumber = accountNumber;
					customerName = bankDatabase.getName(currentAccountNumber);
					
					atmObject.thread = new Thread(customerName);
					atmObject.threads.add(atmObject.thread);
					atmObject.thread.run();
					
					atmObject.customerThreadList.add(customerName);
					atmObject.customerThreadList.add(Long.toString(atmObject.thread.getId()));
					
					threadId = atmObject.customerThreadList.get((atmObject.customerThreadList.indexOf(customerName)+1));
					
					threadMessage += String.format("Thread id:  %s.  Customer name:  %s.  Thread state: %s. Start time: %s    %s %n",threadId,customerName,"Customer Login", dateFormat.format(date),time.format(cal.getTime()));
					startTime = cal.getTimeInMillis();
					startOptionWindow();
					AtmWindow.setTitle(customerName);
					atmObject.addCustomer(customerName);
					atmObject.customerList.setSelectedItem(customerName);
					JSONObject obj = new JSONObject(getWeather());
					String weatherDescription = "";
					Double currentTemp = 0.0;
					JSONArray arr = obj.getJSONArray("weather");
					for (int i = 0; i < arr.length(); i++)
					{
					    weatherDescription = arr.getJSONObject(i).getString("description");
					}
					currentTemp = obj.getJSONObject("main").getDouble("temp")*9/5 - 459.67;
					
					String weatherMessage = String.format("Hi! %s.%nCurrent weather description: %s%nTemperature: %.2f F%n", customerName,weatherDescription,currentTemp);
					JOptionPane.showMessageDialog(null,weatherMessage);
					}
			   else
			   {
				   usernameInput.setText("");
				   passwordInput.setText("");
				   JOptionPane.showMessageDialog(null,"Invalid Account number or Pin. Please try again!");
			   }	
		}catch(Exception error){
			JOptionPane.showMessageDialog(null,"Please provide valid account details");
		}
	}
	public void createCustomerPanel()
	{
		AtmWindow.setSize(400, 270);
		hidePanel(LoginPanel);
		showPanel(CreateCustomerPanel);
	}
  void createNewCustomerPanel()
	{
		JLabel customerFirstNameLabel = new JLabel("First Name");
		JLabel customerLastNameLabel = new JLabel("Last Name");
		JLabel customerAccountNumber = new JLabel("Account Number");
		JLabel customerPinNumber = new JLabel("Login Pin");
		JLabel customerPinVerification = new JLabel("Verify Pin");
		JLabel customerDepositBalance = new JLabel("Deposit Amount");
	
		 customerFirstNameInput = new JTextField();
		 customerLastNameInput = new JTextField();
		 customerAccountNumberInput = new JTextField();
		 customerPinInput = new JPasswordField();
		 customerPinVerificationInput = new JPasswordField();
		 customerBalanceInput = new JTextField();
		
		JButton createCustomerButton = new JButton("Create");
		JButton cancelCustomerButton = new JButton("Cancel");
		
		CreateCustomerPanel.add(customerFirstNameLabel);
		CreateCustomerPanel.add(customerLastNameLabel);
		CreateCustomerPanel.add(customerAccountNumber);
		CreateCustomerPanel.add(customerPinNumber);
		CreateCustomerPanel.add(customerPinVerification);
		CreateCustomerPanel.add(customerDepositBalance);
		
		CreateCustomerPanel.add(customerFirstNameInput);
		CreateCustomerPanel.add(customerLastNameInput);
		CreateCustomerPanel.add(customerAccountNumberInput);
		CreateCustomerPanel.add(customerPinInput);
		CreateCustomerPanel.add(customerPinVerificationInput);
		CreateCustomerPanel.add(customerBalanceInput);
		CreateCustomerPanel.add(createCustomerButton);
		CreateCustomerPanel.add(cancelCustomerButton);
		
		GroupLayout layout = new GroupLayout(CreateCustomerPanel);
		CreateCustomerPanel.setLayout(layout);
		   
		   layout.setAutoCreateGaps(true);
		   
		   layout.setAutoCreateContainerGaps(true);
		   GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		   hGroup.addGroup(layout.createParallelGroup().addComponent(customerFirstNameLabel).addComponent(customerLastNameLabel).addComponent(customerAccountNumber).addComponent(customerAccountNumber).addComponent(customerPinNumber).addComponent(customerPinVerification).addComponent(customerDepositBalance).addComponent(createCustomerButton));
		   hGroup.addGroup(layout.createParallelGroup().addComponent(customerFirstNameInput).addComponent(customerLastNameInput).addComponent(customerAccountNumberInput).addComponent(customerPinInput).addComponent(customerPinVerificationInput).addComponent(customerBalanceInput).addComponent(cancelCustomerButton));
		   layout.setHorizontalGroup(hGroup);
		   
		   
		   GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		   
		   vGroup.addGroup(layout.createParallelGroup().
		            addComponent(customerFirstNameLabel).addComponent(customerFirstNameInput));
		   vGroup.addGroup(layout.createParallelGroup().
				   addComponent(customerLastNameLabel).addComponent(customerLastNameInput));
		   vGroup.addGroup(layout.createParallelGroup().
				   addComponent(customerAccountNumber).addComponent(customerAccountNumberInput));
		   vGroup.addGroup(layout.createParallelGroup().
				   addComponent(customerPinNumber).addComponent(customerPinInput));
		   vGroup.addGroup(layout.createParallelGroup().
				   addComponent(customerPinVerification).addComponent(customerPinVerificationInput));
		   vGroup.addGroup(layout.createParallelGroup().
				   addComponent(customerDepositBalance).addComponent(customerBalanceInput));		   
		   vGroup.addGroup(layout.createParallelGroup().
				   addComponent(createCustomerButton).addComponent(cancelCustomerButton));
		   layout.setVerticalGroup(vGroup);
		   
		   createCustomerButton.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent e)
			   {
				   try
				   {
					   createCustomerExecute();
				   }catch(Exception m)
				   {
					   createCustomerPanelFunction();
					   hidePanel(CreateCustomerPanel);
					   showPanel(CreateCustomerPanel);
					   JOptionPane.showMessageDialog(null,"Kindly enter valid details.");
				   }
			   }
		   });
		   cancelCustomerButton.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent e)
			   {
				   createCustomerPanelFunction();
				   hidePanel(CreateCustomerPanel);
				   AtmWindow.setSize(400, 200);
				   showPanel(LoginPanel);
			   }
		   });
	}
  void createCustomerExecute()
  {
	 if(customerName()&customerAccountNumber()&customerPin()&customerBalance()&&customerPinVerify()&&validAccountNumber()&&validName())
	 {
		 String custoFirstName = customerFirstNameInput.getText();
		 String custoLastName = customerLastNameInput.getText();
		 int custoAccountNumber = Integer.parseInt(customerAccountNumberInput.getText());
		 @SuppressWarnings("deprecation")
		int custoPin = Integer.parseInt(customerPinInput.getText());
		 double custoBalance = Double.parseDouble(customerBalanceInput.getText());
	 Account acc = new Account(custoAccountNumber,custoPin,custoBalance, custoBalance,custoFirstName,custoLastName);
	 bankDatabase.addAccount(acc);
	 String customerWelcomeMessage = String.format("Hi %s %s. Welcome to our bank.%nYour new account number is: %d%nYour account balance: %.2f%nThanks for banking with us.Have a great day.", 
			 custoFirstName,custoLastName,custoAccountNumber,custoBalance);
	 JOptionPane.showMessageDialog(null, customerWelcomeMessage);
	 createCustomerPanelFunction();

	 hidePanel(CreateCustomerPanel);
	 AtmWindow.setSize(400,200);
	 showPanel(LoginPanel);
	 }
	 else
	 {
		 String completeMessage = String.format("Hi! We're sorry that your attempt to create a new account wasn't succesfull.%nReasons for the error:%n%s", createCustomerErrorMessage);
		 JOptionPane.showMessageDialog(null, completeMessage);
		 createCustomerPanelFunction();
		 hidePanel(CreateCustomerPanel);
		 showPanel(CreateCustomerPanel);
	 }
  }
  void createCustomerPanelFunction()
  {
	  customerFirstNameInput.setText("");
		 customerLastNameInput.setText("");
		 customerAccountNumberInput.setText("");
		 customerPinInput.setText("");
		 customerPinVerificationInput.setText("");
		 customerBalanceInput.setText(""); 
  }
  boolean validAccountNumber()
  {
	  if(customerAccountNumberInput.getText().charAt(0) == 0)
	  {
		  createCustomerErrorMessage += String.format("- %s%n","Account number cannot start with '0'.");
		  return false;
	  }
	  else
	  {
		  return true;
	  }
  }
  boolean validName()
  {
	  Pattern p = Pattern.compile(".*\\d.*");
		Matcher m = p.matcher(customerFirstNameInput.getText());
		Matcher n = p.matcher(customerLastNameInput.getText());
		if (m.matches() || n.matches()) {
			if(createCustomerErrorMessage.indexOf("Name")== -1)
			{
				createCustomerErrorMessage += String.format("- %s%n", "Name shouldn't contain numbers");
			}
			return false;
		}
	  else
	  {
		  return true;
	  }
  }
  boolean customerPinVerify()
  {
	  @SuppressWarnings("deprecation")
		int custoPin = Integer.parseInt(customerPinInput.getText());
	  @SuppressWarnings("deprecation")
		int custVerifyPin = Integer.parseInt(customerPinVerificationInput.getText());
	  if(custoPin == custVerifyPin)
	  {
		  return true;
	  }
	  else
	  {
		  createCustomerErrorMessage += String.format("- %s%n","Pin doesn't match");
		  return false;
	  }
  }
  boolean customerName()
  {
	  if(customerFirstNameInput.getText() != null && customerLastNameInput.getText() != null)
	  {
		  return true;
	  }
	  else
	  {
		  return false;
	  }
  }
  boolean customerAccountNumber()
  {
	  int customerAccNumber = Integer.parseInt(customerAccountNumberInput.getText());
	  boolean accountNumberNotPresent = true;
	  if(String.valueOf(customerAccNumber).length()!=5)
	  {
		  createCustomerErrorMessage += String.format("- %s%n", "Enter a valid account number that contains 5 numbers");
		  accountNumberNotPresent = false;
	  }
	  else
	  {
		  for(Account a:bankDatabase.accounts)
		  {
			  if(a.getAccountNumber() == customerAccNumber)
			  {
				  createCustomerErrorMessage += String.format("- %s%n", "Account ID already exists.Kindly choose another number");
				  accountNumberNotPresent = false;
				  break;
			  }
			  else
			  {
				  accountNumberNotPresent = true;
			  }
		  }
	  }
	 
	  return accountNumberNotPresent;
  }
  boolean customerPin()
  {
	  @SuppressWarnings("deprecation")
	int custoPin = Integer.parseInt(customerPinInput.getText());
	  @SuppressWarnings("deprecation")
	int custVerifyPin = Integer.parseInt(customerPinVerificationInput.getText());
	  if(String.valueOf(custoPin).length()!=5 || String.valueOf(custVerifyPin).length() != 5)
	  {
		  if(createCustomerErrorMessage.indexOf("5 digits")==-1)
		  {
			  createCustomerErrorMessage += String.format("- %s%n", "Please choose a pin of 5 digits.");  
		  }
		  return false;
	  }
	  else
	  {
		  return true;
	  }
  }
  boolean customerBalance()
  {
	  if(Double.parseDouble(customerBalanceInput.getText())<500)
	  {
		  if(createCustomerErrorMessage.indexOf("500")==-1)
		  {
		  createCustomerErrorMessage+= String.format("- %s$%n", "Please deposit amount greater than 500");
		  }
		  return false;
	  }
	  else
	  {
		  return true;
	  }
  }
  void createOptionPanel()
	{
		 JButton balance;
		 JButton deposit;
		 JButton withdrawal;
		 JButton exit;
		 JButton statement;
		 JButton promotions;
		 JLabel customerNameLabel = new JLabel("");
		 customerNameLabel.setText(customerName);
	balance  = new JButton("Balance");
	deposit = new JButton("Deposit");
	withdrawal = new JButton("Withdrawal");
	exit = new JButton("Exit");
	statement = new JButton("Statement");
	promotions = new JButton("Promotions");
	
	OptionWindowPanel.add(customerNameLabel);
	OptionWindowPanel.add(balance);
	OptionWindowPanel.add(deposit);
	OptionWindowPanel.add(withdrawal);
	OptionWindowPanel.add(exit);
	OptionWindowPanel.add(statement);
	OptionWindowPanel.add(promotions);
	
	GroupLayout layout = new GroupLayout(OptionWindowPanel);
	   OptionWindowPanel.setLayout(layout);
	  layout.setAutoCreateGaps(true);
	  layout.setAutoCreateContainerGaps(true);
	  
	  GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
	   hGroup.addGroup(layout.createParallelGroup().addComponent(balance).addComponent(deposit));
	   hGroup.addGroup(layout.createParallelGroup().addComponent(withdrawal).addComponent(statement));
	   hGroup.addGroup(layout.createParallelGroup().addComponent(promotions).addComponent(exit));
	   
	   layout.setHorizontalGroup(hGroup);
	   
	   GroupLayout.ParallelGroup vGroup = layout.createParallelGroup();
	   vGroup.addGroup(layout.createSequentialGroup().addComponent(balance).addComponent(deposit));
	   vGroup.addGroup(layout.createSequentialGroup().addComponent(withdrawal).addComponent(statement));
	   vGroup.addGroup(layout.createSequentialGroup().addComponent(promotions).addComponent(exit));
	   layout.setVerticalGroup(vGroup);
	
	promotions.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{
			String promotionList = String.format("%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n","1. Get 10% cash back offer on every purchase at shopping malls",
		    		  "2. Deposit 10,000$ within first 10 days of opening a new account and get 200$ back",
		    		  "3. Get 20% cashback on every purchase at dining services",
		    		  "4. Spend 500$ at any disney store with disney card and get 20% cashback",
		    		  "5. Create a new student account for your child and get 5% on every purchase on school suplies",
		    		  "6. Get a new travel rewards card and earn 5000 miles in Emirates airlines",
		    		  "7. Apply for new credit card before festival and get 10% discount on any purchase during festival.",
		    		  "8. Deposit 1000$ in your child student account and get 30% cashback on every purchase on school supplies",
		    		  "9. Fly around world with your travel credit card and get extra miles on every fly",
		    		  "10. Earn 9% cashback ");
			JOptionPane.showMessageDialog(null,promotionList);
			startPromotionWindow();
		}
	});
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
			
			long secs = TimeUnit.MILLISECONDS.toSeconds(totalTime);
			threadMessage += String.format("Thread id:  %s.  Customer name:  %s.  Thread state: %s. Endtime: %s    %s  Total time: %d seconds %n",threadId,customerName,"Customer Logged out", dateFormat.format(date),time.format(cal.getTime()),secs);
			String message = String.format("%s%n%s%n%n","Exiting the system...","Thank you! Goodbye!");
			JOptionPane.showMessageDialog(null,message);
			JOptionPane.showMessageDialog(null,threadMessage);
			atmObject.customerList.removeItem(customerName);
			hidePanel(OptionWindowPanel);
			usernameInput.setText("");
			passwordInput.setText("");
			AtmWindow.setTitle("");
			showPanel(LoginPanel);
		}
	});
	}
		
  public void setCustomer(String currentCustomerName)
  {
	  this.customerName = currentCustomerName;
	  this.currentAccountNumber = bankDatabase.getAccountNumber(currentCustomerName);
	  threadId = atmObject.customerThreadList.get((atmObject.customerThreadList.indexOf(customerName)+1));
	  AtmWindow.setTitle(customerName);
	  AtmWindow.getContentPane().removeAll();
	  showPanel(OptionWindowPanel);
  }
  
		public void startOptionWindow()
		{
			hidePanel(LoginPanel);
			showPanel(OptionWindowPanel);
		}
		public void createPromotionPanel()
		{
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
			
			PromotionWindowPanel.add(promo1);
			PromotionWindowPanel.add(promo2);
			PromotionWindowPanel.add(promo3);
			PromotionWindowPanel.add(promo4);
			PromotionWindowPanel.add(promo5);
			PromotionWindowPanel.add(promo6);
			PromotionWindowPanel.add(promo7);
			PromotionWindowPanel.add(promo8);
			PromotionWindowPanel.add(promo9);
			PromotionWindowPanel.add(promo10);
			PromotionWindowPanel.add(submit);
			   promo1.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					promotion(1);   
				   }
			   });   
			   promo2.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					promotion(2);   
				   }
			   });   
			   promo3.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					promotion(3);   
				   }
			   });  
			   promo4.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					promotion(4);   
				   }
			   });   
			   promo5.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					promotion(5);   
				   }
			   });   
			   promo6.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					promotion(6);   
				   }
			   }); 
			   promo7.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					promotion(7);   
				   }
			   });   
			   promo8.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					promotion(8);   
				   }
			   });   
			   promo9.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					promotion(9);   
				   }
			   });   
			   promo10.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent e){
					promotion(10);   
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
		public void startPromotionWindow()
		{
			 hidePanel(OptionWindowPanel);
			 showPanel(PromotionWindowPanel);	 
		}
		void promotion(int promoNumber)
		{
			int promotionNumber = promoNumber;
			if(promotions.indexOf(promotionNumber) == -1)
			{
				promotions.add(promotionNumber);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Please choose another promotion");	
			}
		}
		public void submitPromotions()
		{
			hidePanel(PromotionWindowPanel);
			startOptionWindow();
			AtmWindow.setSize(400, 200);
				JOptionPane.showMessageDialog(null, "Thank you for choosing the promotions. We will inform you once the promotions are online.");
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
							int randomNum = randomNumber.nextInt(10)+1;
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
		public void createDepositPanel()
		{
			JButton depositMoney = new JButton("Deposit");
			depositInput = new JTextField();
			depositInput.setText("");
			
			JLabel label = new JLabel("Welcome");
			JLabel label1 = new JLabel("Enter deposit amount in CENTS");
			DepositWindowPanel.add(depositMoney);
			depositInput.setToolTipText("Deposit amount");
			GroupLayout layout = new GroupLayout(DepositWindowPanel);
			   DepositWindowPanel.setLayout(layout);
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
		}
		public void deposit()
		{
			hidePanel(OptionWindowPanel);
			showPanel(DepositWindowPanel);
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
				depositInput.setText("");
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
						threadMessage += String.format("Thread id:  %s.  Customer name:  %s.  Thread state: %s. Start time: %s    %s %n",threadId,customerName,"Deposit", dateFormat.format(date),time.format(cal.getTime()));

						message += String.format("%n%s%n%s%nTransaction type: Deposit%nTransaction amount: $%.2f%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),depositAmount,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber)-depositAmount,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
								String localStatement = String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%.2f%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),depositAmount,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber)-depositAmount,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
								data.add(localStatement);
								depositInput.setText("");
								hidePanel(DepositWindowPanel);
								showPanel(OptionWindowPanel);
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
					  depositInput.setText("");
					  hidePanel(DepositWindowPanel);
					  showPanel(OptionWindowPanel);
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
	void createWithdrawalPanel()
	{
		JButton twenty;
		 JButton fourty;
		 JButton sixty;
		 JButton hundred;
		 JButton hundredTwenty;
		 JButton cancel;
		 
		twenty = new JButton("Twenty");
		fourty = new JButton("Fourty");
		sixty = new JButton("Sixty");
		hundred = new JButton("Hundred");
		hundredTwenty = new JButton("Hundred Twenty");
		cancel = new JButton("Cancel");
		
		WithdrawalWindowPanel.add(twenty);
		WithdrawalWindowPanel.add(fourty);
		WithdrawalWindowPanel.add(sixty);
		WithdrawalWindowPanel.add(hundred);
		WithdrawalWindowPanel.add(hundredTwenty);
		WithdrawalWindowPanel.add(cancel);
		GroupLayout layout = new GroupLayout(WithdrawalWindowPanel);
		WithdrawalWindowPanel.setLayout(layout);
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
		 public void withdrawal(){
				hidePanel(OptionWindowPanel);
				showPanel(WithdrawalWindowPanel);
		}
		public void cancel(){
			withdrawalValue = 0;
			execute(withdrawalValue);
			hidePanel(WithdrawalWindowPanel);
			showPanel(OptionWindowPanel);
		}
		public void twenty(){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			DateFormat time = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			hidePanel(WithdrawalWindowPanel);
			showPanel(WithdrawalWindowPanel);
			withdrawalValue = 20;
			execute(withdrawalValue);
			Transaction foo = new BalanceInquiry(currentAccountNumber,bankDatabase);
			threadMessage += String.format("Thread id:  %s.  Customer name:  %s.  Thread state: %s. Start time: %s    %s %n",threadId,customerName,"Withdrawal", dateFormat.format(date),time.format(cal.getTime()));
			message += String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					String localStatement = String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					data.add(localStatement);
		}
		public void fourty(){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			DateFormat time = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			hidePanel(WithdrawalWindowPanel);
			showPanel(WithdrawalWindowPanel);
			withdrawalValue = 40;
			execute(withdrawalValue);
			Transaction foo = new BalanceInquiry(currentAccountNumber,bankDatabase);
			threadMessage += String.format("Thread id:  %s.  Customer name:  %s.  Thread state: %s. Start time: %s    %s %n",threadId,customerName,"Withdrawal", dateFormat.format(date),time.format(cal.getTime()));
			message += String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					String localStatement = String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					data.add(localStatement);
		}
		public void sixty(){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			DateFormat time = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			hidePanel(WithdrawalWindowPanel);
			showPanel(WithdrawalWindowPanel);
			withdrawalValue = 60;
			execute(withdrawalValue);
			Transaction foo = new BalanceInquiry(currentAccountNumber,bankDatabase);
			threadMessage += String.format("Thread id:  %s.  Customer name:  %s.  Thread state: %s. Start time: %s    %s %n",threadId,customerName,"Withdrawal", dateFormat.format(date),time.format(cal.getTime()));
	 		message += String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					String localStatement = String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					data.add(localStatement);
		}
		public void hundred(){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			DateFormat time = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			hidePanel(WithdrawalWindowPanel);
			showPanel(WithdrawalWindowPanel);
			withdrawalValue = 100;
			execute(withdrawalValue);
			Transaction foo = new BalanceInquiry(currentAccountNumber,bankDatabase);
			threadMessage += String.format("Thread id:  %s.  Customer name:  %s.  Thread state: %s. Start time: %s    %s %n",threadId,customerName,"Withdrawal", dateFormat.format(date),time.format(cal.getTime()));
	 		message += String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					String localStatement = String.format("%n%s%n%s%nTransaction type: Withdrawal%nTransaction amount: $%d%nBefore Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%nAfter Transaction:%nAvailable Balance: $%.2f%nTotal Balance: $%.2f%n", dateFormat.format(date),time.format(cal.getTime()),withdrawalValue,foo.getAvailableBalance(currentAccountNumber)+withdrawalValue,foo.getTotalBalance(currentAccountNumber)+withdrawalValue,foo.getAvailableBalance(currentAccountNumber),foo.getTotalBalance(currentAccountNumber));
					data.add(localStatement);
		}
		public void hundredTwenty(){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			DateFormat time = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			hidePanel(WithdrawalWindowPanel);
			showPanel(WithdrawalWindowPanel);
			withdrawalValue = 120;
			execute(withdrawalValue);
			Transaction foo = new BalanceInquiry(currentAccountNumber,bankDatabase);
			threadMessage += String.format("Thread id:  %s.  Customer name:  %s.  Thread state: %s. Start time: %s    %s %n",threadId,customerName,"Withdrawal", dateFormat.format(date),time.format(cal.getTime()));
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

			threadMessage += String.format("Thread id:  %s.  Customer name:  %s.  Thread state: %s. Start time: %s    %s %n",threadId,customerName,"Balance Inquiry", dateFormat.format(date),time.format(cal.getTime()));
			Transaction transaction = new BalanceInquiry(currentAccountNumber,bankDatabase);
			transaction.execute();
		}
		void createStatementPanel()
		{

			 JButton completeStatement;
			 JButton dateStatement;

			completeStatement = new JButton("Complete Statement");
			dateStatement = new JButton("Select Dates");
			StatementWindowPanel.add(completeStatement);
			StatementWindowPanel.add(dateStatement);
			
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
		public void statement(){
			hidePanel(OptionWindowPanel);
			showPanel(StatementWindowPanel);	
		}
		public void completeStatement(){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			DateFormat time = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			threadMessage += String.format("Thread id:  %s.  Customer name:  %s.  Thread state: %s. Start time: %s    %s %n",threadId,customerName,"Viewed complete statement", dateFormat.format(date),time.format(cal.getTime()));
			if(message == "")
			{
				JOptionPane.showMessageDialog(null, "No transactions made.");
			}
			else
			{
				JOptionPane.showMessageDialog(null,message);	
			}
			hidePanel(StatementWindowPanel);
			showPanel(OptionWindowPanel);
		}
		
		void createModifiedPanel()
		{
			JLabel label1 = new JLabel("Start Date");
			startDate = new JTextField();
			JLabel label2 = new JLabel("End Date");
			endDate = new JTextField();
			
			JButton generateStatement = new JButton("Generate");

			ModifiedWindowPanel.add(generateStatement);
			ModifiedWindowPanel.add(label1);
			ModifiedWindowPanel.add(startDate);
			ModifiedWindowPanel.add(label2);
			ModifiedWindowPanel.add(endDate);
			GroupLayout layout = new GroupLayout(ModifiedWindowPanel);
			   ModifiedWindowPanel.setLayout(layout);

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
				   DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					Date currentDate = new Date();
					DateFormat time = new SimpleDateFormat("HH:mm:ss");
					Calendar cal = Calendar.getInstance();
					threadMessage += String.format("Thread id:  %s.  Customer name:  %s.  Thread state: %s. Start time: %s    %s %n",threadId,customerName,"Viewed statement", dateFormat.format(currentDate),time.format(cal.getTime()));
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
			   hidePanel(ModifiedWindowPanel);
			   showPanel(OptionWindowPanel);
			   startDate.setText("");
			   endDate.setText("");
			   statementMessage = "";
		}
		public void statementBetweenDates(){
			JOptionPane.showMessageDialog(null,"Enter date in the format: dd/MM/yyyy");
			hidePanel(StatementWindowPanel);
			showPanel(ModifiedWindowPanel);
		}
		
		public String doHttpGet(String url) {
			try {
				return httpGet(url);
			} catch(IOException e) {
				return "";
			}
		}
		
		// Implementing web services
		public  String httpGet(String urlStr) throws IOException {
			  URL url = new URL(urlStr);
			  HttpURLConnection conn =
			      (HttpURLConnection) url.openConnection();

			  if (conn.getResponseCode() != 200) {
			    throw new IOException(conn.getResponseMessage());
			  }

			  // Buffer the result into a string
			  BufferedReader rd = new BufferedReader(
			      new InputStreamReader(conn.getInputStream()));
			  StringBuilder sb = new StringBuilder();
			  String line;
			  while ((line = rd.readLine()) != null) {
			    sb.append(line);
			  }
			  rd.close();

			  conn.disconnect();
			  return sb.toString();
			}
		
		public String getWeather() {
			String url = "http://api.openweathermap.org/data/2.5/weather?q=Melbourne,USA;&APPID=22821222dfe57cf93b24637ae2a85367";
			String response = doHttpGet(url);
			return response;
		}
	}