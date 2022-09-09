package it.innotek.demo.bankaccount.excpetion;

import java.util.ArrayList;
import java.util.List;

import it.innotek.demo.bankaccount.model.server.ServerErrorResponse;

public class BankAccountException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final List<ServerErrorResponse> errors = new ArrayList<>();
	
	public BankAccountException() {
		super();

	}

	public BankAccountException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public BankAccountException(String message, Throwable cause) {
		super(message, cause);

	}

	public BankAccountException(String message) {
		super(message);

	}

	public BankAccountException(Throwable cause) {
		super(cause);

	}

	public List<ServerErrorResponse> getErrors() {
		return errors;
	}


}
