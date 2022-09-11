package it.innotek.demo.bankaccount.providers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class DefaultBankAccountProviderConfiguration {

	@Bean
	public WebClient restTemplate(DefaultBankAccountProviderClientSettings settings  ) {
		
		return WebClient.builder()
				.baseUrl(settings.getBaseUrlApi())
				.defaultHeader("Auth-Schema", settings.getAuthSchema())
				.defaultHeader("apikey", settings.getApiKey())
				.build();
	}
	
    
	
	
	
	
	
	
	
	
}
