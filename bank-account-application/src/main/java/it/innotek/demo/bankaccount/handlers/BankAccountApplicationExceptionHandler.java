package it.innotek.demo.bankaccount.handlers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import it.innotek.demo.bankaccount.excpetion.BankAccountException;
import it.innotek.demo.bankaccount.excpetion.BankAccountProviderException;
import it.innotek.demo.bankaccount.model.server.ServerErrorResponse;
import it.innotek.demo.bankaccount.model.server.ServerResponseError;

@ControllerAdvice
public class BankAccountApplicationExceptionHandler {
	
	
	
	
	@ExceptionHandler
	public ResponseEntity<ServerResponseError> handleError(BankAccountProviderException e, HttpServletRequest request) {
		
	   
	    
		Object objRequestID =  request.getAttribute(BankAccountRequestIDInterceptor.RequestIDAttributeName);
		
		ServerResponseError response = new ServerResponseError() ;
		response.setRequestID(String.valueOf(objRequestID));
		response.setDescritption(e.getMessage());
		response.getErrors().addAll(e.getErrors());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	@ExceptionHandler
	public ResponseEntity<List<ServerErrorResponse>> handleError(BankAccountException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getErrors());
	}
	
}
