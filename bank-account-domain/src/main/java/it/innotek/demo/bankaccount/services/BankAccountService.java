package it.innotek.demo.bankaccount.services;

import java.time.LocalDate;
import java.util.List;

import it.innotek.demo.bankaccount.model.balance.Balance;
import it.innotek.demo.bankaccount.model.banktransfer.BankTransfer;
import it.innotek.demo.bankaccount.model.banktransfer.BankTransferResult;
import it.innotek.demo.bankaccount.model.server.ServerResponse;
import it.innotek.demo.bankaccount.model.transaction.Transaction;
import it.innotek.demo.bankaccount.model.transaction.TransactionPayload;

public interface BankAccountService {

	public ServerResponse<Balance> getBalance(int accountID);
	public ServerResponse<TransactionPayload> getTransactions(int accountID,LocalDate from , LocalDate to);
	
	public ServerResponse<BankTransferResult>  moneyTransfers(int accountID,String timeZone,BankTransfer transfer) ;

	
	public  void persistTransactions (int accountId , List<Transaction> transactions) ; 	
}
