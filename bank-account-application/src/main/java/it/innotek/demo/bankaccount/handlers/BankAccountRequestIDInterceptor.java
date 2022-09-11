package it.innotek.demo.bankaccount.handlers;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

public  class BankAccountRequestIDInterceptor implements HandlerInterceptor{

	public static final String RequestIDAttributeName = "BankAccount.RequestID" ;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String reqID = UUID.randomUUID().toString() ;
		request.setAttribute(RequestIDAttributeName,reqID);
		MDC.put(RequestIDAttributeName, reqID);
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) throws Exception {
		MDC.remove(RequestIDAttributeName);
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

   

   
}
