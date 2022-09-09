package it.innotek.demo.bankaccount.mappers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import it.innotek.demo.bankaccount.mappers.BankAccountMapper;

@ExtendWith(SpringExtension.class)
public class BankAccountMapperTest {

	
	@Autowired
	private BankAccountMapper mapper;

	@BeforeAll
	static void initAll() {
		System.out.println("---Inside initAll---");
	}

	@BeforeEach
	void init() {
		assertNotNull(mapper);
	}

	
	
}
