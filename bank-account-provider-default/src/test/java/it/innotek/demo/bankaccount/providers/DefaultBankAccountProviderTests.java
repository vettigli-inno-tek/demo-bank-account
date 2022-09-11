package it.innotek.demo.bankaccount.providers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import it.innotek.demo.bankaccount.excpetion.BankAccountProviderException;
import it.innotek.demo.bankaccount.model.balance.Balance;
import it.innotek.demo.bankaccount.model.server.ServerResponse;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@MockBeans({ @MockBean(DefaultBankAccountProviderClient.class) })
public class DefaultBankAccountProviderTests {
	
	@Autowired
	DefaultBankAccountProvider  provider;
	
	
	@Autowired
	DefaultBankAccountProviderClient  client;
	
	
	@BeforeAll
	static void initAll() {
		
	}

	@BeforeEach
	void init() {
		assertNotNull(provider);
		assertNotNull(client);
		setupClient(client);
	}

	Balance getDefaultBalance() {
		
		Balance expected = new Balance () ;
		expected.setDate(LocalDate.of(2022, 9, 11));
		expected.setBalance(BigDecimal.valueOf(7.27));
		expected.setAvailableBalance(BigDecimal.valueOf(7.27));
		expected.setCurrency("EUR");
		
		return expected;
		
	}
	
	
	void setupClient(DefaultBankAccountProviderClient  client) {
		
		Balance expected = getDefaultBalance() ;
		expected.setDate(LocalDate.of(2022, 9, 11));
		expected.setBalance(BigDecimal.valueOf(7.27));
		expected.setAvailableBalance(BigDecimal.valueOf(7.27));
		expected.setCurrency("EUR");
		Mockito.when(client.getBalance(1)).thenReturn(ServerResponse.OK(expected, null));
		
		BankAccountProviderException exception = new BankAccountProviderException("TEST");
		Mockito.when(client.getBalance(2)).thenThrow(exception);
	}
	
	@Test
	void  givenAccount_whenGetBalance_thenExpectedBalanceOfAccount () {
		
		
		Balance expected = getDefaultBalance() ;
		Balance actual = provider.getBalance(1);
		
		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
		 
		
	}
	@Test
	void givenBadAccount_whenGetBalance_thenExpectedException () {
		
		BankAccountProviderException actual = assertThrows(BankAccountProviderException.class, () -> {
			Balance result = provider.getBalance(2);
	    });
		
		
	}
	
}
