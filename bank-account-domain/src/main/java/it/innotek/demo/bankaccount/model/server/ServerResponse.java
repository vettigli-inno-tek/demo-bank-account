package it.innotek.demo.bankaccount.model.server;



import java.util.ArrayList;
import java.util.List;

import it.innotek.demo.bankaccount.enumeration.ServerResponseStatus;

public class ServerResponse<T> {

	
	private String requestID  ;
	private ServerResponseStatus status;
	private List<ServerErrorResponse> errors = new ArrayList<>();
	private T payload;

	
	public static <TPayload> ServerResponse<TPayload> OK ( TPayload payload,  String requestID ) {
		ServerResponse<TPayload> response = new ServerResponse<>();
		
		response.setStatus(ServerResponseStatus.OK);
		response.setRequestID(requestID);
		response.setPayload(payload);
		return response;
	}
	
	public ServerResponse() {
		
		
	}
	
	
	
	public ServerResponse(ServerResponseStatus status, T payload) {
		super();
		this.status = status;
		this.payload = payload;
	}

	public ServerResponseStatus getStatus() {
		return status;
	}

	public void setStatus(final ServerResponseStatus status) {
		this.status = status;
	}

	public List<ServerErrorResponse> getErrors() {
		return errors;
	}

	public void setErrors(final List<ServerErrorResponse> errors) {
		this.errors = errors;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(final T payload) {
		this.payload = payload;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	
}
