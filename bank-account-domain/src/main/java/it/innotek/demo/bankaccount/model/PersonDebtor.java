package it.innotek.demo.bankaccount.model;

public class PersonDebtor extends Person {

	private Account account;

	public Account getAccount() {
		return account;
	}

	public void setAccount(final Account account) {
		this.account = account;
	}
}
