package it.innotek.demo.bankaccount.restapi;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.innotek.demo.bankaccount.mappers.BankAccountMapper;
//import it.innotek.demo.bankaccount.mappers.impl.BankAccountMapperImpl;
import it.innotek.demo.bankaccount.model.balance.Balance;
import it.innotek.demo.bankaccount.model.balance.ServerResponseBalance;
import it.innotek.demo.bankaccount.model.banktransfer.BankTransfer;
import it.innotek.demo.bankaccount.model.banktransfer.BankTransferResult;
import it.innotek.demo.bankaccount.model.banktransfer.ServerResponseBankTransferResult;
import it.innotek.demo.bankaccount.model.server.ServerResponse;
import it.innotek.demo.bankaccount.model.transaction.ServerResponseTransactions;
import it.innotek.demo.bankaccount.model.transaction.TransactionPayload;
import it.innotek.demo.bankaccount.services.BankAccountService;

@RestController
@RequestMapping("/bank/account/{accountId}")

public class BankAccountRestController {

		
		private final BankAccountService service;
		private final BankAccountMapper mapper ;
 
		
		public BankAccountRestController(
				final BankAccountService service,
				final BankAccountMapper mapper
				
			 	) {
			this.service = service;
			this.mapper = mapper;
			
		}
		
		@GetMapping("/balance")
		@Operation(summary = "Return the actual account balance")
		@ApiResponses(value = {
				@ApiResponse(
						responseCode = "200",
						description = "The actual balance for the account is retrieved",
						content = {
								@Content(
										mediaType = APPLICATION_JSON_VALUE,
										schema = @Schema(implementation = ServerResponseBalance.class)
								)
						}
				)
		})
		
		
		public ResponseEntity<ServerResponseBalance> getBalance(@Parameter(description = "The id of the account", example = "1234") @PathVariable final int accountId) {
			
			ServerResponse<Balance> resultService  =  this.service.getBalance(accountId);	

			ServerResponseBalance result = mapper.toResponseBalance(resultService);
			
			return ResponseEntity
					.ok()
					.body(result);
		}

		
		@GetMapping("/transactions")
		@Operation(summary = "Get all transactions of the given account")
		@ApiResponses(value = {
				@ApiResponse(
						responseCode = "200",
						description = "All transactions for the account are retrieved",
						content = {
								@Content(
										mediaType = APPLICATION_JSON_VALUE,
										schema = @Schema(implementation = ServerResponseTransactions.class)
								)
						}
				)
		})
		public ResponseEntity<ServerResponseTransactions> getTransactions(
				@Parameter(description = "The id of the account", example = "1234") @PathVariable final int accountId,
				@Parameter(description = "The starting date for the list of transactions", example = "2021-01-01") @RequestParam("from") final LocalDate from,
				@Parameter(description = "The ending date for the list of transactions", example = "2021-01-01") @RequestParam("to") final LocalDate to
		) {
			
			ServerResponse<TransactionPayload> resultService  =  this.service.getTransactions(accountId, from, to);	

			
			service. persistTransactions(accountId,resultService.getPayload().getList());
			
			ServerResponseTransactions result = mapper.toResponseTransactions(resultService);
			return ResponseEntity
					.ok()
					.body(result);
		}
		
		@PostMapping(value = "/payments/money-transfers", consumes = APPLICATION_JSON_VALUE)
		@Operation(summary = "Make a money transfer")
		@ApiResponses(value = {
				@ApiResponse(
						responseCode = "200",
						description = "All transactions for the account are retrieved",
						content = {
								@Content(
										mediaType = APPLICATION_JSON_VALUE,
										schema = @Schema(implementation = ServerResponseBankTransferResult.class)
								)
						}
				)
		})
		
		public ResponseEntity<ServerResponseBankTransferResult> moneyTransfer(
				@Parameter(description = "The id of the account", example = "1234") 
				@PathVariable final int accountId,
				@Parameter(description = "The time zone used to provide the request date fields", example = "Europe/Rome") 
				@RequestHeader("X-Time-Zone") String timeZone,
				@Parameter(description = "The data to make a money transfer", required = true, schema = @Schema(implementation = BankTransfer.class))  
				@RequestBody BankTransfer moneyTransfer
		) {
			
			ServerResponse<BankTransferResult> resultService  =  this.service.moneyTransfers(accountId,timeZone, moneyTransfer);	
			
			ServerResponseBankTransferResult result = mapper.toResponseBankTransfer(resultService);
			return ResponseEntity
					.ok()
					.body(result);
			
			
		}
	
	
}
