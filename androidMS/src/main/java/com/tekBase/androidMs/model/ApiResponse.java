package com.tekBase.androidMs.model;

public class ApiResponse {

    private int statusCode;
	
	private String statusMsg;
	
	private Object responseData;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public Object getResponseData() {
		return responseData;
	}

	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}

	@Override
	public String toString() {
		return "ApiResponse [statusCode=" + statusCode + ", statusMsg=" + statusMsg + ", responseData=" + responseData
				+ "]";
	}

	
	
}
