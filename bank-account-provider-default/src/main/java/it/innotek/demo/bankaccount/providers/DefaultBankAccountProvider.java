package it.innotek.demo.bankaccount.providers;


import java.time.LocalDate;

import org.springframework.stereotype.Component;

import it.innotek.demo.bankaccount.model.balance.Balance;
import it.innotek.demo.bankaccount.model.banktransfer.BankTransfer;
import it.innotek.demo.bankaccount.model.banktransfer.BankTransferResult;
import it.innotek.demo.bankaccount.model.server.ServerResponse;
import it.innotek.demo.bankaccount.model.transaction.TransactionPayload;


@Component
public class DefaultBankAccountProvider implements BankAccountProvider {

	
	

	private final DefaultBankAccountProviderClient client;
	
	
	
	
	public DefaultBankAccountProvider(DefaultBankAccountProviderClient client ) {
		this.client = client;
	}

	
	
       
	
	@Override
	public Balance getBalance(int accountId) {
		ServerResponse<Balance> response = client.getBalance(accountId);
		return response.getPayload();
	}

	@Override
	public TransactionPayload getTransactions(int accountId, LocalDate from, LocalDate to) {
		ServerResponse<TransactionPayload> response = client.getTransactions(accountId,from,to);
		return response.getPayload();
	}

	@Override
	public BankTransferResult moneyTransfers(int accountId, String timeZone, BankTransfer transfer) {
		
		ServerResponse<BankTransferResult> response = client.moneyTransfers(accountId,timeZone,transfer);
		return response.getPayload();
	}
	
	
	/*
	private DefaultBankAccountProviderService service;
	private ObjectMapper objectMapper;
	private final DefaultBankAccountProviderServiceFactory serviceFactory;
	
	
	private DefaultBankAccountProviderService getService() {
		if ( service == null) {
			service = serviceFactory.getService();
		}
		return service ; 
		
	}
	
	private ObjectMapper getObjectMapper() {
		if ( objectMapper == null) {
			objectMapper = serviceFactory.getObjectMapper();
		}
		return objectMapper ; 
		
	}
	@Autowired
	public DefaultBankAccountProvider(DefaultBankAccountProviderServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;

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
						ServerResponseObject responseError = getObjectMapper().readValue(errorBodyText, ServerResponseObject.class) ;
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
				return getService().getBalance(accountID);
			}, 
			() -> String.format("retrieving balance for given account %s", accountID)
		); 
		
		return result.getPayload();
	}

	@Override
	public TransactionPayload getTransactions(int accountID, LocalDate from, LocalDate to) {
		
		ServerResponseTransactions result = execute( () -> {
			return getService().getTransactions(accountID, from, to);
			}, 
		    () -> String.format("retrieving transactions for given account %s", accountID)
		);
		return result.getPayload();
	}

	@Override
	public BankTransferResult moneyTransfers(int accountID, String timeZone, BankTransfer transfer) {
		
		
		
		ServerResponseBankTransferResult result = execute( () -> {
			
			
			return  getService().moneyTransfers(accountID, timeZone, transfer);
			}, 
			() -> String.format("make moneyTransfers for given account %s", accountID)
		);
		return result.getPayload();
	}
*/
}
