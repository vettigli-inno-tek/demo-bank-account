package it.innotek.demo.bankaccount.mappers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;



@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BankAccountMapperTest {

	
	@Autowired
	private BankAccountMapper mapper;

	@BeforeAll
	static void initAll() {
		
	}

	@BeforeEach
	void init() {
		assertNotNull(mapper);
	}

	
	@Test
	void instanceTest () {
		
		assertNotNull(mapper);
		
	}
	
	
}
