package atm;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class ATMCaseStudy
{
	ATM atm= new ATM(this);
	JComboBox<String> customerList;
	List<Thread> threads = new ArrayList<>();
	List<String> customerThreadList = new ArrayList<>();
	List<String> customerTransactionMessage = new ArrayList<>();
	@SuppressWarnings("rawtypes")
	ArrayList<ArrayList> customerStatementMessage = new ArrayList<>();
	Thread thread;
	public void run()
	{		
		JFrame customerSetupWindow = new JFrame("Spawn customer window.");
		JPanel customerSetupWindowPanel = new JPanel();
		JButton spawnCustomer = new JButton("Spawn new customer");
		JButton showActivity = new JButton("Show Customer Activity");
		JButton exit = new JButton("Exit");
		customerList = new JComboBox<String>();
		customerList.addItem("New customer");
		customerList.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
		        int state = e.getStateChange();
		        if(state == ItemEvent.SELECTED) {
			        String customerName = (String) e.getItem();
		        	if(customerName == "New customer")
		        	{
		        		atm.newCustomer();
		        	}
		        	else
		        	{
		        		atm.setCustomer(customerName);
		        	}
		        }
			}
		});
		
		customerSetupWindow.add(customerSetupWindowPanel);
		customerSetupWindowPanel.add(spawnCustomer);
		customerSetupWindowPanel.add(showActivity);
		customerSetupWindowPanel.add(customerList);
		customerSetupWindowPanel.add(exit);

		customerSetupWindow.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		customerSetupWindow.setLocation(dim.width/2-customerSetupWindow.getSize().width/2, dim.height/2-customerSetupWindow.getSize().height/2);
		customerSetupWindow.setSize(400,200);
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				exit();
			}
		});
		spawnCustomer.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			spawnCustomer();}
		});		
		showActivity.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				showThreadInfo();
			}
		});
	}
	
	
	ArrayList<String> customerLst = new ArrayList<>();
	
	public void addCustomer(String customerName) {
		if(!customerLst.contains(customerName))
		{
			customerList.addItem(customerName);	
			customerLst.add(customerName);
		}		
	}
	public void spawnCustomer()
	{
		if(customerList.getItemCount()<=11)
		{
			atm.showLoginWindow();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Bank is currently having 10 customers.Kindly wait until there's space available.");
		}
	}
	public void showThreadInfo()
	{
		if(ATM.threadMessage == "")
		{
			JOptionPane.showMessageDialog(null,"No activity detected in the bank.");
		}
		else
		{
			JOptionPane.showMessageDialog(null, ATM.threadMessage);
		}
	}
	public void activeCustomerInfo()
	{
		String activeCustomer = String.format("Currently active customers in the bank: %d", customerList.getItemCount());
		JOptionPane.showMessageDialog(null,activeCustomer);
	}
	public void exit()
	{
		System.exit(0);
	}
	public static void main(String[] args)
	{
		ATMCaseStudy atmCustomerService = new ATMCaseStudy();
		atmCustomerService.run();
	}
}

