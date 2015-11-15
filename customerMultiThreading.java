package atm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class customerMultiThreading implements Runnable{
	private String customerName;
	protected static String message = "";
	
	customerMultiThreading(String userName)
	{
		customerName = userName;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		DateFormat time = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		synchronized (message) {
			message += String.format("Thread id:  %d.  Customer name:  %s.  Thread state: %s. Start time: %s  %s %n",Thread.currentThread().getId(),customerName,Thread.currentThread().getState(), dateFormat.format(date),time.format(cal.getTime()));
		try{
			Thread.sleep(10000, 0);
		}catch(InterruptedException e)
		{
			
		}
		message += String.format("Thread: %d went to sleep for 10 seconds.End time: %s  %s%n%n", Thread.currentThread().getId(),dateFormat.format(date),time.format(cal.getTime()));
		}
	}
	
}

