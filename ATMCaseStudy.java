package atm;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;


public class ATMCaseStudy
{
	List<Thread> threads = new ArrayList<>();
	static int activeCustomers = 0;
	
	public void run()
	{		
		JFrame customerSetupWindow = new JFrame("Spawn customer window.");
		JPanel customerSetupWindowPanel = new JPanel();
		JButton spawnCustomer = new JButton("Spawn new customer");
		JButton showActivity = new JButton("Show Customer Activity");
		JButton activeCustomerInfo = new JButton("Active Customers");
		customerSetupWindow.add(customerSetupWindowPanel);
		customerSetupWindowPanel.add(spawnCustomer);
		customerSetupWindowPanel.add(showActivity);
		customerSetupWindowPanel.add(activeCustomerInfo);
		customerSetupWindow.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		customerSetupWindow.setLocation(dim.width/2-customerSetupWindow.getSize().width/2, dim.height/2-customerSetupWindow.getSize().height/2);
		customerSetupWindow.setSize(400,200);
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
		activeCustomerInfo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				activeCustomerInfo();
			}
		});
	}
	public void spawnCustomer()
	{
		if(activeCustomers<=10)
		{
			activeCustomers++;
			ATM atm = new ATM();
			Thread thread = new Thread(atm);
			atm.setThreadId(thread.getId());
			threads.add(thread);
			thread.run();
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
		String activeCustomer = String.format("Currently active customers in the bank: %d", activeCustomers);
		JOptionPane.showMessageDialog(null,activeCustomer);
	}
	public static void main(String[] args)
	{
		ATMCaseStudy atmCustomerService = new ATMCaseStudy();
		atmCustomerService.run();
	}
}

