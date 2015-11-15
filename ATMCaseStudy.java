package atm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//ATMCaseStudy.java
//Driver program for the ATM case study

public class ATMCaseStudy
{

	public static void runThread()
	{
		BankDatabase bankDB = new BankDatabase();
		
		ExecutorService e = Executors.newFixedThreadPool(10);
//		for(int i = 0; i< bankDB.accounts.length;i++)
		for(int i = 0; i< 1;i++)
		{
			e.execute(new ATM(bankDB.accounts[i]));
	
		}
		
		e.shutdown();
		try {
			e.awaitTermination(10, TimeUnit.MINUTES);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		ATMCaseStudy.runThread();
	}
}

