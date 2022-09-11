package it.innotek.demo.bankaccount.restapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import it.innotek.demo.bankaccount.excpetion.BankAccountProviderException;
import it.innotek.demo.bankaccount.model.balance.Balance;
import it.innotek.demo.bankaccount.model.server.ServerErrorResponse;
import it.innotek.demo.bankaccount.providers.BankAccountProvider;

@SpringBootTest
@ExtendWith(SpringExtension.class)



@AutoConfigureMockMvc()

@MockBeans({ @MockBean(BankAccountProvider.class) })
public  class BankAccountRestControllerTest {

	 
	 @Autowired
	 BankAccountProvider provider;
	 
	 @Autowired
	  private MockMvc mockMvc;
	 
	 @BeforeEach
		void init() {

			assertNotNull(provider);
			setupProvider(provider);
		}

	 Balance getDefaultBalance() {
			
			Balance expected = new Balance () ;
			expected.setDate(LocalDate.of(2022, 9, 11));
			expected.setBalance(BigDecimal.valueOf(7.27));
			expected.setAvailableBalance(BigDecimal.valueOf(7.27));
			expected.setCurrency("EUR");
			
			return expected;
			
		}
	 
	 void setupProvider(BankAccountProvider provider) {
		 
		 Balance expected = getDefaultBalance() ;
			expected.setDate(LocalDate.of(2022, 9, 11));
			expected.setBalance(BigDecimal.valueOf(7.27));
			expected.setAvailableBalance(BigDecimal.valueOf(7.27));
			expected.setCurrency("EUR");
			Mockito.when(provider.getBalance(1)).thenReturn(expected);
			
			BankAccountProviderException exception = new BankAccountProviderException("TEST");
			exception.getErrors().add(new ServerErrorResponse("REQ004","Invalid account identifier"));
			
			
			Mockito.when(provider.getBalance(2)).thenThrow(exception);
		 
		 
		
	 
	 }
	 
	 @Test
	 void  givenAccount_whenGetBalance_thenExpectedBalanceOfAccount () throws Exception {
			
		 
		
	 
	    this.mockMvc
	      .perform(get("/bank/account/1/balance"))
	      .andExpect(status().isOk())
	      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	      .andExpect(jsonPath("$.payload.currency").value("EUR"));

			
	 }
	 
	 

		@Test
		void givenBadAccount_whenGetBalance_thenExpectedException () throws Exception {
			
			 this.mockMvc
		      .perform(get("/bank/account/2/balance"))
		      .andExpect(status().isBadRequest())
		      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		      .andExpect(jsonPath("$.errors[0].code").value("REQ004"));
			 
			
		}
	 
}
