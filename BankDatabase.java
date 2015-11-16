package atm;
import java.util.ArrayList;
//BankDatabase.java
//Represents the bank account information database 

public class BankDatabase
{
protected Account accounts[]; // array of Accounts
// no-argument BankDatabase constructor initializes accounts
public BankDatabase()
{
   accounts = new Account[ 10 ]; // just 2 accounts for testing
   accounts[0] = new Account(11111, 11111, 1000.0, 1200.0,"Kevin","Spacey");
   accounts[1] = new Account( 22222, 22222, 200.0, 1200.0,"Sheryl","Brain");  
   accounts[2] = new Account(33333,33333,5493.00,6000.00,"Darth","Vader");
   accounts[3] = new Account(44444,44444,900.00,1502.00,"Han","Solo");
   accounts[4] = new Account(55555,55555,1000.00,3000.00,"Peter","Mayhew");
   accounts[5] = new Account(66666,66666,1002.00,1400.00,"Clairie","Underwood");
   accounts[6] = new Account(77777,77777,500.00,1400.00,"Doug","Stamper");
   accounts[7] = new Account(88888,88888,700.00,1400.00,"Garret","Walker");
   accounts[8] = new Account(99999,99999,600.00,1500.00,"Linda","Vasquez");
   accounts[9] = new Account(12345,54321,700.00,1500.00,"Gillian","Cole");
   
   ArrayList<String> bankersDetails = new ArrayList<>();
   for(Account a : accounts)
   {
	   bankersDetails.add(Integer.toString(a.getAccountNumber()));
	   bankersDetails.add("");
   }
} // end no-argument BankDatabase constructor

// retrieve Account object containing specified account number
private Account getAccount( int accountNumber )
{
   // loop through accounts searching for matching account number
   for ( Account currentAccount : accounts )
   {
      // return current account if match found
      if ( currentAccount.getAccountNumber() == accountNumber)
         return currentAccount;
   } // end for

   return null; // if no matching account was found, return null
} // end method getAccount

// determine whether user-specified account number and PIN match
// those of an account in the database
public boolean authenticateUser( int userAccountNumber, int userPIN)
{
   // attempt to retrieve the account with the account number
   Account userAccount = getAccount( userAccountNumber );

   // if account exists, return result of Account method validatePIN
   if ( userAccount != null )
      return userAccount.validatePIN( userPIN );
   else
      return false; // account number not found, so return false
} // end method authenticateUser

// return available balance of Account with specified account number
public double getAvailableBalance( int userAccountNumber )
{
   return getAccount( userAccountNumber ).getAvailableBalance();
} // end method getAvailableBalance

// return total balance of Account with specified account number
public double getTotalBalance( int userAccountNumber )
{
   return getAccount( userAccountNumber ).getTotalBalance();
} // end method getTotalBalance

// credit an amount to Account with specified account number
public void credit( int userAccountNumber, double amount )
{
   getAccount( userAccountNumber ).credit( amount );
} // end method credit

// debit an amount from of Account with specified account number
public void debit( int userAccountNumber, double amount )
{
   getAccount( userAccountNumber ).debit( amount );
} // end method debit
} // end class BankDatabase
