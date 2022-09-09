package it.innotek.demo.bankaccount.providers;

import java.time.LocalDate;

import it.innotek.demo.bankaccount.model.balance.Balance;
import it.innotek.demo.bankaccount.model.banktransfer.BankTransfer;
import it.innotek.demo.bankaccount.model.banktransfer.BankTransferResult;
import it.innotek.demo.bankaccount.model.transaction.TransactionPayload;

public interface BankAccountProvider {

	public Balance getBalance(int accountID);
	public TransactionPayload getTransactions(int accountID,LocalDate from , LocalDate to);
	
	public BankTransferResult  moneyTransfers(int accountID,String timeZone,BankTransfer transfer) ;
	 
}
