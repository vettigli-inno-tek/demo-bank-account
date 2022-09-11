package it.innotek.demo.bankaccount;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import it.innotek.demo.bankaccount.handlers.BankAccountRequestIDInterceptor;

@Configuration
public class BanckAccountWebMvcConfigurer implements  WebMvcConfigurer  {
	
	
	
	
	 @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        registry.addInterceptor(new BankAccountRequestIDInterceptor());
	    }
}
