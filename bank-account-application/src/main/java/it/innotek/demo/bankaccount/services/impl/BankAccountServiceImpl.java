package it.innotek.demo.bankaccount.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.innotek.demo.bankaccount.mappers.BankAccountMapper;
import it.innotek.demo.bankaccount.model.balance.Balance;
import it.innotek.demo.bankaccount.model.banktransfer.BankTransfer;
import it.innotek.demo.bankaccount.model.banktransfer.BankTransferResult;
import it.innotek.demo.bankaccount.model.server.ServerResponse;
import it.innotek.demo.bankaccount.model.transaction.Transaction;
import it.innotek.demo.bankaccount.model.transaction.TransactionHistory;
import it.innotek.demo.bankaccount.model.transaction.TransactionPayload;
import it.innotek.demo.bankaccount.providers.BankAccountProvider;
import it.innotek.demo.bankaccount.repository.TransactionHistoryRepository;
import it.innotek.demo.bankaccount.services.BankAccountService;

@Component
public  class BankAccountServiceImpl implements BankAccountService {

	
	private final BankAccountProvider provider ; 
	private final BankAccountMapper mapper;
	private final  TransactionHistoryRepository transactionHistoryRepository;

	
	

	
	@Autowired
	public BankAccountServiceImpl ( BankAccountProvider provider,
			final  TransactionHistoryRepository transactionHistoryRepository,
			final BankAccountMapper mapper
			) {
		this.provider = provider;
		this.transactionHistoryRepository = transactionHistoryRepository;
		this.mapper = mapper;
	}
	
   public String createRequestID( ) {
	   return UUID.randomUUID().toString();
   }
   
	
	@Override
	public ServerResponse<Balance> getBalance(int accountID) {
		
		String requestID = createRequestID( );
		Balance result = provider.getBalance(accountID);
		ServerResponse<Balance> response = ServerResponse.OK(result, requestID);	
		return response;
	}

	@Override
	public ServerResponse<TransactionPayload> getTransactions(int accountID, LocalDate from, LocalDate to) {
		String requestID = createRequestID( );
		TransactionPayload result = provider.getTransactions(accountID,from ,to) ;
		ServerResponse<TransactionPayload> response = ServerResponse.OK(result, requestID);	
		return response;
	
	}


	
	
	@Override
	public ServerResponse<BankTransferResult> moneyTransfers(int accountID, String timeZone, BankTransfer transfer) {
		
		String requestID = createRequestID( );
		BankTransferResult result = provider.moneyTransfers(accountID,timeZone ,transfer) ;
		ServerResponse<BankTransferResult> response =ServerResponse.OK(result, requestID);	
		return response;
				 
		 
		
	}
	
	
	
	public  void persistTransactions (int accountId , List<Transaction> transactions) {
		
		final List<TransactionHistory> transactionHistoryList = transactions.stream().map((item) ->  mapper.toTransactionHistory(item, accountId)).collect(Collectors.toList());
		
		
		final List<String> transactionIds = transactionHistoryList.stream().map(TransactionHistory::getTransactionId).collect(Collectors.toList());
		final List<Long> savedHistoryIds = transactionHistoryRepository.findAllByAccountIdAndTransactionIds(accountId, transactionIds);
		transactionHistoryRepository.deleteAllById(savedHistoryIds);

		transactionHistoryRepository.saveAll(transactionHistoryList);

	}
	
	
}
