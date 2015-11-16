package atm;

//Account.java
//Represents a bank account

public class Account 
{
private int accountNumber; // account number
private int pin; // PIN for authentication
protected double availableBalance; // funds available for withdrawal
protected double totalBalance; // funds available + pending deposits
protected String firstName;
protected String lastName;
// Account constructor initializes attributes
public Account(int theAccountNumber)
{
	accountNumber = theAccountNumber;
}
public Account( int theAccountNumber, int thePIN, 
   double theAvailableBalance, double theTotalBalance,String fName, String lName )
{
   accountNumber = theAccountNumber;
   pin = thePIN;
   availableBalance = theAvailableBalance;
   totalBalance = theTotalBalance;
   firstName = fName;
   lastName = lName;
} // end Account constructor

// determines whether a user-specified PIN matches PIN in Account
public boolean validatePIN( int userPIN )
{
   if ( userPIN == pin )
      return true;
   else
      return false;
} // end method validatePIN

public String getFirstName()
{
	return firstName;
}
public String getLastName()
{
	return lastName;
}
public String getName(int accNumber)
{
	int accountNumber = accNumber;
	String name = "";
	if(accountNumber == 11111)
	{
		name= "Kevin Spacey";
	}
	if(accountNumber == 22222)
	{
		name= "Sheryl Brain";
	}
	if(accountNumber == 33333)
	{
		name= "Darth Vader";
	}
	if(accountNumber == 44444)
	{
		name= "Han Solo";
	}
	if(accountNumber == 55555)
	{
		name= "Peter Mayhew";
	}
	if(accountNumber == 66666)
	{
		name = "Clairie Underwood";
	}
	if(accountNumber == 77777)
	{
		name = "Doug Stamper";
	}
	if(accountNumber == 88888)
	{
		name = "Garret Walker";
	}
	if(accountNumber == 99999)
	{
		name = "Linda Vasquez";
	}
	if(accountNumber == 12345)
	{
		name = "Gillian Cole";
	}
	return name;
}
// returns available balance
public double getAvailableBalance()
{
   return availableBalance;
} // end getAvailableBalance

// returns the total balance
public double getTotalBalance()
{
   return totalBalance;
} // end method getTotalBalance


// credits an amount to the account
public void credit( double amount )
{
   totalBalance += amount; // add to total balance
} // end method credit

// debits an amount from the account
public void debit( double amount )
{
   availableBalance -= amount; // subtract from available balance
   totalBalance -= amount; // subtract from total balance
} // end method debit

// returns account number
public int getAccountNumber()
{
   return accountNumber;  
} // end method getAccountNumber
} // end class Account
