package it.innotek.demo.bankaccount.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Component
public class DefaultBankAccountProviderServiceFactory {

	
	
	private DefaultBankAccountProviderServiceConfiguration configuration ; 
	
	
	@Autowired
	public DefaultBankAccountProviderServiceFactory(DefaultBankAccountProviderServiceConfiguration configuration) {
		super();
		this.configuration = configuration;
	}

    private  ObjectMapper objectMapper ;
    
	public ObjectMapper getObjectMapper() {
		if ( objectMapper == null) {
    		objectMapper = new ObjectMapper();
    		//objectMapper.findAndRegisterModules();
    		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    		objectMapper.registerModule(new JavaTimeModule());
    	}
		return objectMapper;
	}

	public DefaultBankAccountProviderService getService() {
		
		
		
		
		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
		
		 httpClient.interceptors().clear();
         httpClient.addInterceptor( chain -> {
             Request original = chain.request();
             Request.Builder builder1 = original.newBuilder()
               .header("Auth-Schema", configuration.getAuthSchema())
               .header("apikey", configuration.getApiKey());
             Request request = builder1.build();
             return chain.proceed(request);
         });
         ObjectMapper objectMapper  = getObjectMapper();
         JacksonConverterFactory factory = JacksonConverterFactory.create(objectMapper);
		
		Retrofit retrofit = new Retrofit.Builder()
		  .baseUrl(configuration.getBaseUrlApi())
		  
		  .addConverterFactory(factory)
		  .client(httpClient.build())
		  .build();
		DefaultBankAccountProviderService service = retrofit.create(DefaultBankAccountProviderService.class);
		return service ;
	}
}
