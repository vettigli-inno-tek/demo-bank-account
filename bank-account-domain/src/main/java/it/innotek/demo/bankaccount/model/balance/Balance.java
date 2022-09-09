package it.innotek.demo.bankaccount.model.balance;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class  Balance
{
	
		
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
		private LocalDate date;
		private BigDecimal balance;
		private BigDecimal availableBalance;
		private String currency;
		public LocalDate getDate() {
			return date;
		}
		public void setDate(LocalDate date) {
			this.date = date;
		}
		public BigDecimal getBalance() {
			return balance;
		}
		public void setBalance(BigDecimal balance) {
			this.balance = balance;
		}
		public BigDecimal getAvailableBalance() {
			return availableBalance;
		}
		public void setAvailableBalance(BigDecimal availableBalance) {
			this.availableBalance = availableBalance;
		}
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		
		
		
}
