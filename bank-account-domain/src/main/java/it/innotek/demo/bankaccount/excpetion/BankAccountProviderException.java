package it.innotek.demo.bankaccount.excpetion;

public class BankAccountProviderException extends BankAccountException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	
	public BankAccountProviderException() {
		super();

	}

	public BankAccountProviderException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public BankAccountProviderException(String message, Throwable cause) {
		super(message, cause);

	}

	public BankAccountProviderException(String message) {
		super(message);

	}

	public BankAccountProviderException(Throwable cause) {
		super(cause);

	}

	


}
