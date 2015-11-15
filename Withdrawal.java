package atm;
//Withdrawal.java
//Represents a withdrawal ATM transaction

import javax.swing.JOptionPane;

public class Withdrawal extends Transaction
{
	ATM atm = new ATM();
private int amount; // amount to withdraw
private CashDispenser cashDispenser; // reference to cash dispenser

// Withdrawal constructor
public Withdrawal( int userAccountNumber, 
   BankDatabase atmBankDatabase, 
   CashDispenser atmCashDispenser,int atmWithdrawalAmount)
{
   // initialize superclass variables
   super( userAccountNumber, atmBankDatabase );
   
   // initialize references to keypad and cash dispenser
   cashDispenser = atmCashDispenser;
} // end Withdrawal constructor

// perform transaction
public void execute()
{
   boolean cashDispensed = false; // cash was not dispensed yet
   double availableBalance; // amount available for withdrawal
   double totalBalance;
   // get references to bank database and screen
   BankDatabase bankDatabase = getBankDatabase(); 

   // loop until cash is dispensed or the user cancels
   do
   {
      // obtain a chosen withdrawal amount from the user 
      amount = atm.withdrawalValue;
      
      // check whether user chose a withdrawal amount or canceled
      if ( amount != 0 )
      {
         // get available balance of account involved
         availableBalance = 
            bankDatabase.getAvailableBalance( getAccountNumber() );
   
         // check whether the user has enough money in the account 
         if ( amount <= availableBalance )
         {   
            // check whether the cash dispenser has enough money
            if ( cashDispenser.isSufficientCashAvailable( amount ) )
            {
               // update the account involved to reflect withdrawal
               bankDatabase.debit( getAccountNumber(), amount );
               
               cashDispenser.dispenseCash( amount ); // dispense cash
               cashDispensed = true; // cash was dispensed
               // instruct user to take cash
               BankDatabase bankDatabase1 = getBankDatabase();

               // get the available balance for the account involved
              availableBalance = 
                  bankDatabase1.getAvailableBalance( getAccountNumber() );

               // get the total balance for the account involved
               totalBalance = 
                  bankDatabase1.getTotalBalance( getAccountNumber());
               
               String message = String.format("Please take your cash now. %nYour remaining balance is: $%.2f%nYour total balance is: $%.2f",availableBalance,totalBalance);
               JOptionPane.showMessageDialog(null, message);
            } // end if
            else	// cash dispenser does not have enough cash
            {
            	String message = String.format("%s%n%n%s", "Insufficient cash available in the ATM.","Please choose a smaller amount.");
                JOptionPane.showMessageDialog(null,message);
                atm.frame4.setVisible(false);
                atm.frame2.setVisible(true);
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

} // end method execute

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


} // end class Withdrawal
