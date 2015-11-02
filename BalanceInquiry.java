package atm;
import javax.swing.JOptionPane;
//BalanceInquiry.java
//Represents a balance inquiry ATM transaction

public class BalanceInquiry extends Transaction
{


// BalanceInquiry constructor
public BalanceInquiry( int userAccountNumber, 
   BankDatabase atmBankDatabase )
{
   super( userAccountNumber, atmBankDatabase );
} // end BalanceInquiry constructor

// performs the transaction

public void execute()
{
	double availableBalance;
	double totalBalance;
   // get references to bank database and screen
   BankDatabase bankDatabase = getBankDatabase();

   // get the available balance for the account involved
  availableBalance = 
      bankDatabase.getAvailableBalance( getAccountNumber() );

   // get the total balance for the account involved
   totalBalance = 
      bankDatabase.getTotalBalance(getAccountNumber());
   String balance = String.format("Available balance: $%,.2f%n%nTotal balance: $%,.2f", availableBalance, totalBalance);
   JOptionPane.showMessageDialog(null,balance);
   // display the balance information on the screen
} // end method execute

public double getAvailableBalance(int userAccountNumber)
{
	BankDatabase bankdatabase = getBankDatabase();
	double availableBalance = bankdatabase.getAvailableBalance(getAccountNumber());
	return availableBalance;
}
public double getTotalBalance(int userAccountNumber)
{
	BankDatabase bankdatabase = getBankDatabase();
	double totalBalance = bankdatabase.getTotalBalance(getAccountNumber());
	return totalBalance;
}

} // end class BalanceInquiry


