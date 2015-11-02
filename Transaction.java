package atm;

//Transaction.java
//Abstract superclass Transaction represents an ATM transaction

public abstract class Transaction
{
private int accountNumber; // indicates account involved
private BankDatabase bankDatabase; // account info database

// Transaction constructor invoked by subclasses using super()
public Transaction( int userAccountNumber, 
   BankDatabase atmBankDatabase )
{
   accountNumber = userAccountNumber;
   bankDatabase = atmBankDatabase;
} // end Transaction constructor

// return account number 
public int getAccountNumber()
{
   return accountNumber; 
} // end method getAccountNumber

// return reference to screen
// return reference to bank database
public BankDatabase getBankDatabase()
{
   return bankDatabase;
} // end method getBankDatabase

// perform the transaction (overridden by each subclass)
abstract public void execute();
abstract public double getAvailableBalance(int userAccountNumber);
abstract public double getTotalBalance(int userAccountNumber);
} // end class Transaction
