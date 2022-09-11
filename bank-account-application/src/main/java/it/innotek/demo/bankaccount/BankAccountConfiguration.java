package it.innotek.demo.bankaccount;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class BankAccountConfiguration {
	
	
	
	@Bean
    @Primary
    public static ObjectMapper primaryObjectMapper(){
		
		
		ObjectMapper objectMapper = new ObjectMapper(); 
       
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        

        
        return objectMapper;
    }
	
}
