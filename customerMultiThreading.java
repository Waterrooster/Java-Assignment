package atm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class customerMultiThreading implements Runnable{
	private Thread t;
	private String threadName;
	private String customerName;
	protected static String message = "";
	
	customerMultiThreading(String userName)
	{
		customerName = userName;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		synchronized (message) {
			message += String.format("Thread id:  %d.  Customer name:  %s.  Thread state: %s  %n%n",Thread.currentThread().getId(),customerName,Thread.currentThread().getState());
		try{
			message += String.format("Thread: %d went to sleep for 10 seconds.%n%n", Thread.currentThread().getId());
			Thread.sleep(10, 0);
		}catch(InterruptedException e)
		{
			
		}  
		}
	}
	
	public void start()
	{
		if(this.t == null)
		{
			t = new Thread(this, this.threadName);
			t.start();
		}
	}
	public static void runThread()
	{
		BankDatabase bankDB = new BankDatabase();
		
		ExecutorService e = Executors.newFixedThreadPool(10);
		for(int i = 0; i< bankDB.accounts.length;i++)
		{
			String customerName = bankDB.accounts[i].getName();
			e.execute(new customerMultiThreading(customerName));
		}
		
		e.shutdown();
		try {
			e.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
}
