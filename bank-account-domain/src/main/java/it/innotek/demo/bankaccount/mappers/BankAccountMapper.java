package it.innotek.demo.bankaccount.mappers;

import org.mapstruct.Mapper;

import it.innotek.demo.bankaccount.model.balance.Balance;
import it.innotek.demo.bankaccount.model.balance.ServerResponseBalance;
import it.innotek.demo.bankaccount.model.banktransfer.BankTransferResult;
import it.innotek.demo.bankaccount.model.banktransfer.ServerResponseBankTransferResult;
import it.innotek.demo.bankaccount.model.server.ServerResponse;
import it.innotek.demo.bankaccount.model.transaction.ServerResponseTransactions;
import it.innotek.demo.bankaccount.model.transaction.Transaction;
import it.innotek.demo.bankaccount.model.transaction.TransactionHistory;
import it.innotek.demo.bankaccount.model.transaction.TransactionPayload;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
	ServerResponseBalance       toResponseBalance(ServerResponse<Balance> sourceResponse);
	ServerResponseTransactions  toResponseTransactions(ServerResponse<TransactionPayload> sourceResponse);
	ServerResponseBankTransferResult  toResponseBankTransfer(ServerResponse<BankTransferResult> sourceResponse);
	
	TransactionHistory			toTransactionHistory ( Transaction source, int accountId);
	
}
