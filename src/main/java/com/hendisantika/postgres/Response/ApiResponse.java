package com.hendisantika.postgres.Response;

public class ApiResponse {


    private Integer statusCode;
    private String message;
    private Object response;


    public ApiResponse(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ApiResponse(Integer statusCode, String message, Object response) {
        this.statusCode = statusCode;
        this.message = message;
        this.response = response;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

}
