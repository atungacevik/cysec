package com.hendisantika.postgres.Response;

public class AuthTokenReponse {

    private String token;

    private String success;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
