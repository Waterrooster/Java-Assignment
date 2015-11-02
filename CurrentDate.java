package atm;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
public class CurrentDate {
   protected static Date date;
   protected static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
   protected static DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
   public static boolean checkDate(Date start, Date end,Date currentDate)
   {
	   if(currentDate.after(start) && currentDate.before(end))
	   {
		   return true;
	   }
	   else
	   {
		   return false;
	   }
   }
   public static boolean isThisDateValid(String dateToValidate, String dateFromat){
		
		if(dateToValidate == null){
			return false;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
		
		try {
			
			//if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
		
		} catch (ParseException e) {
			return false;
		}
		
		return true;
	}
   public static boolean checkDatebetweenDates(String startDate,String endDate,String currentDate) throws ParseException
   {
	   SimpleDateFormat datefrmt = new SimpleDateFormat("dd/MM/yyyy");
	   Date startingDate = datefrmt.parse(startDate);
	   Date endingDate = datefrmt.parse(endDate);
	   Date checkDate = datefrmt.parse(currentDate);
	   
	   if(checkDate.after(startingDate) && checkDate.before(endingDate))
	   {
		   return true;
	   }
	   else
	   {
		   return false;
	   }   
   }
  
}
