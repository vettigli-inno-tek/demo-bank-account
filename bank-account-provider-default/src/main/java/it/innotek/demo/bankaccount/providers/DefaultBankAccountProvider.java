package it.innotek.demo.bankaccount.providers;

import java.time.LocalDate;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.innotek.demo.bankaccount.enumeration.ServerResponseStatus;
import it.innotek.demo.bankaccount.excpetion.BankAccountProviderException;
import it.innotek.demo.bankaccount.model.balance.Balance;
import it.innotek.demo.bankaccount.model.balance.ServerResponseBalance;
import it.innotek.demo.bankaccount.model.banktransfer.BankTransfer;
import it.innotek.demo.bankaccount.model.banktransfer.BankTransferResult;
import it.innotek.demo.bankaccount.model.banktransfer.ServerResponseBankTransferResult;
import it.innotek.demo.bankaccount.model.server.ServerErrorResponse;
import it.innotek.demo.bankaccount.model.server.ServerResponse;
import it.innotek.demo.bankaccount.model.server.ServerResponseObject;
import it.innotek.demo.bankaccount.model.transaction.ServerResponseTransactions;
import it.innotek.demo.bankaccount.model.transaction.TransactionPayload;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

@Component
public class DefaultBankAccountProvider implements BankAccountProvider {

	
	private final static Logger logger = LoggerFactory.getLogger(DefaultBankAccountProvider.class);
	
	private DefaultBankAccountProviderService service;
	private ObjectMapper objectMapper ;
	@Autowired
	public DefaultBankAccountProvider(DefaultBankAccountProviderServiceFactory serviceFactory) {
		this.service = serviceFactory.getService();
		this.objectMapper = serviceFactory.getObjectMapper();
	}

	private <T extends ServerResponse<?>>  T execute(Supplier<Call<T>> supplier, Supplier<String> messageSupplier ) {
		String messageOperation = messageSupplier.get();
		try {
			Call<T> callSync = supplier.get();
			
			
			logger.debug("Start " + messageOperation);
			
			Response<T> callResponse = callSync.execute();
			
			if ( !callResponse.isSuccessful()) {
				String message = "Error on " +messageOperation;
				
				
				BankAccountProviderException exception = new BankAccountProviderException(message);
				
				ServerErrorResponse error = new ServerErrorResponse();
				error.setCode(      Integer.toString(callResponse.code()));
				error.setDescription(callResponse.message());
				error.setDetails("ProviderError");
				exception.getErrors().add(error);
				
				ResponseBody errorBody = callResponse.errorBody();
				String errorBodyText =   new String (errorBody.bytes());
				if ( StringUtils.hasText(errorBodyText))
				{
					try {
						ServerResponseObject responseError = objectMapper.readValue(errorBodyText, ServerResponseObject.class) ;
						exception.getErrors().addAll(responseError.getErrors());
					} catch (JsonProcessingException e) {
						logger.warn("Unable to parse errorBody on " + messageOperation);
					}
				}
				throw exception;
			}
			
			T response = callResponse.body();
			if ( response.getStatus() != ServerResponseStatus.OK) {
				String message = "Error on " +messageOperation;
				BankAccountProviderException exception = new BankAccountProviderException(message);
				exception.getErrors().addAll(response.getErrors());
				throw exception;
			}
			logger.debug("End " + messageOperation);
			return  response ;
			
		} 
		
		catch (BankAccountProviderException e) {
			logger.error("Error on " + messageOperation,e);
			throw e;
		}
		catch (Exception e) {
			logger.error("Error on " + messageOperation,e);
			throw new BankAccountProviderException("Unexpected error by calling server for " + messageOperation ,e);
		}
	}

	@Override
	public Balance getBalance(int accountID) {
		
		ServerResponseBalance result = execute( () -> {
				return service.getBalance(accountID);
			}, 
			() -> String.format("retrieving balance for given account %s", accountID)
		); 
		
		return result.getPayload();
	}

	@Override
	public TransactionPayload getTransactions(int accountID, LocalDate from, LocalDate to) {
		
		ServerResponseTransactions result = execute( () -> {
			return service.getTransactions(accountID, from, to);
			}, 
		    () -> String.format("retrieving transactions for given account %s", accountID)
		);
		return result.getPayload();
	}

	@Override
	public BankTransferResult moneyTransfers(int accountID, String timeZone, BankTransfer transfer) {
		
		
		
		ServerResponseBankTransferResult result = execute( () -> {
			
			
			return  service.moneyTransfers(accountID, timeZone, transfer);
			}, 
			() -> String.format("make moneyTransfers for given account %s", accountID)
		);
		return result.getPayload();
	}

}
