package it.innotek.demo.bankaccount;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class BankAccountConfigurator {
	
	
	
	@Bean
    @Primary
    public static ObjectMapper primaryObjectMapper(){
		
		
		ObjectMapper objectMapper = new ObjectMapper(); 
        //objectMapper.findAndRegisterModules();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        

        
        return objectMapper;
    }
	/*
	@Bean
	public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder(Jackson2ObjectMapperBuilder mapperbuilder ,Jackson2ObjectMapperBuilderCustomizer customizer) {
		
		
	    return mapperbuilder;
	}
	
	
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
	    return builder -> {
	    	builder.failOnUnknownProperties(false)
	    	.findModulesViaServiceLoader(true)
	    	;
	    };
	      
	}
	*/
	
}
