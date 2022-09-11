package it.innotek.demo.bankaccount.providers;

import java.net.URI;
import java.time.LocalDate;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriTemplate;

import it.innotek.demo.bankaccount.enumeration.ServerResponseStatus;
import it.innotek.demo.bankaccount.excpetion.BankAccountProviderException;
import it.innotek.demo.bankaccount.model.balance.Balance;
import it.innotek.demo.bankaccount.model.banktransfer.BankTransfer;
import it.innotek.demo.bankaccount.model.banktransfer.BankTransferResult;
import it.innotek.demo.bankaccount.model.server.ServerResponse;
import it.innotek.demo.bankaccount.model.transaction.TransactionPayload;
import reactor.core.publisher.Mono;


@Service
public class DefaultBankAccountProviderClient {

	private final static Logger logger = LoggerFactory.getLogger(DefaultBankAccountProviderClient.class);
	
	
    private final WebClient webClient;
	
	
	public DefaultBankAccountProviderClient(WebClient webClient) {
		
		this.webClient = webClient;
	}
	
	public <T extends ServerResponse<?>>  T callApi(Supplier<Mono<T>> supplier,  Supplier<String> messageSupplier ) {
		String messageOperation = messageSupplier.get();
		logger.debug("Start " + messageOperation);
		
		Mono<T> mono = supplier.get();

		T response = mono.block(); 
		if ( !ServerResponseStatus.OK.equals(response.getStatus())) {
			String message = "Error on " +messageOperation;
			BankAccountProviderException exception = new BankAccountProviderException(message);
			exception.getErrors().addAll(response.getErrors());
			logger.error("Error " + messageOperation);
			throw exception;
		}
		logger.debug("End " + messageOperation);
		return response;
	}
	

	
	
	public ServerResponse<Balance>  getBalance(int accountId) {
		
		ServerResponse<Balance> response = callApi(() -> this.webClient
				.get()
				.uri("accounts/{accountId}/balance", accountId)
				.exchangeToMono(r -> r.bodyToMono(new ParameterizedTypeReference<ServerResponse<Balance>>() {
				})) 
				,() -> String.format("retrieving balance for given account %s", accountId)
		);
		
		return response;
	}

	
	public ServerResponse<TransactionPayload> getTransactions(int accountId, LocalDate from, LocalDate to) {
		
		
		Function<UriBuilder, URI> uriFunction = (uriBuilder) -> {
			
			UriTemplate uriTemplate =   new UriTemplate("accounts/{accountId}/transactions");
			URI uri		 		    = uriTemplate.expand(accountId);
			String uriPath = uri.getPath();	
			URI resultUri =uriBuilder
					 .path(uriPath)
					 .queryParam("fromAccountingDate", from)
					 .queryParam("toAccountingDate", to)
					 .build(); 
			return resultUri  ; 
		};
		
		ParameterizedTypeReference<ServerResponse<TransactionPayload>> paramType = new ParameterizedTypeReference<ServerResponse<TransactionPayload>>() {
		}; 
		ServerResponse<TransactionPayload> response = callApi(() -> this.webClient
				.get()
				.uri(uriFunction)
				.exchangeToMono(r -> {
					
						return r.bodyToMono(paramType);
					 
					
						
				}) 
				,() -> String.format("retrieving transactions for given account %s", accountId)
		);
		
		return response;
	}

	
	public ServerResponse<BankTransferResult> moneyTransfers(int accountId, String timeZone, BankTransfer transfer) {
		
		
		ServerResponse<BankTransferResult> response = callApi(() -> this.webClient
				.post()
				.uri("accounts/{accountId}/payments/money-transfers", accountId)
				.contentType(MediaType.APPLICATION_JSON )
				.header("X-Time-Zone", timeZone)
				.body(Mono.just(transfer), BankTransfer.class)
				.exchangeToMono(r -> r.bodyToMono(new ParameterizedTypeReference<ServerResponse<BankTransferResult>>() {
				})) 
				,() -> String.format("banck transfer for given account %s", accountId)
		);
		
		return response;
	}
	
	
}
