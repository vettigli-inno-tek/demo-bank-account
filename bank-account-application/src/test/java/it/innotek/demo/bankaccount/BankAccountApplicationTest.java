package it.innotek.demo.bankaccount;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import it.innotek.demo.bankaccount.restapi.BankAccountRestController;

@DisplayName("Bank Account Application")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BankAccountApplicationTest {
 
  @Autowired
  BankAccountRestController bankAccountRestController;
 
  @DisplayName("Context Loads")
  @Test
  public void contextLoads() {
    Assertions.assertThat(bankAccountRestController).isNot(null);
  }
}