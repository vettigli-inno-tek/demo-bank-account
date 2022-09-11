package it.innotek.demo.bankaccount.mappers.impl;

import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import it.innotek.demo.bankaccount.mappers.BankAccountMapper;
import it.innotek.demo.bankaccount.model.balance.Balance;
import it.innotek.demo.bankaccount.model.balance.ServerResponseBalance;
import it.innotek.demo.bankaccount.model.banktransfer.BankTransferResult;
import it.innotek.demo.bankaccount.model.banktransfer.ServerResponseBankTransferResult;
import it.innotek.demo.bankaccount.model.server.ServerResponse;
import it.innotek.demo.bankaccount.model.transaction.ServerResponseTransactions;
import it.innotek.demo.bankaccount.model.transaction.Transaction;
import it.innotek.demo.bankaccount.model.transaction.TransactionHistory;
import it.innotek.demo.bankaccount.model.transaction.TransactionPayload;


@Component
public class BankAccountMapperImpl implements BankAccountMapper  {

	public <S extends ServerResponse<T>, R extends ServerResponse<T>,T> R  cloneTO(S source, Supplier<R> supplier) {
		
		R result = supplier.get();
		result.setErrors( source.getErrors());
		result.setPayload(source.getPayload());
		result.setStatus(source.getStatus());
		result.setRequestID(source.getRequestID());
		
		
		return result ; 
	}
	
	
	@Override
	public ServerResponseBalance toResponseBalance(ServerResponse<Balance> sourceResponse) {
		
		return cloneTO( sourceResponse,  ServerResponseBalance::new);
	}

	@Override
	public ServerResponseTransactions toResponseTransactions(ServerResponse<TransactionPayload> sourceResponse) {
		return cloneTO( sourceResponse,  ServerResponseTransactions::new);
	}

	@Override
	public ServerResponseBankTransferResult toResponseBankTransfer(ServerResponse<BankTransferResult> sourceResponse) {
		return cloneTO( sourceResponse,  ServerResponseBankTransferResult::new);
	}


	@Override
	public TransactionHistory toTransactionHistory(Transaction source, int accountId  ) {
		TransactionHistory result = new TransactionHistory();
		
		result.setAccountId(accountId);
		result.setAccountingDate(source.getAccountingDate());
		result.setAmount(source.getAmount());
		result.setCurrency(source.getCurrency());
		result.setDescription(source.getDescription());
		result.setOperationId(source.getOperationId());
		result.setTransactionId(source.getTransactionId());
		result.setValueDate(source.getValueDate());
		
		return result;
	}

}
